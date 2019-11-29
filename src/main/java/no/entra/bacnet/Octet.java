package no.entra.bacnet;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * String consisting of two hex chars.
 */
public class Octet {
    public static final Pattern REGEX_PATTERN = Pattern.compile("^\\p{XDigit}+$");
    private final char[] octet;

    public Octet(String hexString) {
        if (hexString != null && hexString.length() == 2) {
            if (isValidHexChar(hexString.charAt(0)) && isValidHexChar(hexString.charAt(1))) {
                octet = new char[]{hexString.charAt(0), hexString.charAt(1)};
            } else {
                throw new IllegalArgumentException("hexString may only be two character long. Each 0-F");
            }
        } else {
            throw new IllegalArgumentException("hexString may only be two character long. Each 0-F");
        }
    }

    public static boolean isValidHexChar(char nibble) {
        String nibbleString = String.valueOf(nibble);
        return REGEX_PATTERN.matcher(nibbleString).matches();
    }

    public char[] getOctet() {
        return octet;
    }

    /**
     *
     * @param hexString two char long
     * @return valid octet, if present.
     */
    public static Octet fromHexString(String hexString) {
        return new Octet(hexString);
    }

    @Override
    public String toString() {
        return String.valueOf(octet);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Octet) {
            return Arrays.equals(((Octet) obj).octet, this.octet);
        }
        return false;
    }
}
