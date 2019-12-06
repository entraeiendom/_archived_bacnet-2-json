package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdBuilderTest {

    @Test
    void build() {
        //objectId c40200000b
        OctetReader octetReader = new OctetReader("00000b");
        ObjectId objectId = new ObjectIdBuilder(Octet.fromHexString("02")).withInstanceNumberOctet(octetReader.nextOctets(3)).build();
        assertNotNull(objectId);
        assertEquals(ObjectType.AnalogValue, objectId.getObjectType());
        assertEquals("11", objectId.getInstanceNumber());
    }
}