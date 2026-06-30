package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * Top-level manager; handles game restart and level cycling.
 */
class GameManager extends LevelManager {

    /**
     * Creates the game manager and starts the first level.
     *
     * @param gameView the game view
     */
    public GameManager(GameView gameView) {
        super(gameView);
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
            initializeGame();
        } else if (endOfLevel()) {
            switchToNextLevel();
            initializeLevel();
        }
    }


    /**
     * After 3 seconds ends the current level.
     *
     * @return {@code true} every 3 seconds
     */
    private boolean endOfLevel() {
        return gameView.timer(3000, 0, this);
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
    }
}