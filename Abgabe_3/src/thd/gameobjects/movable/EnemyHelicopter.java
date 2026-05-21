package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * An enemy helicopter that flies in a wavy sinusoidal pattern.
 */
public class EnemyHelicopter extends GameObject {

    private final HelicopterMovementPattern helicopterMovementPattern;
    private double amplitude;
    private double frequency;

    /**
     * Initializes the helicopter with wavy flight parameters.
     *
     * @param gameView GameView to show the game object on.
     */
    public EnemyHelicopter(GameView gameView) {
        super(gameView);
        speedInPixel = 3;
        size = 0.25;
        amplitude = 2.5;
        frequency = 0.05;
        helicopterMovementPattern = new HelicopterMovementPattern();

        position.updateCoordinates(helicopterMovementPattern.startPosition());
        targetPosition.updateCoordinates(helicopterMovementPattern.nextPosition());
    }

    /**
     * Moves horizontally and adds a sine wave to the vertical axis.
     */
    @Override
    public void updatePosition() {
        position.moveToPosition(targetPosition, speedInPixel);

        double wavyY = Math.sin(position.getX() * frequency) * amplitude;
        position.updateCoordinates(position.getX(), position.getY() + wavyY);

        if (position.getX() < -150) {
            position.updateCoordinates(helicopterMovementPattern.startPosition());
        }
    }

    /**
     * Renders the Helicopter PNG onto the canvas.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("enemyhelicopter.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public String toString() {
        return "EnemyHelicopter: " + position;
    }
}
