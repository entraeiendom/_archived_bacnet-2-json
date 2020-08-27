package no.entra.bacnet.json.objects;

public class ObjectIdMapperResult<T> {

    private final T parsedObject;
    private final int numberOfOctetsRead;

    public ObjectIdMapperResult(T parsedObject, int numberOfOctetsRead) {
        this.parsedObject = parsedObject;
        this.numberOfOctetsRead = numberOfOctetsRead;
    }

    public T getParsedObject() {
        return parsedObject;
    }

    public int getNumberOfOctetsRead() {
        return numberOfOctetsRead;
    }
}
