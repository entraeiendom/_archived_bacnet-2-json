package no.entra.bacnet.json.parser;

import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.ObjectIdMapper;
import no.entra.bacnet.json.objects.ObjectIdMapperResult;
import no.entra.bacnet.json.objects.ObjectType;
import no.entra.bacnet.json.reader.OctetReader;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.objects.ObjectType.*;
import static org.junit.jupiter.api.Assertions.*;

class ObjectIdParserTest {

    @Test
    void parseDevice517() {
        String device517 = "0c02000205";
        ObjectIdMapperResult<ObjectId> result = ObjectIdMapper.parse(device517);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(5, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.Device, result.getParsedObject().getObjectType());
        assertEquals("517", result.getParsedObject().getInstanceNumber());

    }

    @Test
    void parseDevice131109() {
        String device131109 = "1c02020025";
        ObjectIdMapperResult<ObjectId> result = ObjectIdMapper.parse(device131109);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(5, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.Device, result.getParsedObject().getObjectType());
        assertEquals("131109", result.getParsedObject().getInstanceNumber());
    }

    @Test
    void parseAnalogInput0() {
        String device131109 = "2c00000000";
        ObjectIdMapperResult<ObjectId> result = ObjectIdMapper.parse(device131109);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(5, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.AnalogInput, result.getParsedObject().getObjectType());
        assertEquals("0", result.getParsedObject().getInstanceNumber());
    }

    @Test
    void findObjectTypeIntTest() {
        String objectTypeAsBitString = "00000010000000000000001000000101";
        int objectTypeInt = ObjectIdMapper.findObjectTypeInt(objectTypeAsBitString);
        assertEquals(8, objectTypeInt);
    }

    @Test
    void findInstanceNumberTest() {
        String objectTypeAsBitString = "00000010000000000000001000000101";
        int instanceNumber = ObjectIdMapper.findInstanceNumber(objectTypeAsBitString);
        assertEquals(517, instanceNumber);
    }

    @Test
    void validObjectIdTest() {
        String objectIdHexString = "0c02000204";
        ObjectIdMapperResult<ObjectId> objectIdResult = ObjectIdMapper.parse(objectIdHexString);
        assertNotNull(objectIdResult);
        assertEquals("Device", objectIdResult.getParsedObject().getObjectType().toString());
        assertEquals("516", objectIdResult.getParsedObject().getInstanceNumber());
    }

    @Test
    void decode4OctetsTest() {
        String objectIdentifierTypeAndInstance = "0200000c";
        OctetReader objectIdReader = new OctetReader(objectIdentifierTypeAndInstance);
        ObjectId objectId = ObjectIdMapper.decode4Octets(objectIdReader.nextOctets(4));
        assertNotNull(objectId);
        assertEquals(ObjectType.Device, objectId.getObjectType());
        assertEquals("12", objectId.getInstanceNumber());
    }

    @Test
    void fromHexString() {
        String idHexString = "00000000";
        ObjectId objectId = ObjectIdMapper.fromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(AnalogInput, objectId.getObjectType());
        assertEquals("0", objectId.getInstanceNumber());
        assertEquals(AnalogInput + " 0", objectId.toString());
        idHexString = "05000001";
        objectId = ObjectIdMapper.fromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(TrendLog + " 1", objectId.toString());
    }

    @Test
    void toHexString() {
        ObjectId objectId = new ObjectId(TrendLog,"1");
        String hexString = ObjectIdMapper.toHexString(objectId);
        //TrendLog = int 20 = bits? = hex?
        assertEquals("05000001", hexString);
    }

    @Test
    void paddedBitStringTest() {
        String expected = "0000000000001000000101";
        String padded = ObjectIdMapper.paddedInstanceNumberBits(517);
        assertEquals(expected, padded);
    }

    @Test
    void paddedObjectTypeBitsTest() {
        String expected = "0000001000";
        String padded = ObjectIdMapper.paddedObjectTypeBits(Device);
        assertEquals(expected,padded);
        expected = "0000010100";
        padded = ObjectIdMapper.paddedObjectTypeBits(TrendLog);
        assertEquals(expected,padded);
        expected = "0000000000";
        padded = ObjectIdMapper.paddedObjectTypeBits(AnalogInput);
        assertEquals(expected,padded);
    }
}