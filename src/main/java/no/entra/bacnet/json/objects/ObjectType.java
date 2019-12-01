package no.entra.bacnet.json.objects;

public enum ObjectType {
    AccessCredential(32),
    AccessDoor(30),
    AccessPoint(33),
    AccessRights(34),
    AccessUser(35),
    AccessZone(36),
    Accumulator(23),
    AlertEnrollment(52),
    AnalogInput(0),
    AnalogOutput(1),
    AnalogValue(2),
    Averaging(18),
    BinaryInput(3),
    BinaryLightingOutput(55),
    BinaryOutput(4),
    BinaryValue(5),
    BitstringValue(39),
    Calendar(6),
    Channel(53),
    CharacterstringValue(40),
    Command(7),
    CredentialDataInput(37),
    DatepatternValue(41),
    DateValue(42),
    DatetimepatternValue(43),
    DatetimeValue(44),
    Device(8),
    ElevatorGroup(57),
    Escalator(58),
    EventEnrollment(9),
    EventLog(25),
    File(10),
    GlobalGroup(26),
    Group(11),
    IntegerValue(45),
    LargeAnalogValue(46),
    LifeSafetyPoint(21),
    LifeSafetyZone(22),
    Lift(59),
    LightingOutput(54),
    LoadControl(28),
    Loop(12),
    MultiStateInput(13),
    MultiStateOutput(14),
    MultiStateValue(19),
    NetworkPort(56),
    NetworkSecurity(38),
    NotificationClass(15),
    NotificationForwarder(51),
    OctetstringValue(47),
    PositiveIntegerValue(48),
    Program(16),
    PulseConverter(24),
    Schedule(17),
    StructuredView(29),
    TimepatternValue(49),
    TimeValue(50),
    Timer(31),
    TrendLog(20),
    TrendLogMultiple(27);

    private int objectTypeInt;

    public static ObjectType fromObjectTypeInt(int objectTypeInt) {
        for (ObjectType type : values()) {
            if (type.getObjectTypeInt() == objectTypeInt) {
                return type;
            }
        }
        return null;
    }

    /*
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
    */


    public int getObjectTypeInt() {
        return objectTypeInt;
    }

    // enum constructor - cannot be public or protected
    private ObjectType(int objectTypeInt) {
        this.objectTypeInt = objectTypeInt;
    }





}
