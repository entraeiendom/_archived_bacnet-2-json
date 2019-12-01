package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectTypeTest {

    @Test
    void fromObjectTypeInt() {
        assertEquals(ObjectType.AnalogInput, ObjectType.fromObjectTypeInt(0));
    }

    @Test
    void fromOctet() {
        Octet objectTypeOctet = new Octet("00");
        assertEquals(ObjectType.AnalogInput, ObjectType.fromOctet(objectTypeOctet));
    }
}