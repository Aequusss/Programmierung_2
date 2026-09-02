package thd.game.level;


import java.util.Arrays;

/**
 * Represents the global difficulty settings of the game.
 */
public enum Difficulty {
    EASY("Einfach"),
    STANDARD("Standard");


    public final String name;

    Difficulty(String name) {
        this.name = name;
    }

    /**
     * Maps the user's UI selection ("Einfach"/"Standard") back to the Enum using the Stream API.
     *
     * @param name The user's selection from the StartScreen.
     * @return The matching Difficulty, or STANDARD as a fallback.
     */
    public static Difficulty fromName(String name) {
        return Arrays.stream(values())
                .filter(d -> d.name.equals(name))
                .findFirst()
                .orElse(EASY);
    }
}
