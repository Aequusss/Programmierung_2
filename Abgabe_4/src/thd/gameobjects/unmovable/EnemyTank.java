package thd.gameobjects.unmovable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;

import java.awt.*;

/**
 * Represents a stationary enemy tank on the ground.
 */
public class EnemyTank extends GameObject {


    /**
     * Creates an EnemyTank at a fixed position on the screen.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public EnemyTank(GameView gameView) {
        super(gameView);
        this.size = 0.33;
        this.rotation = 0;
        this.width = 190;
        this.height = 30;
        position.updateCoordinates(900, 560);
    }

    @Override
    public String toString() {
        return "EnemyTank: " + position;
    }

    /**
     * Updates the position of the game object on the canvas.
     * The enemy tank does not move, so this method has no effect.
     */
    @Override
    public void updatePosition() {
    }

    /**
     * Adds the game object to the canvas at its current position.
     */
    @Override
    public void addToCanvas() {
        // gameView.addBlockImageToCanvas(SilkwormBlockImages.ENEMY_TANK, position.getX(), position.getY(), size, rotation);
        gameView.addImageToCanvas("enemytank.png", position.getX(), position.getY(), size, rotation);
    }

}
