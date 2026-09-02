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
import thd.gameobjects.unmovable.HealthPickup;

import java.awt.*;

/**
 * An enemy helicopter that flies in a wavy sinusoidal pattern.
 */
public class EnemyHelicopter extends CollidingGameObject implements ShiftableGameObject, Damageable, ActivatableGameObject<PlayerHelicopter> {
    /**
     * Possible states of the helicopter.
     */
    private enum State {
        ACTIVE,
        DESTROYED
    }

    private HelicopterMovementPattern helicopterMovementPattern;

    private int shotDurationInMilliseconds;
    private State currentState;
    private int health;

    /**
     * Initializes the helicopter with wavy flight parameters.
     *
     * @param gameView        GameView to show the game object on.
     * @param gamePlayManager the gameplay manager for communication
     */
    public EnemyHelicopter(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        speedInPixel = 3;
        size = 0.15;
        shotDurationInMilliseconds = 1200;
        width = 145;
        height = 55;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        distanceToBackground = 100;
        currentState = State.ACTIVE;
        health = 2;
        helicopterMovementPattern = new HelicopterMovementPattern();

        position.updateCoordinates(helicopterMovementPattern.startPosition());
        targetPosition.updateCoordinates(helicopterMovementPattern.nextPosition());

        if (Level.difficulty == Difficulty.EASY) {
            speedInPixel = 1;
            shotDurationInMilliseconds = 3500;
        }
    }

    /**
     * Moves the helicopter according to its complex arcade behavior pattern.
     */
    @Override
    public void updatePosition() {

        helicopterMovementPattern.executeArcadeFlight(this.position, this.speedInPixel, gameView.gameTimeInMilliseconds());

        // If it flies completely off the left edge, recycle it back to the right with a fresh randomized behavior
        if (position.getX() < -150) {
            this.helicopterMovementPattern = new HelicopterMovementPattern();
            position.updateCoordinates(helicopterMovementPattern.startPosition());
        }
    }

    @Override
    public void updateStatus() {
        fire();
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter playerHelicopter) {
            playerHelicopter.takeDamage();
        }
    }

    /**
     * Renders the Helicopter PNG onto the canvas.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("enemyhelicopter.png", position.getX(), position.getY(), size, rotation);

        double rotorCycle = Math.sin(gameView.gameTimeInMilliseconds() * 0.15);
        double maxRotorWidth = Math.abs(rotorCycle) * (width * size) * 2.5;
        double rotorWidth = Math.abs(rotorCycle) * maxRotorWidth;

        double xOffset = 20;
        double yOffset = 10;


        double rotorX = position.getX() + (width * size) / 2 + xOffset;
        double rotorY = position.getY() + yOffset;

        gameView.addLineToCanvas(rotorX - rotorWidth / 2, rotorY, rotorX + rotorWidth, rotorY,
                3, Color.DARK_GRAY);
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
                }
            }
        }
    }

    @Override
    public String toString() {
        return "EnemyHelicopter: " + position;
    }

    @Override
    public boolean tryToActivate(PlayerHelicopter player) {
        double distance = position.distance(player.getPosition());
        return distance < 600;
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
        if (currentState == State.DESTROYED) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            currentState = State.DESTROYED;

            Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
            gamePlayManager.spawnGameObject(explosion);

            gamePlayManager.addPoints(150);

            tryDropPickup();

            gamePlayManager.destroyGameObject(this);
        }
    }

}
