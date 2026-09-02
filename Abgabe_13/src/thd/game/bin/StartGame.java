package thd.game.bin;

import thd.game.managers.GameViewManager;

/**
 * Entry point for the Silkworm game.
 */
public class StartGame {

    /**
     * Visitors can be allowed to play this game at THD-Events, such as the "Tag der offenen Tür" (Open Day).
     * Mostly children and teenagers love to play self-made games at such events.
     */
    public static final boolean VISITORS_ARE_ALLOWED_TO_PLAY_THIS_GAME = false;

    /**
     * Starts the game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new GameViewManager();
    }
}
