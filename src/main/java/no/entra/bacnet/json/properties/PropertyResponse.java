package no.entra.bacnet.json.properties;

import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.ServiceChoice;

import java.time.LocalDateTime;

public class PropertyResponse {
    private String source;
    private String destination;
    private PduType pduType; //eg Complex-ACK
    private boolean isSegmentedResponse = false;
    private boolean hasMoreSegments = false;
    private boolean isReplyExpected = false;
    private Integer maxAdpuOctetLenghtAccepted = null;
    private Integer invokeId = null;
    private Integer sequenceNumber = null;
    private Integer propsedWindowSize = null;
    private ServiceChoice serviceChoice;
    private ObjectId desiredObjectId;
    private String messageContent;
    private LocalDateTime observedAt;
    private String segmentBodyHexString;

    public PropertyResponse() {
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

    public boolean isSegmentedResponse() {
        return isSegmentedResponse;
    }

    public void setSegmentedResponse(boolean segmentedResponse) {
        isSegmentedResponse = segmentedResponse;
    }

    public boolean isHasMoreSegments() {
        return hasMoreSegments;
    }

    public void setHasMoreSegments(boolean hasMoreSegments) {
        this.hasMoreSegments = hasMoreSegments;
    }

    public Integer getMaxAdpuOctetLenghtAccepted() {
        return maxAdpuOctetLenghtAccepted;
    }

    public void setMaxAdpuOctetLenghtAccepted(Integer maxAdpuOctetLenghtAccepted) {
        this.maxAdpuOctetLenghtAccepted = maxAdpuOctetLenghtAccepted;
    }

    public boolean isReplyExpected() {
        return isReplyExpected;
    }

    public void expectingReply(boolean replyExpected) {
        isReplyExpected = replyExpected;
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

    public ObjectId getDesiredObjectId() {
        return desiredObjectId;
    }

    public void setDesiredObjectId(ObjectId desiredObjectId) {
        this.desiredObjectId = desiredObjectId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }

    public String getSegmentBodyHexString() {
        return segmentBodyHexString;
    }

    public void setSegmentBodyHexString(String segmentBodyHexString) {
        this.segmentBodyHexString = segmentBodyHexString;
    }
}
