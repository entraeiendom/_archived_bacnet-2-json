package no.entra.bacnet.json.services;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.BacnetMessage;
import no.entra.bacnet.json.objects.PduType;
import org.slf4j.Logger;

import static no.entra.bacnet.json.configuration.ConfigurationParser.*;
import static no.entra.bacnet.json.observation.ObservationParser.parseChangeOfValueNotification;
import static org.slf4j.LoggerFactory.getLogger;

public class UnconfirmedService extends Service {
    private static final Logger log = getLogger(UnconfirmedService.class);
    public UnconfirmedService(PduType pduType, Octet serviceChoice) {
        super(pduType, UnconfirmedServiceChoice.fromOctet(serviceChoice));
    }

    public static BacnetMessage tryToUnderstandUnconfirmedRequest(Service service) {
        BacnetMessage bacnetMessage = null;
        if (service == null) {
            return null;
        }
        ServiceChoice serviceChoice = service.getServiceChoice();
        if (serviceChoice != null && serviceChoice instanceof UnconfirmedServiceChoice) {
            UnconfirmedServiceChoice unconfirmedServiceChoice = (UnconfirmedServiceChoice) serviceChoice;
            switch (unconfirmedServiceChoice) {
                case WhoIs:
                    log.trace("Is WhoIsMessage. hexString: {}", service.getUnprocessedHexString());
                    String whoIsBody = service.getUnprocessedHexString();
                    bacnetMessage = parseWhoIsRequest(whoIsBody);
                    break;
                case WhoHas:
                    log.trace("Is WhoHasMessage");
                    bacnetMessage = parseWhoHasRequest(service.getUnprocessedHexString());
                    break;
                case TimeSynchronization:
                    String timeSyncHexString = service.getUnprocessedHexString();
                    bacnetMessage = parseTimeSynchronizationRequest(timeSyncHexString);
                    break;
                case IAm:
                    String iamHexString = service.getUnprocessedHexString();
                    bacnetMessage = parseIamRequest(iamHexString);
                    break;
                case IHave:
                    String ihaveHexString = service.getUnprocessedHexString();
                    bacnetMessage = parseIHaveRequest(ihaveHexString);
                    break;
                case UnconfirmedCovNotification:
                    String changeOfValueHexString = service.getUnprocessedHexString();
                    bacnetMessage = parseChangeOfValueNotification(service, changeOfValueHexString);
                    break;
                default:
                    log.trace("I do not know how to parse this service: {}", service);
            }
        }
        return bacnetMessage;
    }


}
