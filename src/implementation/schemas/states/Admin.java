package implementation.schemas.states;

import libraries.jsetl.LVar;
import libraries.z.schemas.StateSchema;

import java.util.Arrays;

public final class Admin extends StateSchema {
	public LVar rolePresent;

	public Admin(String rolePresent) {
		assert Arrays
				.asList("nil", "guard", "auditManager", "securityOfficer")
				.contains(rolePresent);

		this.rolePresent = new LVar("", rolePresent);
	}
}