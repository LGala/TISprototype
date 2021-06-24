package implementation.schemas.operations;

import implementation.schemas.states.Admin;
import implementation.schemas.states.LoginContext;
import libraries.jsetl.LSet;
import libraries.jsetl.LVar;
import libraries.jsetl.exception.Failure;
import libraries.z.schemas.OperationSchema;
import libraries.z.schemas.decorators.Xi;

import java.util.Arrays;
import java.util.HashSet;

public final class ReadAdminToken extends OperationSchema {
	public LoginContext loginContext;

	public Xi<Admin> admin;

	public ReadAdminToken(LoginContext loginContext, Admin admin) throws Failure {
		this.loginContext = loginContext;
		this.admin = new Xi<>(new Admin((String) admin.rolePresent.getValue()), admin);

		predicates();
	}

	@Override
	protected void predicates() throws Failure {
		System.out.println("Reading Admin Token...");

		add(status().in(new LSet(new HashSet<>(Arrays.asList(new LVar("", "quiescent"), new LVar("", "waitingRemoveTokenFail"))))));
		add(enclaveStatus().eq(new LVar("", "enclaveQuiescent")));
		add(admin.immutable().rolePresent.eq(new LVar("", "nil")));
		add(adminTokenPresence().eq(new LVar("", "present")));

		add(enclaveStatus_().eq(new LVar("", "gotAdminToken")));

		solve();

		System.out.println("Admin Token Correctly Read...");
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }

	private LVar enclaveStatus_() { return (loginContext.enclaveContext.idStation.internal.enclaveStatus = new LVar("")); }

	private LVar status() { return new LVar("", loginContext.enclaveContext.idStation.internal.status); }
}
