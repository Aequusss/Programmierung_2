package thd.gameobjects.unmovable;

import thd.game.interfaces.Damageable;
import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.Explosion;
import thd.gameobjects.movable.PlayerBullet;
import thd.gameobjects.movable.PlayerHelicopter;


/**
 * Represents a destructible environmental ruin.
 */
public class DestructibleRuin extends CollidingGameObject implements ShiftableGameObject, Damageable {
    private int health;
    private boolean destroyed;

    /**
     * Creates a new ruin at the specified coordinates.
     *
     * @param gameView        the game view
     * @param gamePlayManager the game play manager
     * @param x               the x coordinate
     * @param y               the y coordinate
     */
    public DestructibleRuin(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 60;
        height = 80;
        speedInPixel = 0;
        distanceToBackground = 15;
        size = 0.2;
        health = 2;
        destroyed = false;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        // Static object
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("ruin.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public void takeDamage(int amount) {
        if (destroyed) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            destroyed = true;
            Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
            gamePlayManager.spawnGameObject(explosion);
            gamePlayManager.addPoints(50);
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter player) {
            player.takeDamage();
        }
    }
}