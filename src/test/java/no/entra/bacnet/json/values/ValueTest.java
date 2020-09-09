package no.entra.bacnet.json.values;

import no.entra.bacnet.json.objects.PropertyIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ValueTest {

    @Test
    void simpleValueTest() {
        float observedValue = 65.0f;
        PropertyIdentifier presentValue = PropertyIdentifier.PresentValue;
        Value value = new Value(presentValue, observedValue);
        assertEquals(observedValue, value.getValue());
        assertEquals(presentValue, value.getPropertyIdentifier());
    }

    @Test
    void equalsTest() {
        float observedValue = 65.0f;
        PropertyIdentifier presentValueKey = PropertyIdentifier.PresentValue;
        Value value = new Value(presentValueKey, observedValue);
        Value sameObservation = new Value(presentValueKey, observedValue);
        assertEquals(value, sameObservation);
        Value notSameVale = new Value(presentValueKey, 65.1f);
        assertNotEquals(value, notSameVale);
        Value notSameKey = new Value(PropertyIdentifier.PropertyList, observedValue);
        assertNotEquals(value, notSameKey);

    }
}