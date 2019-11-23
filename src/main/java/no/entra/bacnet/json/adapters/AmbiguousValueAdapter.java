package no.entra.bacnet.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.serotonin.bacnet4j.type.AmbiguousValue;

import java.io.IOException;

public class AmbiguousValueAdapter extends TypeAdapter<AmbiguousValue> {
    @Override
    public void write(JsonWriter writer, AmbiguousValue ambiguousValue) throws IOException {
        writer.beginObject();
        writer.name("ambiguousValue");
        writer.value(ambiguousValue.toString());
        writer.endObject();
    }

    @Override
    public AmbiguousValue read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
