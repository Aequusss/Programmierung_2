package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.PlayerHelicopter;

import java.awt.Color;

/**
 * A pickup that restores ammunition.
 */
public class AmmoPickup extends CollidingGameObject implements ShiftableGameObject {

    private double bobOffset;

    /**
     * Constructs an ammo pickup.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public AmmoPickup(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 0;
        size = 4;
        width = 30;
        height = 30;
        rotation = 0;
        bobOffset = Math.PI;
        distanceToBackground = 3;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter player) {
            gameView.playSound("pickup.wav", false, 1.0f);

            player.upgradeWeapon();
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void updateStatus() {
        bobOffset += 0.05;
    }

    @Override
    public void updatePosition() {
        // Base position stays the same
    }

    @Override
    public void addToCanvas() {
        double bobY = Math.sin(bobOffset) * 3;
        gameView.addBlockImageToCanvas(SilkwormBlockImages.AMMO_PICKUP, position.getX(), position.getY() + bobY, size, rotation);

    }

    @Override
    public String toString() {
        return "AmmoPickup: " + position;
    }
}