package no.entra.bacnet.json.bacnet4j;

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

public class BacNet4jParser {
    private static final Logger log = getLogger(BacNet4jParser.class);

    private final Gson gson;

    public BacNet4jParser() {
        gson = new GsonBuilder()
                .registerTypeAdapter(AmbiguousValue.class, new AmbiguousValueAdapter())
                .registerTypeAdapter(Encodable.class, new EncodableAdapter())
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    public BacNet4jParser(Gson gson) {
        this.gson = gson;
    }

    public String findApduHexString(String hexString) {
        String apduHexString = null;
        if (hexString != null && hexString.startsWith("81")) {
            apduHexString = hexString.substring(12);
        } else {
            apduHexString = hexString;
        }
        return apduHexString;
    }

    public String jsonFromApdu(String apduHexString) {
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
