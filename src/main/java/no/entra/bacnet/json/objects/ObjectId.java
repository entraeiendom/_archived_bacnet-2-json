package no.entra.bacnet.json.objects;

import org.slf4j.Logger;

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


    @Override
    public String toString() {
        if (objectType == null) {
            return "type-missing " + " " + instanceNumber;
        }
        return objectType.name() + " " + instanceNumber;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj != null && obj instanceof ObjectId) {
            if (((ObjectId) obj).getObjectType().equals(this.objectType) && ((ObjectId)obj).getInstanceNumber().equals(this.getInstanceNumber())) {
                isEqual = true;
            }
            return isEqual;
        }
        return super.equals(obj);
    }
}
