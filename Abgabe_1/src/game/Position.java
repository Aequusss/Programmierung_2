package game;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position other) {
        this(other.x, other.y);
    }

    public Position() {
        this(0, 0);
    }

    public void updateCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void updateCoordinates(Position other) {
        updateCoordinates(other.x, other.y);
    }

    public void left(double pixel) {
        x -= pixel;
    }

    public void left() {
        left(1);
    }

    public void right(double pixel) {
        x += pixel;
    }

    public void right() {
        right(1);
    }

    public void up(double pixel) {
        y -= pixel;
    }

    public void up() {
        up(1);
    }

    public void down(double pixel) {
        y += pixel;
    }

    public void down() {
        down(1);
    }


    @Override
    public String toString() {
        return "Position (" + (int) Math.round(x) + ", " + (int) Math.round(y) + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
