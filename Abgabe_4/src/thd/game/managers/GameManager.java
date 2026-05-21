package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

/**
 * Manages the overall game state, initialization, and frame loop.
 * Inherits keyboard handling from UserControlledGameObjectPool.
 */
class GameManager extends UserControlledGameObjectPool {

    /**
     * Manages all active game objects using dynamic lists.
     */
    private final GameObjectManager gameObjectManager;


    /**
     * Constructs the GameManager, initializes all objects, and starts the game loop.
     */
    public GameManager(GameView gameView) {
        super(gameView);
        this.gameObjectManager = new GameObjectManager();
        mainCharacter = new MainCharacter(gameView);
        gameObjectManager.add(mainCharacter);

        enemyTank = new EnemyTank(gameView);
        gameObjectManager.add(enemyTank);

        enemyHelicopter = new EnemyHelicopter(gameView);
        gameObjectManager.add(enemyHelicopter);

        enemyTank = new EnemyTank(gameView);
        gameObjectManager.add(enemyTank);

        playerBullet = new PlayerBullet(gameView, mainCharacter.getPosition().getX() + mainCharacter.getWidth(),
                mainCharacter.getPosition().getY() + mainCharacter.getHeight() / 2);
        gameObjectManager.add(playerBullet);


        enemyBullet = new EnemyBullet(gameView, enemyHelicopter.getPosition().getX() - 20,
                enemyHelicopter.getPosition().getY() + 10);
        gameObjectManager.add(enemyBullet);

        explosion = new Explosion(gameView, 700, 400);
        gameObjectManager.add(explosion);

        bridge = new Bridge(gameView, 300, 600);
        gameObjectManager.add(bridge);

        healthPickup = new HealthPickup(gameView, 200, 200);
        gameObjectManager.add(healthPickup);

        ammoPickup = new AmmoPickup(gameView, 900, 500);
        gameObjectManager.add(ammoPickup);

        healthUnit = new HealthUnit(gameView, 20, 20);
        gameObjectManager.add(healthUnit);


    }

    /**
     * Processes one frame. First handles keyboard input via superclass,
     * then updates all game objects via the GameObjectManager.
     */
    @Override
    protected void processFrame() {
        super.processFrame();

        gameObjectManager.processFrame();

    }


}
