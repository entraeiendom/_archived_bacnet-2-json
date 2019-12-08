package no.entra.bacnet.json.properties;

import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceChoice;

public class PropertyResponseBuilder {
    private Service service;
    private String unprocessedHexString;
    private String source;
    private String destination;
    private PduType pduType; //eg Confirmed-Req
    private Integer invokeId = null;
    private Integer sequenceNumber = null;
    private Integer proposedWindowSize = null;
    private ServiceChoice serviceChoice;


    /**
     *
     * @param service already built APDU service info
     * @param unprocessedHexString identify ObjectId and PropertyIdentifier
     */
    public PropertyResponseBuilder(Service service, String unprocessedHexString) {
        withService(service);
        withUnprocessedHexString(unprocessedHexString);
    }

    public PropertyResponseBuilder withService(Service service) {
        this.service = service;
        this.pduType = service.getPduType();
        this.invokeId = service.getInvokeId();
        this.serviceChoice = service.getServiceChoice();
        this.sequenceNumber = service.getSequenceNumber();
        this.proposedWindowSize = service.getProposedWindowSize();
        return this;
    }

    public PropertyResponseBuilder withUnprocessedHexString(String unprocessedHexString) {
        this.unprocessedHexString = unprocessedHexString;
        /*
        //We would expect two elements
        //1. Object Id
        //2. Requested Properties
        ObjectIdParserResult<ObjectId> objectIdResult = ObjectIdParser.parse(unprocessedHexString);
        desiredObjectId = objectIdResult.getParsedObject();
        int parsedOctets = objectIdResult.getNumberOfOctetsRead();
        OctetReader propertyReader = new OctetReader(unprocessedHexString);
        propertyReader.fastForward(parsedOctets); //Skip the part witch parsed the ObjectId.
        Octet sdContextTag = propertyReader.next();
        if (sdContextTag.equals(Octet.fromHexString("19"))) {
            Octet pIdOctet = propertyReader.next();
            PropertyIdentifier desiredProperty = PropertyIdentifier.fromOctet(pIdOctet);
            desiredPropertyIds.add(desiredProperty);
        }
        */
        return this;
    }

    public PropertyResponseBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public PropertyResponseBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public PropertyResponse build() {
        PropertyResponse response = null;
        if (service == null) {
            return null;
        }

        response = new PropertyResponse();
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
