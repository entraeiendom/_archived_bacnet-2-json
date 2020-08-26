package no.entra.bacnet.json.objects;

import no.entra.bacnet.json.parser.ObjectIdParser;
import no.entra.bacnet.json.parser.ObjectIdParserResult;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.objects.ObjectType.AnalogInput;
import static no.entra.bacnet.json.objects.ObjectType.TrendLog;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdTest {

    @Test
    void parseTest() {
        String idHexString = "0c002dc6ef";
        ObjectIdParserResult<ObjectId> result = ObjectIdParser.parse(idHexString);
        ObjectId objectId = result.getParsedObject();
        assertNotNull(objectId);
        assertEquals(AnalogInput, objectId.getObjectType());
        assertEquals("3000047", objectId.getInstanceNumber());
        assertEquals(AnalogInput + " 3000047", objectId.toString());
    }

    @Test
    void fromHexString() {
        String idHexString = "00000000";
        ObjectId objectId = ObjectIdParser.fromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(AnalogInput, objectId.getObjectType());
        assertEquals("0", objectId.getInstanceNumber());
        assertEquals(AnalogInput + " 0", objectId.toString());
        idHexString = "05000001";
        objectId = ObjectIdParser.fromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(TrendLog + " 1", objectId.toString());
    }
}