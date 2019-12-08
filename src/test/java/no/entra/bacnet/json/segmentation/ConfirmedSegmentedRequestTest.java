package no.entra.bacnet.json.segmentation;

import no.entra.bacnet.json.ConfigurationRequest;
import no.entra.bacnet.json.bvlc.BvlcParser;
import no.entra.bacnet.json.bvlc.BvlcResult;
import no.entra.bacnet.json.npdu.Npdu;
import no.entra.bacnet.json.npdu.NpduParser;
import no.entra.bacnet.json.npdu.NpduResult;
import no.entra.bacnet.json.objects.ObjectId;
import no.entra.bacnet.json.objects.ObjectType;
import no.entra.bacnet.json.objects.PduType;
import no.entra.bacnet.json.objects.PropertyIdentifier;
import no.entra.bacnet.json.properties.PropertyRequest;
import no.entra.bacnet.json.properties.PropertyRequestBuilder;
import no.entra.bacnet.json.properties.PropertyResponse;
import no.entra.bacnet.json.properties.PropertyResponseBuilder;
import no.entra.bacnet.json.services.ConfirmedService;
import no.entra.bacnet.json.services.Service;
import no.entra.bacnet.json.services.ServiceParser;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.json.objects.PduType.ConfirmedRequest;
import static no.entra.bacnet.json.services.ConfirmedServiceChoice.ReadProperty;
import static org.junit.jupiter.api.Assertions.*;

public class ConfirmedSegmentedRequestTest {
    //server: Request Confirmed 88f0314123408c1645f7f51208004500002d7f280000801100000a3f17b20a3f0a13bac0bac00019366d810a001101040275010c0c02000457194c
    //device: Complex Ack - fragment 0/1 8c1645f7f51288f031412340080045000422433600007d11c0520a3f0a130a3f17b2bac0bac0040e613a810a040601043c0100030c0c02000457194c3ec402000457c4002dd2a9c4002dd2aac4002dd2abc4002dd2acc4002dd2adc4002dd2aec4002dd2afc4002dd2b0c4002dd2b1c4002dd2b2c4002dd2b9c4002dd2bac4002dd2bbc4002dd2bcc4002dd2bdc4002dd2bec4002dd2bfc4002dd2c0c4002dd2c2c4002dd2c3c4002dd2c4c4002dd2c5c4002dd2c7c4002dd2c8c4002dd2c9c4002dd2cac4002dd2cbc4002dd2ccc4002dd2cdc4002dd2cec4002dd2cfc4002dd2d0c4002dd2d1c4002dd2d2c4002dd2d3c4002dd2d4c4002dd2d6c4002dd2d7c4002dd2d8c4002dd2d9c4002dd2dbc4002dd2dcc4002dd33fc4002dd340c4002dd341c4002dd342c4002dd343c4002dd344c4002dd345c4002dd346c4002dd347c4002dd348c4002dd349c4002dd34ac4002dd34bc4002dd367c4002dd368c4002dd369c4002dd370c4002dd371c4002dd41fc4002dd420c4002dd421c4002dd422c4002dd423c4002dd424c4002dd425c4002dd43fc4002dd440c4002dd441c4002dd442c4002dd443c4002dd444c4002dd445c4002dd446c4002dd455c4002dd456c4002dd457c4002dd458c4002dd459c4002dd45ac4002dd45bc4002dd465c4002dd466c4002dd467c4002dd468c4002dd469c4002dd46ac4002dd46bc4002dd474c4002dd475c4002dd478c4002dd479c4002dd47ac4006dd2c1c4006dd2c6c4006dd2d5c4006dd2dac4006dd34cc4006dd34dc4006dd34ec4006dd34fc4006dd350c4006dd46cc4006dd47bc400add225c400add226c400add24cc400add24dc400add24ec400add24fc400add250c400add251c400add252c400add253c400add254c400add255c400add256c400add257c400add258c400add259c400add25ac400add25bc400add25cc400add25dc400add25ec400add25fc400add260c400add261c400add262c400add263c400add264c400add265c400add266c400add267c400add268c400add269c400add26ac400add26bc400add26cc400add26dc400add26ec400add26fc400add270c400add271c400add272c400add273c400add274c400add275c400add276c400add277c400add278c400add279c400add27ac400add27bc400add27cc400add27dc400add27ec400add27fc400add280c400add281c400add282c400add283c400add284c400add285c400add286c400add287c400add288c400add289c400add28ac400add28bc400add28cc400add28dc400add28ec400add2b4c400add2f6c400add2f7c400add2f8c400add2f9c400add2fac400add2fbc400add2fcc400add2fdc400add2fec400add2ffc400add300c400add301c400add302c400add303c400add304c400add305c400add306c400add307c400add308c400add309c400add30ac400add30bc400add30cc400add30dc400add30ec400add30fc4
    //server: Segment Ack 88f0314123408c1645f7f5120800450000267f290000801100000a3f17b20a3f0a13bac0bac000123666810a000a010440010003
    //device: Complex Ack - fragment 1/1 8c1645f7f51288f031412340080045000422433700007d11c0510a3f0a130a3f17b2bac0bac0040eb839810a040601043c0101030c00add310c400add311c400add312c400add313c400add314c400add315c400add316c400add317c400add318c400add319c400add31ac400add31bc400add31cc400add31dc400add31ec400add31fc400add320c400add321c400add322c400add323c400add324c400add325c400add326c400add327c400add328c400add329c400add36ac400add36bc400add36cc400add36dc400add36ec400add36fc400add447c400add448c400add449c400add44ac400add44bc400add44fc400add450c400add451c400add452c400add453c400add493c400add494c400add4a4c400add4a5c400edd351c400edd352c400edd353c400edd354c400edd355c400edd356c400edd357c400edd358c400edd359c400edd35ac400edd35bc400edd35cc400edd35dc400edd35ec400edd35fc400edd372c400edd373c400edd374c400edd375c400edd376c400edd377c400edd378c400edd379c400edd380c400edd381c400edd382c400edd46dc400edd46ec400edd46fc400edd470c400edd471c400edd472c400edd47cc400edd47dc400edd47ec400edd47fc400edd480c400edd481c400edd487c400edd488c4012dd360c4012dd361c4012dd362c4012dd363c4012dd364c4012dd365c4012dd366c4012dd37ac4012dd37bc4012dd37cc4012dd37dc4012dd37ec4012dd483c4012dd485c4016dd48dc4046dd210c4046dd22ec404edd28fc404edd290c404edd291c404edd292c404edd293c404edd294c404edd295c404edd296c404edd297c404edd298c404edd299c404edd29ac404edd29bc404edd29cc404edd29dc404edd29ec404edd29fc404edd2a0c404edd2a1c404edd2a2c404edd2a3c404edd2a4c404edd2a5c404edd2a6c404edd2a7c404edd2a8c404edd32ac404edd32bc404edd32cc404edd32dc404edd32ec404edd32fc404edd330c404edd331c404edd332c404edd333c404edd334c404edd335c404edd336c404edd337c404edd338c404edd339c404edd33ac404edd33bc404edd33cc404edd33dc404edd33ec404edd37fc4052dd1f4c4052dd1f5c4052dd1f6c4052dd383c4052dd384c4052dd385c4052dd386c4052dd387c4052dd388c4052dd389c4052dd38ac4052dd38bc4052dd38cc4052dd38dc4052dd38ec4052dd38fc4052dd390c4052dd391c4052dd392c4052dd393c4052dd394c4052dd395c4052dd396c4052dd397c4052dd398c4052dd399c4052dd39ac4052dd39bc4052dd39cc4052dd39dc4052dd39ec4052dd39fc4052dd3a0c4052dd3a1c4052dd3a2c4052dd3a3c4052dd3a4c4052dd3a5c4052dd3a6c4052dd3a7c4052dd3b1c4052dd3b8c4052dd3b9c4052dd3bac4052dd3bbc4052dd3bcc4052dd3bdc4052dd3bec4052dd3bfc4052dd3c0c4052dd3c1c4052dd3c2c4052dd3c3
    //??device: Complex Ack Message Reaseembled 8c1645f7f51288f0314123400800450001c2433800007d11c2b00a3f0a130a3f17b2bac0bac001ae6352810a01a60104380102030cc4052dd3c4c4052dd3c5c4052dd3c6c4052dd3c7c4052dd3c8c4052dd3c9c4052dd3cac4052dd3cbc4052dd3ccc4052dd3cdc4052dd3cec4052dd3cfc4052dd3d0c4052dd3d1c4052dd3d2c4052dd3d3c4052dd3d4c4052dd3d5c4052dd3d6c4052dd3d7c4052dd3d8c4052dd3d9c4052dd3dac4052dd3dbc4052dd3dcc4052dd3ddc4052dd3dec4052dd3dfc4052dd3e0c4052dd3e1c4052dd3e2c4052dd3e3c4052dd3e4c4052dd3e5c4052dd3e6c4052dd3e7c4052dd3e8c4052dd3e9c4052dd3eac4052dd3ebc4052dd3ecc4052dd3f9c4052dd3fac4052dd3fbc4052dd3fcc4052dd3fdc4052dd3fec4052dd3ffc4052dd400c4052dd401c4052dd402c4052dd403c4052dd404c4052dd405c4052dd406c4052dd407c4052dd408c4052dd409c4052dd40ac4052dd40bc4052dd40cc4052dd413c4052dd414c4052dd415c4052dd426c4052dd427c4052dd44cc4052dd44dc4052dd44ec4052dd45cc4052dd45dc4052dd4b6c4052dd4b7c4052dd4b8c4052dd4c5c4052dd4c6c4052dd4c7c4052dd4c8c4052dd4c9c4052dd4cac4052dd4cbc4052dd4cc3f
    //server: Segment Ack 88f0314123408c1645f7f5120800450000267f2a0000801100000a3f17b20a3f0a13bac0bac000123666810a000a010040010203

    @Test
    void confirmedRequestReadProperty() {
        String confirmedReqExpectingReadProperty = "810a001101040275370c0c02000204194c";
        BvlcResult bvlcResult = BvlcParser.parse(confirmedReqExpectingReadProperty);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Npdu npdu = npduResult.getNpdu();
        assertNotNull(npdu);
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertEquals(ConfirmedRequest, service.getPduType());
        assertEquals(ReadProperty, service.getServiceChoice());
        assertNotNull(service.getMaxAcceptedPduSize());
        assertEquals(55, service.getInvokeId());
//        get properties
        assertTrue(service instanceof ConfirmedService);
        ConfigurationRequest request = ConfirmedService.tryToUnderstandConfirmedRequest(service);
        assertNotNull(request);
        //objectIdentifier, device, 516
        //property-identifier, object-list (76)
    }

    @Test
    void buildPropertyRequest() {
        String confirmedReqExpectingReadProperty = "810a001101040275370c0c02000204194c";
        BvlcResult bvlcResult = BvlcParser.parse(confirmedReqExpectingReadProperty);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Npdu npdu = npduResult.getNpdu();
        assertNotNull(npdu);
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        PropertyRequest request = new PropertyRequestBuilder(service, service.getUnprocessedHexString()).build();

        assertEquals(ConfirmedRequest, request.getPduType());
        assertEquals(ReadProperty, request.getServiceChoice());
        assertNotNull(request.getMaxAdpuOctetLenghtAccepted());
        assertEquals(55, request.getInvokeId());
        ObjectId objectId = new ObjectId(ObjectType.Device, "516");
        assertEquals(objectId, request.getDesiredObjectId());
        assertEquals(PropertyIdentifier.ObjectList, request.getDesiredPropertyIds().get(0));
    }

    @Test
    void buildFirstPropertyResponse() {
        String firstSegmentHexString = "810a040601043c1f00040c0c02000205194c3ec402000205c4002dcaa6c4002dcaa7c4002dcbb4c4002dcbb5c4002dcbe9c4002dcbeac4002dcbebc4002dcbecc4002dcbf0c4002dcbf1c4002dcc27c4002dcc28c4002dcc32c4002dcc33c4002dcc34c4002dcc35c4002dcc36c4006dcaa2c4006dcaa3c4006dcaa4c4006dcbb0c4006dcbb1c4006dcbb2c4006dcc2fc4006dcc30c4006dcc31c4006dcc38c4006dcc39c400adca70c400adca71c400adca72c400adca73c400adca74c400adca75c400adca76c400adca77c400adca78c400adca79c400adca7ac400adca7bc400adca7cc400adca7dc400adca7ec400adca7fc400adca80c400adca81c400adca82c400adca83c400adca84c400adca85c400adca86c400adca87c400adca88c400adca89c400adca8ac400adca8bc400adca8cc400adca8dc400adca8ec400adca8fc400adca90c400adca91c400adca92c400adca93c400adca94c400adca95c400adca96c400adca97c400adca98c400adca99c400adca9ac400adca9bc400adca9cc400adca9dc400adca9ec400adca9fc400adcaa0c400adcb7ec400adcb7fc400adcb80c400adcb81c400adcb82c400adcb83c400adcb84c400adcb85c400adcb86c400adcb87c400adcb88c400adcb89c400adcb8ac400adcb8bc400adcb8cc400adcb8dc400adcb8ec400adcb8fc400adcb90c400adcb91c400adcb92c400adcb93c400adcb94c400adcb95c400adcb96c400adcb97c400adcb98c400adcb99c400adcb9ac400adcb9bc400adcb9cc400adcb9dc400adcb9ec400adcb9fc400adcba0c400adcba1c400adcba2c400adcba3c400adcba4c400adcba5c400adcba6c400adcba7c400adcba8c400adcba9c400adcbaac400adcbabc400adcbacc400adcbadc400adcbaec400adcbf4c400adcbf5c400adcbf6c400adcbf7c400adcbf9c400adcbfbc400adcbfcc400adcbfdc400adcbfec400adcbffc400adcc00c400adcc01c400adcc02c400adcc04c400adcc05c400adcc06c400adcc07c400adcc08c400adcc09c400adcc14c400adcc19c400adcc1ac400adcc1bc400adcc1cc400adcc23c400adcc24c400adcc25c400adcc26c400adccbbc400edca62c400edca63c400edca64c400edca65c400edca66c400edca67c400edca68c400edca69c400edca6ac400edca6bc400edca6cc400edca6dc400edca6ec400edcb70c400edcb71c400edcb72c400edcb73c400edcb74c400edcb75c400edcb76c400edcb77c400edcb78c400edcb79c400edcb7ac400edcb7bc400edcb7cc400edcbe6c400edcbe7c400edcbe8c400edcbedc400edcbeec400edcc2bc400edcc2cc400edcc2dc400edcc37c400edcc3ac400edcc3bc4012dca4fc4012dca50c4012dca51c4012dca52c4012dca53c4012dca54c4012dca55c4012dca56c4012dca57c4";
        BvlcResult bvlcResult = BvlcParser.parse(firstSegmentHexString);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Npdu npdu = npduResult.getNpdu();
        assertNotNull(npdu);
        assertFalse(npdu.isDestinationAvailable());
        assertTrue(npdu.isExpectingResponse());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertFalse(service.isWillAcceptSegmentedResponse());
        assertTrue(service.isSegmented());
        assertTrue(service.isHasMoreSegments());
        assertEquals(PduType.ComplexAck,service.getPduType());
        assertEquals(31, service.getInvokeId());
        assertEquals(0, service.getSequenceNumber());
        assertEquals(4, service.getProposedWindowSize());
        assertEquals(ReadProperty, service.getServiceChoice());

        PropertyResponse response = new PropertyResponseBuilder(service, service.getUnprocessedHexString()).build();
        assertNotNull(response);
        assertEquals(31, response.getInvokeId());
        assertEquals(0, response.getSequenceNumber());
        assertEquals(4, response.getPropsedWindowSize());
        assertEquals(ReadProperty, response.getServiceChoice());
        String segmentBodyHexString = response.getSegmentBodyHexString();
        assertNotNull(segmentBodyHexString);
        assertTrue(segmentBodyHexString.startsWith("0c"));
        assertTrue(segmentBodyHexString.endsWith("c4"));
        assertEquals(2038, segmentBodyHexString.length());

    }

    @Test
    void buildLastSegmentedMessage() {
        String reassembled = "810a02010104381f03040c0cc404edcc0dc404edcc0ec404edcc0fc404edcc10c404edcc12c404edcc16c404edcc17c404edcc18c404edcc1ec404edcc20c404edcd00c4052dc6c5c4052dcc4bc4052dcc4cc4052dcc4dc4052dcc4ec4052dcc4fc4052dcc50c4052dcc51c4052dcc52c4052dcc53c4052dcc54c4052dcc55c4052dcc56c4052dcc57c4052dcc58c4052dcc59c4052dcc5ac4052dcc5bc4052dcc5cc4052dcc5dc4052dcc5ec4052dcc5fc4052dcc60c4052dcc61c4052dcc62c4052dcc63c4052dcc64c4052dcc65c4052dcc66c4052dcc67c4052dcc68c4052dcc69c4052dcc6ac4052dcc6bc4052dcc6cc4052dcc6dc4052dcc6ec4052dcc6fc4052dcc70c4052dcc71c4052dcc72c4052dcc73c4052dcc74c4052dcc75c4052dcc76c4052dcc77c4052dcc78c4052dcc79c4052dcc7ac4052dcc7bc4052dcc7cc4052dcc7dc4052dcc7ec4052dcc7fc4052dcc80c4052dcc81c4052dcc82c4052dcc83c4052dcc84c4052dcc85c4052dcc86c4052dcc87c4052dcc88c4052dcc89c4052dcc8ac4052dcc8bc4052dcc8cc4052dcc8dc4052dcc8ec4052dcc8fc4052dcc90c4052dcc91c4052dcc93c4052dcc94c4052dcc95c4052dcc96c4052dcc97c4052dcc98c4052dcc99c4052dcc9ac4052dcc9bc4052dcc9cc4052dcc9dc4052dcc9ec4052dcc9fc4052dcca0c4052dccb9c4052dccbac4052dcd013f";
        BvlcResult bvlcResult = BvlcParser.parse(reassembled);
        NpduResult npduResult = NpduParser.parse(bvlcResult.getUnprocessedHexString());
        Npdu npdu = npduResult.getNpdu();
        assertNotNull(npdu);
        assertFalse(npdu.isDestinationAvailable());
        assertTrue(npdu.isExpectingResponse());
        Service service = ServiceParser.fromApduHexString(npduResult.getUnprocessedHexString());
        assertNotNull(service);
        assertFalse(service.isWillAcceptSegmentedResponse());
        assertTrue(service.isSegmented());
        assertFalse(service.isHasMoreSegments());
        assertEquals(PduType.ComplexAck,service.getPduType());
        assertEquals(31, service.getInvokeId());
        assertEquals(3, service.getSequenceNumber());
        assertEquals(4, service.getProposedWindowSize());
        assertEquals(ReadProperty, service.getServiceChoice());

        PropertyResponse response = new PropertyResponseBuilder(service, service.getUnprocessedHexString()).build();
        assertNotNull(response);
        assertEquals(31, response.getInvokeId());
        assertEquals(3, response.getSequenceNumber());
        assertEquals(4, response.getPropsedWindowSize());
        assertEquals(ReadProperty, response.getServiceChoice());
        String segmentBodyHexString = response.getSegmentBodyHexString();
        assertNotNull(segmentBodyHexString);
        assertTrue(segmentBodyHexString.startsWith("0c"));
        assertTrue(segmentBodyHexString.endsWith("3f"));
        assertEquals(1004, segmentBodyHexString.length());
    }
}
