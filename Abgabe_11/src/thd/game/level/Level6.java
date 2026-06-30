package thd.game.level;

/**
 * Third level with a different enemy arrangement.
 */
public class Level6 extends Level {
    /**
     * Creates level 6.
     */
    public Level6() {
        name = "Wave 6";
        number = 6;
        backgroundImage = "background_6.png";
        backgroundScale = 1;
        world = "                                                                                                                                    \n"
                + " H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H h H \n"
                + "                                                                                                                                    \n"
                + "                                                                                                                                    \n"
                + "                                                                                                                                    \n"
                + "E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e \n"
                + " B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B B ";



        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}