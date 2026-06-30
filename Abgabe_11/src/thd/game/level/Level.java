package thd.game.level;

/**
 * Stores the data of a level.
 */
public class Level {

    /**
     * The global difficulty setting for the game.
     */
    public static Difficulty difficulty = Difficulty.STANDARD;

    public String name;

    public int number;

    public String world;

    public int worldOffsetColumns;

    public int worldOffsetLines;

    public String backgroundImage;

    public double backgroundScale;

}