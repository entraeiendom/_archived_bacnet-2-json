package no.entra.bacnet.json.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdentifierTest {

    @Test
    void buildFromHexString() {
        String idHexString = "0c002dc6ef";
        ObjectIdentifier objectIdentifier = ObjectIdentifier.buildFromHexString(idHexString);
        assertNotNull(objectIdentifier);
    }
}