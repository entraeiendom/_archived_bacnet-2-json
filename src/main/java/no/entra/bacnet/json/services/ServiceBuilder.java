package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;

public class ServiceBuilder {
    private final PduType pduType;
    private Service service;
    private Octet serviceChoice;

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
           default:
               service = null;
       }
       return service;
    }
}
