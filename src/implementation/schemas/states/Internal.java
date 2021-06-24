package implementation.schemas.states;

import libraries.jsetl.LVar;
import libraries.z.schemas.StateSchema;

import java.util.Arrays;

public final class Internal extends StateSchema {
	public LVar enclaveStatus;

	public LVar status;

	public Internal(String enclaveStatus, String status) {
		assert Arrays
				.asList("enclaveQuiescent", "gotAdminToken", "waitingRemoveAdminTokenFail")
				.contains(enclaveStatus);

		assert Arrays
				.asList("quiescent", "waitingRemoveTokenFail")
				.contains(status);

		this.enclaveStatus = new LVar("", enclaveStatus);
		this.status = new LVar("", status);
	}
}