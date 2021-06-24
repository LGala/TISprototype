package implementation.schemas.operations;

import implementation.schemas.states.LoginContext;
import libraries.jsetl.LVar;
import libraries.jsetl.exception.Failure;
import libraries.z.schemas.OperationSchema;

public final class ValidateAdminTokenFail extends OperationSchema {
	public LoginContext loginContext;

	public ValidateAdminTokenFail(LoginContext loginContext) throws Failure {
		this.loginContext = loginContext;

		predicates();
	}

	@Override
	protected void predicates() throws Failure {
		System.out.println("Validating Admin Token...");

		add(enclaveStatus().eq(new LVar("", "gotAdminToken")));
		add(adminTokenPresence().eq(new LVar("", "present")));
		add(new NotAdminTokenOK(currentAdminToken()));
		add(enclaveStatus_().eq(new LVar("", "waitingRemoveAdminTokenFail")));

		solve();

		System.out.println("Admin Token Validation Has Failed...");
	}

	private LVar adminTokenPresence() { return loginContext.enclaveContext.idStation.adminToken.adminTokenPresence; }

	private LVar enclaveStatus() { return loginContext.enclaveContext.idStation.internal.enclaveStatus; }

	private LVar enclaveStatus_() { return (loginContext.enclaveContext.idStation.internal.enclaveStatus = new LVar("")); }

	private LVar currentAdminToken() { return loginContext.enclaveContext.idStation.adminToken.currentAdminToken; }

	private static class NotAdminTokenOK extends OperationSchema {
		private LVar currentAdminToken;

		private LVar rightCurrentAdminToken = new LVar("", "0001");

		public NotAdminTokenOK(LVar currentAdminToken) {
			this.currentAdminToken = currentAdminToken;
		}

		@Override
		protected void predicates() throws Failure {
			add(currentAdminToken.neq(rightCurrentAdminToken));
		}
	}
}
