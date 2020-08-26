package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SDContextTagTest {

    @Test
    void findEnumeration() {
        SDContextTag tag = new SDContextTag(Octet.fromHexString("09"));
        assertEquals(0, tag.findEnumeration());
        tag = new SDContextTag(Octet.fromHexString("19"));
        assertEquals(1, tag.findEnumeration());
        tag = new SDContextTag(Octet.fromHexString("29"));
        assertEquals(2, tag.findEnumeration());
        tag = new SDContextTag(Octet.fromHexString("39"));
        assertEquals(3, tag.findEnumeration());
    }

    @Test
    void findLength() {
    }
}