package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.utils.HexUtils;
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

        if (npdu.isDestinationAvailable()) {
            result = addDestinationSpecifierInfo(npdu, npduReader);
            if (result.isParsedOk()) {
                npdu = result.getNpdu();
                npduReader = new OctetReader(result.getUnprocessedHexString());
            }
        }
        if (npdu.isSourceAvailable()) {
            result = addSourceSpecifierInfo(npdu, npduReader);
            if (result.isParsedOk()) {
                npdu = result.getNpdu();
                npduReader = new OctetReader(result.getUnprocessedHexString());
            }
        }
        if (result == null) {
            String unprocessedHexString = npduReader.unprocessedHexString();
            result = new NpduResult(npdu, unprocessedHexString);
        }
        return result;
    }

    static NpduResult addSourceSpecifierInfo(Npdu npdu, OctetReader npduReader) {
        NpduResult result = null;
        Octet[] sourceNetworkAddress = npduReader.nextOctets(2);
        npdu.setSourceNetworkAddress(sourceNetworkAddress);
        Octet sourceMacLayerAddressNumberOfOctets = npduReader.next();
        int readOctetLength = HexUtils.toInt(sourceMacLayerAddressNumberOfOctets);
        Octet[] sourceMacLayerAddress = npduReader.nextOctets(readOctetLength);
        npdu.setSourceMacLayerAddress(sourceMacLayerAddress);
//        Octet hopCount = npduReader.next();
//        npdu.setHopCount(hopCount);
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
