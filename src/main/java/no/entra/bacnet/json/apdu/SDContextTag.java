package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SDContextTag {
    private static final Logger log = getLogger(SDContextTag.class);

    private final Octet contextTag;

    public static final String TAG0LENGTH1 = "09";
    public static final String TAG0LENGTH2 = "0a";
    public static final String TAG0LENGTH3 = "0b";
    public static final String TAG0LENGTH4 = "0c";
    public static final String TAG1LENGTH1 = "19";
    public static final String TAG1LENGTH2 = "1a";
    public static final String TAG1LENGTH3 = "1b";
    public static final String TAG1LENGTH4 = "1c";
    public static final String TAG2LENGTH1 = "29";
    public static final String TAG2LENGTH2 = "2a";
    public static final String TAG2LENGTH3 = "2b";
    public static final String TAG2LENGTH4 = "2c";
    public static final String TAG3LENGTH1 = "39";
    public static final String TAG3LENGTH2 = "3a";
    public static final String TAG3LENGTH3 = "3b";
    public static final String TAG3LENGTH4 = "3c";

    public SDContextTag(Octet contextTag) {
        this.contextTag = contextTag;
    }

    public int findEnumeration() {
        int enumeration = -1;
        char nibble = contextTag.getFirstNibble();
        try {
           enumeration = Character.getNumericValue(nibble);
        } catch (NumberFormatException e) {
            log.trace("Could not find integer from {}. Expected 0-9", nibble);
        }
        return enumeration;
    }

    public int findLength() {
        int length = -1;
        char nibble = contextTag.getSecondNibble();
        switch (nibble) {
            case '9':
                length = 1;
                break;
            case 'a':
                length = 2;
                break;
            case 'b':
                length = 3;
                break;
            case 'c':
                length = 4;
                break;
            default:
                length = -1;
        }
        return length;
    }
}
