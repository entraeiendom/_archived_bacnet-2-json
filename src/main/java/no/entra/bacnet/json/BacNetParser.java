package no.entra.bacnet.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.objects.ReadAccessResult;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import static no.entra.bacnet.json.objects.ReadAccessResult.LIST_END_HEX;
import static no.entra.bacnet.json.objects.ReadAccessResult.OBJECT_IDENTIFIER;
import static org.slf4j.LoggerFactory.getLogger;

    public class BacNetParser {
    private static final Logger log = getLogger(BacNetParser.class);

    private final Gson gson;

    public BacNetParser() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    public BacNetParser(Gson gson) {
        this.gson = gson;
    }

    public static String findApduHexString(String hexString) {
        String apduHexString = null;
        if (hexString != null && hexString.startsWith("81")) {
            apduHexString = hexString.substring(12);
        } else {
            apduHexString = hexString;
        }
        return apduHexString;
    }

    public String jsonFromApdu(String apduHexString) {
        //FIXME need implementation.
        String json = null;
        ReadAccessResult accessResult = buildReadAccessResult(apduHexString);
        Observation observation = null;
        Source source = null;


        return json;
    }

    //TODO move to separate class
    private ReadAccessResult buildReadAccessResult(String apduHexString) {
        return null;
    }


    public Observation buildObservation(String hexString) {
        OctetReader apduReader = new OctetReader(hexString);
        Octet pduTypeKey = apduReader.next();
        PduType pduType = PduType.fromOctet(pduTypeKey);
        Observation observation = null;
        String devicdId = "TODO"; //#4 FIXME find deviceId from hexString.

        String resultListHexString = findListResultHexString(hexString);
        try {
            ReadAccessResult accessResult = ReadAccessResult.buildFromResultList(resultListHexString);
            if (accessResult != null) {
                log.info("ReadAccessResult: {}", accessResult);

                String objectId = null;
                if (accessResult.getObjectId() != null) {
                    objectId = accessResult.getObjectId().toString();
                    Source source = new Source(devicdId, objectId);
                    Object presentValue = accessResult.getResultByKey(PropertyIdentifier.PresentValue);
                    observation = new Observation(source, presentValue);
                    observation.setName((String)accessResult.getResultByKey(PropertyIdentifier.ObjectName));
                    observation.setDescription((String) accessResult.getResultByKey(PropertyIdentifier.Description));
                    observation.setUnit((String) accessResult.getResultByKey(PropertyIdentifier.Units));
                }
            }
        } catch (Exception e) {
            log.debug("Failed to build ReadAccessResult from {}. Reason: {}", resultListHexString, e.getMessage());
        }

        return observation;
    }

    String findListResultHexString(String hexString) {
        int listStartPos = hexString.indexOf(OBJECT_IDENTIFIER);
        int listEndPos = hexString.indexOf(LIST_END_HEX);
        String listResulHexString = null;
        if (listStartPos > 0 && listEndPos > 0) {
            listResulHexString = hexString.substring(listStartPos, listEndPos + LIST_END_HEX.length());
        }
        return listResulHexString;
    }
}
