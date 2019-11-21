package no.entra.bacnet.json;

import com.serotonin.bacnet4j.exception.BACnetException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        bacNetParser = new BacNetParser();
    }

    @Test
    void buildJsonFromAdpuHexString() throws BACnetException, JSONException {
        String byteHex = "100209001c020007d12c020007d139004e09702e91002f09cb2e2ea4770b0105b40d2300442f2f09c42e91002f4f";
        String json = bacNetParser.jasonFromApdu(byteHex);
        log.debug("Json built: {}", json);
        assertEquals(partOfApu, json, false);

    }
}
