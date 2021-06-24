package implementation.schemas.states;

import libraries.z.schemas.StateSchema;

public final class LoginContext extends StateSchema {
	public EnclaveContext enclaveContext;

	public LoginContext(EnclaveContext enclaveContext) {
		this.enclaveContext = enclaveContext;
	}
}