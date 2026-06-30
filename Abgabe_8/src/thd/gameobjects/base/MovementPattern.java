package thd.gameobjects.base;

import java.util.Random;

/**
 * Base class for all movement patterns in the game.
 */
public class MovementPattern {

    /**
     * Random generator for child patterns.
     */
    protected final Random random;

    /**
     * Initializes the shared random generator.
     */
    protected MovementPattern() {
        random = new Random();
    }

    /**
     * Returns the default starting position (0,0).
     *
     * @return New position at origin.
     */
    protected Position startPosition() {
        return new Position(0, 0);
    }

    /**
     * Returns the default next position (0,0).
     *
     * @return New position at origin.
     */
    protected Position nextPosition() {
        return new Position(0, 0);
    }
}
