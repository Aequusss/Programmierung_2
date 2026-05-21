package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;

import java.awt.Color;

/**
 * A single segment of the health bar UI.
 */
public class HealthUnit extends GameObject {

    /**
     * Constructs a health unit segment.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public HealthUnit(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 0;
        size = 1.0;
        width = 25;
        height = 10;
        rotation = 0;
    }

    @Override
    public void updatePosition() {
        // UI element, static position
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.HEALTH_BAR_UNIT, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public String toString() {
        return "HealthUnit: " + position;
    }
}