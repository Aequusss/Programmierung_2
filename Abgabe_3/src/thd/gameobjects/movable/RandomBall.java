package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * A ball that starts randomly but follows a fixed quadratic movement pattern.
 */
public class RandomBall extends GameObject {

    private final RandomMovementPattern randomMovementPattern;
    private final QuadraticMovementPattern quadraticMovementPattern;

    /**
     * Initializes the ball with a random start and a quadratic target loop.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public RandomBall(GameView gameView) {
        super(gameView);
        speedInPixel = 4;
        size = 55;

        randomMovementPattern = new RandomMovementPattern();
        quadraticMovementPattern = new QuadraticMovementPattern();

        position.updateCoordinates(randomMovementPattern.startPosition());
        targetPosition.updateCoordinates(quadraticMovementPattern.nextPosition());

    }

    /**
     * Handles movement timing and updates targets based on the quadratic pattern.
     */
    @Override
    public void updatePosition() {
        if (gameView.timer(3000, 0, this)) {
            speedInPixel += 1;
        }

        if (gameView.timer(1000, 4000, this)) {
            position.moveToPosition(targetPosition, speedInPixel);
        }
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(quadraticMovementPattern.nextPosition());
        }
    }

    /**
     * Draws the ball and manages its 5-second color transition.
     */
    @Override
    public void addToCanvas() {

        if (gameView.gameTimeInMilliseconds() <= 5000) {
            gameView.addOvalToCanvas(position.getX(), position.getY(), size, size, 2, true, Color.YELLOW);
        } else {
            gameView.addOvalToCanvas(position.getX(), position.getY(), size, size, 2, true, Color.RED);
        }


        gameView.addOvalToCanvas(targetPosition.getX(), targetPosition.getY(), size, size, 2, false, Color.WHITE);
    }

    @Override
    public String toString() {
        return "RandomBall: " + position;
    }
}
