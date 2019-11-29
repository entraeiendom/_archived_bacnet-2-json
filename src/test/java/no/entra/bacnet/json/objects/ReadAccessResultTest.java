package no.entra.bacnet.json.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static no.entra.bacnet.json.PropertyId.description;
import static no.entra.bacnet.json.PropertyId.presentValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadAccessResultTest {

    private ReadAccessResult accessResult;
    private String objectId = "123erfgh";
    private Gson gson;

    @BeforeEach
    void setUp() {
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
}