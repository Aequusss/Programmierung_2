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


    /**
     * Constructs an enemy bullet.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public EnemyBullet(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 7;
        size = 0.6;
        width = 12;
        height = 4;
        rotation = 0;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        distanceToBackground = 300;
    }

    @Override
    public void updatePosition() {
        position.left(speedInPixel);
        if (position.getX() < -50) {
            position.updateCoordinates(1300, position.getY());
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter) {
            gamePlayManager.destroyGameObject(this);
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