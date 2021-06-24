package implementation.schemas.operations;

import implementation.schemas.states.*;
import libraries.jsetl.Constraint;
import libraries.jsetl.exception.Failure;

public final class TISAdminLogon {
	String enclaveStatus = "enclaveQuiescent";
	String status = "";
	String adminTokenPresence = "present";
	String rolePresent = "nil";

	Internal internal = new Internal(enclaveStatus, status);
	AdminToken adminToken = new AdminToken(adminTokenPresence, "0001");
	IDStation idStation = new IDStation(internal, adminToken);
	EnclaveContext enclaveContext = new EnclaveContext(idStation);
	LoginContext loginContext = new LoginContext(enclaveContext);

	Admin admin = new Admin(rolePresent);

	private Constraint TISReadAdminToken() throws Failure {
		return new ReadAdminToken(loginContext, admin);
	}

	private Constraint TISValidateAdminToken() throws Failure {
		return new ValidateAdminTokenOK(loginContext).or(new ValidateAdminTokenFail(loginContext).or(new LoginAborted(loginContext)));
	}

	private Constraint TISCompleteFailedAdminLogon() throws Failure {
		return new FailedAdminTokenRemoved(loginContext, admin).or(new WaitingAdminTokenRemoval(loginContext));
	}

	public TISAdminLogon() throws Failure {
		TISReadAdminToken().or(TISValidateAdminToken().or(TISCompleteFailedAdminLogon())).solve();
	}

	public static void main(String[] args) throws Failure {
		new TISAdminLogon();
	}
}
