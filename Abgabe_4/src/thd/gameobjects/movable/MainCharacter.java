package thd.gameobjects.movable;

import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;


import java.awt.*;


/**
 * The main player-controlled character for the Silkworm game.
 * Moves in four directions and displays a shooting effect when space is held.
 * Size increases automatically every 5 seconds.
 */
public class MainCharacter extends GameObject {

    private boolean shotInProgress;


    /**
     * Constructs the main game character.
     *
     * @param gameView the game view for rendering
     */
    public MainCharacter(GameView gameView) {
        super(gameView);
        position.updateCoordinates(600, 360);
        width = 40;
        height = 40;
        speedInPixel = 5;
        rotation = 0;
        size = 0.25;
        shotInProgress = false;

    }


    /**
     * Moves the character one step to the left.
     */
    public void left() {
        position.left(speedInPixel);
    }

    /**
     * Moves the character one step to the right.
     */
    public void right() {
        position.right(speedInPixel);
    }

    /**
     * Moves the character one step up.
     */
    public void up() {
        position.up(speedInPixel);
    }

    /**
     * Moves the character one step down.
     */
    public void down() {
        position.down(speedInPixel);
    }

    /**
     * Activates shooting mode. Called while space is held.
     */
    public void shoot() {
        shotInProgress = true;
    }

    /**
     * Resets shooting mode at the start of each frame.
     */
    public void resetShot() {
        shotInProgress = false;
    }

    /**
     * Uses GameView's timer to increase size every 5 seconds.
     * The timer returns true once every 5000ms, triggering the size increase.
     */
    @Override
    public void updateStatus() {
        if (gameView.timer(5000, 0, this)) {
            width += 1;
            height += 1;
        }
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

    /**
     * Renders the character. Displays a large "X" when shooting.
     */
    @Override
    public void addToCanvas() {
        if (shotInProgress) {
            double xPos = position.getX();
            double yPos = position.getY() - 5;
            gameView.addTextToCanvas("X", xPos, yPos, (int) width,
                    true, Color.RED, rotation, "droidsansmono.ttf");
        } else {

            gameView.addImageToCanvas("helicopter.png", position.getX(), position.getY(), size, rotation);
        }
    }

    @Override
    public String toString() {
        return "MainCharacter: " + position;
    }
}


