package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.*;
import thd.game.interfaces.Damageable;
import thd.gameobjects.movable.EnemyBullet;
import thd.gameobjects.movable.PlayerBullet;
import thd.gameobjects.movable.PlayerHelicopter;


/**
 * Represents an armored anti-air gun with a toggling shield.
 */
public class ArmoredAAGun extends CollidingGameObject implements ShiftableGameObject, Damageable {
    private boolean isShielded;
    private long lastShieldToggle;
    private final int shotDurationInMilliseconds;

    /**
     * Creates a new armored AA gun.
     *
     * @param gameView the game view
     * @param gamePlayManager the game play manager
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public ArmoredAAGun(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 50;
        height = 40;
        speedInPixel = 0; // Static turret
        size = 15;
        distanceToBackground = 15;
        isShielded = true;
        lastShieldToggle = System.currentTimeMillis();
        shotDurationInMilliseconds = 2000;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        // Static object, no movement needed
    }

    @Override
    public void updateStatus() {
        // 1. Toggle shield every 3 seconds (3000ms)
        if (System.currentTimeMillis() - lastShieldToggle > 3000) {
            isShielded = !isShielded;
            lastShieldToggle = System.currentTimeMillis();
        }

        // 2. Fire homing missiles!
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

        gameView.addBlockImageToCanvas(SilkwormBlockImages.ARMORED_TANK_IMAGE, position.getX(), position.getY(), size, 0);
    }

    @Override
    public void takeDamage(int amount) {
        if (!isShielded) {
            gamePlayManager.destroyGameObject(this);
            // Note: Add your ScoreToken spawn here tomorrow for the live demo!
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerBullet bullet) {
            if (isShielded) {
                gamePlayManager.destroyGameObject(bullet); // Bullet bounces off shield
            } else {
                takeDamage(1);
                gamePlayManager.destroyGameObject(bullet);
            }
        } else if (other instanceof PlayerHelicopter player) {
            player.takeDamage(); // Crash damage
        }
    }
}