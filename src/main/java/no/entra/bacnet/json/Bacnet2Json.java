package no.entra.bacnet.json;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.Npdu;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Bacnet2Json {


    public static final String SENDER = "sender";
    public static final String OBSERVATION = "observataion";
    public static final String OBSERVED_AT = "observedAt";

    public static String hexStringToJson(String hexString) {
        String bacnetMessage = null;
        JSONObject bacnetJson = new JSONObject();
        BvlcResult bvlcResult = BvlcParser.parse(hexString);
        if (bvlcResult != null) {
            NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
            if (npduResult != null) {
                Npdu npdu = npduResult.getNpdu();
                bacnetJson = addNetworkInfo(npdu);
                Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
                if (service != null) {
                    bacnetJson = addServiceInfo(bacnetJson, service);
                }
            }
        }
        if (bacnetJson != null) {
            bacnetMessage = bacnetJson.toString();
        }

        return bacnetMessage;
    }

    static JSONObject addServiceInfo(JSONObject bacnetJson, Service service) {
        Map<String, String> observationMap = new HashMap<>();
        observationMap.put(OBSERVED_AT, LocalDateTime.now().toString());
        JSONObject observationJson = new JSONObject(observationMap);
        return bacnetJson.put(OBSERVATION, observationJson);
    }

    static JSONObject addNetworkInfo(Npdu npdu) {
        if (npdu != null && npdu.isSourceAvailable()) {
            return new JSONObject().put(SENDER, npdu.getSourceNetworkAddress().toString());
        } else {
            return new JSONObject().put(SENDER, "unknown");
        }
    }
}
