package no.entra.bacnet.json.objects;

public class PropertyResult {
    private final String unprocessedHexString;
    private Property property = null;

    public PropertyResult(String unprocessedHexString) {
        this.unprocessedHexString = unprocessedHexString;
    }

    public PropertyResult(String unprocessedHexString, Property property) {
        this.unprocessedHexString = unprocessedHexString;
        this.property = property;
    }

    public String getUnprocessedHexString() {
        return unprocessedHexString;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
