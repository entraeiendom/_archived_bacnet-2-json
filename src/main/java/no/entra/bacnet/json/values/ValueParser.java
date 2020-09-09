package no.entra.bacnet.json.values;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.apdu.ApplicationTag;
import no.entra.bacnet.json.apdu.PDTag;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static no.entra.bacnet.json.apdu.ApplicationTag.INT_VALUE;
import static no.entra.bacnet.json.apdu.ApplicationTag.REAL_VALUE;
import static no.entra.bacnet.json.apdu.ArrayTag.ARRAYLENGTH1;
import static no.entra.bacnet.json.apdu.SDContextTag.TAG0LENGTH1;
import static no.entra.bacnet.json.utils.HexUtils.toFloat;
import static no.entra.bacnet.json.utils.HexUtils.toInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ValueParser {
    private static final Logger log = getLogger(ValueParser.class);


    /*
      Present Value, Real 09552e44428200002f
      1. Property Identifier (SD Context Tag 0, Length 1)
      2. Value (PD Opening Tag, 2e)
      3. Value type (real, bit string...) (Application Tag, Length 1-4)
      4. Obsrved Value (hex or bit sequence)
    */
    public static ValueParserResult parseValue(String hexString) {
        ValueParserResult parserResult;
        OctetReader valueReader = new OctetReader(hexString);
        Octet firstOctet = valueReader.next();
        if (firstOctet.equals(TAG0LENGTH1)) {  //09
            Value value = null;
            PropertyIdentifier propertyId = null;
                Octet propertyIdOctet = valueReader.next(); //55
                propertyId = PropertyIdentifier.fromPropertyIdentifierHex(propertyIdOctet.toString());

            Octet arrayOrValue = valueReader.next();
            if (arrayOrValue.equals(ARRAYLENGTH1)) {
                Octet lengthOfArrayOctet = valueReader.next();
                int arrayLength = toInt(lengthOfArrayOctet);
                arrayOrValue = valueReader.next();
            }
            if (arrayOrValue.equals(PDTag.PDOpen2)) { //2f
                    Octet applicationTagOctet = valueReader.next();
                    ApplicationTag applicationTag = new ApplicationTag(applicationTagOctet);
                    int readLength = applicationTag.findLength();
                    String valueHex = valueReader.next(readLength);
                    Object valueObj = findValue(applicationTag, valueHex);
                    value = new Value(propertyId, valueObj);
                }
            String unprocessedHexString = valueReader.unprocessedHexString();
                Octet nextInString = valueReader.next(); {
                    if (nextInString.equals(PDTag.PDClose2)) {
                        // do nothing
                        unprocessedHexString = valueReader.unprocessedHexString();
                    } else {
                        //TODO parse eg 3c 03173400 - Time of Change
                    }
            }
            log.trace("unprocessed: {}", unprocessedHexString);
            parserResult = new ValueParserResult(hexString, value);
            parserResult.setUnparsedHexString(unprocessedHexString);
        } else {
           parserResult = new ValueParserResult(hexString, "HexString not starting with " + TAG0LENGTH1);
        }
        return parserResult;
    }

    /**
     * Parse from eg 4e095519012e4441a4cccd2f4f
     * @param listOfValuesHexString Should start with 4e and end with 4f
     * @return List of the values found.
     */
    public static List<Value> parseListOfValues(String listOfValuesHexString) {
        List<Value> values = new ArrayList<>();
        OctetReader valuesReader = new OctetReader(listOfValuesHexString);
        if (listOfValuesHexString != null) {
            if (valuesReader.next().equals(PDTag.PDOpen4)) {
                String unprocessedHexString = valuesReader.unprocessedHexString();
                while (unprocessedHexString != null && !unprocessedHexString.startsWith(PDTag.PDClose4.toString())) {
                    ValueParserResult valueResult = parseValue(unprocessedHexString);
                    Value value = valueResult.getValue();
                    if (value != null) {
                        values.add(value);
                    } else {
                        log.info("Failed to parse unprocesedHexString: {}", unprocessedHexString);
                    }
                    unprocessedHexString = valueResult.getUnparsedHexString();
                }
            }
        }
        return values;
    }

    private static Object findValue(ApplicationTag applicationTag, String valueHex) {
        Object value = null;
        int type = applicationTag.findType();
        switch (type) {
            case INT_VALUE:
                int intValue = toInt(valueHex);
                value = Integer.valueOf(intValue);
                break;
            case REAL_VALUE:
                float floatValue = toFloat(valueHex);
                value = Float.valueOf(floatValue);
                break;
            default:
                value = null;
        }
        return value;
    }

}
