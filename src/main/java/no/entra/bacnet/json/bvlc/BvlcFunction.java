package no.entra.bacnet.json.bvlc;

import no.entra.bacnet.Octet;

public enum BvlcFunction {
    WriteBroadcastDistributionTable("01"),
    ReadBroadcastDistributionTable("02"),
    ReadBroadcastDistributionTableAck("03"),
    ForwardedNpdu("04"),
    RegisterForeignDevice("05"),
    ReadForeignDeviceTable("06"),
    ReadForeignDeviceTableAck("07"),
    DeleteForeignDeviceTableEntry("08"),
    DistributeBroadcastToNetwork("09"),
    OriginalUnicastNpdu("0a"),
    OriginalBroadcastNpdu("0b"),
    SecureBvll("0c");

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
