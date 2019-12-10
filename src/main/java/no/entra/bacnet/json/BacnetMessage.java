package no.entra.bacnet.json;

import org.json.JSONObject;

import java.io.Serializable;

public interface BacnetMessage extends Serializable {
   String ID = "id";
   String SOURCE = "source";
    String SOURCE_DEVICE_ID = "deviceId";
   String SOURCE_OBJECT_ID = "objectId";
   String VALUE = "value";
   String UNIT = "unit";
   String NAME = "name";
   String DESCRIPTION = "description";
   String OBSERVED_AT = "observedAt";
   String PROPERTIES = "properties";
    /**
     * All Bacnet messages must be presented as Json string.
     * @return
     */
    String toJson();

    JSONObject asJsonObject();
}
