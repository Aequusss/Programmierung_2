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
        world =
                "                                                                                                                                            \n"
                        + "    H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H \n"
                        + "    h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h   h h \n"
                        + "    H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H   H H \n"
                        + "                                                                                                                                            \n"
                        + "                                                                                                                                            \n"
                        + "    T  T  R  R  G  G  BBB  BBB  T  T  R  R  G  G  BBB  BBB  T  T  R  R  G  G  BBB  BBB  T  T  R  R  G  G  BBB  BBB  T  T  R  R  G  G  BBB  BBB \n";



        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}