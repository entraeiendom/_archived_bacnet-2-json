package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.utils.HexUtils;

public class ObjectIdBuilder {
    private final ObjectType objectType;
    private String instanceNumber;

    public ObjectIdBuilder(Octet objectTypeOctet) {
        this.objectType = ObjectType.fromOctet(objectTypeOctet);
    }

    public ObjectIdBuilder withInstanceNumberOctet(Octet[] instanceNumberOctets) {
        String instanceNumberString = HexUtils.octetsToString(instanceNumberOctets);
        instanceNumber = Integer.valueOf(HexUtils.toInt(instanceNumberString)).toString();
        return this;
    }

    public ObjectId build() {
        ObjectId objectId = new ObjectId(objectType, instanceNumber);
        return objectId;
    }
}
