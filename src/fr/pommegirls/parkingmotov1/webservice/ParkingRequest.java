package fr.pommegirls.parkingmotov1.webservice;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.content.SharedPreferences;

public class ParkingRequest extends RequestManager {
	public static final String TAG = "ParkingRequest";

	private static final String URL_PARKINGS = "parkings";
	private static final String URL_PARKINGS_FAVORITES = "parkingsFavorites/";
	private static final String URL_CREATE_PARKING = "createParking";

	private SharedPreferences sp;
	
	public ParkingRequest(Context context) {
		super(context);
		sp = context.getSharedPreferences("USER", 0);
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
			ErrorListener errorListener) {
		String url = getUrl(URL_PARKINGS_FAVORITES) + sp.getString("ID", "none");
		
		JsonArrayRequest request = new JsonArrayRequest(
				url,  
				successListener, 
				errorListener)
		{
		    @Override
		    public HashMap<String, String> getHeaders() {
		        HashMap<String, String> params = new HashMap<String, String>();
		        params.put("Authorization", sp.getString("APIKEY", "none"));
		        return params;
		    }
		};
		
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	
	// Ajout d'un nouveau parking
	public void addNewParking(Listener<JSONObject> successListener,
			ErrorListener errorListener, String name, double lat, double lng,  
			int places, int outside, int free, int prive, int attached, String phone )
	{
		String url = getUrl(URL_CREATE_PARKING) + "?name=" + name + "&longitude=" + lng + "&latitude=" + lat 
				+"&nbSpaces=" + places +"&outside="+ outside +"&free="+ free + "&private="+ prive +"&phone="+ phone +"&attache="+ attached;
		
		System.out.println(url);
		
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, null, successListener, errorListener);
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
		
	}
}
