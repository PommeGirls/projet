package webservice;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

import android.content.Context;

public class ParkingRequest extends RequestManager {
	public static final String TAG = "ParkingRequest";

	private static final String URL_PARKINGS = "parkings";

	public ParkingRequest(Context context) {
		super(context);
	}
	
	protected static String getUrl(String suffixUrl) {
		return getAbsoluteUrl() + suffixUrl;
	}
	
	public void getParkingsFromAddress(Listener<JSONObject> successListener,
			ErrorListener errorListener, String address) {
		// TODO : ajout header apikey
		String url = getUrl(URL_PARKINGS) + "?from=" + address;

		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, null, successListener, errorListener);
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
}
