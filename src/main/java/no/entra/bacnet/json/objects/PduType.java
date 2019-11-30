package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;

public enum PduType {
    ConfirmedRequest('0'),
    UnconfirmedRequest('1'),
    SimpleAck('2'),
    ComplexAck('3'),
    SegmentACK('4'),
    Error('5'),
    Reject('6'),
    Abort('7');

    private char pduTypeChar;

    public static PduType fromPduTypeChar(char pduTypeChar) {
        for (PduType type : values()) {
            if (type.getPduTypeChar() == pduTypeChar) {
                return type;
            }
        }
        return null;
    }

    public static PduType fromOctet(Octet pduTypeOctet) {
        if (pduTypeOctet == null) {
            return null;
        }
        switch (pduTypeOctet.toString()) {
            case "00":
                return ConfirmedRequest;
            case "10":
                return UnconfirmedRequest;
            case "20":
                return SimpleAck;
            case "30":
                return ComplexAck;
            default:
                return null;
        }
    }


    public char getPduTypeChar() {
        return pduTypeChar;
    }

    // enum constructor - cannot be public or protected
    private PduType(char pduTypeChar) {
        this.pduTypeChar = pduTypeChar;
    }

}
