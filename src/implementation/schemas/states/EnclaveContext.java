package implementation.schemas.states;

import libraries.z.schemas.StateSchema;

public final class EnclaveContext extends StateSchema {
	public IDStation idStation;

	public EnclaveContext(IDStation idStation) {
		this.idStation = idStation;
	}
}