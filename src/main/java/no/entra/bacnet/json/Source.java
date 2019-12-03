package no.entra.bacnet.json;

public class Source {

    private String deviceId;
    private String objectId;

    private Source() {
    }

    public Source(String deviceId, String objectId) {
        this.deviceId = deviceId;
        this.objectId = objectId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Source{" +
                "deviceId='" + deviceId + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
