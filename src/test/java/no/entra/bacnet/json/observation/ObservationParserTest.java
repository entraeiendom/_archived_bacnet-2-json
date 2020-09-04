package no.entra.bacnet.json.observation;

import no.entra.bacnet.json.ObservationList;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.observation.ObservationParser.buildChangeOfValueObservation;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        ObservationList observations = buildChangeOfValueObservation(covHexString);
        assertNotNull(observations);
        assertEquals(2, observations.getObservations().size());
    }

    @Test
    void buildObservationFromConfirmedCovNotificationTest() {
        String line = "810a002b01040005060109121c020003e92c008000013a012b4e09552e44000000002f096f2e8204002f4f";
        BvlcResult bvlcResult = BvlcParser.parse(line);
        assertNotNull(bvlcResult);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        assertNotNull(npduResult);
//        String apduHexString = npduResult.getUnprocessedHexString();
//        Service service = ServiceParser.fromApduHexString(apduHexString);
    }

    @Test
    void validateRestartTime() {
        String line = "810b00340100100209001c020007d22c020007d239004e09702e91002f09cb2e2ea4770c0b03b40a341d402f2f09c42e91002f4f000";
        BvlcResult bvlcResult = BvlcParser.parse(line);
        assertNotNull(bvlcResult);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        assertNotNull(npduResult);
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        String covHexString = service.getUnprocessedHexString();
        ObservationList observations = buildChangeOfValueObservation(covHexString);
        assertNotNull(observations);
        assertEquals(2, observations.getObservations().size());
        //#13 Fix test to fail, then succed
    }

    @Test
    void findArray() {
        String arrayWithMoreHex = "2e4441a4cccd2f4f";
        OctetReader reader = new OctetReader(arrayWithMoreHex);
        String arrayContent = ObservationParser.findArrayContent(reader);
        assertEquals("4441a4cccd", arrayContent);
        assertEquals("4f", reader.unprocessedHexString());
    }
}