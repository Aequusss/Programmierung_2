package thd.game.managers;


/**
 * Thrown when the number of active game objects exceeds the allowed maximum.
 */
public class TooManyGameObjectsException extends RuntimeException {

    /**
     * Creates a new exception with the given detail message.
     *
     * @param message the detail message
     */
    public TooManyGameObjectsException(String message) {
        super(message);
    }
}