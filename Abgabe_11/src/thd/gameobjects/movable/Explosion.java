package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.ShiftableGameObject;

import java.awt.Color;

/**
 * Represents an explosion animation that destroys itself upon completion.
 */
class Explosion extends GameObject implements ShiftableGameObject {

    /**
     * Enum representing the sequential frames of the explosion using .png images.
     */
    private enum State {
        FRAME_1("explosion_1.png"),
        FRAME_2("explosion_2.png"),
        FRAME_3("explosion_3.png"),
        FRAME_4("explosion_4.png");

        private final String imageName;

        State(String imageName) {
            this.imageName = imageName;
        }
    }

    private State currentState;

    /**
     * Constructs an explosion at the specified coordinates.
     *
     * @param gameView        the game view
     * @param gamePlayManager the gameplay manager for communication
     * @param startX          initial x-coordinate
     * @param startY          initial y-coordinate
     */
    public Explosion(GameView gameView, GamePlayManager gamePlayManager, double startX, double startY) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(startX, startY);
        speedInPixel = 0;
        size = 0.5;
        width = 100;
        height = 100;
        rotation = 0;
        distanceToBackground = 500;
        currentState = State.FRAME_1;
        gameView.playSound("explosion.wav", false, 0.8f);
    }

    @Override
    public void updateStatus() {
        if (gameView.timer(100, 0, this)) {
            int nextOrdinal = currentState.ordinal() + 1;

            if (nextOrdinal >= State.values().length) {
                gamePlayManager.destroyGameObject(this);
            } else {
                currentState = State.values()[nextOrdinal];
            }
        }
    }

    @Override
    public void updatePosition() {
        // Static position
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(currentState.imageName, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public String toString() {
        return "Explosion: " + position;
    }
}
