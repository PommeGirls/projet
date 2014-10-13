package fr.pommegirls.parkingmotov1;


import org.json.JSONObject;


import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import fr.pommegirls.parkingmotov1.R;

import fr.pommegirls.parkingmotov1.webservice.UserRequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyAccount extends Fragment {
	private static View view;
	
	private boolean isLoad = false;
	private UserRequest ur;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.my_account, null);

		getActivity().getActionBar().setTitle("Mon profil");
		
		ur = new UserRequest(getActivity().getApplicationContext());
		
		if(!isLoad)
			loadAccount();
		else{
			show();
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void loadAccount() {
		Listener<JSONObject> suListener = new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject json) {
//				try {
					System.out.println(json.toString());
//					String name = json.getString("login");
//					String points = json.getString("totaux");

//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
				show();
				
			}
		};

		ErrorListener erListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.toString());
				Toast.makeText(getActivity(), error.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
		// Appel au ws
		ur.getUser(suListener, erListener, 1);
		isLoad = true;
	}
	
	
	public void show() {
	}
}