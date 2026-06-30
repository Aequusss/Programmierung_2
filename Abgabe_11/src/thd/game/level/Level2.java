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
        world = "                                                                                                                    \n"
                + "        H                 h                 H                 h                 H                 h                 \n"
                + "                                                                                                                    \n"
                + "  E    e   B   B    E    e   B   B    E    e   B   B    E    e   B   B    E    e   B   B    E    e   B   B    ";


        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}