package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;

public class UnconfirmedService extends Service {
    public UnconfirmedService(PduType pduType, Octet serviceChoice) {
        super(pduType, UnconfirmedServiceChoice.fromOctet(serviceChoice));
    }


}
