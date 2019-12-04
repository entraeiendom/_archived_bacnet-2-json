package no.entra.bacnet.json.configuration;

import no.entra.bacnet.json.ConfigurationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigurationParserTest {

    @Test
    void buildWhoIsRequest() {
        String whoIsBody = "0911191153004f004d00530032002d004e004100450058002f005300630068006500640075006c0065002e004b0061006c0065006e00640065007200200041004b0053004500200038002e00500072006f006700720061006d006d0065007200620061007200650020006d006f00640075006c00650072002e00440043004f002e00440043004f0035004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076";
        ConfigurationRequest configuration = ConfigurationParser.buildWhoIsRequest(whoIsBody);
        assertNotNull(configuration);
        assertEquals(1966,configuration.getProperty("LowerLimit"));
    }

    @Test
    void buildWhoHasRequest() {
        String whoHasBody = "3d15040053004f004d00530032002d004e004100450058002f005300630068006500640075006c0065002e004b0061006c0065006e00640065007200200041004b0053004500200038002e00500072006f006700720061006d006d0065007200620061007200650020006d006f00640075006c00650072002e00440043004f002e00440043004f0035004600310031002e0044006100670042007600690031002d00310031002e004400610067004200760076";
        ConfigurationRequest configuration = ConfigurationParser.buildWhoHasRequest(whoHasBody);
        assertNotNull(configuration);
        assertEquals("SOMS2-NAEX", configuration.getProperty("ObjectName"));
    }
}