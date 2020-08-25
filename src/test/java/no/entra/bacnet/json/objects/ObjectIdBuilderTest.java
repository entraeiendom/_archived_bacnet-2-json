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
        OctetReader octetReader = new OctetReader("0200000b");
        Octet objectIdType= octetReader.next(); //02
        ObjectId objectId = new ObjectIdBuilder(objectIdType).withInstanceNumberOctet(octetReader.nextOctets(3)).build();
        assertNotNull(objectId);
        assertEquals(ObjectType.AnalogValue, objectId.getObjectType());
        assertEquals("11", objectId.getInstanceNumber());
    }

    @Test
    void analogInput0() {
        //objectId 1c00000000
        OctetReader octetReader = new OctetReader("00000000");
        Octet objectIdType= octetReader.next(); //02
        ObjectId objectId = new ObjectIdBuilder(objectIdType).withInstanceNumberOctet(octetReader.nextOctets(3)).build();
        assertNotNull(objectId);
        assertEquals(ObjectType.AnalogInput, objectId.getObjectType());
        assertEquals("0", objectId.getInstanceNumber());
    }
}