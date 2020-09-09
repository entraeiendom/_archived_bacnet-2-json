package no.entra.bacnet.json.values;

import no.entra.bacnet.json.objects.PropertyIdentifier;

public class Value {

    private final PropertyIdentifier propertyIdentifier;
    private final Object value;

    public Value(PropertyIdentifier propertyIdentifier, Object value) {
        this.propertyIdentifier = propertyIdentifier;
        this.value = value;
    }

    public PropertyIdentifier getPropertyIdentifier() {
        return propertyIdentifier;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Value{" +
                "propertyIdentifier=" + propertyIdentifier +
                ", value=" + value +
                '}';
    }
}
