package no.entra.bacnet.json.segmentation;

import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceChoice;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SegmentResponseBuilder {
    private static final Logger log = getLogger(SegmentResponseBuilder.class);
    private Service service;
    private String unprocessedHexString;
    private String source;
    private String destination;
    private PduType pduType; //eg Confirmed-Req
    private Integer invokeId = null;
    private Integer sequenceNumber = null;
    private Integer proposedWindowSize = null;
    private ServiceChoice serviceChoice;
    private String segmentBodyHexString = null;
    public SegmentResponseBuilder(Service service, String unprocessedHexString) {
        withService(service);
        withUnprocessedHexString(unprocessedHexString);
    }

    public SegmentResponseBuilder withService(Service service) {
        this.service = service;
        this.pduType = service.getPduType();
        this.invokeId = service.getInvokeId();
        this.serviceChoice = service.getServiceChoice();
        this.sequenceNumber = service.getSequenceNumber();
        this.proposedWindowSize = service.getProposedWindowSize();
        return this;
    }

    public SegmentResponseBuilder withUnprocessedHexString(String unprocessedHexString) {
        this.unprocessedHexString = unprocessedHexString;
        //We would expect unprocessedHexString to contain only the rest of the valid Bacnet Message
        this.segmentBodyHexString = unprocessedHexString;
        return this;
    }

    public SegmentResponseBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public SegmentResponseBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public SegmentResponse build() {
        SegmentResponse response = null;
        if (service == null) {
            return null;
        }

        response = new SegmentResponse();
        response.setSource(source);
        response.setDestination(destination);
        response.setPduType(pduType);
        response.setInvokeId(invokeId);
        response.setServiceChoice(serviceChoice);
        response.setSequenceNumber(sequenceNumber);
        response.setPropsedWindowSize(proposedWindowSize);
        return response;
    }
}
