package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

/**
 * Represents a stationary enemy tank on the ground.
 */
public class EnemyTank extends GameObject {

    private int shotDurationInMilliseconds;

    /**
     * Creates an EnemyTank at a fixed position on the screen.
     *
     * @param gameView        The GameView instance used for rendering.
     * @param gamePlayManager the gameplay manager for communication
     */
    public EnemyTank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        this.size = 0.33;
        this.rotation = 0;
        this.width = 190;
        this.height = 30;
        this.speedInPixel = 2;
        position.updateCoordinates(900, 560);
        this.shotDurationInMilliseconds = 1500;
    }

    @Override
    public String toString() {
        return "EnemyTank: " + position;
    }

    /**
     * Updates the position of the game object on the canvas.
     */
    @Override
    public void updatePosition() {
        position.left(speedInPixel);
    }

    /**
     * When Tank leaves the visible screen, it gets deleted, otherwise shoots.
     */
    @Override
    public void updateStatus() {
        if (position.getX() < 0) {
            gamePlayManager.destroyGameObject(this);
            return;
        }
        fire();
    }

    /**
     * Adds the game object to the canvas at its current position.
     */
    @Override
    public void addToCanvas() {
        // gameView.addBlockImageToCanvas(SilkwormBlockImages.ENEMY_TANK, position.getX(), position.getY(), size, rotation);
        gameView.addImageToCanvas("enemytank.png", position.getX(), position.getY(), size, rotation);
    }

    /**
     * Shoots bullet every specified time interval.
     */
    private void fire() {
        if (gameView.timer(shotDurationInMilliseconds, 0, this)) {
            EnemyBullet bullet = new EnemyBullet(gameView, gamePlayManager, getPosition().getX(),
                    getPosition().getY() + 2 * getHeight());
            gamePlayManager.spawnGameObject(bullet);
        }
    }

}
