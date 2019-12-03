package no.entra.bacnet.json.bvlc;

import no.entra.bacnet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BvlcBuilderTest {

    @Test
    void build() {
        Octet[] messageLength = {Octet.fromHexString("00"), Octet.fromHexString("18")};
        Bvlc bvlc = new BvlcBuilder(BvlcFunction.ForwardedNpdu).withMessageLength(messageLength).build();
        assertNotNull(bvlc);
        assertEquals(BvlcFunction.ForwardedNpdu, bvlc.getFunction());
        assertEquals(24, bvlc.getFullMessageLength());
    }
}