package implementation.schemas.operations;

import implementation.schemas.states.LoginContext;
import libraries.jsetl.LVar;
import libraries.jsetl.exception.Failure;
import libraries.z.schemas.OperationSchema;

public final class LoginAborted extends OperationSchema {
	public LoginContext loginContext;

	public LoginAborted(LoginContext loginContext) throws Failure {
		this.loginContext = loginContext;

		predicates();
	}

	@Override
	protected void predicates() throws Failure {
		System.out.println("Starting Login Aborting Operation...");

		add(adminTokenPresence().eq(new LVar("", "absent")));

		add(enclaveStatus().eq(new LVar("", "gotAdminToken")));

		solve();

		System.out.println("Login Correctly Aborted...");
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }
}
