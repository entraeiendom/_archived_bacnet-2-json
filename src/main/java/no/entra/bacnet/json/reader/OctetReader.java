package no.entra.bacnet.json.reader;

import no.entra.bacnet.Octet;

public class OctetReader {

    //Zero based pointer to where you last read.
    private int currentPos = 0;
    private final String hexString;

    public OctetReader(String hexString) {
        this.hexString = hexString;
    }

    public Octet next() {
        Octet octet = null;
        if (hasNext()) {
            char char1 = hexString.charAt(currentPos + 1);
            char char2 = hexString.charAt(currentPos + 2);
            octet = new Octet(char1, char2);
            currentPos += 2;
        }
        return octet;
    }

    public boolean hasNext() {
        boolean hasNext = false;
        if (hexString != null) {
            if (hexString.length() >= currentPos +2) {
                return true;
            }
        }
        return hasNext;
    }

    public int getCurrentPos() {
        return currentPos;
    }
}
