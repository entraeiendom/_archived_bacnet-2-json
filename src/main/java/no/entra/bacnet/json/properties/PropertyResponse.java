package no.entra.bacnet.json.properties;

import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.services.ServiceChoice;

public class PropertyResponse {
    String source;
    String destination;
    PduType pduType; //eg Complex-ACK
    boolean isSegmentedResponse = false;
    boolean hasMoreSegments = false;
    Integer maxAdpuOctetLenghtAccepted = null;
    Integer invokeId = null;
    ServiceChoice serviceChoice;
    ObjectId desiredObjectId;
    PropertyIdentifier desiredPropertyId;

}
