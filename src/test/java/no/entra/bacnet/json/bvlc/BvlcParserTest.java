package no.entra.bacnet.json.bvlc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BvlcParserTest {

    public static final String bacnetHexString = "810400180a3f510cbac00120ffff00ff10080a07ae1a07ae";

    @Test
    void parse() {
        BvlcResult result = BvlcParser.parse(bacnetHexString);
        assertNotNull(result);
        Bvlc bvlc = result.getBvlc();
        assertNotNull(bvlc);
        assertEquals(BvlcFunction.ForwardedNpdu, bvlc.getFunction());

    }
}