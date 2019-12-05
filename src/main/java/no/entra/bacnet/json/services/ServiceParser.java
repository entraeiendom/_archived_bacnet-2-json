package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.utils.HexUtils;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ServiceParser {
    private static final Logger log = getLogger(ServiceParser.class);

    public static Service fromApduHexString(String apduHexString) {
        Service service = null;
        Octet serviceChoiceOctet = null;
        OctetReader serviceReader = new OctetReader(apduHexString);
        Octet pduTypeOctet = serviceReader.next();
        log.debug("PDU Type {} in bits: {}{}", pduTypeOctet,HexUtils.toBitString(pduTypeOctet.getFirstNibble()),HexUtils.toBitString(pduTypeOctet.getSecondNibble()));
        PduType pduType = PduType.fromOctet(pduTypeOctet);
        if (pduType != null) {
            if (pduTypeOctet.getSecondNibble() != '0') {
                //TODO need to handle set sender rec error etc.
            }
            serviceChoiceOctet = serviceReader.next();
            service = new ServiceBuilder(pduType).withServiceChoice(serviceChoiceOctet).build();
            service.setUnprocessedHexString(serviceReader.unprocessedHexString());
        } else {
            log.debug("Could not find PduType. pduTypeOctet: {}, from apduHexString: {}", pduTypeOctet, apduHexString);
        }
        return service;
    }
}
