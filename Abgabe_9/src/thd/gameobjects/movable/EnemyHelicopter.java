package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;

import java.awt.*;

/**
 * An enemy helicopter that flies in a wavy sinusoidal pattern.
 */
public class EnemyHelicopter extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject<PlayerHelicopter> {
    /**
     * Possible states of the helicopter.
     */
    private enum State {
        ACTIVE,
        DESTROYED
    }

    private final HelicopterMovementPattern helicopterMovementPattern;
    private double amplitude;
    private double frequency;
    private int shotDurationInMilliseconds;
    private State currentState;

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
        distanceToBackground = 100;
        currentState = State.ACTIVE;
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
        if (other instanceof PlayerBullet && currentState == State.ACTIVE) {
            currentState = State.DESTROYED;

            Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
            gamePlayManager.spawnGameObject(explosion);
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

        double rotorCycle = Math.sin(gameView.gameTimeInMilliseconds() * 0.15);
        double maxRotorWidth = Math.abs(rotorCycle) * (width * size) * 2.5;
        double rotorWidth = Math.abs(rotorCycle) * maxRotorWidth;

        double xOffset = 20;
        double yOffset = 10;


        double rotorX = position.getX() + (width * size) / 2 + xOffset;
        double rotorY = position.getY() + yOffset;

        gameView.addLineToCanvas(rotorX - rotorWidth / 2, rotorY, rotorX + rotorWidth, rotorY,
                3, Color.DARK_GRAY);
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

    @Override
    public boolean tryToActivate(PlayerHelicopter player) {
        double distance = position.distance(player.getPosition());
        return distance < 600;
    }

}
