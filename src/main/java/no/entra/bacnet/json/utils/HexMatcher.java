package no.entra.bacnet.json.utils;

import java.util.regex.Pattern;

public class HexMatcher {

    public static final Pattern REGEX_PATTERN = Pattern.compile("^[0-9a-f]+$");

    public static boolean isValidHex(String hexString) {
        if (hexString == null || hexString.isEmpty()) {
            return false;
        }
        return REGEX_PATTERN.matcher(hexString).matches();
    }

    public static boolean isValidHexChar(char nibble) {
        String nibbleString = String.valueOf(nibble);
        return REGEX_PATTERN.matcher(nibbleString).matches();
    }
}
