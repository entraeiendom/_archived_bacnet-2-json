package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SDContextTag {
    private static final Logger log = getLogger(SDContextTag.class);

    private final Octet contextTag;

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
        try {
            length = Character.getNumericValue(nibble);
        } catch (NumberFormatException e) {
            log.trace("Could not find integer from {}. Expected 0-9", nibble);
        }
        return length;
    }
}
