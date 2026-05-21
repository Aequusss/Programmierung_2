package thd.gameobjects.base;

import thd.game.utilities.GameView;

import java.util.Objects;

/**
 * Repräsentiert eine Position im Koordinatensystem des {@link GameView}-Fensters.
 * Die Auflösung des Fensters beträgt 1280 x 720 Pixel.
 * Der Ursprung (0, 0) liegt oben links,
 * x wächst nach rechts und y wächst nach unten.
 *
 * @see GameView
 */
public class Position {

    private double x;
    private double y;

    /**
     * Creates a position on (0, 0).
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Creates a position with the coordinates of the given position.
     *
     * @param other Another position.
     */
    public Position(Position other) {
        this(other.x, other.y);
    }

    /**
     * Creates a position on (x, y).
     *
     * @param x X-coordinate on the window.
     * @param y Y-coordinate on the window.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x coordinate.
     *
     * @return x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y coordinate.
     *
     * @return y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Updates this position to the coordinates of the given position.
     *
     * @param other Another position.
     */
    public void updateCoordinates(Position other) {
        x = other.x;
        y = other.y;
    }

    /**
     * Updates this position to the coordinates of the new position.
     *
     * @param x X-coordinate on the window.
     * @param y Y-coordinate on the window.
     */
    public void updateCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * One pixel to the right.
     */
    public void right() {
        x++;
    }

    /**
     * To the right by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void right(double pixel) {
        x += pixel;
    }

    /**
     * One pixel to the left.
     */
    public void left() {
        x--;
    }

    /**
     * To the left by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void left(double pixel) {
        x -= pixel;
    }

    /**
     * One pixel upwards.
     */
    public void up() {
        y--;
    }

    /**
     * Upwards by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void up(double pixel) {
        y -= pixel;
    }

    /**
     * One pixel downwards.
     */
    public void down() {
        y++;
    }

    /**
     * Downwards by the given number of pixels.
     *
     * @param pixel Number of pixels.
     */
    public void down(double pixel) {
        y += pixel;
    }

    @Override
    public String toString() {
        return "Position (" + (int) Math.round(x) + ", " + (int) Math.round(y) + ")";
    }

    /**
     * Calculates the distance to another position using the Pythagorean theorem.
     *
     * @param other Another position.
     * @return Distance to the other position.
     */
    public double distance(Position other) {
        double distanceX = other.x - x;
        double distanceY = other.y - y;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Moves this position one step toward the other position at constant speed.
     *
     * @param other        Another position.
     * @param speedInPixel Speed in pixels per step.
     */
    public void moveToPosition(Position other, double speedInPixel) {
        double distance = distance(other);
        if (distance <= speedInPixel) {
            this.x = other.x;
            this.y = other.y;

        } else if (distance > 0) {
            double stepX = (other.x - x) / distance * speedInPixel;
            double stepY = (other.y - y) / distance * speedInPixel;
            x += stepX;
            y += stepY;
        }
    }

    /**
     * Checks if this position is similar to the other position.
     *
     * @param other Another position.
     * @return True if this position has the same x- and y-coordinates as the other position,
     * when both are rounded to int
     */
    public boolean similarTo(Position other) {
        return Math.round(x) == Math.round(other.x) && Math.round(y) == Math.round(other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Double.compare(x, position.x) == 0
                && Double.compare(y, position.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);

    }

}