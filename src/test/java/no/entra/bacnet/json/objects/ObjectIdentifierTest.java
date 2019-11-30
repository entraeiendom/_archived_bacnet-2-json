package no.entra.bacnet.json.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdentifierTest {

    @Test
    void buildFromHexString() {
        String idHexString = "0c002dc6ef";
        ObjectIdentifier objectIdentifier = ObjectIdentifier.buildFromHexString(idHexString);
        assertNotNull(objectIdentifier);
        assertEquals("0c", objectIdentifier.getObjectType());
        assertEquals("3000047", objectIdentifier.getInstanceNumber());
    }
}