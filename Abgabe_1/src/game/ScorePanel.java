package game;

import java.awt.*;

public class ScorePanel {
    private final GameView gameView;
    private final Position position;
    private final double size;
    private final double width;
    private double rotation;

    public ScorePanel(GameView gameView) {
        this.gameView = gameView;
        this.width = 149;
        this.position = new Position(GameView.WIDTH - width, -6);
        this.size = 30;
        this.rotation = 90;
    }

    @Override
    public String toString() {
        return "UFO: " + position;
    }

    public void updatePosition() {
    }

    public void addToCanvas() {
        gameView.addTextToCanvas("Objekt 3", position.getX() + 50, position.getY() + 70,
                size, true, Color.WHITE, rotation);
    }

}
