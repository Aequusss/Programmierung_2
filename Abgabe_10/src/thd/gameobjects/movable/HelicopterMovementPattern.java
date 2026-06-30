package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.MovementPattern;
import thd.gameobjects.base.Position;

import java.util.Random;

/**
 * Defines a side-scrolling movement pattern for helicopters.
 */
class HelicopterMovementPattern extends MovementPattern {


    /**
     * Initializes the pattern generator.
     */
    HelicopterMovementPattern() {
        super();
    }

    /**
     * Returns the starting position for the helicopter.
     *
     * @return A position on the right side of the screen at a random height.
     */
    @Override
    protected Position startPosition() {
        return new Position(GameView.WIDTH + 100, random.nextInt(GameView.HEIGHT / 2));
    }

    /**
     * Determines the target position on the left side of the screen.
     *
     * @return The position the helicopter will move towards.
     */
    @Override
    protected Position nextPosition() {
        return new Position(-200, random.nextInt(GameView.HEIGHT / 2));
    }

}
