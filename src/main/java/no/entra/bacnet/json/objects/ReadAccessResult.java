package no.entra.bacnet.json.objects;

import no.entra.bacnet.json.PropertyId;

import java.util.HashMap;

public class ReadAccessResult {
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

}
