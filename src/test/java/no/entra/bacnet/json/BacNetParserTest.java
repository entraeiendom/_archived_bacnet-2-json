package no.entra.bacnet.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.serotonin.bacnet4j.apdu.APDU;
import com.serotonin.bacnet4j.apdu.UnconfirmedRequest;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.service.unconfirmed.UnconfirmedRequestService;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.ServicesSupported;
import com.serotonin.bacnet4j.util.sero.ByteQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BacNetParserTest {
    private static final Logger log = LoggerFactory.getLogger( BacNetParserTest.class );
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Encodable.class, new EncodableAdapter())
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    @Test
    void buildJsonFromAdpuHexString() throws BACnetException {
        String byteHex = "100209001c020007d12c020007d139004e09702e91002f09cb2e2ea4770b0105b40d2300442f2f09c42e91002f4f";
        final ByteQueue queue = new ByteQueue(byteHex);
        ServicesSupported services = new ServicesSupported();
        services.setAll(true);
        APDU apdu = APDU.createAPDU(services, queue);
        assertNotNull(apdu);
        assertTrue(apdu instanceof UnconfirmedRequest);

        UnconfirmedRequest unconfirmedRequest = (UnconfirmedRequest)apdu;
        unconfirmedRequest.parseServiceData();
        UnconfirmedRequestService service = unconfirmedRequest.getService();
        String json = gson.toJson(service);
        log.debug("Json built: {}", json);

    }
}
