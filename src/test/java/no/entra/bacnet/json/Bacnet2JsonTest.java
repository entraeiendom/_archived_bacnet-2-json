package no.entra.bacnet.json;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

class Bacnet2JsonTest {
    private static final Logger log = getLogger(Bacnet2JsonTest.class);

    @Test
    void observationBacnetHexString() {
        String observationHexString = "810a00b5010030030e0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        String expected = " {\"sender\":\"unknown\",\"observataion\":{}}";
        String observationJson = Bacnet2Json.hexStringToJson(observationHexString);
        log.trace("ObservationJson: {}", observationJson);
        assertNotNull(observationJson);
        JSONAssert.assertEquals(expected, observationJson, false);
    }

    @Test
    void configurationRequest() {
        /*
        TODO
        String configurationRequestHexString = "810400750a3f000bbac00120ffff00ff10073d6104004f00530042004700310034002d004e004100450033002f004e00320020005400720075006e006b00200031002e0033003600300036003600200041004b0053004500200038002e0055007400670061006e006700650072002e0044004f0036000000";
        String expected = " {\"sender\":\"unknown\",\"configurationRequest\":{}}";
        String configurationRequestJson = Bacnet2Json.hexStringToJson(configurationRequestHexString);
        log.trace("ObservationJson: {}", configurationRequestJson);
        assertNotNull(configurationRequestJson);
        JSONAssert.assertEquals(expected, configurationRequestJson, false);
        */
    }

    @Test
    void addServiceInfo() {
    }

    @Test
    void addNetworkInfo() {
    }
}