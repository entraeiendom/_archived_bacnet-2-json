package no.entra.bacnet.json.observation;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.ObservationList;
import no.entra.bacnet.json.Source;
import no.entra.bacnet.json.apdu.PDTag;
import no.entra.bacnet.json.apdu.SDContextTag;
import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.ObjectIdMapper;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.values.Value;
import no.entra.bacnet.json.values.ValueParser;
import no.entra.bacnet.json.values.ValueParserResult;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.entra.bacnet.json.utils.HexUtils.toFloat;
import static no.entra.bacnet.json.utils.HexUtils.toInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ObservationParser {
    private static final Logger log = getLogger(ObservationParser.class);

    /**
     * Transform a BacNet message notification describing values updated. Each message may contain a single update,
     * or multiple updates from multiple sensors.
     *
     * @param service                detect if the notification is single, multiple, confirmed or unconfirmed.
     * @param changeOfValueHexString BacNet hex string where the BVLC and NPDU part is previously parsed, and removed.
     * @return list of individual observations seen in this COV Notification.
     */
    public static ObservationList parseChangeOfValueNotification(Service service, String changeOfValueHexString) {

        /*
        Confirmed COV                       060109121c020003e92c008000013a012b4e09552e44000000002f096f2e8204002f4f
        Confirmed COV Multiple Parameters   0f0109121c020200252c0000000039004e095519012e4441a4cccd2f4f
        1. Subscriber Process Identifier (SD Context Tag 0, Length 1)
        2. Initating Device Identifier (SD Context Tag 1, Length 4)
        3. Monitored Object Idenfiter (SD Context Tag 2, Length 1-4)
        4. Time remaining (SD Context Tag 3, Length 1)
        5. List of values
         */
        List<Observation> observations = new ArrayList<>();
        Map<String, Object> properties = new HashMap<>();

        OctetReader covReader = new OctetReader(changeOfValueHexString);
        if (service.getPduType() == PduType.ConfirmedRequest) {
            Octet invokeId = covReader.next(); //06
            Octet serviceChoice = covReader.next();//01
        }
        //Need to take these from the reader.
        Octet contextTag0 = covReader.next(); //09
        Octet subscriberProcess = covReader.next(); //12
        //Parse the actual message.
        SDContextTag contextTag1 = new SDContextTag(covReader.next()); //1c
        Octet[] initiatingDeviceIdValue = covReader.nextOctets(contextTag1.findLength()); //020003e9
        ObjectId deviceId = ObjectIdMapper.decode4Octets(initiatingDeviceIdValue);
        SDContextTag contextTag2 = new SDContextTag(covReader.next()); //2c
        Octet[] monitoredDeviceIdValue = covReader.nextOctets(contextTag2.findLength()); //00800001
        ObjectId monitoredObjectId = ObjectIdMapper.decode4Octets(monitoredDeviceIdValue);
        SDContextTag contextTag3 = new SDContextTag(covReader.next());
        int numTimeRemainingOctets = contextTag3.findLength();
        Octet[] timeRemainingOctets = covReader.nextOctets(numTimeRemainingOctets);
        int timeRemainingSec = toInt(timeRemainingOctets);

        String objectId = monitoredObjectId.getObjectType() + "_" + monitoredObjectId.getInstanceNumber();
        Source source = new Source(deviceId.getInstanceNumber(), objectId);

        String resultListHexString = covReader.unprocessedHexString();
        List<Value> bacnetValues = parseListOfValues(resultListHexString);
        for (Value bacnetValue : bacnetValues) {
            String observationId = null; //TODO how to create unique id for the observation. Is that needed?
            Object value = bacnetValue.getValue();
            String name = bacnetValue.getPropertyIdentifier().name();
            Observation observation = new Observation(observationId, source, value, name);
            observations.add(observation);
        }

        ObservationList observationList = new ObservationList(observations);
        observationList.setSubscriptionRemainingSeconds(timeRemainingSec);
        return observationList;
    }


    protected static List<Value> parseListOfValues(String resultListHexString) {
        ArrayList<Value> values = new ArrayList<>();
        OctetReader listReader = new OctetReader(resultListHexString);
        Octet startList = listReader.next();
        if (startList.equals(PDTag.PDOpen4)) {
            String unprocessedHexString = listReader.unprocessedHexString();
            ValueParserResult valueResult = ValueParser.parseValue(unprocessedHexString);
            if (valueResult != null && valueResult.getValue() != null) {
                values.add(valueResult.getValue());
                unprocessedHexString = valueResult.getUnparsedHexString();
            }
            while (unprocessedHexString != null && !unprocessedHexString.startsWith(PDTag.PDClose4.toString())) {
                valueResult = ValueParser.parseValue(unprocessedHexString);
                if (valueResult != null && valueResult.getValue() != null) {
                    values.add(valueResult.getValue());
                    unprocessedHexString = valueResult.getUnparsedHexString();
                } else {
                    unprocessedHexString = null;
                }
            }
        }
        return values;
    }

    protected static Map<String, Object> findProperties(OctetReader covReader, String resultListHexString) {
        Map<String, Object> properties = new HashMap<>();
        try {
            Octet startList = covReader.next();
            while (resultListHexString != null && resultListHexString.length() >= 2) {
                Octet contextTagKey = covReader.next();
                PropertyIdentifier propertyId = null;
                if (contextTagKey != null && contextTagKey.equals(new Octet("09"))) {
                    Octet contextTagValue = covReader.next();
                    //PresentValue
                    propertyId = PropertyIdentifier.fromPropertyIdentifierHex(contextTagValue.toString());
                }
                //Property Array Index
                //1901, 19 = array, 01 = length

                if (propertyId != null) {
                    Octet valueTypeOctet = covReader.next(); //19
                    int arraySize = -1;
                    if (valueTypeOctet.equals(new Octet("19"))) {
                        Octet sizeOctet = covReader.next();
                        arraySize = toInt(sizeOctet);
                    }
                    if (arraySize > -1) {
                        //exptect array
                        //array start = 2e, end i 2f
                        String arrayContent = findArrayContent(covReader);
                        OctetReader arrayReader = new OctetReader(arrayContent);
                        Octet propertyIdKey = arrayReader.next();
                        if (propertyIdKey.toString().equals("44")) {
                            char lengthChar = propertyIdKey.getSecondNibble();
                            int length = toInt(lengthChar);
                            String realValueString = arrayReader.next(length);
                            Float realValue = toFloat(realValueString);
                            properties.put(propertyId.name(), realValue);
                        }
                    }

                }
                resultListHexString = covReader.unprocessedHexString();
            }
        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }
        return properties;
    }

    static String findArrayContent(OctetReader fullReader) {
        String arrayContent = "";
        Octet foundOctet = null;
        do {
            foundOctet = fullReader.next();
            if (foundOctet.equals(PDTag.PDClose2)) {
                break;
            }
            if (!foundOctet.equals(PDTag.PDOpen2)) {
                arrayContent += foundOctet.toString();
            }

        } while (fullReader.hasNext());
        return arrayContent;
    }
}
