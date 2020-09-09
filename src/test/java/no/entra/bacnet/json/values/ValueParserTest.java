package no.entra.bacnet.json.values;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.apdu.SDContextTag.TAG0LENGTH1;
import static no.entra.bacnet.json.values.ValueParser.parseValue;
import static org.junit.jupiter.api.Assertions.*;

class ValueParserTest {

    @Test
    void parseTest() {
        String hexString = "09552e44428200002f";
        ValueParserResult result = parseValue(hexString);
        assertNotNull(result);
        Value expectedValue = new Value(PropertyIdentifier.PresentValue, 65.0f);
        assertEquals(expectedValue, result.getValue());
    }

    @Test
    void sdtagMatch() {
        Octet firstOctet = new Octet("09");
        assertFalse(firstOctet == TAG0LENGTH1);
        assertTrue(firstOctet.equals(TAG0LENGTH1));
    }
}