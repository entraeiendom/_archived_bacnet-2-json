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

import static no.entra.bacnet.json.Observation.*;

public class Bacnet2Json {


    public static final String SENDER = "sender";
    public static final String OBSERVATION = "observataion";
    public static final String OBSERVED_AT = "observedAt";
    private static final String CONFIGURATION_REQUEST = "configurationRequest";

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
                bacnetJson.put(CONFIGURATION_REQUEST, "{}");
                break;
            case UnconfirmedRequest:
                bacnetJson.put(CONFIGURATION_REQUEST, "{}");
                break;
            default:
                //do nothing
        }

        return bacnetJson;
    }

    static JSONObject buildObservationJson(Bvlc bvlc, Npdu npdu, Observation observation) {
        if (observation == null) {
            return null;
        }

        JSONObject json = new JSONObject();
        json.put(ID, observation.getId());
        if (npdu != null && npdu.isSourceAvailable()) {
            String source = npdu.getSourceNetworkAddress().toString();
            json.put(SOURCE, source);
        }
        json.put(VALUE, observation.getValue());
        json.put(UNIT, observation.getUnit());
        json.put(NAME, observation.getName());
        json.put(DESCRIPTION, observation.getDescription());
        json.put(OBSERVED_AT, observation.getObservedAt());

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
