package thd.gameobjects.unmovable;

import thd.game.interfaces.Damageable;
import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.*;

import java.awt.*;


/**
 * Represents an armored anti-air gun with a toggling shield.
 */
public class ArmoredAAGun extends CollidingGameObject implements ShiftableGameObject, Damageable {
    private boolean isShielded;
    private long lastShieldToggle;
    private final int shotDurationInMilliseconds;
    private boolean destroyed;
    private int health;

    /**
     * Creates a new armored AA gun.
     *
     * @param gameView        the game view
     * @param gamePlayManager the game play manager
     * @param x               the x coordinate
     * @param y               the y coordinate
     */
    public ArmoredAAGun(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 50;
        height = 40;
        speedInPixel = 0;
        size = 0.15;
        distanceToBackground = 15;
        isShielded = true;
        lastShieldToggle = System.currentTimeMillis();
        shotDurationInMilliseconds = 2000;
        destroyed = false;
        health = 4;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        // Static object, no movement needed
    }

    @Override
    public void updateStatus() {
        // Toggle shield every 3 seconds (3000ms)
        if (System.currentTimeMillis() - lastShieldToggle > 3000) {
            isShielded = !isShielded;
            lastShieldToggle = System.currentTimeMillis();
        }
        shoot();
    }

    private void shoot() {
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

                    EnemyBullet bullet = new EnemyBullet(
                            gameView,
                            gamePlayManager,
                            position.getX(),
                            position.getY(),
                            vx,
                            vy
                    );
                    gamePlayManager.spawnGameObject(bullet);
                }
            }
        }
    }

    @Override
    public void addToCanvas() {

        gameView.addImageToCanvas("armoredaagun.png", position.getX(), position.getY() + 30, size, rotation);

        if (isShielded && !destroyed) {
            int shieldWidth = 100;
            int shieldHeight = 60;

            double manualOffsetX = 65.0; // Increase to move RIGHT, decrease to move LEFT
            double manualOffsetY = 90.0; // Increase to move DOWN, decrease to move UP


            double shieldX = position.getX() + (this.width / 2.0) - (shieldWidth / 2.0) + manualOffsetX;
            double shieldY = position.getY() + (this.height / 2.0) - (shieldHeight / 2.0) + manualOffsetY;

            gameView.addOvalToCanvas(shieldX, shieldY, shieldWidth, shieldHeight, 2, false, Color.CYAN);
        }
    }


    @Override
    public void takeDamage(int amount) {
        if (destroyed) {
            return;
        }
        if (!isShielded) {
            health -= amount;

            if (health <= 0) {
                destroyed = true;
                gamePlayManager.addPoints(200);

                Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
                gamePlayManager.spawnGameObject(explosion);

                gamePlayManager.spawnGameObject(new ScoreToken(gameView, gamePlayManager, position.getX(), position.getY(), 100));

                gamePlayManager.destroyGameObject(this);
            }
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerBullet bullet) {
            if (isShielded) {
                gamePlayManager.destroyGameObject(bullet); // Bullet bounces off shield
            }
        } else if (other instanceof PlayerHelicopter player) {
            player.takeDamage();
        }
    }
}
