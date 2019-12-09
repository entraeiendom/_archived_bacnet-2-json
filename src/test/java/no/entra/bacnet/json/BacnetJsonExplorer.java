package no.entra.bacnet.json;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.ServiceChoice;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

public class BacnetJsonExplorer {
    private static final org.slf4j.Logger log = getLogger(BacnetJsonExplorer.class);
    final File hexStringFile;
    private Map<PduType, Map<ServiceChoice, Integer>> unknowns = new HashMap<>();

    public BacnetJsonExplorer(File hexStringFile) {
        if (hexStringFile == null || !hexStringFile.isFile()) {
            throw new IllegalArgumentException("File is not readable: " + hexStringFile);
        }
        this.hexStringFile = hexStringFile;
        log.info("Building Json from file:  {}", hexStringFile);
    }

    void explore() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(hexStringFile);
            long unknownBvlc = 0;
            long validBvlc = 0;
            long unknownNpdu = 0;
            long validNpdu = 0;
            long validApdu = 0;
            long verifiedService = 0;
            long lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                try {
                    String json = Bacnet2Json.hexStringToJson(line);

                    log.info(json);
                } catch (Exception e) {
                    log.error("....");
                }
            }
        } catch (Exception e) {
            log.error("....");
        }
    }

    public static void main(String[] args) {
        Logger entra = (Logger) getLogger("no.entra");
        entra.setLevel(Level.INFO);
        File testDataFile = new File("testdata/recordedHexString");
        BacnetJsonExplorer explorer = new BacnetJsonExplorer(testDataFile);
        explorer.explore();

    }
}
