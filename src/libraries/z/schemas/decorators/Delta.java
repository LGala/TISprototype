package libraries.z.schemas.decorators;

public final class Delta<T> {
	private T stateSchemaValue;
	
	public Delta(T stateSchemaValue) {		
		this.stateSchemaValue = stateSchemaValue;
	}
	
	public T beforeOperation() {
		return stateSchemaValue;
	}
	
	public T afterOperation() {
		return stateSchemaValue;
	}	
}
