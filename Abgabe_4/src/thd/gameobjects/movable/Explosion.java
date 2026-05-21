package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;

import java.awt.Color;

/**
 * An explosion visual effect.
 */
public class Explosion extends GameObject {

    private double pulse;

    /**
     * Constructs an explosion effect.
     *
     * @param gameView the game view
     * @param startX   initial x-coordinate
     * @param startY   initial y-coordinate
     */
    public Explosion(GameView gameView, double startX, double startY) {
        super(gameView);
        position.updateCoordinates(startX, startY);
        speedInPixel = 0;
        size = 1.0;
        width = 40;
        height = 40;
        rotation = 0;
        pulse = 0;
    }

    @Override
    public void updateStatus() {
        pulse += 0.1;
        if (pulse > Math.PI * 2) {
            pulse = 0;
        }
    }

    @Override
    public void updatePosition() {
        // Static position
    }

    @Override
    public void addToCanvas() {
        double currentSize = size + Math.sin(pulse) * 0.2;
        gameView.addBlockImageToCanvas(SilkwormBlockImages.EXPLOSION, position.getX(), position.getY(), currentSize, rotation);
    }

    @Override
    public String toString() {
        return "Explosion: " + position;
    }
}
