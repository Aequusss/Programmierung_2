package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;


import java.awt.Color;

/**
 * A projectile fired by the player's main character.
 */
class PlayerBullet extends CollidingGameObject implements ShiftableGameObject {

    /**
     * Constructs a player bullet.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public PlayerBullet(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 10;
        size = 0.6;
        width = 12;
        height = 4;
        rotation = 0;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        distanceToBackground = 300;
    }

    @Override
    public void updateStatus() {
        if (position.getX() > GameView.WIDTH
                || position.getX() < 0
                || position.getY() > GameView.HEIGHT
                || position.getY() < 0) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void updatePosition() {
        position.right(speedInPixel);
        if (position.getX() > 1300) {
            position.updateCoordinates(-50, position.getY());
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof EnemyTank) {
            gamePlayManager.addPoints(100);
            gamePlayManager.destroyGameObject(this);
            gamePlayManager.destroyGameObject(other);
        } else if (other instanceof EnemyHelicopter) {
            gamePlayManager.addPoints(150);
            gamePlayManager.destroyGameObject(this);
            gamePlayManager.destroyGameObject(other);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.PLAYER_BULLET, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public String toString() {
        return "PlayerBullet: " + position;
    }
}