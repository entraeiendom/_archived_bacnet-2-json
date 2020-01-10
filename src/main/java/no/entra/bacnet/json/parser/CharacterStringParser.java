package no.entra.bacnet.json.parser;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static no.entra.bacnet.json.utils.HexByteConverter.hexStringToByteArray;
import static org.slf4j.LoggerFactory.getLogger;

public class CharacterStringParser {
    private static final Logger log = getLogger(CharacterStringParser.class);
    public static final Octet UTF_8_ENCODING = Octet.fromHexString("00");
    public static final Octet UTF_16_ENCODING = Octet.fromHexString("04");

    /*
    examples:
    7100 -> empty UTF-8
    7504... -> extended UTF-16 result
     */
    public static String decodeCharacterHexString(String hexString) {
        OctetReader encodedStringReader = new OctetReader(hexString);
        Octet applicationTag = encodedStringReader.next();
        Charset encoding = StandardCharsets.UTF_16;
        Octet encodingOctet = null;
        if (applicationTag.getFirstNibble() == '7' && applicationTag.getSecondNibble() == '1') {
            encodingOctet = encodedStringReader.next();
        }

        if (encodingOctet == UTF_8_ENCODING) {
            encoding = StandardCharsets.UTF_8;
        } else if (encodingOctet == UTF_16_ENCODING) {
            encoding = StandardCharsets.UTF_16;
        }

        String encodedHexText = encodedStringReader.unprocessedHexString();
        byte[] bytes = hexStringToByteArray(encodedHexText);
        String text = new String(bytes, encoding);
        /*
        if (applicationTag.getSecondNibble() == ExtendedValue) {
            log.debug("Expecting extended value");
            Octet valueLength = propertyReader.next();
            int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
            Octet encoding = propertyReader.next();
            String objectNameHex = propertyReader.next(valueOctetLength - 1);
            log.debug("ObjectNameHex: {}", objectNameHex);
            objectName = HexUtils.parseExtendedValue(encoding,objectNameHex);
            log.debug("The rest: {}", propertyReader.unprocessedHexString());
        } else {
            log.debug("Do not know what to do....");
            Octet next;
            do {
                next = propertyReader.next();
                log.debug("next: {}", next);
            } while (!next.equals(Octet.fromHexString("4f")));
        }
        */

        return text;
    }
}
