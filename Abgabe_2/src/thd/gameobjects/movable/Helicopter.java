package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.Position;

/**
 * Represents the player-controlled helicopter in the game.
 */
public class Helicopter {

    private final GameView gameView;
    private final Position position;
    private final double speedInPixel;
    private final double size;
    private double rotation;
    private final double width;
    private final double height;

    /**
     * Creates a Helicopter at a fixed starting position.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public Helicopter(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position(100, 300);
        this.speedInPixel = 3;
        this.size = 10;
        this.rotation = 0;
        this.width = 170;
        this.height = 40;
    }

    @Override
    public String toString() {
        return "HELICOPTER: " + position;
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
        gameView.addBlockImageToCanvas(SilkwormBlockImages.HELICOPTER, position.getX(), position.getY(), size, rotation);
    }


}

