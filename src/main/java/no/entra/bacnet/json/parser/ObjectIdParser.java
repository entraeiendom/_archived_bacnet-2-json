package no.entra.bacnet.json.parser;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.ObjectType;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.utils.HexUtils;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ObjectIdParser {
    private static final Logger log = getLogger(ObjectIdParser.class);

    //4 octets
    //10 bits for ObjectType
    //22 bits for Instance Number
    public static ObjectIdParserResult<ObjectId> parse(String hexString) {
        ObjectId objectId = null;
        OctetReader idReader = new OctetReader(hexString);
        Octet contextTag0 = idReader.next();
        String typeAndInstance = idReader.next(4);
        String typeAndInstanceBits = HexUtils.toBitString(typeAndInstance);
        int objectTypeInt = findObjectTypeInt(typeAndInstanceBits);
        Integer instanceNumber = findInstanceNumber(typeAndInstanceBits);
        ObjectType objectType = ObjectType.fromObjectTypeInt(objectTypeInt);
        objectId = new ObjectId(objectType,instanceNumber.toString());
        ObjectIdParserResult result = new ObjectIdParserResult(objectId, 5);

        return result;

    }

    protected static int findInstanceNumber(String typeAndInstanceBits) {
        String instanceNuberBits = typeAndInstanceBits.substring(10,32);
        return Integer.parseInt(instanceNuberBits,2);
    }

    protected static int findObjectTypeInt(String typeAndInstanceBits) {
        String objectTypeBits = typeAndInstanceBits.substring(0,10);
        return Integer.parseInt(objectTypeBits, 2);
    }
}
