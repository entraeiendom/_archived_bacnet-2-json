package no.entra.bacnet.json.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OctetReaderTest {

    private OctetReader octetReader;

    @BeforeEach
    void setUp() {
        octetReader = new OctetReader("af01a0");
    }

    @Test
    void next() {
        assertEquals("af", octetReader.next().toString());
        assertEquals("01", octetReader.next().toString());
        assertEquals("a0", octetReader.next().toString());
        assertNull(octetReader.next());
    }

    @Test
    void hasNext() {
        octetReader = new OctetReader("af");
        assertTrue(octetReader.hasNext());
        octetReader.next();
        assertFalse(octetReader.hasNext());
    }

    @Test
    void getCurrentPos() {
        octetReader.next();
        assertEquals(2, octetReader.getCurrentPos());
    }
}