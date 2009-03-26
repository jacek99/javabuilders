package org.audiolord;

import java.util.prefs.Preferences;

public class UserPreferences {

	private static Preferences prefs = Preferences.userNodeForPackage(AudioLord.class);

	/**
	 * @return Singleton prefs
	 */
	public static Preferences getInstance() {
		return prefs;
	}
}
