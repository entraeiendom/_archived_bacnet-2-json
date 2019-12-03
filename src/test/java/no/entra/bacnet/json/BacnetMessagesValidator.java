package no.entra.bacnet.json;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
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
            long validBvlc = 0;
            long validNpdu = 0;
            long validApdu = 0;
            long verifiedService = 0;
            long lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                try {
                    BvlcResult bvlcResult = BvlcParser.parse(line);
                    if (bvlcResult != null && bvlcResult.isParsedOk()) {
                        validBvlc++;
                        log.trace("Verified BVLC: {}", bvlcResult.getBvlc().getFunction());
                        String npduHexString = bvlcResult.getUnprocessedHexString();
                        NpduResult npduResult = NpduParser.parse(npduHexString);
                        if (npduResult != null && npduResult.isParsedOk()) {
                            validNpdu++;
                            String apduHexString = npduResult.getUnprocessedHexString();
                            Service service = ServiceParser.fromApduHexString(apduHexString);
                            if (service != null && service.getPduType().equals(PduType.ComplexAck)) {
                                try {
//FIXME need to change buildObservation to use BVLC and NPDU are removed.
//                                    observationHexString = ServiceResult.getUnprocessedHexString();
                                    Observation observation = bacnetParser.buildObservation(apduHexString);
                                    if (observation != null) {
                                        log.debug("Observation: {} \n\thexString: {}", observation, apduHexString);
                                        validApdu++;
                                    }
                                } catch (Exception e) {
                                    log.debug("Failed to build observation from: {}. Reason: {}", line, e.getMessage());
                                }
                            } else if (service != null){
                                log.trace("Verified Service: {}", service );
                                verifiedService ++;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.debug("Failed to parse line number: {}. Reason: {}", lineNum, e.getMessage());
                }
            }
            log.info("Verified BVLC: {}, Verified NPUD: {}, Verified Service: {}, Understood {} observations.",
                    validBvlc, validNpdu, verifiedService,validApdu);
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
