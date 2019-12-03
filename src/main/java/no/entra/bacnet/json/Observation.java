package no.entra.bacnet.json;

import java.time.LocalDateTime;

/**
 * "id": "uuid when known",
 *   "source": {
 *     "device-id": "dsfas",
 *     "object-id": "analog-input 300047"
 *     },
 *    "value": "string or number",
 *    "name": "eg the Norwegian \"tverrfaglig merkesystem\" aka tfm",
 *    "description": any string",
 *    "observedAt"
 */
public class Observation {
    private String id;
    private Source source;
    private Object value;
    private String unit;
    private String name;
    private String description;
    private LocalDateTime observedAt;

    private Observation() {

    }

    /**
     *
     * @param source
     * @param value
     */
    public Observation(Source source, Object value) {
        this(null, source, value);
    }

    /**
     *
     * @param id
     * @param source
     * @param value
     */
    public Observation(String id, Source source, Object value) {
        this.id = id;
        this.source = source;
        this.value = value;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "id='" + id + '\'' +
                ", source=" + source +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", observedAt=" + observedAt +
                '}';
    }
}
