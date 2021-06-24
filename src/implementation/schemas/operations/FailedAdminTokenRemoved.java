package implementation.schemas.operations;

import implementation.schemas.states.Admin;
import implementation.schemas.states.LoginContext;
import libraries.jsetl.LVar;
import libraries.jsetl.exception.Failure;
import libraries.z.schemas.OperationSchema;
import libraries.z.schemas.decorators.Xi;

public final class FailedAdminTokenRemoved extends OperationSchema {
	public LoginContext loginContext;

	public Xi<Admin> admin;

	public FailedAdminTokenRemoved(LoginContext loginContext, Admin admin) throws Failure {
		this.loginContext = loginContext;
		this.admin = new Xi<>(new Admin((String) admin.rolePresent.getValue()), admin);

		predicates();
	}

	@Override
	protected void predicates() throws Failure {
		add(enclaveStatus().eq(new LVar("", "waitingRemoveAdminTokenFail")));
		add(adminTokenPresence().eq(new LVar("", "absent")));

		add(enclaveStatus_().eq(new LVar("", "enclaveQuiescent")));
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }

	private LVar enclaveStatus_() { return (loginContext.enclaveContext.idStation.internal.enclaveStatus = new LVar("")); }

	private LVar status() { return loginContext.enclaveContext.idStation.internal.status; }
}
