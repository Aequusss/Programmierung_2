package thd.game.managers;


import thd.gameobjects.base.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages all game objects dynamically and handles collision detection.
 * Inherits from {@link CollisionManager} to delegate collision management.
 */
public class GameObjectManager extends CollisionManager {

    private final List<GameObject> gameObjects;

    private final List<GameObject> gameObjectsToBeAdded;

    private final List<GameObject> gameObjectsToBeRemoved;

    private static final int MAXIMUM_NUMBER_OF_GAME_OBJECTS = 500;

    /**
     * Constructs a new GameObjectManager with empty lists.
     */
    public GameObjectManager() {
        super();
        gameObjects = new LinkedList<>();
        gameObjectsToBeAdded = new LinkedList<>();
        gameObjectsToBeRemoved = new LinkedList<>();
    }

    /**
     * Adds a game object to be included in the next frame update.
     *
     * @param gameObject the game object to add
     */
    public void add(GameObject gameObject) {
        gameObjectsToBeAdded.add(gameObject);
    }

    /**
     * Marks a game object for removal in the next frame update.
     *
     * @param gameObject the game object to remove
     */
    public void remove(GameObject gameObject) {
        gameObjectsToBeRemoved.add(gameObject);
    }

    /**
     * Clears the add buffer and marks all current objects for removal.
     */
    public void removeAll() {
        gameObjectsToBeAdded.clear();
        gameObjectsToBeRemoved.addAll(gameObjects);
    }

    /**
     * Processes one frame: updates lists, then calls updateStatus,
     * updatePosition, and addToCanvas for each object , then triggers
     * collision detection and hitbox display.
     */
    public void processFrame() {
        updateLists();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }
        manageCollisions(false);
    }

    /**
     * Updates the main list by first removing, then adding buffered objects.
     */
    private void updateLists() {
        removeFromGameObjects();
        addToGameObjects();
        if (gameObjects.size() > MAXIMUM_NUMBER_OF_GAME_OBJECTS) {
            throw new TooManyGameObjectsException(
                    "Maximum number of game objects (" + MAXIMUM_NUMBER_OF_GAME_OBJECTS
                            + ") exceeded. Current count: " + gameObjects.size());
        }
    }

    /**
     * Removes all marked objects from the main list and from collision management,
     * then clears the removal buffer.
     */
    private void removeFromGameObjects() {
        for (GameObject toRemove : gameObjectsToBeRemoved) {
            gameObjects.remove(toRemove);
            removeFromCollisionManagement(toRemove);
        }
        gameObjectsToBeRemoved.clear();
    }

    /**
     * Adds all buffered objects to the main list and clears the add buffer.
     */
    private void addToGameObjects() {
        for (GameObject toAdd : gameObjectsToBeAdded) {
            sortIntoGameObjects(toAdd);
            addToCollisionManagement(toAdd);
        }
        gameObjectsToBeAdded.clear();
    }

    /**
     * Sorts all game objects by shortest to furthest distance to background.
     *
     * @param toAdd next game object to sort
     */
    private void sortIntoGameObjects(GameObject toAdd) {
        int indexToSortIn = 0;
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getDistanceToBackground() >= toAdd.getDistanceToBackground()) {
                break;
            }
            indexToSortIn++;
        }
        gameObjects.add(indexToSortIn, toAdd);
    }
}


