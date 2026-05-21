package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.Position;

/**
 * Represents the player-controlled jeep driving along the ground.
 */
public class Jeep {
    private final GameView gameView;
    private final Position position;
    private final double speedInPixel;
    private final double size;
    private double rotation;
    private final double width;
    private final double height;

    /**
     * Creates a Jeep at a fixed starting position near the bottom of the screen.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public Jeep(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position(100, 580);
        this.speedInPixel = 2;
        this.size = 10;
        this.rotation = 0;
        this.width = 190;
        this.height = 40;
    }

    @Override
    public String toString() {
        return "JEEP: " + position;
    }

    /**
     * Updates the position of the game object on the canvas.
     */
    public void updatePosition() {
        position.right(speedInPixel);
    }

    /**
     * Adds the game object to the canvas at its current position.
     */
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.JEEP, position.getX(), position.getY(), 10, rotation);
    }
}

