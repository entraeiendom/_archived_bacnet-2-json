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

    private final char pduTypeChar;

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
        return fromPduTypeChar(pduTypeOctet.getFirstNibble());
    }


    public char getPduTypeChar() {
        return pduTypeChar;
    }

    // enum constructor - cannot be public or protected
    PduType(char pduTypeChar) {
        this.pduTypeChar = pduTypeChar;
    }

}
