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
		/*
		JsonObjectRequest request = new JsonObjectRequest(
				Request.Method.POST, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError response) {
						// TODO Auto-generated method stub
						Log.d(TAG, response.toString());
					}
				}) {

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("email", "postsylvia@gmail.com");
				params.put("password", "postsylvia");

				return params;
			}

		};
		*/
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
	}
	
	public void getRanking(Listener<JSONArray> successListener,
			ErrorListener errorListener) {
		
		String url = getUrl(URL_RANKING);

		JsonArrayRequest request = new JsonArrayRequest(url, successListener, errorListener);
		
		boolean ok = addRequest(request);
		if (!ok)
			errorListener.onErrorResponse(null);
		
	}
	/*
	 * public void getUser(Listener<JSONArray> successListener, ErrorListener
	 * errorListener, int userId) { String url = getUrl(URL_TASKS_USER) +
	 * userId;
	 * 
	 * JsonArrayRequest request = new JsonArrayRequest(url, successListener,
	 * errorListener);
	 * 
	 * addRequest(request); }
	 * 
	 * public void postInscription(Listener<JSONObject> successListener,
	 * ErrorListener errorListener, User user) { String url =
	 * getUrl(URL_REGISTER);
	 * 
	 * JSONObject jsonParams = UserParser.parseUserToJson(user);
	 * 
	 * Log.v(TAG, "PARAMS = "+jsonParams); JsonObjectRequest request = new
	 * JsonObjectRequest(url, jsonParams, successListener, errorListener);
	 * 
	 * boolean ok = addRequest(request); if (!ok)
	 * errorListener.onErrorResponse(null); }
	 * 
	 * public void postUpdate(Listener<JSONObject> successListener,
	 * ErrorListener errorListener, User user) { String url =
	 * getUrl(URL_UPDATE);
	 * 
	 * // final Map<String, String> params = UserParser.parseUserToMap(user);
	 * JSONObject jsonParams = UserParser.parseUserToJson(user);
	 * 
	 * Log.v(TAG, "PARAMS = "+jsonParams); JsonObjectRequest request = new
	 * JsonObjectRequest(url, jsonParams, successListener, errorListener); // {
	 * // @Override // protected Map<String,String> getParams() { // return
	 * params; // } // };
	 * 
	 * boolean ok = addRequest(request); if (!ok)
	 * errorListener.onErrorResponse(null); }
	 */
}
