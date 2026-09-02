package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * Represents a shield pickup that grants armor or triggers a screen nuke.
 */
public class ShieldPickup extends CollidingGameObject implements ShiftableGameObject {

    /**
     * Creates a new shield pickup at the specified coordinates.
     *
     * @param gameView        the game view
     * @param gamePlayManager the game play manager
     * @param x               the x coordinate
     * @param y               the y coordinate
     */
    public ShieldPickup(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 30;
        height = 30;
        speedInPixel = 3;
        distanceToBackground = 15;
        size = 4;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        position.updateCoordinates(position.getX() - speedInPixel, position.getY());
    }

    @Override
    public void addToCanvas() {

        gameView.addBlockImageToCanvas(SilkwormBlockImages.SHIELD_IMAGE, position.getX(), position.getY(), size, 0);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerBullet) {
            gameView.playSound("shield_bomb.wav", false, 2.0f);
            gamePlayManager.nukeScreen(); // Destroys all enemies!
            gamePlayManager.destroyGameObject(this);
        }
    }
}