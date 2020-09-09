package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ArrayTag {
    private static final Logger log = getLogger(ArrayTag.class);

    private final Octet arrayTag;

    public static final Octet ARRAYLENGTH1 = new Octet("19");


    public ArrayTag(Octet arrayTag) {
        this.arrayTag = arrayTag;
    }

    public int findType() {
        int enumeration = -1;
        char nibble = arrayTag.getFirstNibble();
        try {
           enumeration = Character.getNumericValue(nibble);
        } catch (NumberFormatException e) {
            log.trace("Could not find integer from {}. Expected 0-9", nibble);
        }
        return enumeration;
    }

    public int findLength() {
        int length = -1;
        char nibble = arrayTag.getSecondNibble();
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
