package libraries.z.schemas.decorators;

public final class Xi<T> {
    private T stateSchemaValue;

    public Xi(T stateSchemaValueCopy, T stateSchemaValue) {
        assert stateSchemaValueCopy != stateSchemaValue && stateSchemaValue.equals(stateSchemaValueCopy);

        this.stateSchemaValue = stateSchemaValue;
    }

    public T immutable() {
        return stateSchemaValue;
    }
}
