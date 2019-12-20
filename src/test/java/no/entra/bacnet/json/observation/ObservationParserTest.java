package no.entra.bacnet.json.observation;

import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.observation.ObservationParser.buildChangeOfValueObservation;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObservationParserTest {

    @Test
    void buildObservationFromUnconfirmedCovNotificationTest() {
        String line = "810b00340100100209001c020007d22c020007d239004e09702e91002f09cb2e2ea4770c0b03b40a341d402f2f09c42e91002f4f000";
        BvlcResult bvlcResult = BvlcParser.parse(line);
        assertNotNull(bvlcResult);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        assertNotNull(npduResult);
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        String covHexString = service.getUnprocessedHexString();
        Observation observation = buildChangeOfValueObservation(covHexString);
        assertNotNull(observation);
    }

}