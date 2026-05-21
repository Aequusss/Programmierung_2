package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

/**
 * Manages the overall game state, initialization, and frame loop.
 * Now inherits from {@link GamePlayManager} to separate gameplay logic
 * and object lifecycle management.
 */
class GameManager extends GamePlayManager {

    /**
     * Constructs the GameManager, creates a new {@link GameObjectManager}
     * for the superclass, initializes all starting objects, and starts the game loop.
     *
     * @param gameView the game view for rendering and input (passed to the parent)
     */
    public GameManager(GameView gameView) {

        super(gameView, new GameObjectManager());

        playerHelicopter = new PlayerHelicopter(gameView, this);
        spawnGameObject(playerHelicopter);

        explosion = new Explosion(gameView, this, 700, 400);
        spawnGameObject(explosion);

        bridge = new Bridge(gameView, this, 300, 600);
        spawnGameObject(bridge);

        healthPickup = new HealthPickup(gameView, this, 200, 200);
        spawnGameObject(healthPickup);

        ammoPickup = new AmmoPickup(gameView, this, 900, 500);
        spawnGameObject(ammoPickup);

        healthUnit = new HealthUnit(gameView, this, 20, 20);
        spawnGameObject(healthUnit);
    }

    /**
     * Processes one frame of the game.
     *
     * <p>Calls {@link GamePlayManager#processFrame()} =
     * keyboard handling + object updates + empty gamePlayManagement().
     * Triggers the (currently empty) game-specific management logic.
     *
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
}