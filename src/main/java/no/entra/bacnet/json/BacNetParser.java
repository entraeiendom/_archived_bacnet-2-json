package no.entra.bacnet.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.serotonin.bacnet4j.apdu.*;
import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.service.acknowledgement.AcknowledgementService;
import com.serotonin.bacnet4j.service.confirmed.ConfirmedRequestService;
import com.serotonin.bacnet4j.service.unconfirmed.UnconfirmedRequestService;
import com.serotonin.bacnet4j.type.AmbiguousValue;
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.ServicesSupported;
import com.serotonin.bacnet4j.util.sero.ByteQueue;
import no.entra.bacnet.json.adapters.AmbiguousValueAdapter;
import no.entra.bacnet.json.adapters.EncodableAdapter;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class BacNetParser {
    private static final Logger log = getLogger(BacNetParser.class);

    private final Gson gson;

    public BacNetParser() {
        gson = new GsonBuilder()
                .registerTypeAdapter(AmbiguousValue.class, new AmbiguousValueAdapter())
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
            if (apdu instanceof UnconfirmedRequest) {
                UnconfirmedRequest unconfirmedRequest = (UnconfirmedRequest) apdu;
                unconfirmedRequest.parseServiceData();
                UnconfirmedRequestService service = unconfirmedRequest.getService();
                json = gson.toJson(service);
            } else if (apdu instanceof ConfirmedRequest) {
                ConfirmedRequest confirmedRequest = (ConfirmedRequest) apdu;
                confirmedRequest.parseServiceData();
                ConfirmedRequestService service = confirmedRequest.getServiceRequest();
                json = gson.toJson(service);
            } else if (apdu instanceof ComplexACK) {
                ComplexACK complexACK = (ComplexACK) apdu;
                complexACK.parseServiceData();
                AcknowledgementService service = complexACK.getService();
                json = gson.toJson(service);
            } else if (apdu instanceof com.serotonin.bacnet4j.apdu.Error) {
                com.serotonin.bacnet4j.apdu.Error errorAPDU = (com.serotonin.bacnet4j.apdu.Error) apdu;
                json = gson.toJson(errorAPDU);
            } else if (apdu instanceof SimpleACK) {
                SimpleACK simpleACK = (SimpleACK) apdu;
                json = gson.toJson(simpleACK);
            } else {
                log.debug("Failed to find service for apdu: {}", apdu);
            }
        } catch (BACnetException e) {
            log.debug("Failed to build Bacnet object from {}. Reason: {}", apduHexString, e.getMessage());
        } catch (JsonParseException e) {
            log.debug("Failed to parse {}. Reason: {}", apduHexString, e.getMessage());
        }

        return json;
    }


}
