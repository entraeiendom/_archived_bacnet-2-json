package no.entra.bacnet.json.request;

import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.services.ServiceChoice;

import java.util.ArrayList;
import java.util.List;

public class PropertyRequest {
    String source;
    String destination;
    PduType pduType; //eg Confirmed-Req
    boolean isSegmentedResponseAccepted = false;
    Integer maxAdpuOctetLenghtAccepted = null;
    Integer invokeId = null;
    ServiceChoice serviceChoice;
    ObjectId desiredObjectId;
    List<PropertyIdentifier> desiredPropertyIds = new ArrayList<>();

}
