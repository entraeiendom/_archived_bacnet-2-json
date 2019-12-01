package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;

import static java.lang.Integer.parseInt;

public enum PropertyIdentifier {
    AbsenteeLimit(244),
    AcceptedModes(175),
    AccessAlarmEvents(245),
    AccessDoors(246),
    AccessEvent(247),
    AccessEventAuthenticationFactor(248),
    AccessEventCredential(249),
    AccessEventTag(322),
    AccessEventTime(250),
    AccessTransactionEvents(251),
    Accompaniment(252),
    AccompanimentTime(253),
    AckRequired(1),
    AckedTransitions(0),
    Action(2),
    ActionText(3),
    ActivationTime(254),
    ActiveAuthenticationPolicy(255),
    ActiveCovMultipleSubscriptions(481),
    ActiveCovSubscriptions(152),
    ActiveText(4),
    ActiveVtSessions(5),
    ActualShedLevel(212),
    AdjustValue(176),
    AlarmValue(6),
    AlarmValues(7),
    AlignIntervals(193),
    All(8),
    AllWritesSuccessful(9),
    AllowGroupDelayInhibit(365),
    ApduLength(399),
    ApduSegmentTimeout(10),
    ApduTimeout(11),
    ApplicationSoftwareVersion(12),
    Archive(13),
    AssignedAccessRights(256),
    AssignedLandingCalls(447),
    AttemptedSamples(124),
    AuthenticationFactors(257),
    AuthenticationPolicyList(258),
    AuthenticationPolicyNames(259),
    AuthenticationStatus(260),
    AuthorizationExemptions(364),
    AuthorizationMode(261),
    AutoSlaveDiscovery(169),
    AverageValue(125),
    BackupAndRestoreState(338),
    BackupFailureTimeout(153),
    BackupPreparationTime(339),
    BacnetIpGlobalAddress(407),
    BacnetIpMode(408),
    BacnetIpMulticastAddress(409),
    BacnetIpNatTraversal(410),
    BacnetIpUdpPort(412),
    BacnetIpv6Mode(435),
    BacnetIpv6UdpPort(438),
    BacnetIpv6MulticastAddress(440),
    BaseDeviceSecurityPolicy(327),
    BbmdAcceptFdRegistrations(413),
    BbmdBroadcastDistributionTable(414),
    BbmdForeignDeviceTable(415),
    BelongsTo(262),
    Bias(14),
    BitMask(342),
    BitText(343),
    BlinkWarnEnable(373),
    BufferSize(126),
    CarAssignedDirection(448),
    CarDoorCommand(449),
    CarDoorStatus(450),
    CarDoorText(451),
    CarDoorZone(452),
    CarDriveStatus(453),
    CarLoad(454),
    CarLoadUnits(455),
    CarMode(456),
    CarMovingDirection(457),
    CarPosition(458),
    ChangeOfStateCount(15),
    ChangeOfStateTime(16),
    ChangesPending(416),
    ChannelNumber(366),
    ClientCovIncrement(127),
    Command(417),
    CommandTimeArray(430),
    ConfigurationFiles(154),
    ControlGroups(367),
    ControlledVariableReference(19),
    ControlledVariableUnits(20),
    ControlledVariableValue(21),
    Count(177),
    CountBeforeChange(178),
    CountChangeTime(179),
    CovIncrement(22),
    CovPeriod(180),
    CovResubscriptionInterval(128),
    CovuPeriod(349),
    CovuRecipients(350),
    CredentialDisable(263),
    CredentialStatus(264),
    Credentials(265),
    CredentialsInZone(266),
    CurrentCommandPriority(431),
    DatabaseRevision(155),
    DateList(23),
    DaylightSavingsStatus(24),
    DaysRemaining(267),
    Deadband(25),
    DefaultFadeTime(374),
    DefaultRampRate(375),
    DefaultStepIncrement(376),
    DefaultSubordinateRelationship(490),
    DefaultTimeout(393),
    DeployedProfileLocation(484),
    DerivativeConstant(26),
    DerivativeConstantUnits(27),
    Description(28),
    DescriptionOfHalt(29),
    DeviceAddressBinding(30),
    DeviceType(31),
    DirectReading(156),
    DistributionKeyRevision(328),
    DoNotHide(329),
    DoorAlarmState(226),
    DoorExtendedPulseTime(227),
    DoorMembers(228),
    DoorOpenTooLongTime(229),
    DoorPulseTime(230),
    DoorStatus(231),
    DoorUnlockDelayTime(232),
    DutyWindow(213),
    EffectivePeriod(32),
    EgressActive(386),
    EgressTime(377),
    ElapsedActiveTime(33),
    ElevatorGroup(459),
    Enable(133),
    EnergyMeter(460),
    EnergyMeterRef(461),
    EntryPoints(268),
    ErrorLimit(34),
    EscalatorMode(462),
    EventAlgorithmInhibit(354),
    EventAlgorithmInhibitRef(355),
    EventDetectionEnable(353),
    EventEnable(35),
    EventMessageTexts(351),
    EventMessageTextsConfig(352),
    EventParameters(83),
    EventState(36),
    EventTimeStamps(130),
    EventType(37),
    ExceptionSchedule(38),
    ExecutionDelay(368),
    ExitPoints(269),
    ExpectedShedLevel(214),
    ExpirationTime(270),
    ExtendedTimeEnable(271),
    FailedAttemptEvents(272),
    FailedAttempts(273),
    FailedAttemptsTime(274),
    FaultHighLimit(388),
    FaultLowLimit(389),
    FaultParameters(358),
    FaultSignals(463),
    FaultType(359),
    FaultValues(39),
    FdBbmdAddress(418),
    FdSubscriptionLifetime(419),
    FeedbackValue(40),
    FileAccessMethod(41),
    FileSize(42),
    FileType(43),
    FirmwareRevision(44),
    FloorText(464),
    FullDutyBaseline(215),
    GlobalIdentifier(323),
    GroupId(465),
    GroupMemberNames(346),
    GroupMembers(345),
    GroupMode(467),
    HighLimit(45),
    HigherDeck(468),
    InProcess(47),
    InProgress(378),
    InactiveText(46),
    InitialTimeout(394),
    InputReference(181),
    InstallationId(469),
    InstanceOf(48),
    InstantaneousPower(379),
    IntegralConstant(49),
    IntegralConstantUnits(50),
    InterfaceValue(387),
    IntervalOffset(195),
    IpAddress(400),
    IpDefaultGateway(401),
    IpDhcpEnable(402),
    IpDhcpLeaseTime(403),
    IpDhcpLeaseTimeRemaining(404),
    IpDhcpServer(405),
    IpDnsServer(406),
    IpSubnetMask(411),
    Ipv6Address(436),
    Ipv6AutoAddressingEnable(442),
    Ipv6DefaultGateway(439),
    Ipv6DhcpLeaseTime(443),
    Ipv6DhcpLeaseTimeRemaining(444),
    Ipv6DhcpServer(445),
    Ipv6DnsServer(441),
    Ipv6PrefixLength(437),
    Ipv6ZoneIndex(446),
    IsUtc(344),
    KeySets(330),
    LandingCallControl(471),
    LandingCalls(470),
    LandingDoorStatus(472),
    LastAccessEvent(275),
    LastAccessPoint(276),
    LastCommandTime(432),
    LastCredentialAdded(277),
    LastCredentialAddedTime(278),
    LastCredentialRemoved(279),
    LastCredentialRemovedTime(280),
    LastKeyServer(331),
    LastNotifyRecord(173),
    LastPriority(369),
    LastRestartReason(196),
    LastRestoreTime(157),
    LastStateChange(395),
    LastUseTime(281),
    LifeSafetyAlarmValues(166),
    LightingCommand(380),
    LightingCommandDefaultPriority(381),
    LimitEnable(52),
    LimitMonitoringInterval(182),
    LinkSpeed(420),
    LinkSpeedAutonegotiate(422),
    LinkSpeeds(421),
    ListOfGroupMembers(53),
    ListOfObjectPropertyReferences(54),
    LocalDate(56),
    LocalForwardingOnly(360),
    LocalTime(57),
    Location(58),
    LockStatus(233),
    Lockout(282),
    LockoutRelinquishTime(283),
    LogBuffer(131),
    LogDeviceObjectProperty(132),
    LogInterval(134),
    LoggingObject(183),
    LoggingRecord(184),
    LoggingType(197),
    LowDiffLimit(390),
    LowLimit(59),
    LowerDeck(473),
    MacAddress(423),
    MachineRoomId(474),
    MaintenanceRequired(158),
    MakingCarCall(475),
    ManipulatedVariableReference(60),
    ManualSlaveAddressBinding(170),
    MaskedAlarmValues(234),
    MaxActualValue(382),
    MaxApduLengthAccepted(62),
    MaxFailedAttempts(285),
    MaxInfoFrames(63),
    MaxMaster(64),
    MaxPresValue(65),
    MaxSegmentsAccepted(167),
    MaximumOutput(61),
    MaximumValue(135),
    MaximumValueTimestamp(149),
    MemberOf(159),
    MemberStatusFlags(347),
    Members(286),
    MinActualValue(383),
    MinPresValue(69),
    MinimumOffTime(66),
    MinimumOnTime(67),
    MinimumOutput(68),
    MinimumValue(136),
    MinimumValueTimestamp(150),
    Mode(160),
    ModelName(70),
    ModificationDate(71),
    MusterPoint(287),
    NegativeAccessRules(288),
    NetworkAccessSecurityPolicies(332),
    NetworkInterfaceName(424),
    NetworkNumber(425),
    NetworkNumberQuality(426),
    NetworkType(427),
    NextStoppingFloor(476),
    NodeSubtype(207),
    NodeType(208),
    NotificationClass(17),
    NotificationThreshold(137),
    NotifyType(72),
    NumberOfApduRetries(73),
    NumberOfAuthenticationPolicies(289),
    NumberOfStates(74),
    ObjectIdentifier(75),
    ObjectList(76),
    ObjectName(77),
    ObjectPropertyReference(78),
    PropertyIdentifier(79),
    OccupancyCount(290),
    OccupancyCountAdjust(291),
    OccupancyCountEnable(292),
    OccupancyLowerLimit(294),
    OccupancyLowerLimitEnforced(295),
    OccupancyState(296),
    OccupancyUpperLimit(297),
    OccupancyUpperLimitEnforced(298),
    OperationDirection(477),
    OperationExpected(161),
    Optional(80),
    OutOfService(81),
    OutputUnits(82),
    PacketReorderTime(333),
    PassbackMode(300),
    PassbackTimeout(301),
    PassengerAlarm(478),
    Polarity(84),
    PortFilter(363),
    PositiveAccessRules(302),
    Power(384),
    PowerMode(479),
    Prescale(185),
    PresentValue(85),
    Priority(86),
    PriorityArray(87),
    PriorityForWriting(88),
    ProcessIdentifier(89),
    ProcessIdentifierFilter(361),
    ProfileLocation(485),
    ProfileName(168),
    ProgramChange(90),
    ProgramLocation(91),
    ProgramState(92),
    PropertyList(371),
    ProportionalConstant(93),
    ProportionalConstantUnits(94),
    ProtocolLevel(482),
    ProtocolPropertyIdentifiersSupported(96),
    ProtocolRevision(139),
    ProtocolServicesSupported(97),
    ProtocolVersion(98),
    PulseRate(186),
    ReadOnly(99),
    ReasonForDisable(303),
    ReasonForHalt(100),
    RecipientList(102),
    RecordCount(141),
    RecordsSinceNotification(140),
    ReferencePort(483),
    RegisteredCarCall(480),
    Reliability(103),
    ReliabilityEvaluationInhibit(357),
    RelinquishDefault(104),
    Represents(491),
    RequestedShedLevel(218),
    RequestedUpdateInterval(348),
    Required(105),
    Resolution(106),
    RestartNotificationRecipients(202),
    RestoreCompletionTime(340),
    RestorePreparationTime(341),
    RoutingTable(428),
    Scale(187),
    ScaleFactor(188),
    ScheduleDefault(174),
    SecuredStatus(235),
    SecurityPduTimeout(334),
    SecurityTimeWindow(335),
    SegmentationSupported(107),
    SerialNumber(372),
    Setpoint(108),
    SetpointReference(109),
    Setting(162),
    ShedDuration(219),
    ShedLevelDescriptions(220),
    ShedLevels(221),
    Silenced(163),
    SlaveAddressBinding(171),
    SlaveProxyEnable(172),
    StartTime(142),
    StateChangeValues(396),
    StateDescription(222),
    StateText(110),
    StatusFlags(111),
    StopTime(143),
    StopWhenFull(144),
    StrikeCount(391),
    StructuredObjectList(209),
    SubordinateAnnotations(210),
    SubordinateList(211),
    SubordinateNodeTypes(487),
    SubordinateRelationships(489),
    SubordinateTags(488),
    SubscribedRecipients(362),
    SupportedFormatClasses(305),
    SupportedFormats(304),
    SupportedSecurityAlgorithms(336),
    SystemStatus(112),
    Tags(486),
    ThreatAuthority(306),
    ThreatLevel(307),
    TimeDelay(113),
    TimeDelayNormal(356),
    TimeOfActiveTimeReset(114),
    TimeOfDeviceRestart(203),
    TimeOfStateCountReset(115),
    TimeOfStrikeCountReset(392),
    TimeSynchronizationInterval(204),
    TimeSynchronizationRecipients(116),
    TimerRunning(397),
    TimerState(398),
    TotalRecordCount(145),
    TraceFlag(308),
    TrackingValue(164),
    TransactionNotificationClass(309),
    Transition(385),
    Trigger(205),
    Units(117),
    UpdateInterval(118),
    UpdateKeySetTimeout(337),
    UpdateTime(189),
    UserExternalIdentifier(310),
    UserInformationReference(311),
    UserName(317),
    UserType(318),
    UsesRemaining(319),
    UtcOffset(119),
    UtcTimeSynchronizationRecipients(206),
    ValidSamples(146),
    ValueBeforeChange(190),
    ValueChangeTime(192),
    ValueSet(191),
    ValueSource(433),
    ValueSourceArray(434),
    VarianceValue(151),
    VendorIdentifier(120),
    VendorName(121),
    VerificationTime(326),
    VirtualMacAddressTable(429),
    VtClassesSupported(122),
    WeeklySchedule(123),
    WindowInterval(147),
    WindowSamples(148),
    WriteStatus(370),
    ZoneFrom(320),
    ZoneMembers(165),
    ZoneTo(321);

    private int propertyIdentifierInt;

    public static PropertyIdentifier fromPropertyIdentifierInt(int propertyIdentifierInt) {
        for (PropertyIdentifier type : values()) {
            if (type.getPropertyIdentifierInt() == propertyIdentifierInt) {
                return type;
            }
        }
        return null;
    }


    public static PropertyIdentifier fromOctet(Octet propertyIdentifierOctet) throws NumberFormatException {
        if (propertyIdentifierOctet == null) {
            return null;
        }
        Integer propertyIdentifierInt = parseInt(propertyIdentifierOctet.toString(), 16);
        PropertyIdentifier propertyIdentifier = fromPropertyIdentifierInt(propertyIdentifierInt.intValue());
        return propertyIdentifier;
    }


    public int getPropertyIdentifierInt() {
        return propertyIdentifierInt;
    }

    private PropertyIdentifier(int propertyIdentifierInt) {
        this.propertyIdentifierInt = propertyIdentifierInt;
    }
}
