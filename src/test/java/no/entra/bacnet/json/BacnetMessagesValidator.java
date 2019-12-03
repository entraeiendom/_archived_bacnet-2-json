package no.entra.bacnet.json;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Run manual verification and learning from data you place in "testdata" folder.
 */
public class BacnetMessagesValidator {
    private static final Logger log = getLogger(BacnetMessagesValidator.class);

    final File hexStringFile;
    private final BacNetParser bacnetParser;

    public BacnetMessagesValidator(File hexStringFile) {
        if (hexStringFile == null || !hexStringFile.isFile()) {
            throw new IllegalArgumentException("File is not readable: " + hexStringFile);
        }
        this.hexStringFile = hexStringFile;
        bacnetParser = new BacNetParser();
    }

    void validateTestdata() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(hexStringFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
//                String apduHexString = bacnetParser.findApduHexString(line);
//                String json = bacnetParser.jsonFromApdu(apduHexString);
                try {
                    Observation observation = bacnetParser.buildObservation(line);
                    log.debug("Observation: {}", observation);
                } catch (Exception e) {
                    log.debug("Failed to build observation from: {}. Reason: {}", line, e.getMessage());
                }
//                log.info("Hextring: {} \n{}", line, json);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File testDataFile = new File("testdata/recordedHexString");
        BacnetMessagesValidator validator = new BacnetMessagesValidator(testDataFile);
        validator.validateTestdata();

    }
}
