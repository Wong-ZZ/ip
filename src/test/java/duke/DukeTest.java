package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import duke.exception.InvalidSaveFileException;

public class DukeTest {
    private static final Path TEST_FILE_PATH = Paths.get("test", "data", "duke.txt");
    private static final ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final InputStream SYSIN_BACKUP = System.in;

    // recursive function to delete directory that stores the save file
    private static void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    private static void clearPath() {
        Path topDir = TEST_FILE_PATH.subpath(0, 1);
        if (!java.nio.file.Files.exists(topDir)) {
            return;
        }
        File toDelete = new File(String.valueOf(topDir));
        deleteDirectory(toDelete);
    }

    @BeforeAll
    static void setUpStreams() throws IOException {
        clearPath();
        System.setOut(new PrintStream(OUT_CONTENT));
        System.out.println();
        BufferedReader reader = new BufferedReader(new FileReader(Paths.get("input.txt").toString()));
        String[] input = reader.lines().toArray(String[]::new);
        reader.close();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(String.join("\n", input).getBytes());
        System.setIn(inputStream);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(ORIGINAL_OUT);
        clearPath();
        System.setIn(SYSIN_BACKUP);
    }

    @Test
    public void testInputs() throws IOException, InvalidSaveFileException {
        Duke duke = new Duke(TEST_FILE_PATH);
        duke.run();

        File file = new File("EXPECTED.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String expectedOutput = new String(data, "UTF-8");

        String actualOutput = OUT_CONTENT.toString();

        FileWriter myWriter = new FileWriter("ACTUAL.txt");
        myWriter.write(actualOutput);
        myWriter.close();

        assertEquals(
                expectedOutput,
                actualOutput
        );
    }
}
