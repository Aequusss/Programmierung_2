package thd.game.level;

/**
 * The first level of the game.
 */
public class Level1 extends Level {
    /**
     * Creates level 1 with its world layout.
     */
    public Level1() {
        name = "Wave 1";
        number = 1;
        backgroundImage = "background_1.png";
        backgroundScale = 1;
        world =
                "                                                                                                                                            \n"
                        + "    H       H       H       H       H       H       H       H       H       H       H       H       H       H       H       H       H       \n"
                        + "        h       h       h       h       h       h       h       h       h       h       h       h       h       h       h       h       h   \n"
                        + "    h       h       h       h       h       h       h       h       h       h       h       h       h       h       h       h       h       \n"
                        + "                                                                                                                                            \n"
                        + "                                                                                                                                            \n"
                        + "    T   R   G   T   G   BBB   G   R   G   BBB   G   T   G   BBB   G   R   G   BBB   G   T   G   BBB   G   R   G   BBB   G   T   G   BBB   T   G \n";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;

    }
}