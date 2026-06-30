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
        backgroundScale = 2.0;
        world = "                                                                                                    \n"
                + "                                                                                                    \n"
                + "                                                                                                    \n"
                + "   E           E       B      e           E       B      e           E       B      e           E       B       ";



        worldOffsetColumns = 0;
        worldOffsetLines = 0;

    }
}