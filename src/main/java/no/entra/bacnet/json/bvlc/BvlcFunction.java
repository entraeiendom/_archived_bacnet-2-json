package no.entra.bacnet.json.bvlc;

import no.entra.bacnet.Octet;

public enum BvlcFunction {
    ForwardedNpdu("04");
    private String bvlcFunctionHex;

    public static BvlcFunction fromBvlcFuntionHex(String hexString) {
        for (BvlcFunction type : values()) {
            if (type.getBvlcFunctionHex().equals(hexString)) {
                return type;
            }
        }
        return null;
    }


    public static BvlcFunction fromOctet(Octet bvlcFuntionOctet) throws NumberFormatException {
        if (bvlcFuntionOctet == null) {
            return null;
        }
        return fromBvlcFuntionHex(bvlcFuntionOctet.toString());
    }


    public String getBvlcFunctionHex() {
        return bvlcFunctionHex;
    }

    private BvlcFunction(String hexString) {
        this.bvlcFunctionHex = hexString;
    }
}
