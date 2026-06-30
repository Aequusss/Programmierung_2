package thd.game.level;

/**
 * Third level with a different enemy arrangement.
 */
public class Level3 extends Level {
    /**
     * Creates level 3.
     */
    public Level3() {
        name = "Level 3";
        number = 3;
        backgroundImage = "background_3.jpg";
        backgroundScale = 0.667;
        world =
                "                    \n"
                        + "   H       H        \n"
                        + "       H            \n"
                        + "   H       H        \n"
                        + "                    \n"
                        + "   e       e        \n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n"
                        + "                    \n"
                        + "         B          \n"
                        + "                    \n"
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