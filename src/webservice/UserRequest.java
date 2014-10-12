package webservice;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class UserRequest extends RequestManager {
	public static final String TAG = "UserRequest";

	private static final String URL_USER = "users/";
	private static final String URL_AUTHENTICATION = "login";
	private static final String URL_RANKING = "ranking";
	

	public UserRequest(Context context) {
		super(context);
	}

	protected static String getUrl(String suffixUrl) {
		return getAbsoluteUrl() + suffixUrl;
	}

	// Vérification des identifiants
	public void checkAuthentication(Listener<JSONObject> successListener,
			ErrorListener errorListener, String email, String password) {
		String url = getUrl(URL_AUTHENTICATION) + "?email=" + email
				+ "&password=" + password;

		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, null, successListener, errorListener);
	
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	
	// Récupération du classement
	public void getRanking(Listener<JSONArray> successListener,
			ErrorListener errorListener) {
		
		String url = getUrl(URL_RANKING);

		JsonArrayRequest request = new JsonArrayRequest(url, successListener, errorListener);
		
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
		
	}
	
	// Récupération des informations de compte de l'utilisateur
	public void getUser(Listener<JSONObject> successListener,
			ErrorListener errorListener, int id) {
		String url = getUrl(URL_USER) + id;
		
		JsonObjectRequest request = new JsonObjectRequest(
				url, null, successListener, errorListener){
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
