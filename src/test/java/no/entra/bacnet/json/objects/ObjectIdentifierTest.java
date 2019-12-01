package no.entra.bacnet.json.objects;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.objects.ObjectType.AnalogInput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdentifierTest {

    @Test
    void buildFromHexString() {
        String idHexString = "0c002dc6ef";
        ObjectIdentifier objectIdentifier = ObjectIdentifier.buildFromHexString(idHexString);
        assertNotNull(objectIdentifier);
        assertEquals(AnalogInput, objectIdentifier.getObjectType());
        assertEquals("3000047", objectIdentifier.getInstanceNumber());
        assertEquals(AnalogInput + " 3000047", objectIdentifier.toString());
    }
}