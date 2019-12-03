package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import java.math.BigInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class ObjectId {
    private static final Logger log = getLogger(ObjectId.class);

    private ObjectType objectType;
    private String instanceNumber;

    public ObjectId() {
    }

    public ObjectId(ObjectType objectType, String instanceNumber) {
        this.objectType = objectType;
        this.instanceNumber = instanceNumber;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public static ObjectId buildFromHexString(String hexString) {
        ObjectId ObjectId = null;
        OctetReader idReader = new OctetReader(hexString);
        if (idReader != null) {
            Octet objectTypeOctetxx = idReader.next();
            log.debug("ObjectType: {}", objectTypeOctetxx);
            if (objectTypeOctetxx.equals(new Octet("0c"))) {
                if (objectTypeOctetxx.getSecondNibble() == 'c') {
                    int length = 4; //number of octets, as by specification 20.2.14 one is for Object Type 00 = Analog Input
                    Octet objectTypeOctet = idReader.next();
                    String instanceNumberHex = idReader.next(length-1);
                    BigInteger instanceNumberBI = new BigInteger(instanceNumberHex, 16);
                    ObjectType objectType = ObjectType.fromOctet(objectTypeOctet);
                    ObjectId = new ObjectId(objectType, instanceNumberBI.toString());
                }
            }
        }
        return ObjectId;
    }

    @Override
    public String toString() {
        if (objectType == null) {
            return "typ-missing " + " " + instanceNumber;
        }
        return objectType.name() + " " + instanceNumber;
    }
}
