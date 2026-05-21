package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.EnemyHelicopter;
import thd.gameobjects.movable.Square;
import thd.gameobjects.movable.EnemyTank;

/**
 * Manages the gameplay by dynamically creating and destroying game objects.
 * Inherits from {@link UserControlledGameObjectPool} to handle user input
 * and delegates object lifecycle operations to a {@link GameObjectManager}.
 */
public class GamePlayManager extends UserControlledGameObjectPool {

    private final GameObjectManager gameObjectManager;

    private int currentNumberOfVisibleSquares;

    private long lastSpawnTime;

    private int currentTankCount;

    private long lastTankSpawnTime;

    private int currentHelicopterCount;

    private long lastHelicopterSpawnTime;

    /**
     * Constructs a new GamePlayManager.
     *
     * @param gameView          the game view for rendering and keyboard input
     * @param gameObjectManager the manager responsible for handling game objects
     */
    public GamePlayManager(GameView gameView, GameObjectManager gameObjectManager) {
        super(gameView);
        this.gameObjectManager = gameObjectManager;
        currentNumberOfVisibleSquares = 0;
        lastSpawnTime = System.currentTimeMillis();
        currentTankCount = 0;
        lastTankSpawnTime = System.currentTimeMillis() - 2000;
        currentHelicopterCount = 0;
        lastHelicopterSpawnTime = System.currentTimeMillis() - 4000;

    }

    /**
     * Constructs a new GamePlayManager with a default [@link GameObjectManager}.
     *
     * @param gameView the game view for rendering and input
     */
    protected GamePlayManager(GameView gameView) {
        super(gameView);
        this.gameObjectManager = new GameObjectManager();
        currentNumberOfVisibleSquares = 0;
        lastSpawnTime = System.currentTimeMillis();
        currentTankCount = 0;
        lastTankSpawnTime = System.currentTimeMillis();
        currentHelicopterCount = 0;
        lastHelicopterSpawnTime = System.currentTimeMillis();
    }

    /**
     * Adds a game object to the game world by delegating to the object manager.
     *
     * @param gameObject the game object to spawn
     */
    public void spawnGameObject(GameObject gameObject) {
        gameObjectManager.add(gameObject);
    }

    /**
     * Removes a game object from the game world by delegating to the object manager.
     *
     * @param gameObject the game object to destroy
     */
    public void destroyGameObject(GameObject gameObject) {
        gameObjectManager.remove(gameObject);
    }

    /**
     * Destroys all active game objects by delegating to the object manager.
     */
    protected void destroyAllGameObjects() {
        gameObjectManager.removeAll();
    }

    /**
     * Called once per frame to process gameplay updates.
     * First executes the parent class frame logic (keyboard input),
     * then updates all managed game objects, and finally calls
     * the gameplay management routine.
     */
    @Override
    protected void processFrame() {
        super.processFrame();
        gameObjectManager.processFrame();
        gamePlayManagement();
    }

    /**
     * Spawns Game Objects until specified number within time intervals.
     */
    private void gamePlayManagement() {
        long now = System.currentTimeMillis();


        if (currentNumberOfVisibleSquares < 5 && now - lastSpawnTime >= 1000) {
            spawnGameObject(new Square(gameView, this));
            currentNumberOfVisibleSquares++;
            lastSpawnTime = now;
        }

        if (currentTankCount < 3 && now - lastTankSpawnTime >= 2000) {
            spawnGameObject(new EnemyTank(gameView, this));
            currentTankCount++;
            lastTankSpawnTime = now;
        }
        if (currentHelicopterCount < 2 && now - lastHelicopterSpawnTime >= 5000) {
            spawnGameObject(new EnemyHelicopter(gameView, this));
            currentHelicopterCount++;
            lastHelicopterSpawnTime = now;
        }
    }
}