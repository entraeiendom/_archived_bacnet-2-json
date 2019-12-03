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
        Octet[] messageLength = {bvlcReader.next(), bvlcReader.next()}; //Length is two octets
        Bvlc bvlc = new BvlcBuilder(function).withMessageLength(messageLength).build();
        result = new BvlcResult(bvlc, bvlcReader.unprocessedHexString());
        return result;
    }
}
