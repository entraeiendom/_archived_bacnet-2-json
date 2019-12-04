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
    private static final Octet LOWER_LIMIT_KEY = Octet.fromHexString("0a");
    private static final Octet HIGH_LIMIT_KEY = Octet.fromHexString("1a");

    public static ConfigurationRequest buildWhoIsRequest(String whoIsBody) {
        ConfigurationRequest configuration = null;
        Integer rangeLowLimit = null;
        Integer rangeHighLimit = null;
        log.debug("whoisbody: {}", whoIsBody);
        OctetReader whoHasReader = new OctetReader(whoIsBody);
        Octet lowerLimitOctet = whoHasReader.next();
        if (lowerLimitOctet.equals(LOWER_LIMIT_KEY)) {
            String lowerLimitHex = whoHasReader.next(2);
            rangeLowLimit = HexUtils.toInt(lowerLimitHex);
        }
        Octet highLimitOctet = whoHasReader.next();
        if (highLimitOctet.equals(HIGH_LIMIT_KEY)) {
            String highLimitHex = whoHasReader.next(2);
            rangeHighLimit = HexUtils.toInt(highLimitHex);
        }

        if (rangeLowLimit != null || rangeHighLimit != null) {
            configuration = new ConfigurationRequest("TODO", null);
        }
        if (rangeLowLimit != null) {
            configuration.setProperty("DeviceInstanceRangeLowLimit", rangeLowLimit.toString());
        }
        if (rangeHighLimit != null) {
            configuration.setProperty("DeviceInstanceRangeHighLimit", rangeHighLimit.toString());
        }

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
            Octet valueLength = whoHasReader.next();
            int valueOctetLength = parseInt(String.valueOf(valueLength), 16);
            Octet encoding = whoHasReader.next();
            String objectNameHex = whoHasReader.next(valueOctetLength - 1);
            log.debug("WhoHas-ObjectNameHex: {}", objectNameHex);
            objectName = HexUtils.parseExtendedValue(encoding,objectNameHex);
        }
        if (objectName != null) {
            configuration = new ConfigurationRequest("TODO", null);
            configuration.setProperty("WhoHasObjectName", objectName);
        }

        return configuration;

    }
}
