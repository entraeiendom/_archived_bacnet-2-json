package no.entra.bacnet.json.services;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void verifyConfirmedServiceTest() {
        String unknownPduType = "8104001e0a3f0010bac001080961010f1001c40200000fc403c00000710045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e003400330033003300300031002e002d004f0045003000300034005f004d004f004d0072006d006500200031002000650074006700670067005f0046004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076007600760000000";
        BvlcResult bvlcResult = BvlcParser.parse(unknownPduType);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertTrue(service instanceof ConfirmedService);
    }
}