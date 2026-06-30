package thd.game.level;

/**
 * The first level of the game.
 */
public class Level1 extends Level {
    /**
     * Creates level 1 with its world layout.
     */
    public Level1() {
        name = "Level 1";
        number = 1;
        world =
                "                    \n"
                        + "                    \n"
                        + "       h            \n"
                        + "                    \n"
                        + "         B          \n"
                        + "                    \n"
                        + "   e       E        \n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n"
                        + "                 A L\n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n";
        worldOffsetColumns = 0;
        worldOffsetLines = 4;

    }
}