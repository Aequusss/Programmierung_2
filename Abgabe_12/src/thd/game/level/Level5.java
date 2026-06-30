package thd.game.level;

/**
 * Third level with a different enemy arrangement.
 */
public class Level5 extends Level {
    /**
     * Creates level 3.
     */
    public Level5() {
        name = "Wave 5";
        number = 5;
        backgroundImage = "background_5.png";
        backgroundScale = 3.9;
        world = "                                                                                                    \n"
                + "  h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   \n"
                + "    S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S   S \n"
                + "                                                                                                    \n"
                + " T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G T G \n"
                + "  R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   R   ";


        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}