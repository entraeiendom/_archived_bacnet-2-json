package no.entra.bacnet.json.objects;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.objects.ObjectType.AnalogInput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdTest {

    @Test
    void buildFromHexString() {
        String idHexString = "0c002dc6ef";
        ObjectId objectId = ObjectId.buildFromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(AnalogInput, objectId.getObjectType());
        assertEquals("3000047", objectId.getInstanceNumber());
        assertEquals(AnalogInput + " 3000047", objectId.toString());
    }
}