package thd.game.level;

/**
 * Third level with a different enemy arrangement.
 */
public class Level3 extends Level {
    /**
     * Creates level 3.
     */
    public Level3() {
        name = "Wave 3";
        number = 3;
        backgroundImage = "background_3.png";
        backgroundScale = 1;
        world = "                                                                                                    \n"
                + "      H       h       H       h       H       h       H       h       H       h       H       h     \n"
                + "                                                                                                    \n"
                + "         S       B       R       S       B       R       S       B       R       S       B       R  \n"
                + "   T   G     E   G     T   G     E   G     T   G     E   G     T   G     E   G     T   G     E   G  ";



        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}