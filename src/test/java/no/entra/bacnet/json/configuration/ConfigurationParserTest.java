package no.entra.bacnet.json.configuration;

import no.entra.bacnet.json.ConfigurationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigurationParserTest {

    @Test
    void buildWhoIsRequest() {
        String whoIsBody = "0a07ae1a07ae";
        ConfigurationRequest configuration = ConfigurationParser.buildWhoIsRequest(whoIsBody);
        assertNotNull(configuration);
        assertEquals("1966",configuration.getProperty("DeviceInstanceRangeLowLimit"));
        assertEquals("1966",configuration.getProperty("DeviceInstanceRangeHighLimit"));
        whoIsBody = "0b26259f1b26259f310036002d004e004100450032002f004600430042002e004c006f00630061006c0020004";
        configuration = ConfigurationParser.buildWhoIsRequest(whoIsBody);
        assertNotNull(configuration);
        assertEquals("2499999",configuration.getProperty("DeviceInstanceRangeLowLimit"));
        assertEquals("2499999",configuration.getProperty("DeviceInstanceRangeHighLimit"));
        whoIsBody = "09101910";
        configuration = ConfigurationParser.buildWhoIsRequest(whoIsBody);
        assertNotNull(configuration);
        assertEquals("16",configuration.getProperty("DeviceInstanceRangeLowLimit"));
        assertEquals("16",configuration.getProperty("DeviceInstanceRangeHighLimit"));

    }

    @Test
    void mapToValueOctetLengthTest() {
        assertEquals(1, ConfigurationParser.mapWhoIsLength('9'));
        assertEquals(2, ConfigurationParser.mapWhoIsLength('a'));
        assertEquals(3, ConfigurationParser.mapWhoIsLength('b'));
        assertEquals(0, ConfigurationParser.mapWhoIsLength('c'));
    }

    @Test
    void buildWhoHasRequest() {
        String whoHasBody = "3d15040053004f004d00530032002d004e004100450058002f005300630068006500640075006c0065002e004b0061006c0065006e00640065007200200041004b0053004500200038002e00500072006f006700720061006d006d0065007200620061007200650020006d006f00640075006c00650072002e00440043004f002e00440043004f0035004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076";
        ConfigurationRequest configuration = ConfigurationParser.buildWhoHasRequest(whoHasBody);
        assertNotNull(configuration);
        assertEquals("SOMS2-NAEX", configuration.getProperty("ObjectName"));
    }

    @Test
    void buildTimeSynchronizationRequestTest() {
        String timeSyncBody = "a4770b1b03b40c0b3939";
        ConfigurationRequest configuration = ConfigurationParser.buildTimeSynchronizationRequest(timeSyncBody);
        assertNotNull(configuration);
        assertEquals("2019-11-27",configuration.getProperty("TimeSyncDate"));
        assertEquals("12:11:57",configuration.getProperty("TimeSyncTime"));
    }
}