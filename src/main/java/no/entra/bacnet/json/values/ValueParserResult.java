package no.entra.bacnet.json.values;

public class ValueParserResult {

    private final String hexString;
    private Value value = null;
    private String status = "";
    private RuntimeException error = null;
    private String unparsedHexString = null;

    public ValueParserResult(String hexString, Value value) {
        this.hexString = hexString;
        this.value = value;
        this.status = "OK";
    }


    public ValueParserResult(String hexString, String status) {
        this.hexString = hexString;
        this.status = status;
    }
    public ValueParserResult(String hexString, String status, RuntimeException error) {
        this.hexString = hexString;
        this.status = status;
        this.error = error;
    }

    public String getHexString() {
        return hexString;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RuntimeException getError() {
        return error;
    }

    public void setError(RuntimeException error) {
        this.error = error;
    }

    public void setUnparsedHexString(String unparsedHexString) {
        this.unparsedHexString = unparsedHexString;
    }

    public String getUnparsedHexString() {
        return unparsedHexString;
    }
}
