package no.entra.bacnet.json.services;

import com.jayway.jsonpath.JsonPath;
import no.entra.bacnet.json.Bacnet2Json;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.PduType;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

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
        //                    810a0025010400050f0109121c020200252c0000000039004e095519012e4441a4cccd2f4f00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
        String expectedJson = "{" +
                "  \"configurationRequest\": {" +
                "    \"observations\": [{" +
                "      \"observedAt\": \"2020-08-25T11:49:14.394374\"," +
                "      \"name\": \"PresentValue\"," +
                "      \"source\": {" +
                "        \"deviceId\": \"131109\"," +
                "        \"objectId\": \"0\"" +
                "      }," +
                "      \"value\": \"2e\"" +
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
        assertEquals("uncknown", JsonPath.read(observedJson, "$.configurationRequest.sender"));
        assertEquals("SubscribeCov", JsonPath.read(observedJson, "$.configurationRequest.service"));
        assertEquals("PresentValue", JsonPath.read(observedJson, "$.configurationRequest.observations[0].name"));
        assertEquals("131109",JsonPath.read(observedJson, "$.configurationRequest.observations[0].source.deviceId"));
        /*
        String observedAt = JsonPath.read(observedJson, "$.configurationRequest.observations[0].observedAt");
        String expectedAt = JsonPath.read(expectedJson, "$.configurationRequest.observations[0].observedAt");
        assertNotNull(observedAt);
        assertEquals(observedAt, expectedAt);
        JSONAssert.assertEquals(expectedJson, observedJson,
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("configurationRequest.observedAt", (o1, o2) -> true),
                        new Customization("configurationRequest.observations.observedAt", (o1, o2) -> true)
                ));
//        JSONAssert.assertEquals(expectedJson, observedJson, new CustomComparator(JSONCompareMode.LENIENT,
//                new Customization("configurationRequest.observations.observedAt", (o1, o2) -> true)));
//        assertEqualsIgnoreTimestamp(expectedJson, observedJson, "observedAt");

         */
    }

    @Test
    public void ignoringMultipleAttributesWorks() throws JSONException {
        String expected = "{" +
                "  \"configurationRequest\": {" +
                "    \"observations\": [{" +
                "      \"observedAt\": \"2020-08-26T11:49:14.394374\"," +
                "      \"name\": \"PresentValue\"," +
                "      \"source\": {" +
                "        \"deviceId\": \"131109\"," +
                "        \"objectId\": \"0\"" +
                "      }," +
                "      \"value\": \"2e\"" +
                "    }]," +
                " \"observedAt\":\"now\"," +
                "  }," +
                "  \"sender\": \"unknown\"," +
                "  \"service\": \"SubscribeCov\"" +
                "}";
        expected = "{\"configurationRequest\":{\"observations\":[{\"observedAt\":\"2020-08-25T12:23:02.183803\",\"name\":\"PresentValue\",\"source\":{\"deviceId\":\"131109\",\"objectId\":\"0\"},\"value\":\"2e\"}]},\"sender\":\"unknown\",\"service\":\"SubscribeCov\"}";
        String actual = "{" +
                "  \"configurationRequest\": {" +
                "    \"observations\": [{" +
                "      \"observedAt\": \"2020-08-26T11:49:14.394374\"," +
                "      \"name\": \"PresentValue\"," +
                "      \"source\": {" +
                "        \"deviceId\": \"131109\"," +
                "        \"objectId\": \"0\"" +
                "      }," +
                "      \"value\": \"2e\"" +
                "    }]," +
                " \"observedAt\":\"later\"," +
                "  }," +
                "  \"sender\": \"unknown\"," +
                "  \"service\": \"SubscribeCov\"" +
                "}";
        actual = "{\"configurationRequest\":{\"observations\":[{\"observedAt\":\"2020-08-25T12:23:02.183803\",\"name\":\"PresentValue\",\"source\":{\"deviceId\":\"131109\",\"objectId\":\"0\"},\"value\":\"2e\"}]},\"sender\":\"unknown\",\"service\":\"SubscribeCov\"}";

        JSONAssert.assertEquals(expected, actual,
                new CustomComparator(JSONCompareMode.LENIENT,
                        new Customization("configurationRequest.observedAt", (o1, o2) -> true),
                        new Customization("configurationRequest.observations.observedAt", (o1, o2) -> true)
                ));
    }


    void assertEqualsIgnoreTimestamp(String expectedJson, String observedJson, String ignoredParameter) {
        JSONAssert.assertEquals(expectedJson, observedJson, new CustomComparator(JSONCompareMode.LENIENT,
                new Customization(ignoredParameter, (o1, o2) -> true)));

    }
}
