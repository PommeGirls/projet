package parkingmotov1;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MyApp extends Application {

	private boolean isConnected;

	public boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(boolean co) {
		isConnected = co;
	}

	public MyApp() {
		super();
		this.setIsConnected(false);
	}

	// Check if Google Play Services is available on device to display Google Maps
	public static boolean isGooglePlayServicesAvailable(Context context) {
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		return status == ConnectionResult.SUCCESS;
	}
}
