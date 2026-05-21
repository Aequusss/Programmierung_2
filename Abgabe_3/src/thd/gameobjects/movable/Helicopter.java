package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;

/**
 * Represents the player-controlled helicopter in the game.
 */
public class Helicopter extends GameObject {


    /**
     * Creates a Helicopter at a fixed starting position.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public Helicopter(GameView gameView) {
        super(gameView);
        this.speedInPixel = 3;
        this.size = 10;
        this.rotation = 0;
        this.width = 170;
        this.height = 40;
        position.updateCoordinates(100, 300);
    }

    @Override
    public String toString() {
        return "HELICOPTER: " + position;
    }

    /**
     * Updates the position of the game object on the canvas.
     */
    @Override
    public void updatePosition() {
        position.right(speedInPixel);

    }

    /**
     * Adds the game object to the canvas at its current position.
     */
    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.HELICOPTER, position.getX(), position.getY(), size, rotation);
    }


}

