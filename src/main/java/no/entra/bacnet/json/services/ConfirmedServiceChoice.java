package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;

public enum ConfirmedServiceChoice implements ServiceChoice {
    AcknowledgeAlarm("00"),
    ConfirmedCovNotification("01"),
    ConfirmedCovNotificationMultiple ("1f"),
    ConfirmedEventNotification("02"),
    GetAlarmSummary("03"),
    GetEnrollmentSummary("04"),
    GetEventInformation("1d"),
    LifeSafetyOperation("1b"),
    SubscribeCov("05"),
    SubscribeCovProperty("1c"),
    SubscribeCovPropertyMultiple ("1e"),
    AtomicReadFile("06"),
    AtomicWriteFile ("07"),
    AddListElement("08"),
    RemoveListElement("09"),
    CreateObject("0a"),
    DeleteObject("0b"),
    ReadProperty("0c"),
    ReadPropertyMultiple("0e"),
    ReadRange("1a"),
    WriteProperty("0f"),
    WritePropertyMultiple("10"),
    DeviceCommunicationControl("11"),
    ConfirmedPrivateTransfer("12"),
    ConfirmedTextMessage("13"),
    ReinitializeDevice("14"),
    VtOpen ("15"),
    VtClose ("16"),
    VtData ("17"),
    Authenticate ("18"),
    RequestKey ("19"),
    ReadPropertyConditional("0d");

    private String serviceChoiceHex;

    public static ConfirmedServiceChoice fromServiceChoiceHex(String hexString) {
        for (ConfirmedServiceChoice type : values()) {
            if (type.getServiceChoiceHex().equals(hexString)) {
                return type;
            }
        }
        return null;
    }


    public static ConfirmedServiceChoice fromOctet(Octet serviceChoiceOctet) throws NumberFormatException {
        if (serviceChoiceOctet == null) {
            return null;
        }
        return fromServiceChoiceHex(serviceChoiceOctet.toString());
    }


    public String getServiceChoiceHex() {
        return serviceChoiceHex;
    }

    private ConfirmedServiceChoice(String hexString) {
        this.serviceChoiceHex = hexString;
    }

}
