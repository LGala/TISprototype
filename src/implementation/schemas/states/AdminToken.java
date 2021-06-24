package implementation.schemas.states;

import libraries.jsetl.LVar;
import libraries.z.schemas.StateSchema;

import java.util.Arrays;

public final class AdminToken extends StateSchema {
	public LVar adminTokenPresence;

	public LVar currentAdminToken;

	public AdminToken(String adminTokenPresence, String currentAdminToken) {
		assert Arrays
				.asList("present", "absent")
				.contains(adminTokenPresence);

		this.adminTokenPresence = new LVar("", adminTokenPresence);
		this.currentAdminToken = new LVar("", currentAdminToken);
	}
}