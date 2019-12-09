package no.entra.bacnet.json;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * "id": "uuid when known",
 * "source": {
 * "device-id": "dsfas",
 * "object-id": "analog-input 300047"
 * },
 * "properties": {
 * "key": "value"
 * }",
 * "observedAt"
 */
public class Configuration implements BacnetMessage {
    private String id;
    private Source source;
    private Map<String, String> properties = new HashMap<>();
    private LocalDateTime observedAt;

    private Configuration() {

    }

    /**
     * @param id
     * @param source
     */
    public Configuration(String id, Source source) {
        this.id = id;
        this.source = source;
        observedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        String property = properties.get(key);
        return property;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id='" + id + '\'' +
                ", source=" + source +
                ", properties=" + properties +
                ", observedAt=" + observedAt +
                '}';
    }

    @Override
    public String toJson() {

        return asJsonObject().toString();
    }

    @Override
    public JSONObject asJsonObject() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(SOURCE, source);
        json.put(PROPERTIES, properties);
        json.put(OBSERVED_AT, observedAt);
        return json;
    }
}
