package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;


/**
 * An enemy tank that moves left and shoots. Uses an internal state machine
 * for hit reactions.
 */
public class EnemyTank extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject<PlayerHelicopter> {

    /**
     * Possible states of tank.
     */
    private enum State {
        STANDARD,
        EXPLODING,
        EXPLODED
    }

    private State currentState;
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
        hitBoxOffsets(new HitBoxOffsets(0, 50, (-width / 2) + 15, 0));
        this.speedInPixel = 2;
        distanceToBackground = 100;
        position.updateCoordinates(900, 560);
        currentState = State.STANDARD;
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
        if (currentState == State.STANDARD) {
            position.left(speedInPixel);
        }
    }

    /**
     * When Tank leaves the visible screen, it gets deleted, otherwise shoots.
     */
    @Override
    public void updateStatus() {
        switch (currentState) {
            case STANDARD -> {
                fire();
                if (position.getX() < 0) {
                    gamePlayManager.destroyGameObject(this);

                }
                break;
            }
            case EXPLODING -> {
                currentState = State.EXPLODED;
                break;
            }
            case EXPLODED -> {
                gamePlayManager.destroyGameObject(this);
                break;
            }
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerBullet && currentState == State.STANDARD) {
            currentState = State.EXPLODING;
            gamePlayManager.destroyGameObject(this);
            gamePlayManager.destroyGameObject(other);
        }
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

    @Override
    public boolean tryToActivate(PlayerHelicopter player) {

        double distance = position.distance(player.getPosition());
        return distance < 500;
    }

}


