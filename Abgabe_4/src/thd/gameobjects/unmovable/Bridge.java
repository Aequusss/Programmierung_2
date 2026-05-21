package thd.gameobjects.unmovable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;

import java.awt.Color;

/**
 * A bridge obstacle spanning the play area.
 */
public class Bridge extends GameObject {

    /**
     * Constructs a bridge.
     *
     * @param gameView the game view
     * @param startX   initial x-coordinate
     * @param startY   initial y-coordinate
     */
    public Bridge(GameView gameView, double startX, double startY) {
        super(gameView);
        position.updateCoordinates(startX, startY);
        speedInPixel = 0;
        size = 10;
        width = 200;
        height = 40;
        rotation = 0;
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
    public String toString() {
        return "Bridge: " + position;
    }
}