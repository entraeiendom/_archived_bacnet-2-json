package no.entra.bacnet.json.properties;

import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.services.ServiceChoice;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PropertyRequest implements Serializable {
    private String source;
    private String destination;
    private PduType pduType; //eg Confirmed-Req
    private boolean isSegmentedResponseAccepted = false;
    private Integer maxAdpuOctetLenghtAccepted = null;
    private Integer invokeId = null;
    private ServiceChoice serviceChoice;
    private ObjectId desiredObjectId;
    private List<PropertyIdentifier> desiredPropertyIds = new ArrayList<>();
    private LocalDateTime observedAt;

    public PropertyRequest() {
        observedAt = LocalDateTime.now();
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

    public boolean isSegmentedResponseAccepted() {
        return isSegmentedResponseAccepted;
    }

    public void setSegmentedResponseAccepted(boolean segmentedResponseAccepted) {
        isSegmentedResponseAccepted = segmentedResponseAccepted;
    }

    public Integer getMaxAdpuOctetLenghtAccepted() {
        return maxAdpuOctetLenghtAccepted;
    }

    public void setMaxAdpuOctetLenghtAccepted(Integer maxAdpuOctetLenghtAccepted) {
        this.maxAdpuOctetLenghtAccepted = maxAdpuOctetLenghtAccepted;
    }

    public Integer getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public ServiceChoice getServiceChoice() {
        return serviceChoice;
    }

    public void setServiceChoice(ServiceChoice serviceChoice) {
        this.serviceChoice = serviceChoice;
    }

    public ObjectId getDesiredObjectId() {
        return desiredObjectId;
    }

    public void setDesiredObjectId(ObjectId desiredObjectId) {
        this.desiredObjectId = desiredObjectId;
    }

    public List<PropertyIdentifier> getDesiredPropertyIds() {
        return desiredPropertyIds;
    }

    public void setDesiredPropertyIds(List<PropertyIdentifier> desiredPropertyIds) {
        this.desiredPropertyIds = desiredPropertyIds;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }
}
