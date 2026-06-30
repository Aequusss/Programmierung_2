package thd.game.level;

/**
 * Second level with adjusted world layout.
 */
public class Level2 extends Level {
    /**
     * Creates level 2.
     */
    public Level2() {
        name = "Level 2";
        number = 2;
        backgroundImage = "background_2.jpg";
        backgroundScale = 0.735;
        world =
                "                    \n"
                        + "     B              \n"
                        + "       H            \n"
                        + "   h       h        \n"
                        + "                    \n"
                        + "   e   E   e        \n"
                        + "       E            \n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n"
                        + "                 A L\n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n";

        if (Level.difficulty == Difficulty.EASY) {
            world = world.replace("H", " ");
        }

        worldOffsetColumns = 0;
        worldOffsetLines = 4;
    }
}