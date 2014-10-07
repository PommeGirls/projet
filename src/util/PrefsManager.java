package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefsManager {

	//User
	public static final String PREF_USER_ID = "api_key";
	
	private static SharedPreferences prefs;
	
	public static SharedPreferences getPreferences(Context context) {
		if (prefs == null)
			prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		return prefs; 
	}
	
	public static Editor getPreferencesEditor(Context context) {		
		return getPreferences(context).edit();
	}
	
}

