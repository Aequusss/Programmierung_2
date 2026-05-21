package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;

import java.awt.*;

/**
 * A ball that continuously chases the RandomBall.
 */
public class FollowerBall extends GameObject {

    private final RandomBall followMe;

    /**
     * Initializes the follower with a reference to the ball it should chase.
     *
     * @param gameView GameView to show the game object on.
     * @param followMe The RandomBall instance to track.
     */
    public FollowerBall(GameView gameView, RandomBall followMe) {
        super(gameView);
        this.followMe = followMe;
        speedInPixel = 3;
        size = 55;
        position.updateCoordinates(0, 0);
    }

    /**
     * Updates target to leader's position and moves toward it.
     */
    @Override
    public void updatePosition() {
        targetPosition.updateCoordinates(followMe.getPosition());
        position.moveToPosition(targetPosition, speedInPixel);

    }

    /**
     * Draws the ball as a green filled circle.
     */
    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.getX(), position.getY(), size, size, 0, true, Color.GREEN);
    }

    @Override
    public String toString() {
        return "FollowerBall: " + position;
    }

}
