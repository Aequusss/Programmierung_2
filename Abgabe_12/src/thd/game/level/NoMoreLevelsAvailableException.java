package thd.game.level;

/**
 * Thrown when the game tries to switch to a level that does not exist.
 */
public class NoMoreLevelsAvailableException extends RuntimeException {
    /**
     * Constructs the exception with the given detail message.
     *
     * @param message the detail message
     */
    public NoMoreLevelsAvailableException(String message) {
        super(message);
    }
}
