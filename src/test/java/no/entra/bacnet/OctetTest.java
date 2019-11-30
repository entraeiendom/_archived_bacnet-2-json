package no.entra.bacnet;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.utils.HexMatcher.isValidHexChar;
import static org.junit.jupiter.api.Assertions.*;

class OctetTest {

    @Test
    void createFromChars() {
        char nibble1 = 'a';
        char nibble2 = '0';
        Octet octet = new Octet(nibble1, nibble2);
        assertNotNull(octet);
        assertEquals("a0", octet.toString());
        assertThrows(IllegalArgumentException.class,() -> {
            Octet failedOctet = new Octet(nibble1, 'g');
        });
    }

    @Test
    void isValidHexCharTest() {
        assertTrue(isValidHexChar('0'));
        assertTrue(isValidHexChar('9'));
        assertTrue(isValidHexChar('a'));
        assertTrue(isValidHexChar('f'));
        assertFalse(isValidHexChar('F'));
        assertFalse(isValidHexChar('g'));

    }

    @Test
    void getOctet() {
    }

    @Test
    void fromHexString() {
    }

    @Test
    void toStringTest() {
    }

    @Test
    void equalsTest() {
        Octet first = Octet.fromHexString("0a");
        Octet second = Octet.fromHexString("0a");
        assertEquals(first, second);
    }
}