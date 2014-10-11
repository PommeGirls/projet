package webservice;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

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
	
	public void getParkingsFromAddress(Listener<String> successListener,
			ErrorListener errorListener, String address) {
		
		String url = getUrl(URL_PARKINGS) + "?from=" + "toto";

		StringRequest request1 = new StringRequest(Request.Method.POST,
				url, successListener, errorListener);
		
		boolean ok = addRequest(request1);
		if (!ok)
			errorListener.onErrorResponse(null);
		
	}
	
	public void getParkingsFromId(Listener<JSONObject> successListener,
			ErrorListener errorListener, int id) {
		String url = getUrl(URL_PARKINGS) + "/" + id;
		
		JsonObjectRequest request = new JsonObjectRequest(
				url, null, successListener, errorListener);
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
}
