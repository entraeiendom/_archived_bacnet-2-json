package no.entra.bacnet.json.segmentation;

import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.ServiceChoice;

import java.time.LocalDateTime;

public class SegmentResponse {
    private String source;
    private String destination;
    private PduType pduType;
    private Integer invokeId = null;
    private Integer sequenceNumber = null;
    private Integer propsedWindowSize = null;
    private ServiceChoice serviceChoice;
    private LocalDateTime observedAt;

    public SegmentResponse() {
        this.observedAt = LocalDateTime.now();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public PduType getPduType() {
        return pduType;
    }

    public void setPduType(PduType pduType) {
        this.pduType = pduType;
    }

    public Integer getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getPropsedWindowSize() {
        return propsedWindowSize;
    }

    public void setPropsedWindowSize(Integer propsedWindowSize) {
        this.propsedWindowSize = propsedWindowSize;
    }

    public ServiceChoice getServiceChoice() {
        return serviceChoice;
    }

    public void setServiceChoice(ServiceChoice serviceChoice) {
        this.serviceChoice = serviceChoice;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }
}
