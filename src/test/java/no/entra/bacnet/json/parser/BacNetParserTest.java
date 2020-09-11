package no.entra.bacnet.json.parser;

import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.services.ConfirmedServiceChoice;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static no.entra.bacnet.json.objects.ReadAccessResult.LIST_END_HEX;
import static no.entra.bacnet.json.objects.ReadAccessResult.OBJECT_IDENTIFIER;
import static org.junit.jupiter.api.Assertions.*;

public class BacNetParserTest {
    private static final Logger log = LoggerFactory.getLogger( BacNetParserTest.class );

    private BacNetParser bacNetParser;
    private static final String partOfApu = "{\"subscriberProcessIdentifier\": {\n" +
            "    \"smallValue\": 0,\n" +
            "    \"bigValue\": null,\n" +
            "    \"tagNumber\": 0,\n" +
            "    \"typeId\": 2,\n" +
            "    \"contextSpecific\": true\n" +
            "  }}";
    @BeforeEach
    void setUp() {
        bacNetParser = new BacNetParser();
    }

    @Test
    void buildObservationFromReadAccessResult() {
        String complexAck = "810a00b5010030030e0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String complexAckApdu = bacNetParser.findApduHexString(complexAck);
        assertNotNull(complexAckApdu);
        Service service = ServiceParser.fromApduHexString(complexAckApdu);
        assertNotNull(service);
        assertEquals(PduType.ComplexAck, service.getPduType());
        assertEquals(3, service.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, service.getServiceChoice());
        Observation observation = bacNetParser.buildObservation(complexAckApdu);
        assertNotNull(observation);
        BvlcResult bvlcResult = BvlcParser.parse(complexAck);
        log.debug("Bvlc: {}", bvlcResult.getBvlc());
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        log.debug("Npdu: {}", npduResult.getNpdu());
        assertEquals(complexAckApdu, npduResult.getUnprocessedHexString());
    }

    @Test
    void findListResultHexStringTest() {
        String complexAck = "810a00b5010030030e0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String expected = "0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String listResultHexString = bacNetParser.findListResultHexString(complexAck);
        assertTrue(listResultHexString.startsWith(OBJECT_IDENTIFIER));
        assertTrue(listResultHexString.endsWith(LIST_END_HEX));
        assertEquals(expected, listResultHexString);
    }

    @Test
    void figureOutWhatThisIs() {
        String line = "8104001e0a3f0010bac001080961010c1001c40200000cc403c00000710045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e003400330033003300300031002e002d004f0045003000300034005f004d004f004d0072006d0065002000310020006500740067004f002e00440043004f0035004600310031002e0044006100670042007600690031002d00310031002e0044006100670042007600760000000";
        BvlcResult bvlcResult = BvlcParser.parse(line);
        assertNotNull(bvlcResult);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        assertNotNull(npduResult);
    }

    @Test
    void verifyStartEndFindListResult() {
        String hexString = "0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String resultListHexString = BacNetParser.findListResultHexString(hexString);
        assertEquals(hexString, resultListHexString);
        hexString = "xxxx0cxxxx1fyyy";
        assertEquals("0cxxxx1f", BacNetParser.findListResultHexString(hexString));
    }
}
