package no.entra.bacnet.json.utils;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import static no.entra.bacnet.json.Constants.ENCODING_UCS_2;
import static no.entra.bacnet.json.utils.HexByteConverter.hexStringToByteArray;
import static org.slf4j.LoggerFactory.getLogger;

public class HexUtils {
    private static final Logger log = getLogger(HexUtils.class);

    public static String parseUCS2(String hexString) {
        return parseUTF16(hexString);
    }

    private static String parseUTF16(String hexString) {
        return parseExtendedValue(ENCODING_UCS_2, hexString);
    }

    public static String parseExtendedValue(Octet encoding, String hexString) {
        String value = null;
        log.debug("ObjectNameHex: {}", hexString);
        if (encoding.equals(ENCODING_UCS_2)) {
            byte[] bytes = hexStringToByteArray(hexString);
            value = new String(bytes, StandardCharsets.UTF_16);
        }
        return value;
    }

    public static int toInt(char length) throws IllegalArgumentException {
        try {
            return Integer.parseInt(String.valueOf(length),16);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("length may not be null. length must be 0-9, a-f");
        }
    }

    public static int toInt(String hexString) throws IllegalArgumentException {
        if (hexString == null) {
            throw new IllegalArgumentException("hexString may not be null.");
        }
        return Integer.parseInt(hexString, 16);
    }

    public static int toInt(Octet octet) throws IllegalArgumentException {
        if (octet == null) {
            throw new IllegalArgumentException("octet may not be null.");
        }
        return Integer.parseInt(octet.toString(), 16);
    }

    public static String toBitString(char nibble) {
        String bitString = null;
        if (HexMatcher.isValidHexChar(nibble)) {
            int nibbleAsInt = toInt(nibble);
            bitString = Integer.toBinaryString(nibbleAsInt);
        }
        while (bitString.length() < 4) {
            bitString = "0" + bitString;
        }
        return bitString;
    }

    public static boolean isBitSet(char nibble, int positionRightToLeft) throws IllegalArgumentException {
        String bitString = toBitString(nibble);
        return isBitSet(bitString, positionRightToLeft);
    }

    protected static BitSet fromString(String binary) {
        BitSet bitset = new BitSet(binary.length());
        int len = binary.length();
        for (int i = len-1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                bitset.set(len-i-1);
            }
        }
        return bitset;
    }

    /**
     *
     * @param binary required to be 4 chars in length.
     * @param positionRightToLeft
     * @return true if bit is set.
     */
    protected static boolean isBitSet(String binary, int positionRightToLeft) {

        boolean bitIsSet = false;
        if (binary != null && binary.length() == 4) {
            BitSet bitSet = fromString(binary);
            bitIsSet = bitSet.get(positionRightToLeft);
        } else {
            throw new IllegalArgumentException("bitString must be of length 4");
        }
        return bitIsSet;
    }
}
