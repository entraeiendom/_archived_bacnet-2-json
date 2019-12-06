package no.entra.bacnet.json.utils;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.utils.HexUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class HexUtilsTest {

    private static String DESCRIPTION_UCS2_HEX = "0053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e00520054003000300031";
    private static String EXPECTED_DESCRIPTION = "SOKP16-NAE4/FCB.434_101-1OU001.RT001";

    @Test
    void parseUCS2() {
        assertEquals(EXPECTED_DESCRIPTION, HexUtils.parseUCS2(DESCRIPTION_UCS2_HEX));
    }

    @Test
    void parseExtendedValue() {
        String description = HexUtils.parseExtendedValue(Octet.fromHexString("04"), DESCRIPTION_UCS2_HEX);
        assertEquals(EXPECTED_DESCRIPTION, description);
    }

    @Test
    void toBitStringTest() {
        char nibble = '8';
        assertEquals("1000", toBitString(nibble));
        nibble = '4';
        assertEquals("0100", toBitString(nibble));
    }

    @Test
    void isBitSetTest() {
        char nibble = '8';
        assertEquals("1000", toBitString(nibble));
        assertFalse(isBitSet(nibble, 0));
        assertTrue(isBitSet(nibble, 3));
        nibble = '4';
        assertEquals("0100", toBitString(nibble));
        assertFalse(isBitSet(nibble, 0));
        assertFalse(isBitSet(nibble, 1));
        assertTrue(isBitSet(nibble, 2));
        assertFalse(isBitSet(nibble, 3));
        nibble = 'a';
        assertEquals("1010", toBitString(nibble));
        assertFalse(isBitSet(nibble, 0));
        assertTrue(isBitSet(nibble, 1));
        assertFalse(isBitSet(nibble, 2));
        assertTrue(isBitSet(nibble, 3));
        nibble = 'f';
        assertEquals("1111", toBitString(nibble));
        assertTrue(isBitSet(nibble, 0));
        assertTrue(isBitSet(nibble, 1));
        assertTrue(isBitSet(nibble, 2));
        assertTrue(isBitSet(nibble, 3));

    }

    @Test
    void isBitStringSetTest() {
        String bits = "0101";
        assertTrue(isBitSet(bits, 0));
        assertFalse(isBitSet(bits, 1));
        assertTrue(isBitSet(bits, 2));
        assertFalse(isBitSet(bits, 3));
    }

    @Test
    void octetsToStringTest() {
        Octet[] octets = {Octet.fromHexString("01"), Octet.fromHexString("23")};
        assertEquals("0123", octetsToString(octets));
    }
}