package no.entra.bacnet.json.observation;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.ObservationList;
import no.entra.bacnet.json.Source;
import no.entra.bacnet.json.apdu.PDTag;
import no.entra.bacnet.json.apdu.SDContextTag;
import no.entra.bacnet.json.objects.*;
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

import static no.entra.bacnet.json.objects.ReadAccessResult.PD_CLOSING_TAG_4;
import static no.entra.bacnet.json.objects.ReadAccessResult.PD_OPENING_TAG_4;
import static no.entra.bacnet.json.utils.HexUtils.toFloat;
import static no.entra.bacnet.json.utils.HexUtils.toInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ObservationParser {
    private static final Logger log = getLogger(ObservationParser.class);

    public static ObservationList mapToChangeOfValueObservation(Service service, String changeOfValueHexString) {

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
        Octet contextTag0 = covReader.next(); //09
        Octet subscriberProcess = covReader.next(); //12
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

//        String resultListHexString = covReader.unprocessedHexString();
//        resultListHexString = filterResultList(resultListHexString);
//        log.info("*** {}", resultListHexString);
//        properties = findProperties(covReader, resultListHexString);

        String objectId = monitoredObjectId.getObjectType() + "_" + monitoredObjectId.getInstanceNumber();
        Source source = new Source(deviceId.getInstanceNumber(), objectId);

        String resultListHexString = covReader.unprocessedHexString();
//        resultListHexString = filterResultList(resultListHexString);
        List<Value> bacnetValues = parseListOfValues(resultListHexString);
        for (Value bacnetValue : bacnetValues) {
            String observationId = null; //TODO how to create unique id for the observation. Is that needed?
            Object value = bacnetValue.getValue();
            String name = bacnetValue.getPropertyIdentifier().name();
            Observation observation = new Observation(observationId, source, value, name);
            observations.add(observation);
        }
        /*
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

         */

        /*
        if (properties != null && properties.size() > 0) {
            for (String key : properties.keySet()) {
                Object value = properties.get(key);
                String observationId = null;
                String deviceIdInstance = null;
                if (deviceId != null) {
                    deviceIdInstance = deviceId.getInstanceNumber();
                }
                String sensorId = null;
                if (monitoredObjectId != null) {
                    sensorId = monitoredObjectId.toString();
                }
                Source source = new Source(deviceIdInstance, sensorId);
                Observation observation = new Observation(observationId, source, value, key);
                observation.setName(key);
                observations.add(observation);
            }
        }

         */


        ObservationList observationList = new ObservationList(observations);
        observationList.setSubscriptionRemainingSeconds(timeRemainingSec);
        return observationList;
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
            ObjectIdMapperResult<ObjectId> result = ObjectIdMapper.parse(objectIdHex);
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
            ObjectIdMapperResult<ObjectId> result = ObjectIdMapper.parse(objectIdHex);
            objectIdentifier = result.getParsedObject();
        } else {
            //TODO find ObjcetIdentifier
            throw  new IllegalArgumentException("ObjectIdentifier different key than 2c is not implemented yet.");
        }

        //Time remaining
        SDContextTag contextTag3 = new SDContextTag(covReader.next());
        int numTimeRemainingOctets = contextTag3.findLength();
        Octet[] timeRemainingOctets = covReader.nextOctets(numTimeRemainingOctets);
        int timeRemainingSec = toInt(timeRemainingOctets);

        //List of values
        //4e095519012e4441a4cccd2f4f

        String objectId = objectIdentifier.getObjectType() + "_" + objectIdentifier.getInstanceNumber();
        Source source = new Source(deviceId.getInstanceNumber(), objectId);

        String resultListHexString = covReader.unprocessedHexString();
//        resultListHexString = filterResultList(resultListHexString);
        List<Value> bacnetValues = parseListOfValues(resultListHexString);
        for (Value bacnetValue : bacnetValues) {
            String observationId = null; //TODO how to create unique id for the observation. Is that needed?
            Object value = bacnetValue.getValue();
            String name = bacnetValue.getPropertyIdentifier().name();
            Observation observation = new Observation(observationId, source, value, name);
            observations.add(observation);
        }
        /*
        properties = findProperties(covReader, resultListHexString);

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
        */


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
            /*

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
//                log.trace("unprocessed: {}", resultListHexString);
            }

        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }

        return properties;

             */
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
//                log.trace("unprocessed: {}", resultListHexString);
            }

        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }

        return properties;
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
