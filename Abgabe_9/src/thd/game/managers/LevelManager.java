package thd.game.managers;

import thd.game.level.*;
import thd.game.utilities.GameView;


import java.util.List;

/**
 * Manages the current level and transitions between levels.
 */
class LevelManager extends GameWorldManager {

    private List<Level> levels;

    private int currentLevelIndex;

    protected static final int LIVES = 5;

    /**
     * Constructs the level manager, Level initialization is done later via {@link #initializeGame()}.
     *
     * @param gameView the game view
     */
    protected LevelManager(GameView gameView) {
        super(gameView);

    }

    /**
     * Initializes a new game: set up levels, resets lives and points, and creates the health bar.
     */
    protected void initializeGame() {
        levels = List.of(new Level1(), new Level2(), new Level3());
        currentLevelIndex = 0;
        level = levels.get(0);
        lives = LIVES;
        points = 0;

    }

    /**
     * Checks if there is a next level.
     *
     * @return {@code true} if another level exists after the current one
     */
    protected boolean hasNextLevel() {
        return currentLevelIndex < levels.size() - 1;
    }

    /**
     * Switches to the next level.
     *
     * @throws NoMoreLevelsAvailableException if there is no next level
     */
    protected void switchToNextLevel() {
        if (!hasNextLevel()) {
            throw new NoMoreLevelsAvailableException("No more levels after " + level.name + ".");
        }
        currentLevelIndex++;
        level = levels.get(currentLevelIndex);
    }

    @Override
    protected void initializeLevel() {
        super.initializeLevel();
        levelStartTime = System.currentTimeMillis();
        initializeGameObjects();
    }

    /**
     * Adjusts game objects for the new level (background, score, etc.).
     * Currently empty; will be  extended later.
     */
    @Override
    protected void initializeGameObjects() {
        super.initializeGameObjects();


    }
}
