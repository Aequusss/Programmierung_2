package thd.game.interfaces;

import thd.gameobjects.movable.PlayerHelicopter;

/**
 * A functional interface representing the effect a pickup has on the player.
 */
@FunctionalInterface
public interface PickupEffect {
    /**
     * Applies the specific effect to the player helicopter.
     *
     * @param player The player receiving the pickup.
     */
    void apply(PlayerHelicopter player);
}