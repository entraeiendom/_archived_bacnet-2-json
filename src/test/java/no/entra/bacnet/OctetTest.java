package no.entra.bacnet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OctetTest {

    @Test
    void isValidHexChar() {
    }

    @Test
    void getOctet() {
    }

    @Test
    void fromHexString() {
    }

    @Test
    void toStringTest() {
    }

    @Test
    void equalsTest() {
        Octet first = Octet.fromHexString("0a");
        Octet second = Octet.fromHexString("0a");
        assertEquals(first, second);
    }
}