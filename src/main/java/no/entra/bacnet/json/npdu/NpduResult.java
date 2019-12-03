package no.entra.bacnet.json.npdu;

public class NpduResult {
    private final Npdu npdu;
    private final String unprocessedHexString;
    private boolean parsedOk = true;

    public NpduResult(Npdu npdu, String unprocessedHexString) {
        this.npdu = npdu;
        this.unprocessedHexString = unprocessedHexString;
    }

    public Npdu getNpdu() {
        return npdu;
    }

    public String getUnprocessedHexString() {
        return unprocessedHexString;
    }

    public boolean isParsedOk() {
        return parsedOk;
    }

    public void setParsedOk(boolean parsedOk) {
        this.parsedOk = parsedOk;
    }
}
