# bacnet-2-json
Parse BacnetUDP and convert to Json. 

## Json "schema"

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

## How To

String bacnetMessage -> You need to find the HexString from an Datagram Packet. [Baelung's UDP example](https://www.baeldung.com/udp-in-java)

```
import no.entra.bacnet.json.Bacnet2Json
String json = Bacnet2Json.hexStringToJson(backentMessage);
```
