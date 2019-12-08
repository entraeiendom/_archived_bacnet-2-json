package no.entra.bacnet.json.properties;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.parser.ObjectIdParser;
import no.entra.bacnet.json.parser.ObjectIdParserResult;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceChoice;

import java.util.ArrayList;
import java.util.List;

public class PropertyRequestBuilder {

    private Service service;
    private String unprocessedHexString;
    private String source;
    private String destination;
    private PduType pduType; //eg Confirmed-Req
    private boolean isSegmentedResponseAccepted = false;
    private Integer maxAdpuOctetLenghtAccepted = null;
    private Integer invokeId = null;
    private ServiceChoice serviceChoice;
    private ObjectId desiredObjectId;
    private List<PropertyIdentifier> desiredPropertyIds;

    /**
     *
     * @param service already built APDU service info
     * @param unprocessedHexString identify ObjectId and PropertyIdentifier
     */
    public PropertyRequestBuilder(Service service, String unprocessedHexString) {
        desiredPropertyIds = new ArrayList<>();
        withService(service);
        withUnprocessedHexString(unprocessedHexString);
    }

    public PropertyRequestBuilder withService(Service service) {
        this.service = service;
        this.pduType = service.getPduType();
        this.isSegmentedResponseAccepted = service.isWillAcceptSegmentedResponse();
        this.maxAdpuOctetLenghtAccepted = service.getMaxAcceptedPduSize();
        this.invokeId = service.getInvokeId();
        this.serviceChoice = service.getServiceChoice();
        return this;
    }

    public PropertyRequestBuilder withUnprocessedHexString(String unprocessedHexString) {
        this.unprocessedHexString = unprocessedHexString;
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
        return this;
    }

    public PropertyRequestBuilder withSource(String source) {
        this.source = source;
        return this;
    }

    public PropertyRequestBuilder withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public PropertyRequestBuilder withDesiredObjectId(ObjectId objectId) {
        this.desiredObjectId = objectId;
        return this;
    }

    public PropertyRequestBuilder withDesiredPropertyId(PropertyIdentifier propertyId) {
        desiredPropertyIds.add(propertyId);
        return this;
    }

    public PropertyRequest build() {
        PropertyRequest request = null;
        if (service == null) {
            return null;
        }

        request = new PropertyRequest();
        request.setSource(source);
        request.setDestination(destination);
        request.setPduType(pduType);
        request.setSegmentedResponseAccepted(isSegmentedResponseAccepted);
        request.setMaxAdpuOctetLenghtAccepted(maxAdpuOctetLenghtAccepted);
        request.setInvokeId(invokeId);
        request.setServiceChoice(serviceChoice);
        request.setDesiredObjectId(desiredObjectId);
        request.setDesiredPropertyIds(desiredPropertyIds);
        return request;
    }





}
