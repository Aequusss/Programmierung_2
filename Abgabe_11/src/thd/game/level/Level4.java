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
        backgroundScale = 1.75;
        world = "                                                                                                                            \n"
                + "    h       H       h       H       h       H       h       H       h       H       h       H       h       H       h       \n"
                + "                                                                                                                            \n"
                + "   B  B     B  B     B  B     B  B     B  B     B  B     B  B     B  B     B  B     B  B     B  B     B  B     B  B        \n"
                + " E  e  E  e E  e  E  e E  e  E  e E  e  E  e E  e  E  e E  e  E  e E  e  E  e E  e  E  e E  e  E  e E  e  E  e ";


        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}