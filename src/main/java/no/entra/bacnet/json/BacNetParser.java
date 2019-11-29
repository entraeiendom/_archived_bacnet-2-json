package no.entra.bacnet.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;

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


        return json;
    }


}
