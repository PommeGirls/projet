package webservice;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;

public class ParkingRequest extends RequestManager {
	public static final String TAG = "ParkingRequest";

	private static final String URL_PARKINGS = "parkings";
	private static final String URL_PARKINGS_FAVORITES = "parkingsFavorites/";

	public ParkingRequest(Context context) {
		super(context);
	}
	
	protected static String getUrl(String suffixUrl) {
		return getAbsoluteUrl() + suffixUrl;
	}
	
	// Récupération des parkings au alentours d'une adresse
	public void getParkingsFromAddress(Listener<String> successListener,
			ErrorListener errorListener, String address) {
		
		String url = getUrl(URL_PARKINGS) + "?from=" + address;

		StringRequest request1 = new StringRequest(Request.Method.POST,
				url, successListener, errorListener);
		
		boolean ok = addRequest(request1);
		if (!ok)
			errorListener.onErrorResponse(null);
		
	}
	
	// Récupération d'un parking grâce a son id
	public void getParkingsFromId(Listener<JSONObject> successListener,
			ErrorListener errorListener, int id) {
		String url = getUrl(URL_PARKINGS) + "/" + id;
		
		JsonObjectRequest request = new JsonObjectRequest(
				url, null, successListener, errorListener);
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	
	// Récupération des parkings favoris de l'utilisateur
	public void getFavoritesParkings(Listener<JSONArray> successListener,
			ErrorListener errorListener, int id) {
		String url = getUrl(URL_PARKINGS_FAVORITES) + id;
		
		JsonArrayRequest request = new JsonArrayRequest(
				url,  
				successListener, 
				errorListener)
		{
		    @Override
		    public HashMap<String, String> getHeaders() {
		        HashMap<String, String> params = new HashMap<String, String>();
		        params.put("Authorization", "da40ba9c0d");
		        return params;
		    }
		};
		
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
}
