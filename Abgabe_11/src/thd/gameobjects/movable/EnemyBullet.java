package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;


import java.awt.Color;

/**
 * A projectile fired by enemy units.
 */
class EnemyBullet extends CollidingGameObject implements ShiftableGameObject {

    private final double velocityX;

    private final double velocityY;

    /**
     * Constructs an enemy bullet.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public EnemyBullet(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY, double velocityX, double velocityY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 7;
        size = 1.5;
        width = 7;
        height = 7;
        rotation = 0;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        distanceToBackground = 300;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    @Override
    public void updatePosition() {
        position.updateCoordinates(position.getX() + velocityX, position.getY() + velocityY);
        if (position.getX() < -50) {
            position.updateCoordinates(1300, position.getY());
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter) {
            gamePlayManager.destroyGameObject(this);
        } else if (other instanceof PlayerBullet) {
            gamePlayManager.destroyGameObject(other);
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.ENEMY_BULLET, position.getX(), position.getY(),size, 0);
    }

    @Override
    public String toString() {
        return "EnemyBullet: " + position;
    }

    @Override
    public void updateStatus() {
        if (position.getX() < 0
                || position.getX() > GameView.WIDTH
                || position.getY() < 0
                || position.getY() > GameView.HEIGHT) {
            gamePlayManager.destroyGameObject(this);
        }
    }
}