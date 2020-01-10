package no.entra.bacnet.json.parser;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.parser.CharacterStringParser.decodeCharacterHexString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterStringParserTest {

    @Test
    void decodeCharacterHexStringTest() {
        assertEquals("", decodeCharacterHexString("7100"));
    }

}