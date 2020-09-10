package no.entra.bacnet.json.observation;

import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.ObservationList;
import no.entra.bacnet.json.Source;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.reader.OctetReader;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import no.entra.bacnet.json.values.Value;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;

import static no.entra.bacnet.json.observation.ObservationParser.mapToChangeOfValueObservation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

class ObservationParserTest {
    private static final Logger log = getLogger(ObservationParserTest.class);

    @Test
    void validateUnConfirmedCovNotificationTest() {
        String line = "810a00280100100209f81c020003e92c0080000139004e09552e44000000002f096f2e8204002f4f";
        BvlcResult bvlcResult = BvlcParser.parse(line);
        assertNotNull(bvlcResult);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        assertNotNull(npduResult);
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        String covHexString = service.getUnprocessedHexString();
        ObservationList observations = mapToChangeOfValueObservation(service, covHexString);
        assertNotNull(observations);
        Source source = observations.getObservations().get(0).getSource();
        assertEquals("1001", source.getDeviceId());
        assertEquals("AnalogValue_1", source.getObjectId());
        source = observations.getObservations().get(0).getSource();
        assertEquals("1001", source.getDeviceId());
        assertEquals("AnalogValue_1", source.getObjectId());

        //Verify value
        Observation observation = observations.getObservations().get(1);
        assertEquals("StatusFlags", observation.getName());
        assertEquals("#0400", observation.getValue());
        observation = observations.getObservations().get(0);
        assertEquals("PresentValue", observation.getName());
        assertEquals(0.0f, observation.getValue());

        //TimeRemaining
        int expectedTimeRemaingSeconds = 0;
        int timeRemaing = observations.getSubscriptionRemainingSeconds();
        assertEquals(expectedTimeRemaingSeconds, timeRemaing);
    }

    @Test
    void buildObservationFromUnConfirmedCovNotificationTest() {
        String line = "810b00340100100209001c020007d22c020007d239004e09702e91002f09cb2e2ea4770c0b03b40a341d402f2f09c42e91002f4f000";
        BvlcResult bvlcResult = BvlcParser.parse(line);
        assertNotNull(bvlcResult);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        assertNotNull(npduResult);
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        String covHexString = service.getUnprocessedHexString();
        ObservationList observations = mapToChangeOfValueObservation(service, covHexString);
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
        String apduHexString = npduResult.getUnprocessedHexString();
        Service service = ServiceParser.fromApduHexString(apduHexString);
        String covHexString = service.getUnprocessedHexString();
        ObservationList observations = mapToChangeOfValueObservation(service, covHexString);
        assertNotNull(observations);
        Source source = observations.getObservations().get(0).getSource();
        assertEquals("1001", source.getDeviceId());
        assertEquals("AnalogValue_1", source.getObjectId());
        source = observations.getObservations().get(0).getSource();
        assertEquals("1001", source.getDeviceId());
        assertEquals("AnalogValue_1", source.getObjectId());

        //Verify value
        Observation observation = observations.getObservations().get(0);
        assertEquals("StatusFlags", observation.getName());
        assertEquals("0400", observation.getValue());
        observation = observations.getObservations().get(1);
        assertEquals("PresentValue", observation.getName());
        assertEquals("00000000", observation.getValue());

        //TimeRemaining
        int expectedTimeRemaingSeconds = 299;
        int timeRemaing = observations.getSubscriptionRemainingSeconds();
        assertEquals(expectedTimeRemaingSeconds, timeRemaing);
    }

    @Test
    void validateConfirmedCovNotificationWithPresentValue() {
        String covHexString = "0f0109121c020200252c0000000039004e095519012e4441a4cccd2f4f";
        Service service = new Service(PduType.ConfirmedRequest, null);
        ObservationList observations = mapToChangeOfValueObservation(service, covHexString);
//        ObservationList observations = parseConfirmedCOVNotification(covHexString);
        Observation observation = observations.getObservations().get(0);
        assertEquals("131109", observation.getSource().getDeviceId());
        assertEquals("AnalogInput_0", observation.getSource().getObjectId());
        assertEquals("PresentValue", observation.getName());
        assertEquals(20.6f, observation.getValue());

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
        ObservationList observations = mapToChangeOfValueObservation(service, covHexString);
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

    @Test
    void parseListOfValuesTest() {
        String resultListHexString = "4e09702e91002f09cb2e2ea4770c0b03b40a341d402f2f09c42e91002f4f000";
        List<Value> values = ObservationParser.parseListOfValues(resultListHexString);
        assertNotNull(values);

    }

    @Test
    void parseSingleValue() {
        String listOfValuesHex = "4e095519012e4441a4cccd2f4f";
        List<Value> values = ObservationParser.parseListOfValues(listOfValuesHex);
        assertNotNull(values);
        Value analogValue0 = values.get(0);
        assertEquals(PropertyIdentifier.PresentValue, analogValue0.getPropertyIdentifier());
        assertEquals(20.6f, analogValue0.getValue());
    }
}