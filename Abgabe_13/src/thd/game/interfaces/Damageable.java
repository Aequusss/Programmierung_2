package thd.game.interfaces;

/**
 * Represents any game object that can take damage and be destroyed.
 */
public interface Damageable {
    /**
     * Applies damage to the object.
     *
     * @param amount The amount of damage to apply.
     */
    void takeDamage(int amount);
}