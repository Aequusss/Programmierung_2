package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.Helicopter;
import thd.gameobjects.movable.Jeep;
import thd.gameobjects.unmovable.EnemyTank;

/**
 * Manages all game objects and coordinates each frame update.
 */
class GameManager {

    private final Helicopter helicopter;
    private final Jeep jeep;
    private final EnemyTank enemyTank;

    /**
     * Creates the GameManager and initializes all game objects.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public GameManager(GameView gameView) {
        helicopter = new Helicopter(gameView);
        jeep = new Jeep(gameView);
        enemyTank = new EnemyTank(gameView);
    }

    /**
     * Processes a single frame: updates positions and draws all game objects.
     */
    void processFrame() {

        helicopter.updatePosition();
        jeep.updatePosition();
        enemyTank.updatePosition();

        helicopter.addToCanvas();
        jeep.addToCanvas();
        enemyTank.addToCanvas();

    }


}
