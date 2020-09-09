package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ApplicationTag {
    private static final Logger log = getLogger(ApplicationTag.class);

    private final Octet appliationTag;

    public static final Octet APPTAG4LENGTH4 = new Octet("44");
    public static final Octet APPTAG8LENGTH2 = new Octet("82");

    public static final int INT_VALUE = 1;
    public static final int REAL_VALUE = 4;

    public ApplicationTag(Octet appliationTag) {
        this.appliationTag = appliationTag;
    }

    public int findType() {
        int enumeration = -1;
        char nibble = appliationTag.getFirstNibble();
        try {
           enumeration = Character.getNumericValue(nibble);
        } catch (NumberFormatException e) {
            log.trace("Could not find integer from {}. Expected 0-9", nibble);
        }
        return enumeration;
    }

    public int findLength() {
        int length = -1;
        char nibble = appliationTag.getSecondNibble();
        switch (nibble) {
            case '1':
                length = 1;
                break;
            case '2':
                length = 2;
                break;
            case '3':
                length = 3;
                break;
            case '4':
                length = 4;
                break;
            case '5':
                length = 5;
                break;
            case '6':
                length = 6;
                break;
            case '7':
                length = 7;
                break;
            case '8':
                length = 8;
                break;
            default:
                length = -1;
        }
        return length;
    }
}
