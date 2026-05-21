package game;

import java.awt.*;

public class UfoShot {
    private final GameView gameView;
    private final Position position;
    private final double speedInPixel;
    private final double size;
    private final double width;
    private final double height;
    private double rotation;

    public UfoShot(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position(1100, 650);
        this.speedInPixel = 2;
        this.size = 30;
        this.width = 150;
        this.height = 33;
        this.rotation = 0;
    }

    @Override
    public String toString() {
        return "UFO: " + position;
    }

    public void updatePosition() {
        position.left(speedInPixel);
    }

    public void addToCanvas() {
        int shift = 25;
        gameView.addRectangleToCanvas(position.getX(), position.getY(), width - shift, height, 0, true, Color.GREEN);
        gameView.addRectangleToCanvas(
                position.getX() + width - shift, position.getY(), shift, height, 0, true, Color.YELLOW);
        gameView.addRectangleToCanvas(position.getX(), position.getY(), width, height, 5, false, Color.WHITE);
        gameView.addTextToCanvas("Objekt 2", position.getX() + 3, position.getY() - 5,
                size, true, Color.BLUE, rotation);
    }

}
