package no.entra.bacnet.json;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceChoice;
import no.entra.bacnet.json.services.ServiceParser;
import no.entra.bacnet.json.services.UnconfirmedServiceChoice;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static no.entra.bacnet.json.configuration.ConfigurationParser.*;
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
                            if (service != null) {
                                log.trace("Verified Service: {}", service);
                                verifiedService++;

                                PduType pduType = service.getPduType();
                                switch (pduType) {
                                    case ComplexAck:
                                        Observation observation = buildObservation(validApdu, line, apduHexString);
                                        log.trace("Observation built: ", observation);
                                        if (observation != null) {
                                            validApdu++;
                                        }
                                        break;
                                    case UnconfirmedRequest:
                                        Object request = tryToUnderstandUnconfirmedRequest(service);
                                        if (request != null) {
                                            validApdu++;
                                        }
                                        break;
                                    default:
                                        log.debug("Do not know how to handle PduType: {}. ApduHexString: {}", pduType, apduHexString);

                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.debug("Failed to parse line number: {}. Reason: {}", lineNum, e.getMessage());
                }
            }
            log.info("Verified BVLC: {}, Verified NPUD: {}, Verified Service: {}, Understood {} observations.",
                    validBvlc, validNpdu, verifiedService, validApdu);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ConfigurationRequest tryToUnderstandUnconfirmedRequest(Service service) {
        ConfigurationRequest configuration = null;
        if (service == null) {
            return null;
        }
        ServiceChoice serviceChoice = service.getServiceChoice();
        if (serviceChoice != null && serviceChoice instanceof UnconfirmedServiceChoice) {
            UnconfirmedServiceChoice unconfirmedServiceChoice = (UnconfirmedServiceChoice) serviceChoice;
            switch (unconfirmedServiceChoice) {
                case WhoIs:
                    log.trace("Is WhoIsMessage. hexString: {}", service.getUnprocessedHexString());
                    String whoIsBody = service.getUnprocessedHexString();
                    configuration = buildWhoIsRequest(whoIsBody);
                    break;
                case WhoHas:
                    log.trace("Is WhoHasMessage");
                    configuration = buildWhoHasRequest(service.getUnprocessedHexString());
                    break;
                case TimeSynchronization:
                    String timeSyncHexString = service.getUnprocessedHexString();
                    configuration = buildTimeSynchronizationRequest(timeSyncHexString);
                    break;
                default:
                    log.trace("I do not know how to parse this service: {}", service);
            }
        }
        return configuration;
    }

    Observation buildObservation(long validApdu, String line, String apduHexString) {
        Observation observation = null;
        try {
//FIXME need to change buildObservation to use BVLC and NPDU are removed.
//                                    observationHexString = service.getUnprocessedHexString();
            observation = bacnetParser.buildObservation(apduHexString);
            if (observation != null) {
                log.debug("Observation: {} \n\thexString: {}", observation, apduHexString);
                validApdu++;
            }
        } catch (Exception e) {
            log.debug("Failed to build observation from: {}. Reason: {}", line, e.getMessage());
        }
        return observation;
    }

    public static void main(String[] args) {
        File testDataFile = new File("testdata/recordedHexString");
        BacnetMessagesValidator validator = new BacnetMessagesValidator(testDataFile);
        validator.validateTestdata();

    }
}
