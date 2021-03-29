package no.entra.bacnet.json.services;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServiceParserTest {

    @Test
    void iamService() {
        String iamApdu = "1000c40200020f22040091002105";
        Service service = ServiceParser.fromApduHexString(iamApdu);
        assertNotNull(service);
        assertEquals(UnconfirmedServiceChoice.IAm, service.getServiceChoice());
    }

    @Test
    void wellKnownPduType() {
        String knownPduType = "810400750a3f000bbac00120ffff00ff10073d6104004f00530042004700310034002d004e004100450033002f004e00320020005400720075006e006b00200031002e0033003600300036003600200041004b0053004500200038002e0055007400670061006e006700650072002e0044004f003600";
        BvlcResult bvlcResult = BvlcParser.parse(knownPduType);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
    }

    @Test
    void verifyPduTypeSecondNibble() {
        String unknownPduType = "8104001e0a3f0010bac001080961010f1001c40200000fc403c00000710045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e003400330033003300300031002e002d004f0045003000300034005f004d004f004d0072006d006500200031002000650074006700670067005f0046004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076007600760000000";
        BvlcResult bvlcResult = BvlcParser.parse(unknownPduType);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
    }

    @Test
    void verifyUnconfirmedRequest() {
        //See page 984
        /*
        FIXME See bug report https://github.com/entraeiendom/bacnet-2-json/issues/5

        String unconfirmedRequest = "8104001e0a3f0010bac001080961010b1001c40200000bc403c000007100";
        BvlcResult bvlcResult = BvlcParser.parse(unconfirmedRequest);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        String unprocessedHexString = npduResult.getUnprocessedHexString();
        assertEquals("1001c40200000bc403c000007100", unprocessedHexString);
        unprocessedHexString = "1001c40200000bc403c000007100";
        Service service = ServiceParser.fromApduHexString(unprocessedHexString);
        assertNotNull(service);
        assertEquals(PduType.UnconfirmedRequest, service.getPduType());
        assertEquals(UnconfirmedServiceChoice.IHave, service.getServiceChoice());
        //I have device, 11
        ConfigurationRequest request = UnconfirmedService.tryToUnderstandUnconfirmedRequest(service);
        assertNotNull(request);
        */
    }

    @Test
    void verifyConfirmedServiceTest() {
        /*
        String unknownPduType = "8104001e0a3f0010bac001080961010f1001c40200000fc403c00000710045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e003400330033003300300031002e002d004f0045003000300034005f004d004f004d0072006d006500200031002000650074006700670067005f0046004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076007600760000000";
        BvlcResult bvlcResult = BvlcParser.parse(unknownPduType);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertTrue(service instanceof ConfirmedService);
        ConfigurationRequest request = ConfirmedService.tryToUnderstandConfirmedRequest(service);
        assertNotNull(request);
        */
    }

    @Test
    void  getAlarmSummaryWithInvokeId() {
        String alarmSummaryApduHex = "300103c4000000029103820560c40000000391048205e0";
        Service service = ServiceParser.fromApduHexString(alarmSummaryApduHex);
        assertNotNull(service);
        assertEquals(PduType.ComplexAck, service.getPduType());
        assertEquals(ConfirmedServiceChoice.GetAlarmSummary, service.getServiceChoice());
        assertEquals(1, service.getInvokeId());
    }
    @Test
    void readPropertyMultiple() {
        String complexAckHex = "810a00b5010030030e0c002dc6ef1e29554e4441b1500000";
        BvlcResult bvlcResult = BvlcParser.parse(complexAckHex);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertEquals(PduType.ComplexAck, service.getPduType());
        assertEquals(3, service.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, service.getServiceChoice());
    }

    @Test
    void readPropertyMultipleWithInvokeId() {
        String complexAckApduHex = "30020e0c00000021e29554e44422933334f1f";
        Service service = ServiceParser.fromApduHexString(complexAckApduHex);
        assertNotNull(service);
        assertEquals(PduType.ComplexAck, service.getPduType());
        assertEquals(2, service.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, service.getServiceChoice());
    }

    @Test
    void segmentedReadProperty() {
        String complexAckHex = "810a02010104381f03040c0cc404edcc0dc404ed";
        BvlcResult bvlcResult = BvlcParser.parse(complexAckHex);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        assertNotNull(service);
        assertEquals(PduType.ComplexAck, service.getPduType());
        assertEquals(31, service.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadProperty, service.getServiceChoice());
    }

    @Test
    void readPropertiesObjectNameProtocolVersionRevision() {
        String hexString = "810a0028010030010e0c020000081e294d4e75060046574643554f29624e21014f298b4e210e4f1f";
        BvlcResult bvlcResult = BvlcParser.parse(hexString);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        assertNotNull(service);
        assertEquals(PduType.ComplexAck, service.getPduType());
        assertEquals(1, service.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, service.getServiceChoice());
        assertEquals("0c020000081e294d4e75060046574643554f29624e21014f298b4e210e4f1f",service.getUnprocessedHexString());
    }
}