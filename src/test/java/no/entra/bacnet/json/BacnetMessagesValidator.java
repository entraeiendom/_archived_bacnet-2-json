package no.entra.bacnet.json;

import org.slf4j.Logger;

import java.io.File;
import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Run manual verification and learning from data you place in "testdata" folder.
 */
public class BacnetMessagesValidator {
    private static final Logger log = getLogger(BacnetMessagesValidator.class);

    final File hexStringFile;

    public BacnetMessagesValidator(File hexStringFile) {
        if (hexStringFile == null || !hexStringFile.isFile()) {
            throw new IllegalArgumentException("File is not readable: " + hexStringFile);
        }
        this.hexStringFile = hexStringFile;
    }

    void validateTestdata() {
        Scanner scanner = new Scanner(hexStringFile.getAbsolutePath());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // process the line
        }
    }

    public static void main(String[] args) {
        File testDataFile = new File("testdata/recordedHexString");
        BacnetMessagesValidator validator = new BacnetMessagesValidator(testDataFile);
        validator.validateTestdata();

    }
}
