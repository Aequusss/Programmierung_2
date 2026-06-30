package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.*;
import thd.gameobjects.movable.GroundRocket;

/**
 * Represents a tunnel that periodically spawns ground rockets.
 */
public class GroundRocketTunnel extends CollidingGameObject implements ShiftableGameObject {
    private long lastRocketSpawn;

    /**
     * Creates a new rocket tunnel.
     *
     * @param gameView the game view
     * @param gamePlayManager the game play manager
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public GroundRocketTunnel(GameView gameView, GamePlayManager gamePlayManager, double x, double y) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        width = 40;
        height = 40;
        speedInPixel = 0;
        distanceToBackground = 15;
        size = 15;
        lastRocketSpawn = System.currentTimeMillis();
    }

    @Override
    public void updatePosition() {
        // Static object, no movement
    }

    @Override
    public void updateStatus() {

        if (System.currentTimeMillis() - lastRocketSpawn > 2000) {
            gamePlayManager.spawnGameObject(new GroundRocket(gameView, gamePlayManager, position.getX(), position.getY()));
            lastRocketSpawn = System.currentTimeMillis();
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.TUNNEL_IMAGE, position.getX(), position.getY(), size, 0);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }
}