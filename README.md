# bacnet-2-json
![BuildStatus](https://travis-ci.com/entraeiendom/bacnet-2-json.svg?branch=master)

Parse BacnetUDP and convert to Json. 

## Json "schema"

Observation
```
{"observation": {
  "id": "uuid when known",
  "source": {
    "device-id": "dsfas",
    "object-id": "analog-input 300047"
    },
   "value": "string or number",
   "name": "eg the Norwegian \"tverrfaglig merkesystem\" aka tfm",
   "description": any string"
}
```

ConfigurationRequest
```
{
  "configurationRequest": {
    "observedAt": "2020-01-10T12:40:15.064620",
    "id": "TODO",
    "source": "1234",
    "properties": {
      "Request": "IHave",
      "NotificationClass": "0",
      "Device": "12",
      "ObjectName": "eg the Norwegian \"tverrfaglig merkesystem\" aka tfm"
    }
  },
  "sender": {
    "ipAddress", "10.10.10.10",
    "port": "47808",
    "instanceNumber", "1234",
    gateway":{
      "deviceId":12,
      "instanceNumber":2401
    },
  "service": "IHave"
}
```
{"configurationRequest":{"observedAt":"2020-01-13T14:15:54.643725","id":"TODO","source":"0961","properties":{"Request":"IHave","NotificationClass":"0","Device":"12","ObjectName":"6-NAE2/FCB.Local Application.UR nattsenk gulvvarm"}},"sender":{"gateway":{"gatewayDeviceId":12,"gatewayInstanceNumber":2401}},"service":"IHave"}
## How To

String bacnetMessage -> You need to find the HexString from an Datagram Packet. [Baelung's UDP example](https://www.baeldung.com/udp-in-java)

```
import no.entra.bacnet.json.Bacnet2Json
String json = Bacnet2Json.hexStringToJson(backentMessage);
```
