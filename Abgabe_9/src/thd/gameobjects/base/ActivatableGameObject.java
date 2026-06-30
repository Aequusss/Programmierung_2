package thd.gameobjects.base;

/**
 * Objects that can be inactive and later activated.
 *
 * @param <T> the type of the information object passed to {@link #tryToActivate(Object)}
 */
public interface ActivatableGameObject<T> {
    /**
     * Tries to activate the game object.
     *
     * @param info optional information (e.g. the player) to decide activation
     * @return {@code true} if activation should happen
     */
    boolean tryToActivate(T info);
}
