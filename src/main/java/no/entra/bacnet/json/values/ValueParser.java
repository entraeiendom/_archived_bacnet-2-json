package no.entra.bacnet.json.values;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.apdu.ApplicationTag;
import no.entra.bacnet.json.apdu.PDTag;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.reader.OctetReader;
import org.slf4j.Logger;

import static no.entra.bacnet.json.apdu.ApplicationTag.REAL_VALUE;
import static no.entra.bacnet.json.apdu.SDContextTag.TAG0LENGTH1;
import static no.entra.bacnet.json.utils.HexUtils.toFloat;
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

                if (valueReader.next().equals(PDTag.PDOpen2)) { //2f
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

    private static Object findValue(ApplicationTag applicationTag, String valueHex) {
        Object value = null;
        if (applicationTag.findType() == REAL_VALUE) {
            float floatValue = toFloat(valueHex);
            value = Float.valueOf(floatValue);
        }
        return value;
    }

}
