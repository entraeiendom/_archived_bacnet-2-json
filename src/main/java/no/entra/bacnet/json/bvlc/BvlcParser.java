package no.entra.bacnet.json.bvlc;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;

public class BvlcParser {

    public static BvlcResult parse(String bacnetHexString) {
        BvlcResult result = null;
        OctetReader bvlcReader = new OctetReader(bacnetHexString);
        if (bvlcReader != null) {
            Octet type = bvlcReader.next();
            if (type == Octet.fromHexString("81")) {
                Octet function = bvlcReader.next();

            }
        }
        return result;
    }
}
