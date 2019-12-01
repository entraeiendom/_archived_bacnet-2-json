package no.entra.bacnet.json.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static no.entra.bacnet.json.PropertyId.description;
import static no.entra.bacnet.json.PropertyId.presentValue;
import static org.junit.jupiter.api.Assertions.*;

class ReadAccessResultTest {

    private ReadAccessResult accessResult;
    private String objectIdString = "0c002dc6ef";
    private ObjectIdentifier objectId;
    private Gson gson;

    @BeforeEach
    void setUp() {
        objectId = ObjectIdentifier.buildFromHexString(objectIdString);
        accessResult = new ReadAccessResult(objectId);
        accessResult.setResultByKey(presentValue, Double.valueOf(22.567));
        accessResult.setResultByKey(description, "some short one");
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    @Test
    void validOutput() {
        assertEquals(objectId, accessResult.getObjectId());
        assertEquals(Double.valueOf(22.567), accessResult.getResultByKey("presentValue"));
        assertEquals("some short one", accessResult.getResultByKey(description));
    }

    @Test
    void jsonValidation() throws JSONException {

        String jsonBuilt = gson.toJson(accessResult);
        String expected = "{\n" +
                "  \"objectId\": \"123erfgh\",\n" +
                "  \"results\": {\n" +
                "    \"presentValue\": 22.567,\n" +
                "    \"description\": \"some short one\"\n" +
                "  }\n" +
                "}";
        JSONAssert.assertEquals(expected, jsonBuilt, true);

    }

    @Test
    void buildFromResultListTest() {
        String readPropertyMultiple = "0c002dc6ef1e29554e4441b15c494f29754e913e4f294d4e7549040053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e005200540030003000314f291c4e7541040052006f006d00200031003000310033002c002000640065006c0031002c00200070006c0061006e002000550031002c00200042006c006f006b006b003100204f1f";
        ReadAccessResult result = ReadAccessResult.buildFromResultList(readPropertyMultiple);
        assertNotNull(result);
        assertNotNull(result.getObjectId());
        //FIXME
        assertFalse(result.getResults().isEmpty());
    }
}