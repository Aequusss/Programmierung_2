package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;

/**
 * Represents the player-controlled jeep driving along the ground.
 */
public class Jeep extends GameObject {


    /**
     * Creates a Jeep at a fixed starting position near the bottom of the screen.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public Jeep(GameView gameView) {
        super(gameView);
        this.speedInPixel = 2;
        this.size = 10;
        this.rotation = 0;
        this.width = 190;
        this.height = 40;
        position.updateCoordinates(100, 580);
    }

    @Override
    public String toString() {
        return "JEEP: " + position;
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
        gameView.addBlockImageToCanvas(SilkwormBlockImages.JEEP, position.getX(), position.getY(), 10, rotation);
    }
}

