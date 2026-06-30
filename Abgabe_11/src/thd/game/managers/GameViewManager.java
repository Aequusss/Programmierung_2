package thd.game.managers;

import thd.game.utilities.GameView;

/**
 * Manages the GameView window and runs the main game loop.
 */
public class GameViewManager {
    private final GameView gameView;
    private final GameManager gameManager;

    /**
     * Creates the GameViewManager, sets up the window, and starts the game loop.
     */
    public GameViewManager() {
        this.gameView = new GameView();
        gameView.updateWindowTitle("Silkworm Reloaded");
        gameView.updateStatusText("Alexander Letutschi - Java Programmierung SS 2026");
        gameView.updateWindowIcon("icon.png");
        this.gameManager = new GameManager(gameView);
        gameView.showStatistic(true);

        startGameLoop();
    }

    private void startGameLoop() {
        // Der Game-Loop wird 60-mal pro Sekunde ausgeführt: 60 FPS (frames per second)
        while (gameView.isVisible()) {
            gameManager.processFrame();
            gameView.plotCanvas();
        }

    }
}