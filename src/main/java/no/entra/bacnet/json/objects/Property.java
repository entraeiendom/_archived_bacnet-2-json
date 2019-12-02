package no.entra.bacnet.json.objects;

public class Property {
    private final String key;
    private Object value = null;

    public Property(String key) {
        this.key = key;
    }

    public Property(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
