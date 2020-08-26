package no.entra.bacnet.json.reader;

import no.entra.bacnet.Octet;

import java.util.ArrayList;
import java.util.List;

import static no.entra.bacnet.json.utils.HexMatcher.isValidHex;


/**
 * Enable readaing a HexString Octet by Octet.
 * An Octet has two nibbles.
 * Each nibble is one Hexadecimal character.
 */
public class OctetReader {

    //Zero based pointer to where you last read.
    private int currentPos = 0;
    private final String hexString;

    /**
     *
     * @param hexString 0-9a-f
     * @throws IllegalArgumentException if any character is not Hexadecimal.
     */
    public OctetReader(String hexString) throws IllegalArgumentException {
        if (isValidHex(hexString)) {
            this.hexString = hexString;
        } else {
            throw new IllegalArgumentException("HexString may only contain 0-9a-f.");
        }
    }

    public Octet next() {
        Octet octet = null;
        if (hasNext()) {
            char char1 = hexString.charAt(currentPos);
            char char2 = hexString.charAt(currentPos + 1);
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

    public String next(int numberOfOctets) throws IllegalStateException {
        String nextString = "";
        for (int i = 0; i < numberOfOctets; i++) {
            if (hasNext()) {
                nextString += next().toString();
            } else {
                throw new IllegalStateException("You requested: " + numberOfOctets + ". Only " + (i +1) + " is available.");
            }
        }
        return nextString;
    }

    public Octet[] nextOctets(int numberOfOctets) throws IllegalStateException {
        List<Octet> nextOctets = new ArrayList<>(numberOfOctets);
        for (int i = 0; i < numberOfOctets; i++) {
            if (hasNext()) {
                nextOctets.add(next());
            } else {
                throw new IllegalStateException("You requested: " + numberOfOctets + ". Only " + (i +1) + " is available.");
            }
        }
        Octet[] arr = new Octet[nextOctets.size()];
        arr = nextOctets.toArray(arr);
        return arr;
    }
    public String unprocessedHexString() {
        return hexString.substring(currentPos);
    }

    public void fastForward(int numberOfOctets) {
        nextOctets(numberOfOctets); //Ignore the returned octets
    }

    public int countOctets() {
        return hexString.length()/2;
    }
}
