package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.PropertyId;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import java.util.HashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class ReadAccessResult {
    private static final Logger log = getLogger(ReadAccessResult.class);
    public static final String LIST_START_HEX = "1e";
    public static final String LIST_END_HEX = "1f";
    private String objectId;
    private HashMap<String, Object> results = new HashMap<>();

    private ReadAccessResult() {

    }
    public ReadAccessResult(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
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
        if (resultListHexString != null && resultListHexString.startsWith(LIST_START_HEX) && resultListHexString.endsWith(LIST_END_HEX)) {

            OctetReader listReader = new OctetReader(resultListHexString);

            //List Start Hex
            listReader.next();
            //Expect Object Identifier
            Octet oid = listReader.next();
            log.debug("oid: {}", oid);

        }
        return accessResult;
    }

}
