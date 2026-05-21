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

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Builds the initial game world from a string representation.
 */
class GameWorldManager extends GamePlayManager {

    private static int SCALE = 100;


    private final String world;

    /**
     * Number of columns to shift the viewport to the right.
     */
    private final int worldOffsetColumns;

    /**
     * Number of rows to shift the viewport downward.
     */
    private final int worldOffsetLines;

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
        worldOffsetColumns = 0;
        worldOffsetLines = 4;
        world = "            \n"
                + "   h        \n"
                + "            \n"
                + "     B      \n"
                + "            \n"
                + "   e   e    \n"
                + "            \n"
                + "            \n"
                + "            \n"
                + "            \n"
                + "            \n"
                + "         A L\n"
                + "            \n"
                + "            \n"
                + "            \n";
        playerHelicopter = new PlayerHelicopter(gameView, this);
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
    }

    /**
     * Reads the world string and creates the corresponding game objects.
     */
    private void spawnGameObjectsFromWorldString() {
        String[] lines = world.split("\\R");
        Bridge tempBridge = null;
        for (int row = 0; row < lines.length; row++) {
            String line = lines[row];
            for (int col = 0; col < line.length(); col++) {
                char character = lines[row].charAt(col);
                double x = (col - worldOffsetColumns) * SCALE;
                double y = (row - worldOffsetLines) * SCALE;

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
                    tempBridge = new Bridge(gameView, this, x, y);
                    bridge = tempBridge;
                    spawnGameObject(bridge);
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
        if (tempBridge != null) {
            playerHelicopter.addObstacle(tempBridge);
        }

    }

    private void spawnGameObjects() {
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

    @Override
    protected void processFrame() {
        super.processFrame();
        activateGameObjects();
    }
}
