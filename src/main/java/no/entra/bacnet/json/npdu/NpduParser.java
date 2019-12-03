package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;

public class NpduParser {

    public static NpduResult parse(String bacnetHexString) {
        NpduResult result = null;
        OctetReader bvlcReader = new OctetReader(bacnetHexString);
        if (bvlcReader == null) {
            return null;
        }
        Octet version = bvlcReader.next();
        if (version == null || !version.equals(Npdu.version)) {
            return null;
        }
        Octet control = bvlcReader.next();
        Npdu npdu = new NpduBuilder(control).build();

        String unprocessedHexString = bvlcReader.unprocessedHexString();
        result = new NpduResult(npdu, unprocessedHexString);
        return result;
    }

}
