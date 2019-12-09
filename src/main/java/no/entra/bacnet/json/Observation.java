package no.entra.bacnet.json;

import org.json.JSONObject;

import java.io.Serializable;
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
public class Observation implements Serializable {
    public static final String ID = "id";
    public static final String SOURCE = "source";
    public static final String VALUE = "value";
    public static final String UNIT = "unit";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String OBSERVED_AT = "observedAt";
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

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(SOURCE, source);
        json.put(VALUE,value);
        json.put(UNIT, unit);
        json.put(NAME, name);
        json.put(DESCRIPTION, description);
        json.put(OBSERVED_AT, observedAt);

        return json.toString();
    }
}
