package implementation.schemas.operations;

import implementation.schemas.states.*;
import libraries.jsetl.BoolLVar;
import libraries.jsetl.Constraint;
import libraries.jsetl.exception.Failure;

public final class TISAdminLogon {
	private void TISReadAdminToken(LoginContext loginContext, Admin admin) throws Failure {
		if (new ReadAdminToken(loginContext, admin).check())
			System.out.println("i'm reading the token");
	}

	private void TISValidateAdminToken(LoginContext loginContext) throws Failure {
		if (new ValidateAdminTokenOK(loginContext).check())
			System.out.println("the token is correct");
		else if (new ValidateAdminTokenFail(loginContext).check())
			System.out.println("the token is not valid");
		else if (new LoginAborted(loginContext).check())
			System.out.println("login Aborted");
	}

	private void TISCompleteFailedAdminLogon(LoginContext loginContext, Admin admin) throws Failure {
		if (new FailedAdminTokenRemoved(loginContext, admin).check())
			System.out.println("the token was removed in a bad way");
		else if (new WaitingAdminTokenRemoval(loginContext).check())
			System.out.println("i'm waiting for you to remove the token");
	}

	public TISAdminLogon(LoginContext loginContext, Admin admin) throws Failure {
		TISReadAdminToken(loginContext, admin);
		TISValidateAdminToken(loginContext);
		TISCompleteFailedAdminLogon(loginContext, admin);
	}

	public static void main(String[] args) throws Failure {
		String enclaveStatus = "enclaveQuiescent";
		String status = "";
		String adminTokenPresence = "present";
		String rolePresent = "nil";

		Internal internal = new Internal(enclaveStatus, status);
		AdminToken adminToken = new AdminToken(adminTokenPresence, "0000");
		IDStation idStation = new IDStation(internal, adminToken);
		EnclaveContext enclaveContext = new EnclaveContext(idStation);
		LoginContext loginContext = new LoginContext(enclaveContext);

		Admin admin = new Admin(rolePresent);

		new TISAdminLogon(loginContext, admin);
	}
}
