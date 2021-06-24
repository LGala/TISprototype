package implementation.schemas.operations;

import implementation.schemas.states.LoginContext;
import libraries.jsetl.Constraint;
import libraries.jsetl.LVar;
import libraries.jsetl.exception.Failure;
import libraries.z.schemas.OperationSchema;

public final class ValidateAdminTokenOK extends OperationSchema {
	public LoginContext loginContext;

	public ValidateAdminTokenOK(LoginContext loginContext) throws Failure {
		this.loginContext = loginContext;

		predicates();
	}

	@Override
	protected void predicates() throws Failure {
		add(enclaveStatus().eq(new LVar("", "gotAdminToken")));
		add(adminTokenPresence().eq(new LVar("", "present")));
		add(AdminTokenOK(currentAdminToken()));
		add(enclaveStatus_().eq(new LVar("", "enclaveQuiescent")));
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }

	private LVar enclaveStatus_() { return (loginContext.enclaveContext.idStation.internal.enclaveStatus = new LVar("")); }

	private LVar currentAdminToken() { return loginContext.enclaveContext.idStation.adminToken.currentAdminToken; }

	private Constraint AdminTokenOK(LVar currentAdminToken) {
		var rightToken = new LVar("", "0001");

		return currentAdminToken.eq(rightToken);
	}
}
