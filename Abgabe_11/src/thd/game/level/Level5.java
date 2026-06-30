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
        world = "                                                                                                                                \n"
                + "  h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h   H   h  \n"
                + "                                                                                                                                \n"
                + "                                                                                                                                \n"
                + " E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e E e \n"
                + "  B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B   B     ";



        worldOffsetColumns = 0;
        worldOffsetLines = 0;
    }
}