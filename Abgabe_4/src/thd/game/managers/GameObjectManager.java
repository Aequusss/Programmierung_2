package thd.game.managers;


import thd.gameobjects.base.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages all game objects dynamically using lists.
 * Supports safe addition and removal during iteration via buffer lists.
 */
public class GameObjectManager {

    private final List<GameObject> gameObjects;

    private final List<GameObject> gameObjectsToBeAdded;

    private final List<GameObject> gameObjectsToBeRemoved;

    /**
     * Constructs a new GameObjectManager with empty lists.
     */
    public GameObjectManager() {
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
     * updatePosition, and addToCanvas for each object in that order.
     */
    public void processFrame() {
        updateLists();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }
    }

    /**
     * Updates the main list by first removing, then adding buffered objects.
     */
    private void updateLists() {
        removeFromGameObjects();
        addToGameObjects();
    }

    /**
     * Removes all marked objects and clears the removal buffer.
     */
    private void removeFromGameObjects() {
        gameObjects.removeAll(gameObjectsToBeRemoved);
        gameObjectsToBeRemoved.clear();
    }

    /**
     * Adds all buffered objects to the main list and clears the add buffer.
     */
    private void addToGameObjects() {
        gameObjects.addAll(gameObjectsToBeAdded);
        gameObjectsToBeAdded.clear();
    }
}


