package no.entra.bacnet.json;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceChoice;
import no.entra.bacnet.json.services.ServiceParser;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static no.entra.bacnet.json.services.ConfirmedService.tryToUnderstandConfirmedRequest;
import static no.entra.bacnet.json.services.UnconfirmedService.tryToUnderstandUnconfirmedRequest;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Run manual verification and learning from data you place in "testdata" folder.
 */
public class BacnetMessagesValidator {
    private static final Logger log = getLogger(BacnetMessagesValidator.class);

    final File hexStringFile;
    private final BacNetParser bacnetParser;
    private Map<PduType, Map<ServiceChoice, Integer>> unknowns = new HashMap<>();

    public BacnetMessagesValidator(File hexStringFile) {
        if (hexStringFile == null || !hexStringFile.isFile()) {
            throw new IllegalArgumentException("File is not readable: " + hexStringFile);
        }
        this.hexStringFile = hexStringFile;
        log.info("Validating {}", hexStringFile);
        bacnetParser = new BacNetParser();
    }

    void validateTestdata() {
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
                                        } else {
                                            addUnknown(service.getPduType(), service.getServiceChoice());
                                            log.trace("I did not understand PDU: {} from service: {}. hex: {}", pduType, service, apduHexString);
                                        }
                                        break;
                                    case UnconfirmedRequest:
                                        Object request = tryToUnderstandUnconfirmedRequest(service);
                                        log.trace("UnconfirmedRequest: {}", request);
                                        if (request != null) {
                                            validApdu++;
                                        } else {
                                            addUnknown(service.getPduType(), service.getServiceChoice());
                                            log.trace("I did not understand PDU: {} from service: {}. hex: {}", pduType, service, service.getUnprocessedHexString());
                                        }
                                        break;
                                    case ConfirmedRequest:
                                        Object confirmedRequest = tryToUnderstandConfirmedRequest(service);
                                        log.trace("ConfirmedRequest: {}", confirmedRequest);
                                        if (confirmedRequest != null) {
                                            validApdu++;
                                        } else {
                                            addUnknown(service.getPduType(), service.getServiceChoice());
                                            log.trace("I did not understand PDU: {} from service: {}. hex: {}", pduType, service, service.getUnprocessedHexString());
                                        }
                                        break;
                                    case SegmentACK:
                                        validApdu++;
                                        break;
                                    default:
                                        log.debug("Do not know how to handle PduType: {}. ApduHexString: {}", pduType, apduHexString);

                                }
                            } else {
                                log.trace("Could not detect Service from: {} ", apduHexString);
                            }
                        } else {
                            log.trace("Could not detect NPDU from: {}", npduHexString);
                            unknownNpdu++;
                        }
                    } else {
                        log.trace("Could not detect BVLC from: {}", line);
                        unknownBvlc++;
                    }
                } catch (Exception e) {
                    log.debug("Failed to parse line number: {}. Reason: {} from: {}", lineNum, e.getMessage(), line);
                }
            }
            log.info("Verified BVLC: {}, Unknown BVLC: {}, Verified NPDU: {}, Unknown NPDU: {}, Verified Service: {}, Understood {} APDU's. Unknown APDU's {}",
                    validBvlc, unknownBvlc, validNpdu, unknownNpdu, verifiedService, validApdu, unknowns);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void addUnknown(PduType pduType, ServiceChoice serviceChoice) {
        if (pduType != null) {
            Map<ServiceChoice, Integer> unknown = unknowns.get(pduType);
            if (unknown == null) {
                unknown = new HashMap<>();
            }
            Integer unknownCount = unknown.get(serviceChoice);
            if(unknownCount == null) {
                unknownCount = Integer.valueOf(1);
            } else {
                unknownCount++;
            }
            unknown.put(serviceChoice, unknownCount);
            unknowns.put(pduType, unknown);
        }
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
