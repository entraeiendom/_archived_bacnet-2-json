package no.entra.bacnet.json.bvlc;

public class BvlcResult {
    private final Bvlc bvlc;
    private final String unprocessedHexString;

    public BvlcResult(Bvlc bvlc, String unprocessedHexString) {
        this.bvlc = bvlc;
        this.unprocessedHexString = unprocessedHexString;
    }

    public Bvlc getBvlc() {
        return bvlc;
    }

    public String getUnprocessedHexString() {
        return unprocessedHexString;
    }
}
