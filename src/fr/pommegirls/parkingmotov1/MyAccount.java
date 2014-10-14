package fr.pommegirls.parkingmotov1;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import fr.pommegirls.parkingmotov1.R;

import fr.pommegirls.parkingmotov1.webservice.UserRequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccount extends Fragment {
	private static View view;

	private boolean isLoad = false;
	private UserRequest ur;
	private boolean isConnection;
	private MyApp myApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();

		if(myApp.getIsConnected() && isConnection){
			showNormalView();
			isConnection = false;
   	 	}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myApp = ((MyApp) getActivity().getApplicationContext());
		
		view = LayoutInflater.from(getActivity()).inflate(R.layout.my_account,
				null);

		getActivity().getActionBar().setTitle("Mon profil");
		
		if(!myApp.getIsConnected()){
			isConnection = true;
			Intent intent = new Intent(getActivity(), LoginActivity.class);
   	 		startActivity(intent);
   	 	}else{
   	 		showNormalView();
			isConnection = false;
   	 	}
		return view;
	}
	public void showNormalView(){
		ur = new UserRequest(getActivity().getApplicationContext());
		
		doGetUser();
		
		Button btnModif = (Button) view.findViewById(R.id.btnSave);
		Button btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
		
		btnLogOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				SharedPreferences preferences = getActivity().getSharedPreferences("USER", 0);
				preferences.edit().clear().commit();
				myApp.setIsConnected(false);
			}
		});

		
		btnModif.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String mail = ((EditText) view.findViewById(R.id.userEmail))
						.getText().toString();
				String pwd = ((EditText) view.findViewById(R.id.userPasswd))
						.getText().toString();
				String npwd = ((EditText) view.findViewById(R.id.userNewPasswd))
						.getText().toString();
				String confpwd = ((EditText) view.findViewById(R.id.userConfNewPasswd))
						.getText().toString();

				boolean error = false;
				String errorMessage = "";

				if (mail == null || mail.isEmpty() || pwd == null
						|| pwd.isEmpty() || npwd == null || npwd.isEmpty()
						|| confpwd == null || confpwd.isEmpty()) {
					error = true;
					errorMessage = "Veuillez renseigner tous les champs.";
				} else if (!mail.contains("@")) {
					error = true;
					errorMessage = "Veuillez vérifier votre adresse e-mail.";
				} else if (!npwd.equals(confpwd)) {
					error = true;
					errorMessage = "Votre nouveaux mot de passe a mal été retapé.";
				}

				if (!error) {
					doUpdateUser(mail, npwd);
				} else {
					Toast.makeText(getActivity(), errorMessage,
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	// Récupération des informations de l'utilisateur
	private void doGetUser() {
		Listener<JSONObject> suListener = new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject json) {
				doFillForm(json);
			}
		};

		ErrorListener erListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), error.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
		// Appel au ws
		ur.getUser(suListener, erListener);
		isLoad = true;
	}
	
	// Remplissage du formulaire
	public void doFillForm(JSONObject json) {

		TextView mail = (TextView) view.findViewById(R.id.userEmail);
		TextView postIt = (TextView) view.findViewById(R.id.parkingDetail);

		try {
			if (json.get("email") != null) {
				mail.setText(json.get("email").toString());
			}

			if (json.get("postIt") != null) {
				postIt.setText(json.get("postIt").toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	// Appel de mise a jour
	public void doUpdateUser(String mail, String npwd) {

		Listener<JSONObject> suListener = new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject json) {
				doFillForm(json);
			}
		};

		ErrorListener erListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), error.toString(),
						Toast.LENGTH_LONG).show();
			}
		};
		ur.updateUser(suListener, erListener, mail, npwd);
	}

}