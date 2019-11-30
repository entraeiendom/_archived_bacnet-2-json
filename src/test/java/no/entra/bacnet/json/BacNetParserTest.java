package no.entra.bacnet.json;

import com.serotonin.bacnet4j.exception.BACnetException;
import no.entra.bacnet.json.bacnet4j.BacNet4jParser;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

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
        bacNetParser = new BacNet4jParser();
    }

    @Test
    void buildJsonFromAdpuHexString() throws BACnetException, JSONException {
        String byteHex = "100209001c020007d12c020007d139004e09702e91002f09cb2e2ea4770b0105b40d2300442f2f09c42e91002f4f";
        String json = bacNetParser.jsonFromApdu(byteHex);
        log.debug("Json built: {}", json);
        assertEquals(partOfApu, json, false);

    }

    @Test
    void validateComplexAck() {
        String apduHexString = "30030e0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String json = bacNetParser.jsonFromApdu(apduHexString);
        log.debug("Json: {}", json);
    }

    @Test
    void variableNpduLength() {
        String complexAck = "810a00b5010030030e0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String complexAckApdu = bacNetParser.findApduHexString(complexAck);
        String json = bacNetParser.jsonFromApdu(complexAckApdu);
        assertNotNull(json);
        assertTrue(json.contains("Blokk1"));
        String confirmedRequest = "810b00340100100209001c020004d22c020004d239004e09702e91002f09cb2e2ea4770b1605b40f06303b2f2f09c42e91002f4f0000";
        String apduHexString = bacNetParser.findApduHexString(confirmedRequest);
        String confirmedJson = bacNetParser.jsonFromApdu(apduHexString);
        log.debug("confirmedJson: {}", confirmedJson);
        assertNotNull(confirmedJson);
    }

    @Test
    void buildObservationFromReadAccessResult() {
        String complexAck = "810a00b5010030030e0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String complexAckApdu = bacNetParser.findApduHexString(complexAck);
        Observation observation = bacNetParser.buildObservation(complexAckApdu);
        assertNotNull(observation);
    }
}
