package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class PDTag {
    private static final Logger log = getLogger(PDTag.class);

    private final Octet pdTag;

    public static final Octet PDOpen2 = new Octet("2e");
    public static final Octet PDClose2 = new Octet("2f");
    public static final Octet PDOpen4 = new Octet("4e");
    public static final Octet PDClse4 = new Octet("4f");

    public PDTag(Octet pdTag) {
        this.pdTag = pdTag;
    }

    public int findEnumeration() {
        int enumeration = -1;
        char nibble = pdTag.getFirstNibble();
        try {
           enumeration = Character.getNumericValue(nibble);
        } catch (NumberFormatException e) {
            log.trace("Could not find integer from {}. Expected 0-9", nibble);
        }
        return enumeration;
    }

    public int findLength() {
        int length = -1;
        char nibble = pdTag.getSecondNibble();
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
