package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.EntraUnknownOperationException;
import no.entra.bacnet.json.parser.ObjectIdParser;
import no.entra.bacnet.json.parser.ObjectIdParserResult;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.utils.HexUtils;
import org.slf4j.Logger;

import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ReadAccessResult {
    private static final Logger log = getLogger(ReadAccessResult.class);
    public static final String OBJECT_IDENTIFIER = "0c";
    public static final String LIST_START_HEX = "1e";
    public static final String LIST_END_HEX = "1f";
    public static final String SD_CONTEXT_TAG_2 = "29";
    public static final char ExtendedValue = '5';
    public static final String PRESENT_VALUE_HEX = ExtendedValue + "5";
    public static final String PD_OPENING_TAG_4 = "4e";
    public static final String PD_CLOSING_TAG_4 = "4f";
    private ObjectId objectId;
    private HashMap<String, Object> results = new HashMap<>();

    private ReadAccessResult() {

    }
    public ReadAccessResult(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public HashMap<String, Object> getResults() {
        return results;
    }

    /**
     *
     * @param key, typically "pid" of result
     * @param value
     * @throws IllegalStateException Duplicate key is not allowed.
     */
    public void setResultByKey(String key, Object value) throws IllegalStateException {
        if (results.containsKey(key)) {
            throw new IllegalStateException("Duplicate insert is not implemented. Key: " + key + " is already set.");
        }
        results.put(key, value);
    }

    public void setResultByKey(PropertyIdentifier key, Object value) throws IllegalStateException {
        setResultByKey(key.name(), value);
    }
    public Object getResultByKey(String key) {
        return results.get(key);
    }

    public Object getResultByKey(PropertyIdentifier key) {
        return results.get(key.name());
    }

    public void setResults(HashMap<String, Object> results) {
        this.results = results;
    }

    /**
     *
     * @param resultListHexString must start with "0c" and end with "1f"
     * @return A Bacnet message parsed into ReadAccessResult.
     */
    public static ReadAccessResult buildFromResultList(String resultListHexString) {
        ReadAccessResult accessResult = null;
        try {
            if (resultListHexString != null) { //&& resultListHexString.startsWith(LIST_START_HEX) && resultListHexString.endsWith(LIST_END_HEX)) {

                OctetReader listReader = new OctetReader(resultListHexString);

                //Expect Object Identifier
                Octet oidType = listReader.next();
                log.debug("oid: {}", oidType);
                if (oidType.equals(new Octet(OBJECT_IDENTIFIER))) {
                    String objectIdString = "0c" + listReader.next(4);
                    log.debug("unprocessed before ObjectIdentifier {}", listReader.unprocessedHexString());
                    ObjectIdParserResult<ObjectId> objectIdResult = ObjectIdParser.parse(objectIdString);
                    ObjectId objectIdentifier = objectIdResult.getParsedObject();
                    accessResult = new ReadAccessResult(objectIdentifier);
                }

                //List Start Hex
                Octet startList = listReader.next();
                if (startList.equals(Octet.fromHexString(LIST_START_HEX))) {

                    //PresentValue
                    String unprocessedHexString = listReader.unprocessedHexString();
                    while (unprocessedHexString != null && !unprocessedHexString.isEmpty()) {
                        PropertyResult propertyResult = parseProperty(unprocessedHexString);
                        if (propertyResult != null ) {
                            if (propertyResult.getProperty() != null) {
                                Property property = propertyResult.getProperty();
                                String key = property.getKey();
                                Object value = property.getValue();
                                accessResult.setResultByKey(key, value);
                            }
                            unprocessedHexString = propertyResult.getUnprocessedHexString();
                        }
                    }
                }

            }
        } catch (EntraUnknownOperationException e) {
            log.info("Failed to parse: {}. Reason: {}", resultListHexString, e.getMessage());
        }
        return accessResult;
    }

    static PropertyResult parseProperty(String unprocessedHexString) {
        PropertyResult propertyResult = null;
        OctetReader propertyReader = new OctetReader(unprocessedHexString);
        Octet contextTag = propertyReader.next();
        if (contextTag.equals(Octet.fromHexString(LIST_END_HEX))) {
            propertyResult = new PropertyResult(null);
        } else {
            log.debug("Context Tag: {}", contextTag);
            PropertyIdentifier presentValuePid = null;
            if (contextTag.equals(Octet.fromHexString(SD_CONTEXT_TAG_2))) {
                Octet propertyIdentifierOctet = propertyReader.next();
                presentValuePid = PropertyIdentifier.fromOctet(propertyIdentifierOctet);
            }

            //Expect start of list eg 4e
            Octet startOfList = propertyReader.next();
            log.debug(propertyReader.unprocessedHexString());
            if (startOfList.equals(Octet.fromHexString(PD_OPENING_TAG_4))) {
                Property property = null;
                if (presentValuePid == PropertyIdentifier.PresentValue) {
                    Octet applicationTag = propertyReader.next();
                    char valueLength = applicationTag.getSecondNibble();
                    int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
                    String valueAsString = propertyReader.next(valueOctetLength);
                    log.debug("valueAsString {}", valueAsString);

                    if (presentValuePid != null) {
                        if (applicationTag.getFirstNibble() == '4') {
                            //expect Real value
                            int valueAsNumber = Integer.parseInt(valueAsString, 16);
                            //IEEE 754
                            Float valueAsReal = Float.intBitsToFloat(valueAsNumber);
                            property = new Property(presentValuePid.name(), valueAsReal);
                        } else {
                            //Setting string value
                            property = new Property(presentValuePid.name(), valueAsString);
                        }
                    }
                } else if (presentValuePid == PropertyIdentifier.Units) {
                    log.debug("Find Units from: {}", propertyReader.unprocessedHexString());
                    Octet applicationTag = propertyReader.next();
                    char valueLength = applicationTag.getSecondNibble();
                    int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
                    String valueAsHex = propertyReader.next(valueOctetLength);
                    Unit unit = Unit.fromUnitHex(valueAsHex);
                    if (unit != null) {
                        property = new Property(presentValuePid.name(), unit.name());
                    }
                } else if (presentValuePid == PropertyIdentifier.ObjectName || presentValuePid == PropertyIdentifier.Description) {
                    log.debug("Find ObjectName from: {}", propertyReader.unprocessedHexString());
                    String objectName = null;
                    Octet applicationTag = propertyReader.next();
                    if (applicationTag.getFirstNibble() == '7') {
                        log.debug("Expecting String.");
                    }
                    if (applicationTag.getSecondNibble() == ExtendedValue) {
                        log.debug("Expecting extended value");
                        Octet valueLength = propertyReader.next();
                        int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
                        Octet encoding = propertyReader.next();
                        String objectNameHex = propertyReader.next(valueOctetLength - 1);
                        log.debug("ObjectNameHex: {}", objectNameHex);
                        objectName = HexUtils.parseExtendedValue(encoding,objectNameHex);
                        log.debug("The rest: {}", propertyReader.unprocessedHexString());
                    } else {
                        log.debug("Do not know what to do....");
                        Octet next;
                        do {
                            next = propertyReader.next();
                            log.debug("next: {}", next);
                        } while (!next.equals(Octet.fromHexString("4f")));
                    }


                    if (objectName != null) {
                        property = new Property(presentValuePid.name(), objectName);
                    }

                } else {
                    log.debug("PresentValuePid: {}", presentValuePid);
                    throw new EntraUnknownOperationException("I do not know how to parse PropertyIdentifier: " + presentValuePid + ". UnprocessedHexString: " + propertyReader.unprocessedHexString());
                }

                propertyReader.next();
                unprocessedHexString = propertyReader.unprocessedHexString();
                propertyResult = new PropertyResult(unprocessedHexString, property);
                //Pull closing tag

            } else {
                propertyReader.next();
                unprocessedHexString = propertyReader.unprocessedHexString();
                propertyResult = new PropertyResult(unprocessedHexString, null);
            }
        }
        return propertyResult;
    }

    @Override
    public String toString() {
        return "ReadAccessResult{" +
                "objectId=" + objectId +
                ", results=" + results +
                '}';
    }
}
