package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * A bridge obstacle spanning the play area.
 */
public class Bridge extends CollidingGameObject implements ShiftableGameObject {

    /**
     * Constructs a bridge.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public Bridge(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 0;
        size = 10;
        width = 200;
        height = 40;
        rotation = 0;
        hitBoxOffsets(new HitBoxOffsets(0, 0, 0, -10));
        distanceToBackground = 1;
    }

    @Override
    public void updatePosition() {
        // Static object
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.BRIDGE, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        // Obstacles just block.
    }

    @Override
    public String toString() {
        return "Bridge: " + position;
    }
}