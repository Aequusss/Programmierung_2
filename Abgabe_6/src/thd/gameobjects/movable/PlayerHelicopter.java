package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.MainCharacter;

import javax.print.attribute.standard.JobMediaSheetsSupported;
import java.util.LinkedList;
import java.util.List;


/**
 * The main player-controlled character for the Silkworm game.
 * Moves in four directions and displays a shooting effect when space is held.
 * Size increases automatically every 5 seconds.
 */
public class PlayerHelicopter extends CollidingGameObject implements MainCharacter {

    private int shotDurationInMilliseconds;

    private List<CollidingGameObject> collidingGameObjectsForPathDecision;


    /**
     * Constructs the main game character.
     *
     * @param gameView        the game view for rendering
     * @param gamePlayManager the gameplay manager for communication
     */
    public PlayerHelicopter(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(600, 360);
        collidingGameObjectsForPathDecision = new LinkedList<>();
        width = 145;
        height = 50;
        speedInPixel = 5;
        rotation = 0;
        size = 0.25;
        shotDurationInMilliseconds = 300;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);


    }

    /**
     * Adds an obstacle that the Player Helicopter should avoid.
     *
     * @param obstacle the obstacle to avoid
     */
    public void addObstacle(CollidingGameObject obstacle) {
        collidingGameObjectsForPathDecision.add(obstacle);
    }

    /**
     * Removes an obstacle from the path-decision list.
     *
     * @param obstacle the obstacle to remove
     */
    public void removeObstacle(CollidingGameObject obstacle) {
        collidingGameObjectsForPathDecision.remove(obstacle);
    }

    /**
     * Moves the character one step to the left.
     */
    public void left() {
        position.left(speedInPixel);
        if (collidesWithObstacles()) {
            position.right(speedInPixel);
        }
    }

    /**
     * Moves the character one step to the right.
     */
    public void right() {
        position.right(speedInPixel);
        if (collidesWithObstacles()) {
            position.left(speedInPixel);
        }
    }

    /**
     * Moves the character one step up.
     */
    public void up() {
        position.up(speedInPixel);
        if (collidesWithObstacles()) {
            position.down(speedInPixel);
        }
    }

    /**
     * Moves the character one step down.
     */
    public void down() {
        position.down(speedInPixel);
        if (collidesWithObstacles()) {
            position.up(speedInPixel);
        }
    }

    /**
     * Checks if the character currently collides with any obstacles.
     *
     * @return true if a collision with an obstacle occurs
     */
    private boolean collidesWithObstacles() {
        for (CollidingGameObject obstacle : collidingGameObjectsForPathDecision) {
            if (collidesWith(obstacle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Activates shooting mode. Called while space is held.
     */
    @Override
    public void shoot() {
        if (gameView.timer(shotDurationInMilliseconds, 0, this)) {
            if (gamePlayManager.getLives() <= 0) {
                return;
            } else {
                PlayerBullet bullet = new PlayerBullet(gameView, gamePlayManager, getPosition().getX() + getWidth(),
                        getPosition().getY() + getHeight() / 2);
                gamePlayManager.spawnGameObject(bullet);

            }
        }
    }

    /**
     * Resets shooting mode at the start of each frame.
     */
    public void resetShot() {

    }

    /**
     * Uses GameView's timer to increase size every 5 seconds.
     * The timer returns true once every 5000ms, triggering the size increase.
     */
    @Override
    public void updateStatus() {

    }

    /**
     * Keeps the character within the visible area (1280 x 720).
     */
    @Override
    public void updatePosition() {
        if (position.getX() < 0) {
            position.updateCoordinates(0, position.getY());
        }
        if (position.getY() < 0) {
            position.updateCoordinates(position.getX(), 0);
        }
        if (position.getX() > 1280 - width) {
            position.updateCoordinates(1280 - width, position.getY());
        }
        if (position.getY() > 720 - height) {
            position.updateCoordinates(position.getX(), 720 - height);
        }
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof EnemyBullet) {
            gamePlayManager.lifeLost();
            gamePlayManager.destroyGameObject(other);
            if (gamePlayManager.getLives() <= 0) {
                gamePlayManager.destroyGameObject(this);
            }
        }
    }


    /**
     * Renders the character. Displays a large "X" when shooting.
     */
    @Override
    public void addToCanvas() {

        gameView.addImageToCanvas("helicopter.png", position.getX(), position.getY(), size, rotation);
    }


    @Override
    public String toString() {
        return "PlayerHelicopter: " + position;
    }
}


