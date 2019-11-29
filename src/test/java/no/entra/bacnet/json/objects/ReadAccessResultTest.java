package no.entra.bacnet.json.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.PropertyId.description;
import static no.entra.bacnet.json.PropertyId.presentValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadAccessResultTest {

    private ReadAccessResult accessResult;
    private String objectId = "123erfgh";

    @BeforeEach
    void setUp() {
        accessResult = new ReadAccessResult(objectId);
        accessResult.setResultByKey(presentValue, Double.valueOf(22.567));
        accessResult.setResultByKey(description, "some short one");
    }

    @Test
    void validOutput() {
        assertEquals(objectId, accessResult.getObjectId());
        assertEquals(Double.valueOf(22.567), accessResult.getResultByKey("presentValue"));
        assertEquals("some short one", accessResult.getResultByKey(description));
    }
}