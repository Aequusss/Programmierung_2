package thd.game.bin;

import thd.game.utilities.GameView;
import thd.gameobjects.base.Position;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SortedPositions {
    private static final int NUMBER_OF_POSITIONS = 5;
    private final Position[] positions;

    public static void main(String[] args) {
        SortedPositions sortedPositions = new SortedPositions();
        sortedPositions.sortAndPrintPositions();
    }

    /**
     * Das Array mit Positionen wird in drei verschiedenen Sortierungen ausgegeben.
     */
    private void sortAndPrintPositions() {
        Arrays.sort(positions);
        System.out.println(Arrays.deepToString(positions));

        Arrays.sort(positions, new CompareByXCoordinate());
        System.out.println(Arrays.deepToString(positions));


        Arrays.sort(positions, (p1, p2) -> Double.compare(p1.getY(), p2.getY()));
        System.out.println(Arrays.deepToString(positions));
    }

    /**
     * Compares position by their x-coordinate.
     */
    static class CompareByXCoordinate implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2) {
            return Double.compare(p1.getX(), p2.getX());
        }
    }

    public SortedPositions() {
        this.positions = new Position[NUMBER_OF_POSITIONS];
        createPositions();
    }

    private void createPositions() {
        Random random = new Random(0);
        for (int i = 0; i < NUMBER_OF_POSITIONS; i++) {
            positions[i] = new Position(random.nextInt(GameView.WIDTH), random.nextInt(GameView.WIDTH));
        }
    }
}