package parkingmotov1;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MyApp extends Application {
	//Check if Google Play Services is available on device to display Google Maps
    public static boolean isGooglePlayServicesAvailable(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }
}
