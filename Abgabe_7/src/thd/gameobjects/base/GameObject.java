package thd.gameobjects.base;

import thd.game.managers.GameObjectManager;
import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;

import java.util.Objects;

/**
 * Represents an object in the game.
 */
public abstract class GameObject {

    protected final GameView gameView;
    protected final GamePlayManager gamePlayManager;
    protected final Position position;
    protected final Position targetPosition;
    protected double speedInPixel;
    protected double rotation;
    protected double size;
    protected double width;
    protected double height;
    protected int distanceToBackground;

    /**
     * Creates a new GameObject.
     *
     * @param gameView        GameView to show the game object on.
     * @param gamePlayManager the gameplay manager for communication
     */
    public GameObject(GameView gameView, GamePlayManager gamePlayManager) {
        this.gameView = gameView;
        this.gamePlayManager = gamePlayManager;
        position = new Position();
        targetPosition = new Position();
        distanceToBackground = 0;
    }

    /**
     * Updates the position of the game object.
     */
    public void updatePosition() {

    }

    /**
     * Draws the game object to the canvas.
     */
    public abstract void addToCanvas();

    /**
     * Returns the current position of the game object.
     *
     * @return position of the game object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns width of game object.
     *
     * @return Width of game object
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns height of game object.
     *
     * @return Height of game object
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns distance to background of game object.
     *
     * @return distance of background of game object
     */
    public int getDistanceToBackground() {
        return distanceToBackground;
    }

    /**
     * Updates the internal state of the game object.
     * Override this method in subclasses to implement timed state changes.
     */
    public void updateStatus() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameObject that = (GameObject) o;
        return Double.compare(that.speedInPixel, speedInPixel) == 0
                && Double.compare(that.rotation, rotation) == 0
                && Double.compare(that.size, size) == 0
                && Double.compare(that.width, width) == 0
                && Double.compare(that.height, height) == 0
                && position.equals(that.position)
                && targetPosition.equals(that.targetPosition)
                && distanceToBackground == that.distanceToBackground;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, targetPosition, speedInPixel, rotation, size, width,
                height, distanceToBackground);
    }
}