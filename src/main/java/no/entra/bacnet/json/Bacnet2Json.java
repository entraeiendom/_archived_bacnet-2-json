package no.entra.bacnet.json;

import no.entra.bacnet.json.bvlc.Bvlc;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.Npdu;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.parser.BacNetParser;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static no.entra.bacnet.json.Observation.SOURCE;
import static no.entra.bacnet.json.services.ConfirmedService.tryToUnderstandConfirmedRequest;
import static no.entra.bacnet.json.services.UnconfirmedService.tryToUnderstandUnconfirmedRequest;

public class Bacnet2Json {


    public static final String SENDER = "sender";
    public static final String OBSERVATION = "observataion";
    public static final String OBSERVED_AT = "observedAt";
    public static final String CONFIGURATION_REQUEST = "configurationRequest";
    public static final String CONFIGURATION = "configuration";

    public static String hexStringToJson(String hexString) {
        String bacnetMessage = null;
        JSONObject bacnetJson = new JSONObject();
        BvlcResult bvlcResult = BvlcParser.parse(hexString);
        if (bvlcResult != null) {
            Bvlc bvlc = bvlcResult.getBvlc();
            NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
            if (npduResult != null) {
                Npdu npdu = npduResult.getNpdu();
                bacnetJson = addNetworkInfo(npdu);
                Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
                if (service != null) {
                    bacnetJson = addServiceInfo(bacnetJson, bvlc, npdu, service);
                }
            }
        }
        if (bacnetJson != null) {
            bacnetMessage = bacnetJson.toString();
        }

        return bacnetMessage;
    }

    static JSONObject addServiceInfo(JSONObject bacnetJson, Bvlc bvlc, Npdu npdu, Service service) {
        if (service == null) {
            return null;
        }
        if (bacnetJson == null) {
            bacnetJson = new JSONObject();
        }
        Map<String, String> observationMap = new HashMap<>();
        observationMap.put(OBSERVED_AT, LocalDateTime.now().toString());
        JSONObject observationJson = new JSONObject(observationMap);
        PduType pduType = service.getPduType();

        switch (pduType) {
            case ComplexAck:
                Observation observation = BacNetParser.buildObservation(service.getUnprocessedHexString());
                observationJson = buildObservationJson(bvlc, npdu, observation);
                bacnetJson.put(OBSERVATION, observationJson);
                break;
            case ConfirmedRequest:
                ConfigurationRequest confirmedRequest = tryToUnderstandConfirmedRequest(service);
                observationJson = buildObservationJson(bvlc, npdu, confirmedRequest);
                bacnetJson.put(CONFIGURATION_REQUEST, observationJson);
                break;
            case UnconfirmedRequest:
                ConfigurationRequest unconfirmedRequest = tryToUnderstandUnconfirmedRequest(service);
                observationJson = buildObservationJson(bvlc, npdu, unconfirmedRequest);
                bacnetJson.put(CONFIGURATION_REQUEST, observationJson);
                break;
            default:
                //do nothing
        }

        return bacnetJson;
    }

    static JSONObject buildObservationJson(Bvlc bvlc, Npdu npdu, BacnetMessage message) {
        if (message == null) {
            return null;
        }

        JSONObject json = new JSONObject();

        if (npdu != null && npdu.isSourceAvailable()) {
            String source = npdu.getSourceNetworkAddress().toString();
            json.put(SOURCE, source);
        }
        if (message instanceof Observation) {
            json.put(OBSERVATION, message.asJsonObject());
        } else if (message instanceof ConfigurationRequest) {
            json.put(CONFIGURATION_REQUEST, message.asJsonObject());
        } else if (message instanceof Configuration) {
            json.put(CONFIGURATION, message.asJsonObject());
        } else {
            json.put("message", message.asJsonObject());
        }

        return json;
    }

    static JSONObject addNetworkInfo(Npdu npdu) {
        if (npdu != null && npdu.isSourceAvailable()) {
            return new JSONObject().put(SENDER, npdu.getSourceNetworkAddress().toString());
        } else {
            return new JSONObject().put(SENDER, "unknown");
        }
    }
}
