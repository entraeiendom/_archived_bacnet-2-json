package no.entra.bacnet.json.services;

import no.entra.bacnet.json.objects.PduType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhoIsTest {

    public static final String fullHexString = "ffffffffffff00108d054f9b080045000034767f0000801180750a3f17480a3f17ffbac0bac00020dd48810400180a3f510cbac00120ffff00ff10080a07ae1a07ae";
    public static final String bacnetHexString = "810400180a3f510cbac00120ffff00ff10080a07ae1a07ae";
    public static final String bvlcHexString = "810400180a3f510cbac0";
    public static final String npduHexString = "0120ffff00ff";
    public static final String apduHexString = "10080a07ae1a07ae";

    @Test
    void findWhoIsService() {
        UnconfirmedService whoIsService = (UnconfirmedService) ServiceParser.fromApduHexString(apduHexString);
        assertEquals(PduType.UnconfirmedRequest, whoIsService.getPduType());
        assertEquals(UnconfirmedServiceChoice.WhoIs, whoIsService.getServiceChoice());
    }
}
