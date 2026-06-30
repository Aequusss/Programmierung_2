package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.*;
import thd.game.interfaces.Damageable;
import thd.gameobjects.movable.PlayerHelicopter;


/**
 * Represents a destructible environmental ruin.
 */
public class DestructibleRuin extends CollidingGameObject implements ShiftableGameObject, Damageable {
    private int health;

    /**
     * Creates a new ruin at the specified coordinates.
     *
     * @param gameView the game view
     * @param gamePlayManager the game play manager
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public DestructibleRuin(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 60;
        height = 80;
        speedInPixel = 0;
        distanceToBackground = 15;
        size = 15;
        health = 2;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        // Static object
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.RUIN_IMAGE, position.getX(), position.getY(), size, 0);
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
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