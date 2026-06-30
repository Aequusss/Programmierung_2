package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.MainCharacter;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;


/**
 * The main player-controlled helicopter.
 * Moves in four directions and displays a shooting effect when space is held.
 */
public class PlayerHelicopter extends CollidingGameObject implements MainCharacter {

    private enum Aim {
        UP, STRAIGHT, DOWN
    }

    private Aim currentAim;

    private long rapidFireEndTime;

    private int shotDurationInMilliseconds;

    private List<CollidingGameObject> collidingGameObjectsForPathDecision;

    private static final double LEFT_BOUNDARY = 200;

    private static final double RIGHT_BOUNDARY = GameView.WIDTH - 200;

    private static final double TOP_BOUNDARY = 200;

    private static final double BOTTOM_BOUNDARY = GameView.HEIGHT - 200;

    private int baseShotDuration;

    private boolean isShielded;

    private long lastDamageTime;

    private final long invulnerabilityDuration; // 2 seconds of invulnerability

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
        size = 0.2;
        baseShotDuration = 400;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
        distanceToBackground = 200;
        currentAim = Aim.STRAIGHT;
        isShielded = false;
        lastDamageTime = 0;
        invulnerabilityDuration = 2000;
        shotDurationInMilliseconds = baseShotDuration;


    }

    /**
     * Increases the weapon level, adding a bullet to the spread.
     */
    private void upgradeWeapon() {
        gamePlayManager.upgradeWeapon();
        int lvl = gamePlayManager.getWeaponLevel();


        if (lvl >= 3) {
            rapidFireEndTime = System.currentTimeMillis() + 10_000;
        }
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

            double startX = getPosition().getX() + getWidth();
            double startY = getPosition().getY() + getHeight() / 2;

            // Base vectors
            double forwardX = 14;
            double forwardY = 0;
            double diagDownX = 14;
            double diagDownY = 14;
            double straightDownX = 0;
            double straightDownY = 14;
            double diagUpX = 14;
            double diagUpY = -14;

            double mainX = forwardX;
            double mainY = forwardY;
            double secX = diagDownX;
            double secY = diagDownY;


            if (currentAim == Aim.DOWN) {
                mainX = diagDownX;
                mainY = diagDownY;       // Forward -> Diag Down
                secX = straightDownX;
                secY = straightDownY; // Diag Down -> Straight Down
            } else if (currentAim == Aim.UP) {
                mainX = diagUpX;
                mainY = diagUpY;           // Forward -> Diag Up
                secX = forwardX;
                secY = forwardY;           // Diag Down -> Forward
            }

            int lvl = gamePlayManager.getWeaponLevel();

            // 1. Fire the secondary shot (1 bullet)
            gamePlayManager.spawnGameObject(new PlayerBullet(gameView, gamePlayManager, startX, startY, secX, secY));

            // 2. Fire the primary shots based on weapon level
            if (lvl == 1) {
                // Level 1: 1 primary shot (Total: 2 bullets on screen)
                gamePlayManager.spawnGameObject(new PlayerBullet(gameView, gamePlayManager, startX, startY, mainX, mainY));
            } else {
                // Level 2+: 2 primary shots, slightly offset for a double-barrel look (Total: 3 bullets on screen)
                gamePlayManager.spawnGameObject(new PlayerBullet(gameView, gamePlayManager, startX, startY - 8, mainX, mainY));
                gamePlayManager.spawnGameObject(new PlayerBullet(gameView, gamePlayManager, startX, startY + 8, mainX, mainY));
            }
        }
    }


    /**
     * Resets shooting mode at the start of each frame.
     */
    public void resetShot() {

    }

    /**
     * Updates the aiming direction and manages the rapid-fire timer.
     */
    @Override
    public void updateStatus() {

        if (gameView.keyCurrentlyPressed(KeyEvent.VK_W) || gameView.keyCurrentlyPressed(KeyEvent.VK_UP)) {
            currentAim = Aim.UP;
        } else if (gameView.keyCurrentlyPressed(KeyEvent.VK_S) || gameView.keyCurrentlyPressed(KeyEvent.VK_DOWN)) {
            currentAim = Aim.DOWN;
        } else {
            currentAim = Aim.STRAIGHT;
        }


        if (System.currentTimeMillis() < rapidFireEndTime) {
            shotDurationInMilliseconds = baseShotDuration / 2;
        } else {
            shotDurationInMilliseconds = baseShotDuration;
        }

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
        if (other instanceof AmmoPickup) {
            upgradeWeapon();
            gamePlayManager.destroyGameObject(other);
        } else if (other instanceof ShieldPickup) {
            activateShield();
            gamePlayManager.destroyGameObject(other);
        }

    }

    /**
     * Activates the protective shield.
     */
    private void activateShield() {
        isShielded = true;

    }

    /**
     * Handles damage taken by the player, respecting shields and invulnerability.
     */
    public void takeDamage() {

        if (System.currentTimeMillis() - lastDamageTime < invulnerabilityDuration) {
            return;
        }

        if (isShielded) {
            isShielded = false; // Shield absorbs the hit and breaks
            lastDamageTime = System.currentTimeMillis();
        } else {
            gamePlayManager.lifeLost();
            lastDamageTime = System.currentTimeMillis();


            gameView.playSound("hit.wav", false, 1.0f);
            if (gamePlayManager.getLives() <= 0) {
                Explosion explosion = new Explosion(gameView, gamePlayManager, position.getX(), position.getY());
                gamePlayManager.spawnGameObject(explosion);
                gamePlayManager.destroyGameObject(this);
            }
        }
    }


    /**
     * Renders the character.
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


