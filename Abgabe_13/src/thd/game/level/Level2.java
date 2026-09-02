package thd.game.level;

/**
 * Second level with adjusted world layout.
 */
public class Level2 extends Level {
    /**
     * Creates level 2.
     */
    public Level2() {
        name = "Wave 2";
        number = 2;
        backgroundImage = "background_2.png";
        backgroundScale = 1;
        world =
                "                                                                                                                                            \n"
                        + "    H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H   \n"
                        + "      h   h       h   h       h   h       h   h       h   h       h   h       h   h       h   h       h   h       h   h       h   h       h   h \n"
                        + "    H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H       H   H   \n"
                        + "                                                                                                                                            \n"
                        + "                                                                                                                                            \n"
                        + "    BBB   R   G   T   BBB   R   G   T   BBB   R   G   T   BBB   R   G   T   BBB   R   G   T   BBB   R   G   T   BBB   R   G   T   BBB   R   G   T \n";
        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}