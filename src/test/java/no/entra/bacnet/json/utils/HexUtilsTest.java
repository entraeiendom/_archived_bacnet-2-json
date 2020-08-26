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
    void parseUtf16() {
        String utf16Hex = "0031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e00420065007200650067006e0069006e006700650072002e0045006e006500720067006900420065007200650067006e0069006e006700650072003400330033003300300031002e0044006100670042007600690031002d00310031002e004400610067004200760076";
        String plainText = HexUtils.parseUTF16(utf16Hex);
        assertEquals("1/Programming.Energi.Beregninger.EnergiBeregninger433301.DagBvi1-11.DagBvv", plainText);
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
    void stringToBitString() {
        String hexString = "02000205";
        String expected = "00000010000000000000001000000101";
        String hexAsBits = HexUtils.toBitString(hexString);
        assertEquals(expected, hexAsBits);
        assertEquals(32, hexAsBits.length());
    }

    @Test
    void octetsToStringTest() {
        Octet[] octets = {Octet.fromHexString("01"), Octet.fromHexString("23")};
        assertEquals("0123", octetsToString(octets));
    }

    @Test
    void toIntTest() {
        assertThrows(IllegalArgumentException.class, () -> toInt(""));
        String isnull = null;
        assertThrows(IllegalArgumentException.class, () -> toInt(isnull));
        assertEquals(12, toInt("0c"));
    }

    @Test
    void toFloatTest() {
        assertThrows(IllegalArgumentException.class, () -> toFloat(""));
        String isnull = null;
        assertThrows(IllegalArgumentException.class, () -> toFloat(isnull));
        assertEquals(Float.valueOf("20.6"), toFloat("41a4cccd"));
    }

    @Test
    void binaryToHexTest() {
        String bitString = "00000010000000000000001000000101";
        String expected = "02000205";
        String hexString = binaryToHex(bitString);
        assertEquals(expected, hexString);
    }
}