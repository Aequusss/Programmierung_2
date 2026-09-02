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
        backgroundScale = 1;
        world =
                "                                                                                                                                            \n"
                        + "    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH\n"
                        + "    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh    hhhh\n"
                        + "    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH    HHHH\n"
                        + "                                                                                                                                            \n"
                        + "                                                                                                                                            \n"
                        + "    T R T R BBB BBB G R G T R T R BBB BBB G R G T R T R BBB BBB G R G T R T R BBB BBB G R G T R T R BBB BBB G R G T R T R BBB BBB G R G T R \n";

        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}