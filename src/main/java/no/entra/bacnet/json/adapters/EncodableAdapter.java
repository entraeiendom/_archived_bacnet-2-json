package no.entra.bacnet.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.serotonin.bacnet4j.type.Encodable;

import java.io.IOException;

public class EncodableAdapter extends TypeAdapter<Encodable> {
    @Override
    public void write(JsonWriter writer, Encodable encodable) throws IOException {
        writer.beginObject();
        writer.name("encodable");
        writer.value(encodable.toString());
        writer.endObject();
    }

    @Override
    public Encodable read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
