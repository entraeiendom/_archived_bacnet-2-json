package no.entra.bacnet.json.npdu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NpduParserTest {

    public static final String npduApduHexString = "0120ffff00ff10080a07ae1a07ae";

    @Test
    void parse() {
        NpduResult result = NpduParser.parse(npduApduHexString);
        assertNotNull(result);
        assertNotNull(result.getNpdu());
//        assertEquals("10080a07ae1a07ae", result.getUnprocessedHexString());
    }
}