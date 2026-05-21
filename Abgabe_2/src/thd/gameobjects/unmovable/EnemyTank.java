package thd.gameobjects.unmovable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.Position;

/**
 * Represents a stationary enemy tank on the ground.
 */
public class EnemyTank {
    private final GameView gameView;
    private final Position position;
    private final double size;
    private double rotation;
    private final double width;
    private final double height;

    /**
     * Creates an EnemyTank at a fixed position on the screen.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public EnemyTank(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position(900, 560);
        this.size = 10;
        this.rotation = 0;
        this.width = 190;
        this.height = 30;
    }

    @Override
    public String toString() {
        return "EnemyTank: " + position;
    }

    /**
     * Updates the position of the game object on the canvas.
     * The enemy tank does not move, so this method has no effect.
     */
    public void updatePosition() {
    }

    /**
     * Adds the game object to the canvas at its current position.
     */
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.ENEMY_TANK, position.getX(), position.getY(), 10, rotation);
    }

}
