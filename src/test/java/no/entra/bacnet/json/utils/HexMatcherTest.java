package no.entra.bacnet.json.utils;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.utils.HexMatcher.isValidHex;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HexMatcherTest {

    @Test
    void isValidHexTest() {
        String valid =  "123456789abcdef";
        assertTrue(isValidHex(valid));
        String invalid = "123456789gggg";
        assertFalse(isValidHex(invalid));
    }
}