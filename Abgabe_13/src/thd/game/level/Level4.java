package thd.game.level;

/**
 * Third level with a different enemy arrangement.
 */
public class Level4 extends Level {
    /**
     * Creates level 3.
     */
    public Level4() {
        name = "Wave 4";
        number = 4;
        backgroundImage = "background_4.png";
        backgroundScale = 1;
        world =
                "                                                                                                                                            \n"
                        + "    HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH \n"
                        + "    hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh     hhh \n"
                        + "    HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH     HHH \n"
                        + "                                                                                                                                            \n"
                        + "                                                                                                                                            \n"
                        + "    GGG   TTT   BBB   RRR   GGG   TTT   BBB   RRR   GGG   TTT   BBB   RRR   GGG   TTT   BBB   RRR   GGG   TTT   BBB   RRR   GGG   TTT   BBB   RRR \n";


        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}