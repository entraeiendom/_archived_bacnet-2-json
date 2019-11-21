package no.entra.bacnet.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.serotonin.bacnet4j.apdu.APDU;
import com.serotonin.bacnet4j.apdu.UnconfirmedRequest;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.service.unconfirmed.UnconfirmedRequestService;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.ServicesSupported;
import com.serotonin.bacnet4j.util.sero.ByteQueue;
import no.entra.bacnet.json.adapters.EncodableAdapter;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class BacNetParser {
    private static final Logger log = getLogger(BacNetParser.class);

    private final Gson gson;

    public BacNetParser() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Encodable.class, new EncodableAdapter())
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    public BacNetParser(Gson gson) {
        this.gson = gson;
    }

    public String jasonFromApdu(String apduHexString) {
        String json = null;
        final ByteQueue queue = new ByteQueue(apduHexString);
        ServicesSupported services = new ServicesSupported();
        services.setAll(true);
        APDU apdu = null;
        try {
            apdu = APDU.createAPDU(services, queue);
            UnconfirmedRequest unconfirmedRequest = (UnconfirmedRequest) apdu;
            unconfirmedRequest.parseServiceData();
            UnconfirmedRequestService service = unconfirmedRequest.getService();
            json = gson.toJson(service);
        } catch (BACnetException e) {
            log.debug("Failed to build Bacnet object from {}. Reason: {}", apduHexString, e.getMessage());
        } catch (JsonParseException e) {
            log.debug("Failed to parse {}. Reason: {}", apduHexString, e.getMessage());
        }

        return json;
    }


}
