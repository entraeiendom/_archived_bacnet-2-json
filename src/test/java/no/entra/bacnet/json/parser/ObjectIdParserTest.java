package no.entra.bacnet.json.parser;

import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.ObjectType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectIdParserTest {

    @Test
    void parseDevice517() {
        String device517 = "0c02000205";
        ObjectIdParserResult<ObjectId> result = ObjectIdParser.parse(device517);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(5, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.Device, result.getParsedObject().getObjectType());
        assertEquals("517", result.getParsedObject().getInstanceNumber());

    }

    @Test
    void findObjectTypeIntTest() {
        String objectTypeAsBitString = "00000010000000000000001000000101";
        int objectTypeInt = ObjectIdParser.findObjectTypeInt(objectTypeAsBitString);
        assertEquals(8, objectTypeInt);
    }

    @Test
    void findInstanceNumberTest() {
        String objectTypeAsBitString = "00000010000000000000001000000101";
        int instanceNumber = ObjectIdParser.findInstanceNumber(objectTypeAsBitString);
        assertEquals(517, instanceNumber);
    }
}