package parkingmotov1;

import org.json.JSONException;
import org.json.JSONObject;

import util.PrefsManager;
import webservice.UserRequest;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.parkingmotov1.R;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Fragment {

	private EditText eEmail;
	private EditText ePassword;
	
	private UserRequest ur;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	ur = new UserRequest(getActivity().getApplicationContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.login, null);

		getActivity().getActionBar().setTitle("Connexion");

		ePassword = (EditText) v.findViewById(R.id.password);
		eEmail = (EditText) v.findViewById(R.id.email);

		v.findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						boolean error = false;
						String errorMessage = "";
						  
						String sEmail = eEmail.getText().toString();
						String sPassword = ePassword.getText().toString();
						
						if (sPassword.equals("") || sEmail.equals("")) {
							error = true;
							errorMessage = "Veuillez renseigner les deux champs : e-mail et mot de passe.";
						}else if(!sEmail.contains("@")){
							error = true;
							errorMessage = "Veuillez vérifier votre adresse e-mail.";
						}
						
						if(!error){
							doLogin(sEmail, sPassword);
						}else{
							Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
						}
						
					}
				});

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private void doLogin(String email, String pwd){
    	
    	// Listener du success : arrive ici si la requête a bien répondu
    	Listener<JSONObject> suListener = new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject json) {
				try {
					boolean error = json.getBoolean("error");
					if(error){
						String message = json.getString("message");
						Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
					}else{
						// TODO : changer de vue
						Toast.makeText(getActivity(), "Connexion en cours ...", Toast.LENGTH_LONG).show();
						
						savePref(json.getString("id"), json.getString("login"), json.getString("email"), json.getString("apiKey"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
    	};
    	
    	// Listener d'erreur : arrive ici s'il y a eu une erreur lors de la requête
    	ErrorListener erListener = new ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), error.toString() , Toast.LENGTH_LONG).show();
			}
    	};
    	
    	// Appel au ws
    	ur.checkAuthentication(suListener, erListener, email, pwd);
    }
    
	// Enregistrement des informations du user dans les préférences
    public void savePref(String userId, String login, String email, String apiKey){
    	Editor edit = PrefsManager.getPreferencesEditor(getActivity());
    	edit.putString("PREF_USER_ID", userId);
    	edit.putString("PREF_USER_LOGIN", login);
    	edit.putString("PREF_USER_EMAIL", email);
    	edit.putString("PREF_USER_APIKEY", apiKey);
    	edit.commit();
    }
}
