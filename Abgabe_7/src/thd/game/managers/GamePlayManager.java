package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.EnemyHelicopter;
import thd.gameobjects.movable.EnemyTank;
import thd.gameobjects.movable.PlayerHelicopter;
import thd.gameobjects.unmovable.HealthUnit;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Manages the gameplay logic, spawning, destruction, lives and scoring.
 */
public class GamePlayManager extends WorldShiftManager {

    private final GameObjectManager gameObjectManager;

    private int currentTankCount;

    private long lastTankSpawnTime;

    private int currentHelicopterCount;

    private long lastHelicopterSpawnTime;

    private static final int LIVES = 5;

    protected int lives;

    protected int points;

    private List<HealthUnit> healthUnits;

    private static final int HEALTH_UNIT_SPACING = 30;

    /**
     * Constructs a new GamePlayManager.
     *
     * @param gameView          the game view for rendering and keyboard input
     * @param gameObjectManager the manager responsible for handling game objects
     */
    public GamePlayManager(GameView gameView, GameObjectManager gameObjectManager) {
        super(gameView);
        this.gameObjectManager = gameObjectManager;
        initCommonState();

    }

    /**
     * Constructs a new GamePlayManager with a default [@link GameObjectManager}.
     *
     * @param gameView the game view for rendering and input
     */
    protected GamePlayManager(GameView gameView) {
        super(gameView);
        this.gameObjectManager = new GameObjectManager();
        initCommonState();
    }

    private void initCommonState() {
        currentTankCount = 0;
        lastTankSpawnTime = System.currentTimeMillis() - 2000;
        currentHelicopterCount = 0;
        lastHelicopterSpawnTime = System.currentTimeMillis() - 4000;
        this.lives = LIVES;
        points = 0;
        healthUnits = new LinkedList<>();
        initializeHealthDisplay();
    }

    /**
     * Called when the player helicopter loses a life.
     */
    public void lifeLost() {
        if (lives > 0) {
            lives--;
            if (!healthUnits.isEmpty()) {
                HealthUnit last = healthUnits.removeLast();
                destroyGameObject(last);
            }
        }
    }

    private void initializeHealthDisplay() {
        for (int i = 0; i < LIVES; i++) {
            HealthUnit unit = new HealthUnit(gameView, this, 10 + i * HEALTH_UNIT_SPACING, 10);
            healthUnits.add(unit);
            spawnGameObject(unit);
        }
    }

    /**
     * Adds points, e.g., when an enemy is destroyed.
     *
     * @param amount the number of points to add
     */
    public void addPoints(int amount) {
        points += amount;
    }

    /**
     * Adds a game object to the game world by delegating to the object manager.
     *
     * @param gameObject the game object to spawn
     */
    @Override
    public void spawnGameObject(GameObject gameObject) {
        super.spawnGameObject(gameObject);
        gameObjectManager.add(gameObject);
    }

    /**
     * Removes a game object from the game world by delegating to the object manager.
     *
     * @param gameObject the game object to destroy
     */
    @Override
    public void destroyGameObject(GameObject gameObject) {
        super.destroyGameObject(gameObject);
        gameObjectManager.remove(gameObject);
        cleanupGameObjectReferences(gameObject);
    }

    /**
     * Hook method called after a game object is marked for destruction.
     * Subclassses (e.g. GameManager) can override to nullify their instance variables.
     *
     * @param gameObject the game object that is being destroyed
     */
    protected void cleanupGameObjectReferences(GameObject gameObject) {
        // Subclasses override to null their own fields and remove obstacles.
    }

    /**
     * Destroys all active game objects by delegating to the object manager.
     */
    @Override
    protected void destroyAllGameObjects() {
        super.destroyAllGameObjects();
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
        processFrameUpdate();
        gameView.addTextToCanvas("Score: " + points, GameView.WIDTH - 150, 10,
                16, false, Color.WHITE, 0, "droidsansmono.ttf");
    }

    /**
     * Spawns Game Objects until specified number within time intervals.
     */
    private void gamePlayManagement() {
        long now = System.currentTimeMillis();

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

    public int getLives() {
        return lives;
    }

    /**
     * Called every frame after gameplay management. Override in subclasses.
     */
    protected void processFrameUpdate() {
        // empty in base class
    }
}