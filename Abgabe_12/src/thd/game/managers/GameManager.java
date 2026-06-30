package thd.game.managers;

import thd.game.level.Difficulty;
import thd.game.level.Level;
import thd.game.utilities.FileAccess;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.screens.GameInfo;
import thd.screens.Screens;

/**
 * Top-level manager; handles game restart and level cycling.
 */
class GameManager extends LevelManager {

    private long timeOfDeath;

    private long timeOfLevelEnd;

    private static final long LEVEL_END_DELAY_MS = 2000;

    /**
     * Creates the game manager and starts the first level.
     *
     * @param gameView the game view
     */
    public GameManager(GameView gameView) {
        super(gameView);
        timeOfDeath = -1;
        timeOfLevelEnd = -1;

        gameView.showStatistic(true);

        startNewGame();

    }

    /**
     * Starts a new game by reading the saved difficulty, showing the start screen,
     * and initializing the first level based on the user's choice.
     */
    private void startNewGame() {
        FileAccess.readSaveGameFromDisc();

        String selection = Screens.showStartScreen(gameView, GameInfo.TITLE,
                GameInfo.DESCRIPTION, Level.difficulty.name, "droidsansmono.ttf");

        Level.difficulty = Difficulty.fromName(selection);

        FileAccess.writeSaveGameToDisc();

        initializeGame();
    }

    /**
     * One frame: input, world update, gameplay and game management.
     */
    @Override
    protected void processFrame() {
        super.processFrame();
        gameManagement();
    }

    /**
     * Checks for level completion and restarts the game when needed.
     */
    private void gameManagement() {
        if (endOfGame()) {
            gameView.stopAllSounds();
            String endMessage = "Game Over\nYour Score: " + points;
            Screens.showEndScreen(gameView, endMessage, GameInfo.FONT_NAME);
            startNewGame();
        } else if (endOfLevel()) {
            if (timeOfLevelEnd == -1) {
                timeOfLevelEnd = System.currentTimeMillis();
                if (!overlay.isMessageShown()) {
                    overlay.showMessage("Great Job!", 2);
                }

            } else if (System.currentTimeMillis() - timeOfLevelEnd >= LEVEL_END_DELAY_MS) {
                timeOfLevelEnd = -1;
                overlay.stopShowing();
                switchToNextLevel();
                initializeLevel();
            }
        }
    }


    /**
     * Ends the level when the survival countdown expires.
     *
     * @return {@code true} when the level duration has passed
     */
    private boolean endOfLevel() {
        long timePassed = System.currentTimeMillis() - levelStartTime;
        return timePassed >= LEVEL_DURATION_MS;
    }

    /**
     * Ends the game when lives are 0 or there is no next level when the last level finished.
     *
     * @return {@code true} when lives are zero or the game has finished all levels
     */
    private boolean endOfGame() {
        return lives == 0 || (!hasNextLevel() && endOfLevel());
    }

    @Override
    protected void initializeGame() {
        super.initializeGame();
        initializeLevel();
    }

    /**
     * Nullifies instance variables when a game object is destroyed.
     *
     * @param gameObject the game object that is being destroyed
     */
    @Override
    protected void cleanupGameObjectReferences(GameObject gameObject) {
        super.cleanupGameObjectReferences(gameObject);


    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();

        if (overlay != null) {
            overlay.showMessage(level.name, 2);
        }
    }
}