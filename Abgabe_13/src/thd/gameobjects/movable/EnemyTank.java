package thd.gameobjects.movable;

import thd.game.interfaces.Damageable;
import thd.game.level.Difficulty;
import thd.game.level.Level;
import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.unmovable.Bridge;
import thd.gameobjects.unmovable.HealthPickup;

import java.awt.*;


/**
 * An enemy tank that moves left and shoots. Uses an internal state machine
 * for hit reactions.
 */
public class EnemyTank extends CollidingGameObject implements ShiftableGameObject, Damageable, ActivatableGameObject<PlayerHelicopter>, Comparable<EnemyTank> {

    /**
     * Possible states of tank.
     */
    private enum State {
        STANDARD,
        EXPLODING,
        EXPLODED
    }

    private State currentState;
    private int shotDurationInMilliseconds;
    private boolean isFiring;
    private long lastFireTime;
    private int health;
    private final TankMovementPattern tankMovementPattern;


    /**
     * Creates an EnemyTank at a fixed position on the screen.
     *
     * @param gameView        The GameView instance used for rendering.
     * @param gamePlayManager the gameplay manager for communication
     */
    public EnemyTank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        this.size = 0.25;
        this.rotation = 0;
        this.width = 190;
        this.height = 30;
        hitBoxOffsets(new HitBoxOffsets(0, 50, (-width / 2) + 15, 0));
        this.speedInPixel = 2;
        distanceToBackground = 100;
        position.updateCoordinates(900, 560);
        currentState = State.STANDARD;
        this.shotDurationInMilliseconds = 1500;
        isFiring = false;
        lastFireTime = 0;
        health = 3;
        this.tankMovementPattern = new TankMovementPattern();
        if (Level.difficulty == Difficulty.EASY) {
            speedInPixel = 1;
            shotDurationInMilliseconds = 3500;
        }

    }

    @Override
    public String toString() {
        return "EnemyTank: " + position;
    }

    /**
     * Updates the position of the game object on the canvas.
     */

    @Override
    public void updatePosition() {
        tankMovementPattern.executeTankTactics(this.position, this.speedInPixel);

    }

    /**
     * When Tank leaves the visible screen, it gets deleted, otherwise shoots.
     */
    @Override
    public void updateStatus() {
        switch (currentState) {
            case STANDARD -> {
                fire();
                if (position.getX() < 0) {
                    gamePlayManager.destroyGameObject(this);

                }
                break;
            }
            case EXPLODING -> {
                currentState = State.EXPLODED;
                break;
            }
            case EXPLODED -> {
                gamePlayManager.destroyGameObject(this);
                break;
            }
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter player) {
            player.takeDamage();
        }
    }

    /**
     * Adds the game object to the canvas at its current position.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("enemytank.png", position.getX(), position.getY() + 50, size, rotation);

        if (isFiring) {
            gameView.addOvalToCanvas(position.getX() - 10, position.getY() + 60, 30, 15, 2,
                    true, Color.YELLOW);
            gameView.addOvalToCanvas(position.getX() - 5, position.getY() + 63, 15, 8, 1,
                    true, Color.WHITE);
            if (System.currentTimeMillis() - lastFireTime > 100) {
                isFiring = false;
            }
        }
    }

    /**
     * Shoots bullet every specified time interval.
     */
    private void fire() {
        if (gameView.timer(shotDurationInMilliseconds, 0, this)) {

            PlayerHelicopter player = gamePlayManager.findPlayer();

            if (player != null && gamePlayManager.getLives() > 0) {

                double dx = player.getPosition().getX() - position.getX();
                double dy = player.getPosition().getY() - position.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance > 0) {
                    double speed = 6.0;

                    double vx = (dx / distance) * speed;
                    double vy = (dy / distance) * speed;

                    EnemyBullet bullet = new EnemyBullet(gameView, gamePlayManager, position.getX(),
                            position.getY(), vx, vy);

                    gamePlayManager.spawnGameObject(bullet);

                    gameView.playSound("enemyshoot.wav", false, 0.3f);
                    isFiring = true;
                    lastFireTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    public boolean tryToActivate(PlayerHelicopter player) {

        double distance = position.distance(player.getPosition());
        return distance < 500;
    }

    /**
     * Rolls a 25 % chance to spawn a Health, Ammo, or Shield pickup at the enemy's death location.
     */
    private void tryDropPickup() {
        if (Math.random() < 0.25) {
            double dropTypeRoll = Math.random();

            if (dropTypeRoll < 0.40) {

                gamePlayManager.spawnGameObject(new HealthPickup(gameView, gamePlayManager, position.getX(), position.getY()));
            } else if (dropTypeRoll < 0.80) {

                gamePlayManager.spawnGameObject(new AmmoPickup(gameView, gamePlayManager, position.getX(), position.getY()));
            } else {

                gamePlayManager.spawnGameObject(new ShieldPickup(gameView, gamePlayManager, position.getX(), position.getY()));
            }
        }
    }

    @Override
    public void takeDamage(int amount) {
        if (currentState == State.EXPLODING || currentState == State.EXPLODING) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            currentState = State.EXPLODING;

            Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
            gamePlayManager.spawnGameObject(explosion);

            gamePlayManager.addPoints(100);

            tryDropPickup();

            gamePlayManager.destroyGameObject(this);
        }
    }


    @Override
    public int compareTo(EnemyTank other) {
        return Double.compare(other.position.getX(), this.position.getX());
    }

}


