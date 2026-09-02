package thd.gameobjects.unmovable;

import thd.game.interfaces.Damageable;
import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.Explosion;
import thd.gameobjects.movable.Rocket;
import thd.gameobjects.movable.PlayerHelicopter;

/**
 * Represents a tunnel that periodically spawns ground rockets.
 */
public class RocketTruck extends CollidingGameObject implements ShiftableGameObject, Damageable {

    private long lastRocketSpawn;
    private int health;
    private boolean destroyed;

    /**
     * Creates a new rocket tunnel.
     *
     * @param gameView        the game view
     * @param gamePlayManager the game play manager
     * @param x               the x coordinate
     * @param y               the y coordinate
     */
    public RocketTruck(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 40;
        height = 40;
        speedInPixel = 0;
        distanceToBackground = 15;
        size = 1.5;
        lastRocketSpawn = System.currentTimeMillis();
        health = 2;
        destroyed = false;
    }

    @Override
    public void updatePosition() {
        // Static object, no movement
    }

    @Override
    public void updateStatus() {

        if (System.currentTimeMillis() - lastRocketSpawn > 2000) {
            gamePlayManager.spawnGameObject(new Rocket(gameView, gamePlayManager, position.getX(), position.getY()));
            lastRocketSpawn = System.currentTimeMillis();
        }
    }

    @Override
    public void takeDamage(int amount) {
        if (destroyed) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            destroyed = true;
            gamePlayManager.addPoints(150);
            Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
            gamePlayManager.spawnGameObject(explosion);

            gamePlayManager.destroyGameObject(this);
        }

    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("rockettruck.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter playerHelicopter) {
            playerHelicopter.takeDamage();
        }
    }
}