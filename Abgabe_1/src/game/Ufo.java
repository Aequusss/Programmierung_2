package game;

import java.awt.*;

public class Ufo {
    private final GameView gameView;
    private final Position position;
    private final double speedInPixel;
    private final double size;
    private double rotation;

    public Ufo(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position(0, GameView.HEIGHT / 2d);
        this.speedInPixel = 6;
        this.size = 30;
        this.rotation = 0;
    }

    @Override
    public String toString() {
        return "UFO: " + position;
    }

    public void updatePosition() {
        position.right(speedInPixel);
        rotation++;
    }

    public void addToCanvas() {
        gameView.addTextToCanvas("Objekt 1", position.getX(), position.getY(),
                size, true, Color.YELLOW, rotation);
    }

}
