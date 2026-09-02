package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;


/**
 * Represents a rocket projectile fired from the ground.
 */
public class Rocket extends CollidingGameObject implements ShiftableGameObject {

    /**
     * Creates a new ground rocket.
     *
     * @param gameView the game view
     * @param gamePlayManager the game play manager
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Rocket(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 15;
        height = 30;
        speedInPixel = 5;
        distanceToBackground = 15;
        size = 2;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        // Moves UP and LEFT
        position.updateCoordinates(position.getX() - 3, position.getY() - speedInPixel);
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.ROCKET_IMAGE, position.getX(), position.getY(), size, distanceToBackground);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter player) {
            player.takeDamage();
            gamePlayManager.destroyGameObject(this);
        } else if (other instanceof PlayerBullet) {
            gamePlayManager.destroyGameObject(this);
            gamePlayManager.destroyGameObject(other);
        }
    }
}