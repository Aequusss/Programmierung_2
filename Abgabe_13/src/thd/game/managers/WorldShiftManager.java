package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.ShiftableGameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages the shifting of the gameWorld.
 */
public class WorldShiftManager extends UserControlledGameObjectPool {
    private final List<GameObject> shiftableGameObjects;
    protected double totalWorldShiftX;

    protected WorldShiftManager(GameView gameView) {
        super(gameView);
        shiftableGameObjects = new LinkedList<>();
        totalWorldShiftX = 0;
    }

    protected void addToShiftableGameObjectsIfShiftable(GameObject gameObject) {
        if (gameObject instanceof ShiftableGameObject) {
            if (!shiftableGameObjects.contains(gameObject)) {
                shiftableGameObjects.add(gameObject);
            }
        }
    }

    protected void spawnGameObject(GameObject gameObject) {
        addToShiftableGameObjectsIfShiftable(gameObject);
    }

    protected void destroyGameObject(GameObject gameObject) {
        if (gameObject instanceof ShiftableGameObject) {
            shiftableGameObjects.remove(gameObject);
        }
    }

    protected void destroyAllGameObjects() {
        shiftableGameObjects.clear();
    }

    /**
     * Moves the game world to the left.
     *
     * @param pixels Number of pixels to move the world.
     */
    public void moveWorldToLeft(double pixels) {
        totalWorldShiftX += pixels;
        shiftGameObjects(-pixels, 0);
    }

    /**
     * Moves the game world to the right.
     *
     * @param pixels Number of pixels to move the world.
     */
    public void moveWorldToRight(double pixels) {
        totalWorldShiftX -= pixels;
        shiftGameObjects(pixels, 0);
    }

    /**
     * Moves the game world up.
     *
     * @param pixels Number of pixels to move the world.
     */
    public void moveWorldUp(double pixels) {
        shiftGameObjects(0, -pixels);
    }

    /**
     * Moves the game world down.
     *
     * @param pixels Number of pixels to move the world.
     */
    public void moveWorldDown(double pixels) {
        shiftGameObjects(0, pixels);
    }

    private void shiftGameObjects(double shiftX, double shiftY) {
        for (GameObject gameObject : shiftableGameObjects) {
            gameObject.getPosition().right(shiftX);
            gameObject.getPosition().down(shiftY);
        }
    }

    /**
     * Keeps the player locked to the center of the screen by shifting the world instead.
     *
     * @param player The player helicopter object.
     */
    protected void updateCamera(GameObject player) {

        double screenCenterX = GameView.WIDTH / 2.0;

        // Horizontal Scrolling (Player moving right past the middle)
        if (player.getPosition().getX() > screenCenterX) {
            double excessX = player.getPosition().getX() - screenCenterX;

            // Shift all background elements and enemies to the left
            moveWorldToLeft(excessX);

            // Force the player to stay locked at the center line
            player.getPosition().left(excessX);
        }
    }

    public boolean isAtStartOfLevel() {
        return totalWorldShiftX <= 0;
    }
}