package game;

public class GameViewManager {
    private final GameView gameView;
    private final Ufo ufo;
    private final UfoShot ufoShot;
    private final ScorePanel scorePanel;

    public GameViewManager() {
        gameView = new GameView();
        ufo = new Ufo(gameView);
        ufoShot = new UfoShot(gameView);
        scorePanel = new ScorePanel(gameView);
        startGameLoop();
    }

    private void startGameLoop() {
        // Der Game-Loop wird 60-mal pro Sekunde ausgeführt: 60 FPS (frames per second)
        while (gameView.isVisible()) {
            ufo.updatePosition();
            ufoShot.updatePosition();
            scorePanel.updatePosition();
            ufo.addToCanvas();
            ufoShot.addToCanvas();
            scorePanel.addToCanvas();
            gameView.plotCanvas();
        }
    }
}