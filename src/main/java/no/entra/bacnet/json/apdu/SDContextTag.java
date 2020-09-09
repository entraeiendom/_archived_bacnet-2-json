package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SDContextTag {
    private static final Logger log = getLogger(SDContextTag.class);

    private final Octet contextTag;

    public static final Octet TAG0LENGTH1 = new Octet("09");
    public static final Octet TAG0LENGTH2 = new Octet("0a");
    public static final Octet TAG0LENGTH3 = new Octet("0b");
    public static final Octet TAG0LENGTH4 = new Octet("0c");
    public static final Octet TAG1LENGTH1 = new Octet("19");
    public static final Octet TAG1LENGTH2 = new Octet("1a");
    public static final Octet TAG1LENGTH3 = new Octet("1b");
    public static final Octet TAG1LENGTH4 = new Octet("1c");
    public static final Octet TAG2LENGTH1 = new Octet("29");
    public static final Octet TAG2LENGTH2 = new Octet("2a");
    public static final Octet TAG2LENGTH3 = new Octet("2b");
    public static final Octet TAG2LENGTH4 = new Octet("2c");
    public static final Octet TAG3LENGTH1 = new Octet("39");
    public static final Octet TAG3LENGTH2 = new Octet("3a");
    public static final Octet TAG3LENGTH3 = new Octet("3b");
    public static final Octet TAG3LENGTH4 = new Octet("3c");

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
