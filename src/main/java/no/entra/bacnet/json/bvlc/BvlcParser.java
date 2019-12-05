package no.entra.bacnet.json.bvlc;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;

public class BvlcParser {

    public static BvlcResult parse(String bacnetHexString) {
        BvlcResult result = null;
        OctetReader bvlcReader = new OctetReader(bacnetHexString);
        if (bvlcReader == null) {
            return null;
        }
        Octet type = bvlcReader.next();
        if (type == null || !type.equals(Octet.fromHexString("81"))) {
            return null;
        }
        Octet functionOctet = bvlcReader.next();
        BvlcFunction function = BvlcFunction.fromOctet(functionOctet);
        Octet[] messageLength = bvlcReader.nextOctets(2); //Length is two octets
        Bvlc bvlc = new BvlcBuilder(function).withMessageLength(messageLength).build();
        result = new BvlcResult(bvlc, bvlcReader.unprocessedHexString());

        if (bvlc.getFunction().equals(BvlcFunction.ForwardedNpdu)) {
            //Add BBMD forwarding info for messages routed between Bacnet subnets.
            result = addForwardingInfo(bvlc, bvlcReader);
        }
        return result;
    }

    static BvlcResult addForwardingInfo(Bvlc bvlc, OctetReader bvlcReader) {
        BvlcResult result = null;
        Octet[] originatingAddressOctet = bvlcReader.nextOctets(4); //Specified in spec.
        Octet[] port = bvlcReader.nextOctets(2);
        bvlc.setOriginatingDeviceIp(originatingAddressOctet);
        bvlc.setPort(port);
        String unprocessedBacnetHexString = bvlcReader.unprocessedHexString();
        result = new BvlcResult(bvlc, unprocessedBacnetHexString);

        return result;
    }
}
