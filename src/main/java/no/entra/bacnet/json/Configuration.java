package no.entra.bacnet.json;

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
public class Configuration {
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
}
