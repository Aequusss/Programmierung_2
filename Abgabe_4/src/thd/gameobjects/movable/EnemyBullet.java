package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.GameObject;

import java.awt.Color;

/**
 * A projectile fired by enemy units.
 */
public class EnemyBullet extends GameObject {

    /**
     * Constructs an enemy bullet.
     *
     * @param gameView the game view
     * @param startX   initial x-coordinate
     * @param startY   initial y-coordinate
     */
    public EnemyBullet(GameView gameView, double startX, double startY) {
        super(gameView);
        position.updateCoordinates(startX, startY);
        speedInPixel = 7;
        size = 0.6;
        width = 12;
        height = 4;
        rotation = 0;
    }

    @Override
    public void updatePosition() {
        position.left(speedInPixel);
        if (position.getX() < -50) {
            position.updateCoordinates(1300, position.getY());
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.ENEMY_BULLET, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public String toString() {
        return "EnemyBullet: " + position;
    }
}