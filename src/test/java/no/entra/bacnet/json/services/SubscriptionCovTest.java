package no.entra.bacnet.json.services;

import com.jayway.jsonpath.JsonPath;
import no.entra.bacnet.json.Bacnet2Json;
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
        String observedHex = "810a002501040005720109121c0212c0e72c0000000039004e095519012e4441a5999a2f4f";
        BvlcResult bvlcResult = BvlcParser.parse(observedHex);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        String apduHexString = npduResult.getUnprocessedHexString();
        ConfirmedService whoHasService = (ConfirmedService) ServiceParser.fromApduHexString(apduHexString);
        assertEquals(PduType.ConfirmedRequest, whoHasService.getPduType());
        assertEquals(ConfirmedServiceChoice.SubscribeCov, whoHasService.getServiceChoice());
    }

    @Test
    void verifyConfirmedCOVNotificationSingleProperty() {
        String observedHex = "810a0025010400050f0109121c020200252c0000000039004e095519012e4441a4cccd2f4f";
        String expectedJson = "{" +
                "  \"configurationRequest\": {" +
                "    \"observations\": [{" +
                "      \"observedAt\": \"2020-08-25T11:49:14.394374\"," +
                "      \"name\": \"PresentValue\"," +
                "      \"source\": {" +
                "        \"deviceId\": \"131109\"," +
                "        \"objectId\": \"0\"" +
                "      }," +
                "      \"value\": \"20.6\"" +
                "    }]" +
                "  }," +
                "  \"sender\": \"unknown\"," +
                "  \"service\": \"SubscribeCov\"" +
                "}";
        String observedJson = Bacnet2Json.hexStringToJson(observedHex);
        //Expect
        //ProcessIdentifier: 18
        //DeviceIdentifier: device, 131109
        //ObjectIdentifier: analog-input, 0
        //List of values:
        //  Property Identifier: present-value (85)
        //  property array index
        //  Presen Value (real): 20,60000003814697
        //Hard to get JSONAssert to ignore observedAt. Hardcoding the test for now.
        assertEqualsPath(expectedJson, observedJson, "$.sender");
        assertEqualsPath(expectedJson, observedJson, "$.service");
        assertEqualsPath(expectedJson, observedJson, "$.configurationRequest.observations[0].name");
        assertEqualsPath(expectedJson, observedJson, "$.configurationRequest.observations[0].source.deviceId");
        assertEqualsPath(expectedJson, observedJson, "$.configurationRequest.observations[0].value");
    }

    void assertEqualsPath(String expectedJason, String observedJson, String jsonPath) {
        String expected = JsonPath.read(expectedJason, jsonPath);
        Object observed = JsonPath.read(observedJson, jsonPath);
        assertEquals(expected, observed.toString(), "Unexpected in path: " + jsonPath);
    }
}
