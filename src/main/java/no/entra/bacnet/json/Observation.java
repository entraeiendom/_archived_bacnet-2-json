package no.entra.bacnet.json;

import org.json.JSONObject;

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
public class Observation implements BacnetMessage {

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

    @Override
    public JSONObject asJsonObject() {
        JSONObject json = new JSONObject();
        json.put(ID, getId());
        if (getSource() != null) {
            JSONObject sourceJson = new JSONObject();
            sourceJson.put(SOURCE_DEVICE_ID, getSource().getDeviceId());
            sourceJson.put(SOURCE_OBJECT_ID, getSource().getObjectId());
            json.put(SOURCE, sourceJson);
        }
        json.put(VALUE, getValue());
        json.put(UNIT, getUnit());
        json.put(NAME, getName());
        json.put(DESCRIPTION, getDescription());
        json.put(OBSERVED_AT, getObservedAt());
        return json;
    }
}
