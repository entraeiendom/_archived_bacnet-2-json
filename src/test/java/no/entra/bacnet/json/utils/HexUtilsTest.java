package no.entra.bacnet.json.utils;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HexUtilsTest {

    private static String DESCRIPTION_UCS2_HEX = "0053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e00520054003000300031";
   private static String EXPECTED_DESCRIPTION = "SOKP16-NAE4/FCB.434_101-1OU001.RT001";

    @Test
    void parseUCS2() {
        assertEquals(EXPECTED_DESCRIPTION, HexUtils.parseUCS2(DESCRIPTION_UCS2_HEX));
    }

    @Test
    void parseExtendedValue() {
        String description = HexUtils.parseExtendedValue(Octet.fromHexString("04"), DESCRIPTION_UCS2_HEX);
        assertEquals(EXPECTED_DESCRIPTION, description);
    }

    @Test
    void toInt() {
    }

    @Test
    void toInt1() {
    }
}