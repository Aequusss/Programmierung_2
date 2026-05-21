package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

/**
 * Top-level manager; world setup is inherited from {@link GameWorldManager}.
 */
class GameManager extends GameWorldManager {

    /**
     * Creates the game manager.
     *
     * @param gameView the game view
     */
    public GameManager(GameView gameView) {
        super(gameView);
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
     * Placeholder for GameManager‑specific per‑frame logic.
     *
     */
    private void gameManagement() {

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
}