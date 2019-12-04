package no.entra.bacnet.json.configuration;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.ConfigurationRequest;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.utils.HexUtils;
import org.slf4j.Logger;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ConfigurationParser {
    private static final Logger log = getLogger(ConfigurationParser.class);

    private static final char WHO_HAS_EXTENDED_VALUE = 'd';

    public static ConfigurationRequest buildWhoIsRequest(String whoIsBodyHexString) {
        ConfigurationRequest configuration = null;
        //TODO parse WhoIs
        //Instance Range Low Limit: ContextTag0, length a == 2 Octet
        //Instance Range High Limit: ContextTag1, length a == 2 Octet

        return configuration;
    }

    public static ConfigurationRequest buildWhoHasRequest(String whoHasBody) {
        ConfigurationRequest configuration = null;
        String objectName = null;
        OctetReader whoHasReader = new OctetReader(whoHasBody);
        Octet contextTagOctet = whoHasReader.next();
        char contextTag = contextTagOctet.getFirstNibble();
        char namedTag = contextTagOctet.getSecondNibble();
        if (namedTag == WHO_HAS_EXTENDED_VALUE) {
            log.debug("Expecting extended value");
            Octet valueLength = whoHasReader.next();
            int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
            Octet encoding = whoHasReader.next();
            String objectNameHex = whoHasReader.next(valueOctetLength - 1);
            log.debug("ObjectNameHex: {}", objectNameHex);
            objectName = HexUtils.parseExtendedValue(encoding,objectNameHex);
            log.debug("The rest: {}", whoHasReader.unprocessedHexString());
        }
        if (objectName != null) {
            configuration = new ConfigurationRequest("TODO", null);
            configuration.setProperty("ObjectName", objectName);
        }

        return configuration;

    }
}
