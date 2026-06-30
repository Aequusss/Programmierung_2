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

    private static final double LEFT_BOUNDARY = 200;

    private static final double RIGHT_BOUNDARY = GameView.WIDTH - 200;

    private static final double TOP_BOUNDARY = 200;

    private static final double BOTTOM_BOUNDARY = GameView.HEIGHT - 200;



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
        distanceToBackground = 200;



    }

    /**
     * Increases the weapon level, adding a bullet to the spread.
     */
    public void upgradeWeapon() {
        gamePlayManager.upgradeWeapon();
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
        if (position.getX() > LEFT_BOUNDARY) {
            position.left(speedInPixel);
            if (collidesWithAnyObstacles()) {
                position.right(speedInPixel);
            }
        } else {
            gamePlayManager.moveWorldToRight(speedInPixel);
            if (collidesWithAnyObstacles()) {
                gamePlayManager.moveWorldToLeft(speedInPixel);
            }
        }
    }

    /**
     * Moves the character one step to the right.
     */
    public void right() {
        if (position.getX() < RIGHT_BOUNDARY) {
            position.right(speedInPixel);
            if (collidesWithAnyObstacles()) {
                position.left(speedInPixel);
            }
        } else {
            gamePlayManager.moveWorldToLeft(speedInPixel);
            if (collidesWithAnyObstacles()) {
                gamePlayManager.moveWorldToRight(speedInPixel);
            }
        }
    }

    /**
     * Moves the character one step up.
     */
    public void up() {
        if (position.getY() > TOP_BOUNDARY) {
            position.up(speedInPixel);
            if (collidesWithAnyObstacles()) {
                position.down(speedInPixel);
            }
        } else {
            gamePlayManager.moveWorldDown(speedInPixel);
            if (collidesWithAnyObstacles()) {
                gamePlayManager.moveWorldUp(speedInPixel);
            }
        }
    }

    /**
     * Moves the character one step down.
     */
    public void down() {
        if (position.getY() < BOTTOM_BOUNDARY) {
            position.down(speedInPixel);
            if (collidesWithAnyObstacles()) {
                position.up(speedInPixel);
            }
        } else {
            gamePlayManager.moveWorldUp(speedInPixel);
            if (collidesWithAnyObstacles()) {
                gamePlayManager.moveWorldDown(speedInPixel);
            }
        }
    }

    /**
     * Checks if the character currently collides with any obstacles.
     *
     * @return true if a collision with an obstacle occurs
     */
    private boolean collidesWithAnyObstacles() {
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
            }
            int currentLevel = gamePlayManager.getWeaponLevel();
            double startX = getPosition().getX() + getWidth();
            double baseY = getPosition().getY() + getHeight() / 2;
            double spacing = 15.0;

            for (int i = 0; i < currentLevel; i++) {
                double bulletY = baseY - ((currentLevel - 1) * spacing / 2.0) + (i * spacing);
                PlayerBullet bullet = new PlayerBullet(gameView, gamePlayManager, startX, bulletY);
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
     * Clear the whole Obstacle list so it is empty.
     */
    public void clearObstacleList() {
        collidingGameObjectsForPathDecision.clear();
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
                Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
                gamePlayManager.spawnGameObject(explosion);
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


