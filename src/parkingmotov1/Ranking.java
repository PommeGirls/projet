package parkingmotov1;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.parkingmotov1.R;

import webservice.UserRequest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Ranking extends Fragment {

    private TextView text;

    public Ranking() {

    } 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate !");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

	   	
	   	View v = LayoutInflater.from(getActivity()).inflate(R.layout.ranking, null);


	    getActivity().getActionBar().setTitle("Classement");
	    return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated !");
        
        UserRequest ur = new UserRequest(getActivity().getApplicationContext());   	
        
        // Listener du success : arrive ici si la requête a bien répondu
    	Listener<JSONArray> suListener = new Listener<JSONArray>(){

			@Override
			public void onResponse(JSONArray json) {
				System.out.println(json.toString());
				/*
				try {
					boolean error = json.getBoolean("error");
					if(error){
						String message = json.getString("message");
						Toast.makeText(getActivity(), "Une erreur s'est produite lors de la récupération du classement", Toast.LENGTH_LONG).show();
					}else{
						System.out.println(json.toString());
						Toast.makeText(getActivity(), "Ok voir console", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				*/
			}
		
    	};
    	
    	// Listener d'erreur : arrive ici s'il y a eu une erreur lors de la requête
    	ErrorListener erListener = new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.toString());
				Toast.makeText(getActivity(), error.toString() , Toast.LENGTH_LONG).show();
			}
    	};
    	
    	// Appel au ws
    	ur.getRanking(suListener, erListener);
    }

}