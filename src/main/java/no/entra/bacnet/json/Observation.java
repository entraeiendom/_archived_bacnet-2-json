package no.entra.bacnet.json;

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
    private String name;
    private String description;

    private Observation() {

    }
    public Observation(Source source, Object value) {
        this.source = source;
        this.value = value;
    }

    public Observation(String id, Source source, Object value) {
        this.id = id;
        this.source = source;
        this.value = value;
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
}
