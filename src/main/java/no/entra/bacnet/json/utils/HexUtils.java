package no.entra.bacnet.json.utils;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;

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

    public static int toInt(String hexString) throws IllegalArgumentException{
        if (hexString == null) {
            throw new IllegalArgumentException("hexString may not be null.");
        }
        return Integer.parseInt(hexString, 16);
    }
    public static int toInt(Octet octet) throws IllegalArgumentException{
        if (octet == null) {
            throw new IllegalArgumentException("octet may not be null.");
        }
        return Integer.parseInt(octet.toString(), 16);
    }
}
