package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;

public class ServiceBuilder {
    private final PduType pduType;
    private Service service;
    private Octet serviceChoice;
    private boolean isSegmented = false;
    private boolean hasMoreSegments = false;
    private boolean willAcceptSegmentedResponse = false;
    private Integer maxAcceptedPduSize = null;
    private Integer invokeId = null;
    private Integer sequenceNumber = null;
    private Integer proposedWindowSize = null;

    public ServiceBuilder(PduType pduType) {
        if (pduType == null) {
            throw new IllegalArgumentException("pduType is required.");
        }
        this.pduType = pduType;
    }

    public ServiceBuilder withServiceChoice(Octet serviceChoice) {
        this.serviceChoice = serviceChoice;
        return this;
    }

    public Service build() {
        switch (pduType) {
            case UnconfirmedRequest:
                service = new UnconfirmedService(pduType, serviceChoice);
                break;
            case ConfirmedRequest:
                service = new ConfirmedService(pduType, serviceChoice);
                break;
            case ComplexAck:
                service = new ConfirmedService(pduType, serviceChoice);
                break;
            case SimpleAck:
                service = new ConfirmedService(pduType, serviceChoice);
                break;
            default:
                service = null;
        }
        if (service != null) {
            service.isSegmented(isSegmented);
            service.hasMoreSegments(hasMoreSegments);
            service.willAcceptSegmentedResponse(willAcceptSegmentedResponse);
            service.setMaxAcceptedPduSize(maxAcceptedPduSize);
            service.setInvokeId(invokeId);
            service.setSequenceNumber(sequenceNumber);
            service.setProposedWindowSize(proposedWindowSize);
        }
        return service;
    }

    public ServiceBuilder withIsSegmented(boolean segmented) {
        this.isSegmented = true;
        return this;
    }

    public ServiceBuilder withHasMoreSegments(boolean moreSegments) {
        this.hasMoreSegments = moreSegments;
        return this;
    }

    public ServiceBuilder withWillAcceptSegmentedResponse(boolean acceptSegmentedResponse) {
        this.willAcceptSegmentedResponse = acceptSegmentedResponse;
        return this;
    }

    public ServiceBuilder withMaxAPDUSize(int numberOfOctets) {
        this.maxAcceptedPduSize = numberOfOctets;
        return this;
    }

    public ServiceBuilder withInvokId(int invokeId) {
        this.invokeId = invokeId;
        return this;
    }

    public ServiceBuilder withSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public ServiceBuilder withProposedWindowSize(int proposedWindowSize) {
        this.proposedWindowSize = proposedWindowSize;
        return this;
    }
}
