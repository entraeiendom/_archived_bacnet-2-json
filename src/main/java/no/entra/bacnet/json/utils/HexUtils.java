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

    public static String parseUTF16(String hexString) {
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

    /**
     * Generate hexString from a integer. Prefix with 0's to reach desired minimal length
     * @param value integer value
     * @param minHexLength result will be at least this length
     * @return hexString from the value. If hex is longer than minHexLength the full hexString is returned.
     */
    public static String intToHexString(int value, int minHexLength) throws IllegalArgumentException{
        String hexNumber = Integer.toHexString(value);
        if (hexNumber.length() < minHexLength) {
            return String.format("%1$" + minHexLength + "s", hexNumber).replace(' ', '0');
        } else {
            return hexNumber;
        }
    }

    public static Octet octetFromInt(int num) {
        String intHex =  Integer.toHexString(num);
        if (intHex.length() == 1) {
            return new Octet("0" + intHex);
        } else {
            return new Octet(intHex);
        }
    }

    public static int toInt(char length) throws IllegalArgumentException {
        try {
            return Integer.parseInt(String.valueOf(length),16);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("length may not be null. length must be 0-9, a-f");
        }
    }

    public static int toInt(String hexString) throws IllegalArgumentException {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("hexString may not be null.");
        }
        return Integer.parseInt(hexString, 16);
    }

    public static int toInt(Octet octet) throws IllegalArgumentException {
        if (octet == null) {
            throw new IllegalArgumentException("octet may not be null.");
        }
        return toInt(octet.toString());
    }

    public static int toInt(Octet[] octets) throws IllegalArgumentException {
        if (octets == null) {
            throw new IllegalArgumentException("octet may not be null.");
        }
        return toInt(octetsToString(octets));
    }

    public static float toFloat(String hexString) throws IllegalArgumentException {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("hexString may not be null.");
        }
        Long realLong = Long.parseLong(hexString, 16);
        Float real = Float.intBitsToFloat(realLong.intValue());
        return real;
    }



    public static long toLong(String hexString) {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("hexString may not be null.");
        }
        if (hexString.length() == 16) {
            return (toLong(hexString.substring(0, 1)) << 60)
                    | toLong(hexString.substring(1));
        }
        return Long.parseLong(hexString, 16);
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

    public static String octetsToString(Octet[] octets) {
        String value = null;
        if (octets != null) {
            value = "";
            for (Octet octet : octets) {
                value += octet;
            }
        }
        return value;
    }

    public static String toBitString(String hexString) {
        String bitString = "";
        char[] chars = hexString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char nibble = chars[i];
            bitString += toBitString(nibble);
        }
        return bitString;
    }

    public static String binaryToHex(String bitString) {
        int decimal = Integer.parseInt(bitString,2);
        String hexStr = Integer.toString(decimal,16);
        int lenght = 8;
        return String.format("%1$" + lenght + "s", hexStr).replace(' ', '0');
    }
}
