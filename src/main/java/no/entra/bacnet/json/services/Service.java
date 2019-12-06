package no.entra.bacnet.json.services;

import no.entra.bacnet.json.objects.PduType;

public class Service {
    private final PduType pduType;
    private final ServiceChoice serviceChoice;
    private String unprocessedHexString;
    private boolean isSegmented = false;
    private boolean hasMoreSegments = false;
    private boolean willAcceptSegmentedResponse = false;
    private Integer maxAcceptedPduSize;

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    private Integer invokeId = null;

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

    public void isSegmented(boolean isSegmented) {
        this.isSegmented = isSegmented;
    }

    public void hasMoreSegments(boolean hasMoreSegments) {
        this.hasMoreSegments = hasMoreSegments;
    }

    public void willAcceptSegmentedResponse(boolean willAcceptSegmentedResponse) {
        this.willAcceptSegmentedResponse = willAcceptSegmentedResponse;
    }

    public boolean isSegmented() {
        return isSegmented;
    }

    public boolean isHasMoreSegments() {
        return hasMoreSegments;
    }

    public boolean isWillAcceptSegmentedResponse() {
        return willAcceptSegmentedResponse;
    }

    public Integer getInvokeId() {
        return invokeId;
    }

    public void setMaxAcceptedPduSize(Integer maxAcceptedPduSize) {
        this.maxAcceptedPduSize = maxAcceptedPduSize;
    }

    public Integer getMaxAcceptedPduSize() {
        return maxAcceptedPduSize;
    }
}
