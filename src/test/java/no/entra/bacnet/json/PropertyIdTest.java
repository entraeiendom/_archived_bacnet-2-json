package no.entra.bacnet.json;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyIdTest {

    @Test
    void fromPidHex() {
        PropertyId pid = PropertyId.fromPidHex("75");
        assertEquals(pid, PropertyId.units);
        assertEquals(PropertyId.fromOctet(Octet.fromHexString("75")),PropertyId.units);
    }
}