package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;

public enum UnconfirmedServiceChoice implements ServiceChoice {
    IAm("00"),
    IHave("01"),
    UnconfirmedCovNotification("02"),
    UnconfirmedEventNotification("03"),
    UnconfirmedPrivateTransfer("04"),
    UnconfirmedTextMessage("05"),
    TimeSynchronization("06"),
    WhoHas("07"),
    WhoIs("08"),
    UtcTimeSynchronization("09"),
    WriteGroup("0a"),
    UnconfirmedCovNotificationMultiple ("0b");

    private String serviceChoiceHex;

    public static UnconfirmedServiceChoice fromServiceChoiceHex(String hexString) {
        for (UnconfirmedServiceChoice type : values()) {
            if (type.getServiceChoiceHex().equals(hexString)) {
                return type;
            }
        }
        return null;
    }


    public static UnconfirmedServiceChoice fromOctet(Octet serviceChoiceOctet) throws NumberFormatException {
        if (serviceChoiceOctet == null) {
            return null;
        }
        return fromServiceChoiceHex(serviceChoiceOctet.toString());
    }


    public String getServiceChoiceHex() {
        return serviceChoiceHex;
    }

    private UnconfirmedServiceChoice(String hexString) {
        this.serviceChoiceHex = hexString;
    }

}
