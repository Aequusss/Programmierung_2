package thd.game.utilities;

import thd.game.level.Difficulty;
import thd.game.level.Level;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Handles reading and writing of the savegame file to the user's home directory.
 */
public class FileAccess {

    private static final Path SAVEGAME_DIRECTORY = Path.of(System.getProperty("user.home")).resolve("savegame");

    private static final String SAVEGAME_FILENAME = "alexander_letutschi_savegame.txt";

    private static final Path SAVEGAME_FILE = SAVEGAME_DIRECTORY.resolve(SAVEGAME_FILENAME);

    /**
     * Writes the current difficulty to the savegame file on the hard drive.
     */
    public static void writeSaveGameToDisc() {
        try {
            if (!Files.exists(SAVEGAME_DIRECTORY)) {
                Files.createDirectories(SAVEGAME_DIRECTORY);
            }
            Files.writeString(SAVEGAME_FILE, Level.difficulty.name());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    /**
     * Reads the saved difficulty from the hard drive.
     * Defaults to STANDARD if the file is missing or contains invalid data.
     */
    public static void readSaveGameFromDisc() {
        try {
            String content = Files.readString(SAVEGAME_FILE).trim();


            if ("EASY".equals(content)) {
                Level.difficulty = Difficulty.EASY;
            } else {
                Level.difficulty = Difficulty.STANDARD;
            }
        } catch (IOException e) {

            Level.difficulty = Difficulty.STANDARD;
        }

    }
}
