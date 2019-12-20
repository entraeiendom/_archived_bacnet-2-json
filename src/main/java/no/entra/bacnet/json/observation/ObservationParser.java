package no.entra.bacnet.json.observation;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.Source;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.objects.ReadAccessResult;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import static no.entra.bacnet.json.objects.ReadAccessResult.PD_CLOSING_TAG_4;
import static no.entra.bacnet.json.objects.ReadAccessResult.PD_OPENING_TAG_4;
import static no.entra.bacnet.json.utils.HexUtils.toInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ObservationParser {
    private static final Logger log = getLogger(ObservationParser.class);

    public static Observation buildChangeOfValueObservation(String changeOfValueHexString) {

        /*
        1. Subscriber Process Identifier (SD Context Tag 0, Length 1)
        2. Initating Device Identifier (SD Context Tag 1, Length 4)
        3. Monitored Object Idenfiter (SD Context Tag 2, Length 4)
        4. Time remaining (SD Context Tag 3, Length 1)
        5. List of values
         */

        OctetReader covReader = new OctetReader(changeOfValueHexString);
        Octet subscriberProcessKey = covReader.next();
        Octet subscriverProcess = covReader.next();
        String devicdId = "TODO"; //#4 FIXME find deviceId from hexString.
        Octet intiatingDeviceidKey = covReader.next();
        Octet[] initiatingDeviceIdValue = covReader.nextOctets(4);
        Octet monitoredDeviceIdKey = covReader.next();
        Octet[] monitoredDeviceIdValue = covReader.nextOctets(4);
        Octet timeRemainingKey = covReader.next();
        Octet timeRemainingValue = covReader.next();

        Observation observation = null;

        String resultListHexString = covReader.unprocessedHexString();
        resultListHexString = filterResultList(resultListHexString);
        try {
            Octet startList = covReader.next();
            Octet contextTagKey = covReader.next();
            PropertyIdentifier propertyId = null;
            if (contextTagKey.equals(new Octet("09"))) {
                Octet contextTagValue = covReader.next();
                propertyId = PropertyIdentifier.fromPropertyIdentifierHex(contextTagValue.toString());
            }
            if (propertyId != null) {
                Octet valueTagKey = covReader.next();
                Octet propertyIdKey = covReader.next();
                char lengthChar = propertyIdKey.getSecondNibble();
                int length = toInt(lengthChar);
                String xxx = covReader.next(length);
                log.trace("dddd");
                Octet valueTagEndKey = covReader.next();

            }
            ReadAccessResult accessResult = ReadAccessResult.buildFromResultList(resultListHexString);
            if (accessResult != null) {
                log.info("ReadAccessResult: {}", accessResult);

                String objectId = null;
                if (accessResult.getObjectId() != null) {
                    objectId = accessResult.getObjectId().toString();
                    Source source = new Source(devicdId, objectId);
                    Object presentValue = accessResult.getResultByKey(PropertyIdentifier.PresentValue);
                    observation = new Observation(source, presentValue);
                    observation.setName((String)accessResult.getResultByKey(PropertyIdentifier.ObjectName));
                    observation.setDescription((String) accessResult.getResultByKey(PropertyIdentifier.Description));
                    observation.setUnit((String) accessResult.getResultByKey(PropertyIdentifier.Units));
                }
            }
        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }

        return observation;
    }

    static String filterResultList(String hexString) {
        int listStartPos = hexString.indexOf(PD_OPENING_TAG_4);
        int listEndPos = hexString.indexOf(PD_CLOSING_TAG_4);
        String listResulHexString = null;
        if (listStartPos >= 0 && listEndPos > 0) {
            listResulHexString = hexString.substring(listStartPos, listEndPos + PD_CLOSING_TAG_4.length());
        }
        return listResulHexString;
    }
}
