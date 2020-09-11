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
## How To

String bacnetMessage -> You need to find the HexString from an Datagram Packet. [Bacnet2JsonExample](src/test/java/no/entra/bacnet/Bacnet2JsonExample.java)

```
//Read hexString from UDP
String hexString = "810b00160120ffff00ff10080c000000001c"; //WhoIs message
String json = no.entra.bacnet.json.Bacnet2Json.hexStringToJson(backnetMessage);
// Result is
{
	"configurationRequest": {
		"observedAt": "2020-09-11T16:37:49.287224",
		"id": "TODO",
		"properties": {
			"DeviceInstanceRangeHighLimit": "0",
			"DeviceInstanceRangeLowLimit": "0"
		}
	},
	"sender": "unknown",
	"service": "WhoIs"
}
```
