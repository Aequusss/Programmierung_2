package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.EnemyTank;

/**
 * Manages all game objects and coordinates each frame update.
 */
class GameManager {

    private final Helicopter helicopter;
    private final Jeep jeep;
    private final EnemyTank enemyTank;
    private final RandomBall randomBall;
    private final FollowerBall followerBall;
    private final EnemyHelicopter enemyHelicopter;

    /**
     * Creates the GameManager and initializes all game objects.
     *
     * @param gameView The GameView instance used for rendering.
     */
    public GameManager(GameView gameView) {
        helicopter = new Helicopter(gameView);
        jeep = new Jeep(gameView);
        enemyTank = new EnemyTank(gameView);
        randomBall = new RandomBall(gameView);
        followerBall = new FollowerBall(gameView, randomBall);
        enemyHelicopter = new EnemyHelicopter(gameView);
    }

    /**
     * Processes a single frame: updates positions and draws all game objects.
     */
    void processFrame() {

        helicopter.updatePosition();
        jeep.updatePosition();
        enemyTank.updatePosition();
        randomBall.updatePosition();
        followerBall.updatePosition();
        enemyHelicopter.updatePosition();

        helicopter.addToCanvas();
        jeep.addToCanvas();
        enemyTank.addToCanvas();
        randomBall.addToCanvas();
        followerBall.addToCanvas();
        enemyHelicopter.addToCanvas();

    }


}
