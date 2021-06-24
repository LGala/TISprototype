package implementation.schemas.operations;

import implementation.schemas.states.LoginContext;
import libraries.jsetl.LVar;
import libraries.jsetl.exception.Failure;
import libraries.z.schemas.OperationSchema;

public final class WaitingAdminTokenRemoval extends OperationSchema {
	public LoginContext loginContext;

	public WaitingAdminTokenRemoval(LoginContext loginContext) throws Failure {
		this.loginContext = loginContext;

		predicates();
	}

	@Override
	protected void predicates() throws Failure {
		add(enclaveStatus().eq(new LVar("", "waitingRemoveAdminTokenFail")));
		add(adminTokenPresence().eq(new LVar("", "present")));
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }
}
