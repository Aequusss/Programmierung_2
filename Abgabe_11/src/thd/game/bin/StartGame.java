package thd.game.bin;

import thd.game.managers.GameViewManager;

/**
 * Entry point for the Silkworm game.
 */
public class StartGame {

    /**
     * Starts the game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new GameViewManager();
    }
}
