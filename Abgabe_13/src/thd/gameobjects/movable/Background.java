package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * Represents the static background image of the current level.
 */
public class Background extends GameObject implements ShiftableGameObject {

    private final String imageName;

    /**
     * Constructs a static background locked to the screen.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager
     * @param imageName       the filename of the background image
     * @param scale           the scale factor to fit the 1280x720 window
     */
    public Background(GameView gameView, GamePlayManager gamePlayManager, String imageName, double scale) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(0, 0);
        this.imageName = imageName;
        this.size = scale;
        width = GameView.WIDTH;
        height = GameView.HEIGHT;
        distanceToBackground = -1000;
    }

    @Override
    public void updatePosition() {
        if (position.getX() <= -width) {
            position.right(width);
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(imageName, position.getX(), position.getY(), size, rotation);

        // Draw an identical twin right next to it to cover the gap when scrolling left
        gameView.addImageToCanvas(imageName, position.getX() + width, position.getY(), size, rotation);
    }
}
