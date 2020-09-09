package no.entra.bacnet.json.apdu;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.apdu.ApplicationTag.APPTAG4LENGTH4;
import static no.entra.bacnet.json.apdu.ApplicationTag.APPTAG8LENGTH2;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTagTest {

    @Test
    void getLength() {
        assertEquals(2, new ApplicationTag(APPTAG8LENGTH2).findLength());
        assertEquals(4, new ApplicationTag(APPTAG4LENGTH4).findLength());
    }

    @Test
    void equalsTest() {
        assertEquals(new Octet("82"), APPTAG8LENGTH2);
    }
}