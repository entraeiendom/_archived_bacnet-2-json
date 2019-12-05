package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.objects.PduType;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.objects.PduType.ComplexAck;
import static no.entra.bacnet.json.services.UnconfirmedServiceChoice.IAm;
import static org.junit.jupiter.api.Assertions.*;

class ServiceBuilderTest {

    @Test
    void withServiceChoice() {
        Octet iAm = Octet.fromHexString(IAm.getServiceChoiceHex());
        Service service = new ServiceBuilder(PduType.UnconfirmedRequest)
                .withServiceChoice(iAm)
                .build();
        assertNotNull(service);
        assertEquals(IAm, service.getServiceChoice());

    }

    @Test
    void build() {
        Service service = new ServiceBuilder(ComplexAck).build();
        assertNotNull(service);
        assertEquals(ComplexAck, service.getPduType());
        assertThrows(IllegalArgumentException.class, () -> {new ServiceBuilder(null).build();});
    }
}