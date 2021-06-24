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
		System.out.println("Removing Admin Token...");

		add(enclaveStatus().eq(new LVar("", "waitingRemoveAdminTokenFail")));
		add(adminTokenPresence().eq(new LVar("", "present")));

		solve();

		System.out.println("I'm Waiting For You To Remove Admin Token...");
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }
}
