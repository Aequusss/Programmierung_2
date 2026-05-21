package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;

import java.awt.*;

/**
 * An enemy helicopter that flies in a wavy sinusoidal pattern.
 */
public class EnemyHelicopter extends CollidingGameObject {

    private final HelicopterMovementPattern helicopterMovementPattern;
    private double amplitude;
    private double frequency;
    private int shotDurationInMilliseconds;

    /**
     * Initializes the helicopter with wavy flight parameters.
     *
     * @param gameView        GameView to show the game object on.
     * @param gamePlayManager the gameplay manager for communication
     */
    public EnemyHelicopter(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        speedInPixel = 3;
        size = 0.25;
        amplitude = 2.5;
        frequency = 0.05;
        shotDurationInMilliseconds = 1200;
        width = 145;
        height = 55;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        helicopterMovementPattern = new HelicopterMovementPattern();

        position.updateCoordinates(helicopterMovementPattern.startPosition());
        targetPosition.updateCoordinates(helicopterMovementPattern.nextPosition());
    }

    /**
     * Moves horizontally and adds a sine wave to the vertical axis.
     */
    @Override
    public void updatePosition() {
        position.moveToPosition(targetPosition, speedInPixel);

        double wavyY = Math.sin(position.getX() * frequency) * amplitude;
        position.updateCoordinates(position.getX(), position.getY() + wavyY);

        if (position.getX() < -150) {
            position.updateCoordinates(helicopterMovementPattern.startPosition());
        }
    }

    @Override
    public void updateStatus() {
        fire();
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerBullet) {
            gamePlayManager.destroyGameObject(this);
            gamePlayManager.destroyGameObject(other);
        }
    }

    /**
     * Renders the Helicopter PNG onto the canvas.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("enemyhelicopter.png", position.getX(), position.getY(), size, rotation);
    }

    /**
     * Shoots bullet every specified time interval.
     */
    private void fire() {
        if (gameView.timer(shotDurationInMilliseconds, 0, this)) {
            EnemyBullet bullet = new EnemyBullet(gameView, gamePlayManager, getPosition().getX(),
                    getPosition().getY() + 40);
            gamePlayManager.spawnGameObject(bullet);
        }
    }

    @Override
    public String toString() {
        return "EnemyHelicopter: " + position;
    }
}
