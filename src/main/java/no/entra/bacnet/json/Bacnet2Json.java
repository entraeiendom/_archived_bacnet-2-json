package no.entra.bacnet.json;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.bvlc.Bvlc;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.Npdu;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.parser.BacNetParser;
import no.entra.bacnet.json.services.ConfirmedServiceChoice;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static no.entra.bacnet.json.Observation.SOURCE;
import static no.entra.bacnet.json.services.ConfirmedService.tryToUnderstandConfirmedRequest;
import static no.entra.bacnet.json.services.UnconfirmedService.tryToUnderstandUnconfirmedRequest;
import static no.entra.bacnet.json.utils.HexUtils.octetsToString;
import static no.entra.bacnet.json.utils.HexUtils.toInt;

/**
 *
 */
public class Bacnet2Json {

    public static final String SENDER = "sender";
    public static final String SERVICE = "service";
    public static final String OBSERVATION = "observation";
    public static final String OBSERVATIONS = "observations";
    public static final String OBSERVED_AT = "observedAt";
    public static final String CONFIGURATION_REQUEST = "configurationRequest";
    public static final String CONFIGURATION = "configuration";
    public static final String GATEWAY = "gateway";
    public static final String GATEWAY_DEVICE_ID = "deviceId";
    public static final String GATEWAY_INSTANCE_NUMBER = "instanceNumber";
    public static final String INVOKE_ID = "invokeId";

    /**
     * Parse BackNet message to a more conventional IoT Json format.
     * @param hexString starting with 81...
     * @return parsed hexString to valid Json
     */
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
        Integer invokeId = service.getInvokeId();
        if (invokeId != null) {
            bacnetJson.put(INVOKE_ID, service.getInvokeId());
        }
        bacnetJson.put(SERVICE, service.getServiceChoice());
        Map<String, String> observationMap = new HashMap<>();
        observationMap.put(OBSERVED_AT, LocalDateTime.now().toString());
        JSONObject observationJson = new JSONObject(observationMap);
        PduType pduType = service.getPduType();

        switch (pduType) {
            case ComplexAck:
                String unprocessedHexString = service.getUnprocessedHexString();
                Observation observation = BacNetParser.buildObservation(unprocessedHexString);
                observationJson = buildObservationJson(bvlc, npdu, observation);
                bacnetJson.put(OBSERVATION, observationJson);
                break;
            case ConfirmedRequest:
                BacnetMessage confirmedRequest = tryToUnderstandConfirmedRequest(service);
                observationJson = buildObservationJson(bvlc, npdu, confirmedRequest);
               if (service.getServiceChoice() == ConfirmedServiceChoice.SubscribeCov) {
                   bacnetJson.put(OBSERVATIONS, observationJson.get(OBSERVATIONS));
                   if (observationJson.has("subscriptionRemainingSeconds")) {
                        bacnetJson.put("subscriptionRemainingSeconds", observationJson.get("subscriptionRemainingSeconds"));
                   }
               } else {
                   bacnetJson.put(CONFIGURATION_REQUEST, observationJson);
               }
                break;
            case UnconfirmedRequest:
                BacnetMessage unconfirmedRequest = tryToUnderstandUnconfirmedRequest(service);
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

        JSONObject json = message.asJsonObject();

        if (npdu != null && npdu.isSourceAvailable()) {
            String source = octetsToString(npdu.getSourceNetworkAddress());
            json.put(SOURCE, source);
        }

        return json;
    }

    static JSONObject addNetworkInfo(Npdu npdu) {
        if (npdu != null && npdu.isSourceAvailable()) {
            JSONObject sender = new JSONObject();
            JSONObject gateway = new JSONObject();
            Octet[] sourceNetworkAddress = npdu.getSourceNetworkAddress();
            if (sourceNetworkAddress != null) {
                int instanceNumber = toInt(sourceNetworkAddress);
                gateway.put(GATEWAY_INSTANCE_NUMBER, instanceNumber);
            }
            Octet[] sourceMacLayerAddress = npdu.getSourceMacLayerAddress();
            if (sourceMacLayerAddress != null) {
                int deviceId = toInt(sourceMacLayerAddress);
                gateway.put(GATEWAY_DEVICE_ID, deviceId);
            }
            sender.put(GATEWAY, gateway);
            return new JSONObject().put(SENDER, sender);
        } else {
            return new JSONObject().put(SENDER, "unknown");
        }
    }
}
