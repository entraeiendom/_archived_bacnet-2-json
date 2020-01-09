package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.utils.HexUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static no.entra.bacnet.Octet.fromHexString;
import static org.junit.jupiter.api.Assertions.*;

class NpduParserTest {

    public static final String npduApduHexString = "0120ffff00ff10080a07ae1a07ae";

    @Test
    void parse() {
        NpduResult result = NpduParser.parse(npduApduHexString);
        assertNotNull(result);
        assertTrue(result.isParsedOk());
        Npdu npdu = result.getNpdu();
        assertNotNull(npdu);
        assertEquals("20", npdu.getControl().toString());
        Octet[] networkAddress = {fromHexString("ff"), fromHexString("ff")};
        assertTrue(Arrays.equals(networkAddress, npdu.getDestinationNetworkAddress()));
        assertEquals(fromHexString("00"), npdu.getDestinationMacLayerAddress());
        assertEquals(fromHexString("ff"), npdu.getHopCount());

        assertEquals("10080a07ae1a07ae", result.getUnprocessedHexString());
    }

    @Test
    void validateSadr() {
        String hexString = "01080961010c1001c40200000cc403c";
        //000007100310036002d004e004100450032002f004600430042002e004c006f00630061006c0020004100700070006c00690063006100740069006f006e002e005500520020006e00610074007400730065006e006b002000670075006c0076007600610072006d00650020003100200065007400670072003400330033003300300031002e0044006100670042007600690031002d00310031002e004400610067004200760076";
        NpduResult result = NpduParser.parse(hexString);
        assertNotNull(result);
        assertTrue(result.isParsedOk());
        Npdu npdu = result.getNpdu();
        assertNotNull(npdu);
        Octet[] sadr = npdu.getSourceMacLayerAddress();
        int sadrInt = HexUtils.toInt(sadr[0]);
        assertEquals("0c", sadr[0].toString());
        assertEquals(12, sadrInt);
    }
}