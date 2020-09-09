package no.entra.bacnet.json.values;

import no.entra.bacnet.json.objects.PropertyIdentifier;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return getPropertyIdentifier() == value1.getPropertyIdentifier() &&
                getValue().equals(value1.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPropertyIdentifier(), getValue());
    }

    @Override
    public String toString() {
        return "Value{" +
                "propertyIdentifier=" + propertyIdentifier +
                ", value=" + value +
                '}';
    }
}
