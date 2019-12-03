package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class NpduParser {
    private static final Logger log = getLogger(NpduParser.class);

    public static NpduResult parse(String bacnetHexString) {
        NpduResult result = null;
        OctetReader npduReader = new OctetReader(bacnetHexString);
        if (npduReader == null) {
            return null;
        }
        Octet version = npduReader.next();
        if (version == null || !version.equals(Npdu.version)) {
            return null;
        }
        Octet controlOctet = npduReader.next();
        Npdu npdu = new NpduBuilder(controlOctet).build();
        NpduControl control = npdu.getControl();
        switch (control) {
            case NormalMessage:
                //TODO Do noting?
                break;
            case DestinationSpecifier:
                result = addDestinationSpecifierInfo(npdu, npduReader);
                break;
            default:
                log.debug("No processing available for Npdu Control: {}. BacnetHexString: {}", controlOctet, bacnetHexString);
                result.setParsedOk(false);
        }

        String unprocessedHexString = npduReader.unprocessedHexString();
        result = new NpduResult(npdu, unprocessedHexString);
        return result;
    }

    static NpduResult addDestinationSpecifierInfo(Npdu npdu, OctetReader npduReader) {
        NpduResult result = null;
        Octet[] destinationNetworkAddress = npduReader.nextOctets(2);
        npdu.setDestinationNetworkAddress(destinationNetworkAddress);
        Octet destinationMacLayerAddress = npduReader.next();
        npdu.setDestinationMacLayerAddress(destinationMacLayerAddress);
        Octet hopCount = npduReader.next();
        npdu.setHopCount(hopCount);
        String unprocessedHexString = npduReader.unprocessedHexString();
        result = new NpduResult(npdu, unprocessedHexString);
        return result;
    }

}
