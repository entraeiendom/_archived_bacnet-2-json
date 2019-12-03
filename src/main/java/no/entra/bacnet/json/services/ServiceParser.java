package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.reader.OctetReader;

public class ServiceParser {

    public static Service fromApduHexString(String apduHexString) {
        OctetReader serviceReader = new OctetReader(apduHexString);
        Octet pduTypeOctet = serviceReader.next();
        PduType pduType = PduType.fromOctet(pduTypeOctet);
        Octet serviceChoiceOctet = serviceReader.next();
        Service service = new ServiceBuilder(pduType).withServiceChoice(serviceChoiceOctet).build();
        return service;
    }
}
