package no.entra.bacnet.json.services;

import no.entra.bacnet.json.objects.PduType;

public class Service {
    private final PduType pduType;
    private final ServiceChoice serviceChoice;
    private String unprocessedHexString;

    public Service(PduType pduType, ServiceChoice serviceChoice) {
        this.pduType = pduType;
        this.serviceChoice = serviceChoice;
    }

    public PduType getPduType() {
        return pduType;
    }

    public ServiceChoice getServiceChoice() {
        return serviceChoice;
    }

    public void setUnprocessedHexString(String unprocessedHexString) {
        this.unprocessedHexString = unprocessedHexString;
    }

    public String getUnprocessedHexString() {
        return unprocessedHexString;
    }

    @Override
    public String toString() {
        return "Service{" +
                "pduType=" + pduType +
                ", serviceChoice=" + serviceChoice +
                ", unprocessedHexString='" + unprocessedHexString + '\'' +
                '}';
    }
}
