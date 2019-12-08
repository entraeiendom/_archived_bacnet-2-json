package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;

public enum SDContextTag {
    PropertyIdentifier('0'),
    SequenceNumber('1'),
    EventObjectIdentifier('2'),
    TimeStamp('3'),
    NotificationClass('4'),
    Priority('5'),
    EventType('6'),
    NotifyType('8'),
    AckRequired('9'),
    FromState('A'),
    ToState('B');

    private char contextTagChar;

    public static SDContextTag fromContextTagChar(char contextTagChar) {
        for (SDContextTag type : values()) {
            if (type.getContextTagChar() == contextTagChar) {
                return type;
            }
        }
        return null;
    }

    public static SDContextTag fromOctet(Octet contextTagOctet) {
        if (contextTagOctet == null) {
            return null;
        }
        return fromContextTagChar(contextTagOctet.getFirstNibble());
    }


    public char getContextTagChar() {
        return contextTagChar;
    }

    private SDContextTag(char contextTagChar) {
        this.contextTagChar = contextTagChar;
    }
}
