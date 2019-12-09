package no.entra.bacnet.json;

import java.io.Serializable;

public interface BacnetMessage extends Serializable {
   String ID = "id";
   String SOURCE = "source";
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
}
