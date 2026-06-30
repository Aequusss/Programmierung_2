package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.EnemyHelicopter;
import thd.gameobjects.movable.EnemyTank;
import thd.gameobjects.movable.PlayerHelicopter;
import thd.gameobjects.unmovable.AmmoPickup;
import thd.gameobjects.unmovable.Bridge;
import thd.gameobjects.unmovable.HealthPickup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Builds the initial game world from a string representation.
 */
class GameWorldManager extends GamePlayManager {

    private static int SCALE = 100;


    private final List<GameObject> activatableGameObjects;

    private Bridge bridge;


    /**
     * Creates the world manager and spawns the starting objects.
     *
     * @param gameView the game view
     */
    protected GameWorldManager(GameView gameView) {
        super(gameView);
        activatableGameObjects = new LinkedList<>();


    }

    /**
     * Reads the world string and creates the corresponding game objects.
     */
    private void spawnGameObjectsFromWorldString() {
        ArrayList<String> lines = new ArrayList<>(List.of(level.world.split("\\R")));

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                char character = lines.get(row).charAt(col);
                double x = (col - level.worldOffsetColumns) * SCALE;
                double y = (row - level.worldOffsetLines) * SCALE;

                if (character == 'P') {

                } else if (character == 'E') {
                    EnemyTank tank = new EnemyTank(gameView, this);
                    tank.getPosition().updateCoordinates(x, y);
                    spawnGameObject(tank);
                } else if (character == 'H') {
                    EnemyHelicopter helicopter = new EnemyHelicopter(gameView, this);
                    helicopter.getPosition().updateCoordinates(x, y);
                    spawnGameObject(helicopter);
                } else if (character == 'B') {
                    bridge = new Bridge(gameView, this, x, y);
                    spawnGameObject(bridge);
                    if (playerHelicopter != null) {
                        playerHelicopter.addObstacle(bridge);
                    }
                } else if (character == 'L') {
                    HealthPickup hp = new HealthPickup(gameView, this, x, y);
                    spawnGameObject(hp);
                } else if (character == 'A') {
                    AmmoPickup ap = new AmmoPickup(gameView, this, x, y);
                    spawnGameObject(ap);
                } else if (character == 'e') {
                    EnemyTank tank = new EnemyTank(gameView, this);
                    tank.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(tank);
                } else if (character == 'h') {
                    EnemyHelicopter heli = new EnemyHelicopter(gameView, this);
                    heli.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(heli);
                }
            }
        }
    }

    private void spawnGameObjects() {
        playerHelicopter = new PlayerHelicopter(gameView, this);
        spawnGameObject(playerHelicopter);
    }

    /**
     * Adds a game object that should be activated later.
     *
     * @param gameObject the object to add
     */
    private void addActivatableGameObject(GameObject gameObject) {
        activatableGameObjects.add(gameObject);
        addToShiftableGameObjectsIfShiftable(gameObject);
    }

    @Override
    protected void processFrameUpdate() {
        super.processFrameUpdate();
        activateGameObjects();
    }

    private void activateGameObjects() {
        ListIterator<GameObject> iterator = activatableGameObjects.listIterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            if (gameObject instanceof ActivatableGameObject<?>) {
                @SuppressWarnings("unchecked")
                ActivatableGameObject<PlayerHelicopter> activatable =
                        (ActivatableGameObject<PlayerHelicopter>) gameObject;
                if (activatable.tryToActivate(playerHelicopter)) {
                    spawnGameObject(gameObject);
                    iterator.remove();
                }
            }
        }
    }

    @Override
    protected void cleanupGameObjectReferences(GameObject gameObject) {
        super.cleanupGameObjectReferences(gameObject);
        if (gameObject == bridge) {
            bridge = null;
            if (playerHelicopter != null) {
                playerHelicopter.removeObstacle((CollidingGameObject) gameObject);
            }
        }

    }

    /**
     * Sets up the level by clearing old objects, resetting path lists,
     * and spawning all level objects.
     */
    protected void initializeLevel() {
        activatableGameObjects.clear();
        destroyAllGameObjects();
        clearListsForPathDecisionsInGameObjects();
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
        initializeGameObjects();
    }

    /**
     * Clears obstacle lists in all game objects that use path decisions.
     */
    private void clearListsForPathDecisionsInGameObjects() {
        if (playerHelicopter != null) {
            playerHelicopter.clearObstacleList();
        }
    }

    @Override
    protected void processFrame() {
        super.processFrame();
        activateGameObjects();
    }


    /**
     * Called after the world is built. Override to customize the new level.
     */
    protected void initializeGameObjects() {

    }
}
