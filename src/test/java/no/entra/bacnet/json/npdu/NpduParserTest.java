package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;
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
}