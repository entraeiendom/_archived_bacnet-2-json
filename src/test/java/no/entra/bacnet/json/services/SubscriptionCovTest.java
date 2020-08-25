package no.entra.bacnet.json.services;

import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriptionCovTest {


    @Test
    void findConfirmedCov() {
        String observedHex = "810a002501040005720109121c0212c0e72c0000000039004e095519012e4441a5999a2f4f0000000";
        BvlcResult bvlcResult = BvlcParser.parse(observedHex);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        String apduHexString = npduResult.getUnprocessedHexString();
        ConfirmedService whoHasService = (ConfirmedService) ServiceParser.fromApduHexString(apduHexString);
        assertEquals(PduType.ConfirmedRequest, whoHasService.getPduType());
        assertEquals(ConfirmedServiceChoice.SubscribeCov, whoHasService.getServiceChoice());
    }
}
