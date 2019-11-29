package no.entra.bacnet.json;

import no.entra.bacnet.Octet;

public enum PropertyId {
    units(Octet.fromHexString("75")),
    presentValue(Octet.fromHexString("55")),
    objectName(Octet.fromHexString("4d")),
    description(Octet.fromHexString("1c"));

    private Octet pid;

    public static PropertyId fromPidHex(String hexString) {
        for (PropertyId type : values()) {
            if (type.getPid().equals(Octet.fromHexString(hexString))) {
                return type;
            }
        }
        return null;
    }

    public static PropertyId fromOctet(Octet octet) {
        for (PropertyId type : values()) {
            if (type.getPid().equals(octet)) {
                return type;
            }
        }
        return null;
    }

    public Octet getPid() {
        return pid;
    }

    private PropertyId(Octet pid) {
        this.pid = pid;
    }

}
