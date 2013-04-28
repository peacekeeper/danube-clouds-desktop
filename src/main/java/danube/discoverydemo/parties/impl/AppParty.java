package danube.discoverydemo.parties.impl;

import danube.discoverydemo.parties.Party;

public class AppParty extends AbstractParty implements Party {

	private AppParty() {

		super(null);
	}

	public static AppParty create() {

		return new AppParty();
	}
}
