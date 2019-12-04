# bacnet-2-json
Parse BacnetUDP and convert to Json. Must be GPLv3 due to bacnet4j dependency


## Json "shcema"

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

