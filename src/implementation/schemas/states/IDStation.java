package implementation.schemas.states;

import libraries.z.schemas.StateSchema;

public final class IDStation extends StateSchema {
	public Internal internal;

	public AdminToken adminToken;

	public IDStation(Internal internal, AdminToken adminToken) {
		this.internal = internal;
		this.adminToken = adminToken;
	}
}