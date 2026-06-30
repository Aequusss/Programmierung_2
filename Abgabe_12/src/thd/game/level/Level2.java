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
        world = "                                                                                                    \n"
                + "        H                 h                 H                 h                 H                 h \n"
                + "     S      S         S      S         S      S         S      S         S      S         S      S  \n"
                + "  E    G   B   R    E    G   B   R    E    G   B   R    T    G   B   R    E    G   B   R    E    G  \n"
                + "                                                                                                    ";


        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}