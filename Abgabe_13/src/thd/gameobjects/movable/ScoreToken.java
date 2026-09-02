package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.game.utilities.SilkwormBlockImages;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.HitBoxOffsets;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * Represents a collectible score token dropped by defeated enemies.
 */
public class ScoreToken extends CollidingGameObject implements ShiftableGameObject {

    private final int value;

    /**
     * Creates a new score token.
     *
     * @param gameView        the game view
     * @param gamePlayManager the game play manager
     * @param x               the x coordinate
     * @param y               the y coordinate
     * @param value           the point value of the token
     */
    public ScoreToken(GameView gameView, GamePlayManager gamePlayManager, double x, double y, int value) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(x, y);
        this.value = value;
        width = 20;
        height = 20;
        size = 4;
        speedInPixel = 2;
        distanceToBackground = 15;
        hitBoxOffsets(HitBoxOffsets.DEFAULT);
    }

    @Override
    public void updatePosition() {
        position.updateCoordinates(position.getX() - speedInPixel, position.getY());
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof PlayerHelicopter) {
            gameView.playSound("pickup.wav", false, 1.0f);
            gamePlayManager.addPoints(value);
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(SilkwormBlockImages.TOKEN_IMAGE, position.getX(), position.getY(), size, 0);

    }
}
