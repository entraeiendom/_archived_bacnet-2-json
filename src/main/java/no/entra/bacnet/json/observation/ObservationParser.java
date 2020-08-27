package no.entra.bacnet.json.observation;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.ObservationList;
import no.entra.bacnet.json.Source;
import no.entra.bacnet.json.objects.*;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.entra.bacnet.json.objects.ReadAccessResult.PD_CLOSING_TAG_4;
import static no.entra.bacnet.json.objects.ReadAccessResult.PD_OPENING_TAG_4;
import static no.entra.bacnet.json.utils.HexUtils.*;
import static org.slf4j.LoggerFactory.getLogger;

public class ObservationParser {
    private static final Logger log = getLogger(ObservationParser.class);

    public static ObservationList buildChangeOfValueObservation(String changeOfValueHexString) {

        /*
        1. Subscriber Process Identifier (SD Context Tag 0, Length 1)
        2. Initating Device Identifier (SD Context Tag 1, Length 4)
        3. Monitored Object Idenfiter (SD Context Tag 2, Length 4)
        4. Time remaining (SD Context Tag 3, Length 1)
        5. List of values
         */
        List<Observation> observations = new ArrayList<>();
        Map<String, Object> properties = new HashMap<>();

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

        String resultListHexString = covReader.unprocessedHexString();
        resultListHexString = filterResultList(resultListHexString);
        try {
            Octet startList = covReader.next();
            while (resultListHexString != null && resultListHexString.length() >= 2) {
                Octet contextTagKey = covReader.next();
                PropertyIdentifier propertyId = null;
                if (contextTagKey != null && contextTagKey.equals(new Octet("09"))) {
                    Octet contextTagValue = covReader.next();
                    propertyId = PropertyIdentifier.fromPropertyIdentifierHex(contextTagValue.toString());
                }
                if (propertyId != null) {
                    Octet valueTagKey = covReader.next();
                    Octet propertyIdKey = covReader.next();
                    char lengthChar = propertyIdKey.getSecondNibble();
                    int length = toInt(lengthChar);
                    String value = covReader.next(length);
                    Octet valueTagEndKey = covReader.next();
                    properties.put(propertyId.name(), value);
                }
                resultListHexString = covReader.unprocessedHexString();
                log.trace("unprocessed: {}", resultListHexString);
            }

        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }

        if (properties != null && properties.size() > 0) {
            for (String key : properties.keySet()) {
                Object value = properties.get(key);
                String observationId = null;
                Source source = new Source(devicdId, octetsToString(monitoredDeviceIdValue));
                Observation observation = new Observation(observationId, source, value, key);
                observation.setName(key);
                observations.add(observation);
            }
        }


        return new ObservationList(observations);
    }

    public static ObservationList parseConfirmedCOVNotification(String changeOfValueHexString) {

        /*
        1. Subscriber Process Identifier (SD Context Tag 0, Length 1)
        2. Initating Device Identifier (SD Context Tag 1, Length 4)
        3. Monitored Object Idenfiter (SD Context Tag 2, Length 4)
        4. Time remaining (SD Context Tag 3, Length 1)
        5. List of values
        Example hex: 0f0109121c020200252c0000000039004e095519012e4441a4cccd2f4f
         */
        List<Observation> observations = new ArrayList<>();
        Map<String, Object> properties = new HashMap<>();

        OctetReader covReader = new OctetReader(changeOfValueHexString);
        Octet invokeIdOctet = covReader.next(); //0f == 15
        Octet serviceChoice = covReader.next(); //05 == 05
        //ProcessIdentifier
        Integer subscriberProcessId = null;
        Octet subscriberProcessLength = covReader.next(); //09 == length 1
        if (subscriberProcessLength.toString().equals("09")) {
            Octet subscriberProcessIdOctet = covReader.next();
            subscriberProcessId = toInt(subscriberProcessIdOctet.toString());
        } else {
            //TODO find processId longer than one bit.
            throw new IllegalArgumentException("SubscriberProcessId not implemented for integer > 15");
        }
        //DeviceIdentifier 1c02020025
        Octet deviceIdLengthKey = covReader.next(); //1c
        ObjectId deviceId = null;
        if (deviceIdLengthKey.toString().equals("1c")) {
            String objectIdHex = "1c" + covReader.next(4);
            ObjectIdParserResult<ObjectId> result = ObjectIdParser.parse(objectIdHex);
            deviceId = result.getParsedObject();
        } else {
            //TODO find DeviceIdentifier
            throw  new IllegalArgumentException("ObjectIdentifier different key than 1c is not implemented yet.");
        }
        //ObjectIdentifier 1c00000000
        Octet objectIdLengthKey = covReader.next(); //2c
        ObjectId objectIdentifier = null;
        if (objectIdLengthKey.toString().equals("2c")) {
            //length is 4
            String objectIdHex = "2c" + covReader.next(4);
            ObjectIdParserResult<ObjectId> result = ObjectIdParser.parse(objectIdHex);
            objectIdentifier = result.getParsedObject();
        } else {
            //TODO find ObjcetIdentifier
            throw  new IllegalArgumentException("ObjectIdentifier different key than 2c is not implemented yet.");
        }

        //Time remaining
        int timeRemainingSec = 0;
        Octet contextTag = covReader.next();
        if (SDContextTag.fromOctet(contextTag) == SDContextTag.TimeStamp) {
            if (contextTag.getSecondNibble() == '9') {
                //read one octet
                Octet timeRemainingOctet = covReader.next();
                timeRemainingSec = toInt(timeRemainingOctet);
            }
        }

        //List of values
        //4e095519012e4441a4cccd2f4f

        String resultListHexString = covReader.unprocessedHexString();
        resultListHexString = filterResultList(resultListHexString);
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
                    /*
                    Octet valueTagKey = covReader.next();
                    Octet propertyIdKey = covReader.next();
                    char lengthChar = propertyIdKey.getSecondNibble();
                    int length = toInt(lengthChar);
                    String value = covReader.next(length);
                    Octet valueTagEndKey = covReader.next();
                     */

                }
                resultListHexString = covReader.unprocessedHexString();
//                log.trace("unprocessed: {}", resultListHexString);
            }

        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }

        if (properties != null && properties.size() > 0) {
            for (String key : properties.keySet()) {
                Object value = properties.get(key);
                String observationId = null;
                Source source = null;
                String sourceInstanceNumber = null;
                if (deviceId != null) {
                    sourceInstanceNumber = deviceId.getInstanceNumber();
                } else {
                    sourceInstanceNumber = "TODO";
                }
                String objectId = objectIdentifier.getObjectType() + "_" + objectIdentifier.getInstanceNumber();
                source = new Source(deviceId.getInstanceNumber(), objectId);
                Observation observation = new Observation(observationId, source, value, key);
                observation.setName(key);
                observations.add(observation);
            }
        }


        return new ObservationList(observations);
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

    static String findArrayContent(OctetReader fullReader) {
        String arrayContent = "";
        Octet foundOctet = null;
        do {
            foundOctet = fullReader.next();
            if (foundOctet.equals(new Octet("2f"))) {
                break;
            }
            if (!foundOctet.equals(new Octet("2e"))) {
                arrayContent += foundOctet.toString();
            }

        } while (fullReader.hasNext());
        return arrayContent;
    }
}
