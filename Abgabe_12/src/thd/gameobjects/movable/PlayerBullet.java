package thd.gameobjects.movable;

import thd.game.interfaces.Damageable;
import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * A projectile fired by the player's main character.
 */
public class PlayerBullet extends CollidingGameObject implements ShiftableGameObject {

    private final double velocityX;
    private final double velocityY;

    /**
     * Constructs a player bullet.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     * @param velocityX       the horizontal velocity
     * @param velocityY       the vertical velocity
     */
    public PlayerBullet(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY, double velocityX, double velocityY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 10;
        size = 1.5;
        width = 20;
        height = 6;
        rotation = 0;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        distanceToBackground = 300;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

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
        position.updateCoordinates(position.getX() + velocityX, position.getY() + velocityY);
        if (position.getX() > 1300) {
            position.updateCoordinates(-50, position.getY());
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Damageable target) {
            target.takeDamage(1);
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.PLAYER_BULLET, position.getX(), position.getY(), size, 0);
    }

    @Override
    public String toString() {
        return "PlayerBullet: " + position;
    }
}