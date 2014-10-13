package fr.pommegirls.parkingmotov1.webservice;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class RequestManager {
	public static final String TAG = "RequestManager";
	
	private static final String BASE_URL = "http://parking2roues.alwaysdata.net/v1/";

	protected Context mContext;
	protected RequestQueue mRequestQueue;
	protected ImageLoader mImageLoader;

	public RequestManager(Context context) {
		mContext = context;
		initPoolRequest(context);
	}
	
	protected static String getAbsoluteUrl() {
		return BASE_URL;
	}
	
	// Arrête toutes les requêtes
	public void stop() {
		mRequestQueue.cancelAll(mContext);
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
	
	// Initialisation du pool de requete
	public void initPoolRequest(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.start();
	}
	
	// Envoie de la requete
	public boolean addRequest(Request request) {
		if (isConnected(mContext)) {
			Log.v(TAG, "URL = "+ request.getUrl());
		    request.setTag(mContext);
		    
			mRequestQueue.add(request);
			return true;
		} else
			return false;
	}
	
	// Vérifier que la connexion ests possible
	public static boolean isConnected(Context context) {
		if (context != null) {
	        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        // Permission obligatoire : android.permission.ACCESS_NETWORK_STATE
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	
	        if (networkInfo != null && networkInfo.isConnected()) {
	            return true;
	        } else {
	            // Affichage de l'erreur
	        	if (context != null)
	        		Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show();
	            return false;
	        }
		} else 
			return false;
    } 
}
