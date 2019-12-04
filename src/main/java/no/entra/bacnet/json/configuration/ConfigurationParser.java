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
    private static final char LOWER_LIMIT_KEY = '0';
    private static final char HIGH_LIMIT_KEY = '1';

    public static ConfigurationRequest buildWhoIsRequest(String whoIsBody) {
        ConfigurationRequest configuration = null;
        Integer rangeLowLimit = null;
        Integer rangeHighLimit = null;
        log.debug("whoisbody: {}", whoIsBody);
        OctetReader whoHasReader = new OctetReader(whoIsBody);
        Octet lowerLimitOctet = whoHasReader.next();
        if (lowerLimitOctet.getFirstNibble() == LOWER_LIMIT_KEY) {
            int numberOfOctets = mapToValueOctetLength(lowerLimitOctet.getSecondNibble());
            String lowerLimitHex = whoHasReader.next(numberOfOctets);
            rangeLowLimit = HexUtils.toInt(lowerLimitHex);
        }
        Octet highLimitOctet = whoHasReader.next();
        if (highLimitOctet.getFirstNibble() == HIGH_LIMIT_KEY) {
            int numberOfOctets = mapToValueOctetLength(highLimitOctet.getSecondNibble());
            String highLimitHex = whoHasReader.next(numberOfOctets);
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

    static int mapToValueOctetLength(char lengthKey) {
        int length = 0;
        switch (lengthKey) {
            case '9':
                length = 1;
                break;
            case 'a':
                length = 2;
                break;
            case 'b':
                length = 3;
                break;
            default:
                log.debug("Unknown lenght of who is seccond nibble: {}", lengthKey);
                break;
        }
        return length;
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
            objectName = HexUtils.parseExtendedValue(encoding, objectNameHex);
        }
        if (objectName != null) {
            configuration = new ConfigurationRequest("TODO", null);
            configuration.setProperty("WhoHasObjectName", objectName);
        }

        return configuration;

    }
}
