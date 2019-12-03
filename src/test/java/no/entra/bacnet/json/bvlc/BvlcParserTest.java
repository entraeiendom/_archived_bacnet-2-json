package no.entra.bacnet.json.bvlc;

import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BvlcParserTest {

    public static final String bacnetHexString = "81040018092f510cbac00120ffff00ff10080a07ae1a07ae";

    @Test
    void parse() throws UnknownHostException {
        BvlcResult result = BvlcParser.parse(bacnetHexString);
        assertNotNull(result);
        Bvlc bvlc = result.getBvlc();
        assertNotNull(bvlc);
        assertEquals(BvlcFunction.ForwardedNpdu, bvlc.getFunction());
        assertEquals(24, bvlc.getFullMessageLength());
        assertEquals("9.47.81.12", bvlc.getOriginatingDeviceIp());
        assertEquals(47808, bvlc.getPort());

    }
}