package no.entra.bacnet.json.values;

import no.entra.bacnet.json.objects.PropertyIdentifier;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.values.ValueParser.parseValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValueListParserTest {

    @Test
    void parseValuesTest() {
        String hexString = "09552e44428200002f096f2e8204003f";
        ValueParserResult result = parseValue(hexString);
        assertNotNull(result);
        Value expectedValue = new Value(PropertyIdentifier.PresentValue, 65.0f);
        assertEquals(expectedValue, result.getValue());
        String unparsedHexString = result.getUnparsedHexString();
        assertEquals("096f2e8204002f", unparsedHexString);
    }
}