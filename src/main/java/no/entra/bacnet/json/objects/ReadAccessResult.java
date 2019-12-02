package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.PropertyId;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ReadAccessResult {
    private static final Logger log = getLogger(ReadAccessResult.class);
    public static final String LIST_START_HEX = "1e";
    public static final String LIST_END_HEX = "1f";
    public static final String SD_CONTEXT_TAG_2_MESSGE_PRIORITY = "29";
    public static final String PRESENT_VALUE_HEX = "55";
    public static final String PD_OPENING_TAG_4 = "4e";
    public static final String PD_CLOSING_TAG_4 = "4f";
    private ObjectIdentifier objectId;
    private HashMap<String, Object> results = new HashMap<>();

    private ReadAccessResult() {

    }
    public ReadAccessResult(ObjectIdentifier objectId) {
        this.objectId = objectId;
    }

    public ObjectIdentifier getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectIdentifier objectId) {
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
    public void setResultByKey(String key, String value) throws IllegalStateException {
        if (results.containsKey(key)) {
            throw new IllegalStateException("Duplicate insert is not implemented. Key: " + key + " is already set.");
        }
        results.put(key, value);
    }

    public void setResultByKey(String key, Number value) throws IllegalStateException {
        if (results.containsKey(key)) {
            throw new IllegalStateException("Duplicate insert is not implemented. Key: " + key + " is already set.");
        }
        results.put(key, value);
    }

    public void setResultByKey(PropertyId key, String value) throws IllegalStateException {
        setResultByKey(key.name(), value);
    }

    public void setResultByKey(PropertyId key, Number value) throws IllegalStateException {
        setResultByKey(key.name(), value);
    }

    public Object getResultByKey(String key) {
        return results.get(key);
    }

    public Object getResultByKey(PropertyId key) {
        return results.get(key.name());
    }

    public void setResults(HashMap<String, Object> results) {
        this.results = results;
    }

    public static ReadAccessResult buildFromResultList(String resultListHexString) {
        ReadAccessResult accessResult = null;
        if (resultListHexString != null) { //&& resultListHexString.startsWith(LIST_START_HEX) && resultListHexString.endsWith(LIST_END_HEX)) {

            OctetReader listReader = new OctetReader(resultListHexString);

            //Expect Object Identifier
            Octet oidType = listReader.next();
            log.debug("oid: {}", oidType);
            if (oidType.equals(new Octet("0c"))) {
                String objectIdString = "0c" + listReader.next(4);
                log.debug("unprocessed before ObjectIdentifier {}", listReader.unprocessedHexString());
                ObjectIdentifier objectIdentifier = ObjectIdentifier.buildFromHexString(objectIdString);
                accessResult = new ReadAccessResult(objectIdentifier);
            }

            //List Start Hex
            Octet startList = listReader.next();
            if (startList.equals(Octet.fromHexString(LIST_START_HEX))) {

                Octet contextTag = listReader.next();
                log.debug("Context Tag: {}", contextTag);
                PropertyIdentifier propertyIdentifier = null;
                if (contextTag.equals(Octet.fromHexString(SD_CONTEXT_TAG_2_MESSGE_PRIORITY))) {
                    Octet propertyIdentifierOctet = listReader.next();
                    propertyIdentifier = PropertyIdentifier.fromOctet(propertyIdentifierOctet);
                }
                //Expect start of list eg 4e
                Octet startOfList = listReader.next();
                log.debug(listReader.unprocessedHexString());
                if(startOfList.equals(Octet.fromHexString(PD_OPENING_TAG_4))) {
                    Octet applicationTag = listReader.next();
                    char valueLength = applicationTag.getSecondNibble();
                    int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
                    String valueAsString = listReader.next(valueOctetLength);
                    log.debug("valueAsString {}", valueAsString);
                    if (propertyIdentifier != null) {
                        if (applicationTag.getFirstNibble() == '4') {
                            //expect Real value
                            int valueAsNumber = Integer.parseInt(valueAsString, 16);
                            //IEEE 754
                            Float valueAsReal = Float.intBitsToFloat(valueAsNumber);
                            accessResult.setResultByKey(propertyIdentifier.name(), valueAsReal);
                        } else {
                            //Setting string value
                            accessResult.setResultByKey(propertyIdentifier.name(), valueAsString);
                        }
                    }
                }
            };

        }
        return accessResult;
    }

}
