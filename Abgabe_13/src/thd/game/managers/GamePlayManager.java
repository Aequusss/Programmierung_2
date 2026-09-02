package thd.game.managers;

import thd.game.level.Difficulty;
import thd.game.level.Level;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.ArmoredAAGun;
import thd.gameobjects.unmovable.RocketTruck;

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

    protected int lives;

    protected int points;

    protected int weaponLevel;



    /**
     * The duration of a single level in milliseconds.
     */
    protected static final long LEVEL_DURATION_MS = 50_000;
    /**
     * The system time when the current level started.
     */
    protected long levelStartTime;


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
        weaponLevel = 1;



    }

    /**
     * Called when the player helicopter loses a life.
     */
    public void lifeLost() {
        if (lives > 0) {
            lives--;

        }
    }

    /**
     * Upgrades the player's weapon spread.
     */
    public void upgradeWeapon() {
        weaponLevel++;
    }

    /**
     * Returns the current weapon level.
     *
     * @return the weapon level
     */
    public int getWeaponLevel() {
        return weaponLevel;
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

        if (playerHelicopter != null) {
            updateCamera(playerHelicopter);
        }
        gamePlayManagement();
        processFrameUpdate();
        gameView.addTextToCanvas(level.name + "  Score: " + points,
                GameView.WIDTH - 300, 10, 16, false, Color.WHITE, 0, "droidsansmono.ttf");

        long timePassed = System.currentTimeMillis() - levelStartTime;
        long secondsleft = Math.max(0, (LEVEL_DURATION_MS - timePassed) / 1000);
        gameView.addTextToCanvas("Time: " + secondsleft, GameView.WIDTH / 2 - 40, 10, 16,
                false, Color.YELLOW, 0, "droidsansmono.ttf");

        for (int i = 0; i < lives; i++) {
            gameView.addBlockImageToCanvas(SilkwormBlockImages.HEALTH_BAR_UNIT, 10 + i * HEALTH_UNIT_SPACING,
                    10, 1.0, 0);
        }
        if (playerHelicopter != null) {
            gameObjectManager.findNearestEnemy(playerHelicopter).ifPresent(enemy -> {
                double dist = playerHelicopter.getPosition().distance(enemy.getPosition());
                // System.out.println("Nearest enemy is at: " + dist);
            });
        }
    }

    /**
     * Spawns Game Objects until specified number within time intervals.
     */
    private void gamePlayManagement() {
        long now = System.currentTimeMillis();

        int maxTanks = (Level.difficulty == Difficulty.EASY) ? 1 : 3;
        long tankCooldown = (Level.difficulty == Difficulty.EASY) ? 6000 : 2000;

        int maxHelis = (Level.difficulty == Difficulty.EASY) ? 1 : 2;
        long heliCooldown = (Level.difficulty == Difficulty.EASY) ? 10000 : 5000;

        if (Level.difficulty == Difficulty.STANDARD && level.number >= 4) {
            tankCooldown = 1500;
            heliCooldown = 3500;
        }

        if (currentTankCount < maxTanks && now - lastTankSpawnTime >= tankCooldown) {
            spawnGameObject(new EnemyTank(gameView, this));
            currentTankCount++;
            lastTankSpawnTime = now;
        }
        if (currentHelicopterCount < maxHelis && now - lastHelicopterSpawnTime >= heliCooldown) {
            spawnGameObject(new EnemyHelicopter(gameView, this));
            currentHelicopterCount++;
            lastHelicopterSpawnTime = now;
        }
    }

    public int getLives() {
        return lives;
    }

    /**
     * Restores one life to the player, up to a reasonable maximum.
     */
    public void addLife() {
        if (lives < 15) {
            lives++;
        }
    }

    /**
     * Called every frame after gameplay management. Override in subclasses.
     */
    protected void processFrameUpdate() {
        // empty in base class
    }

    /**
     * Destroys all enemies and enemy projectiles currently visible on the screen (Shield Nuke effect).
     */
    public void nukeScreen() {
        // Create a safe snapshot of the current objects to iterate over
        List<GameObject> currentObjects = new LinkedList<>(gameObjectManager.getGameObjects());

        for (GameObject obj : currentObjects) {
            if (obj instanceof EnemyTank || obj instanceof EnemyHelicopter
                    || obj instanceof ArmoredAAGun || obj instanceof RocketTruck
                    || obj instanceof Rocket || obj instanceof EnemyBullet) {

                // Get the object's current X position on the screen
                double objX = obj.getPosition().getX();

                // ONLY destroy it if it's horizontally within the visible screen space
                if (objX >= 0 && objX <= GameView.WIDTH) {
                    destroyGameObject(obj);
                }
            }
        }
    }



    /**
     * Returns the current player helicopter instance.
     *
     * @return the player helicopter
     */
    public PlayerHelicopter findPlayer() {
        return playerHelicopter;
    }
}