package no.entra.bacnet.json.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServiceParserTest {

    @Test
    void iamService() {
        String iamApdu = "1000c40200020f22040091002105";
        Service service = ServiceParser.fromApduHexString(iamApdu);
        assertNotNull(service);
        assertEquals(UnconfirmedServiceChoice.IAm, service.getServiceChoice());
    }
}