package fr.pommegirls.parkingmotov1;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import fr.pommegirls.parkingmotov1.R;

import fr.pommegirls.parkingmotov1.util.PrefsManager;
import fr.pommegirls.parkingmotov1.webservice.UserRequest;

public class LoginActivity extends ActionBarActivity {
	private EditText eEmail;
	private EditText ePassword;
	
	private UserRequest ur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ur = new UserRequest(getApplicationContext());
		
		ePassword = (EditText) findViewById(R.id.password);
		eEmail = (EditText) findViewById(R.id.email);

		findViewById(R.id.sign_in_button).setOnClickListener(
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
							Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
						}
						
					}
				});

		
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
						Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
					}else{
						// TODO : changer de vue
						Toast.makeText(getApplicationContext(), "Connexion en cours ...", Toast.LENGTH_LONG).show();
						
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
				Toast.makeText(getApplicationContext(), error.toString() , Toast.LENGTH_LONG).show();
			}
    	};
    	
    	// Appel au ws
    	ur.checkAuthentication(suListener, erListener, email, pwd);
    }
	
	// Enregistrement des informations du user dans les préférences
    public void savePref(String userId, String login, String email, String apiKey){
    	Editor edit = PrefsManager.getPreferencesEditor(this);
    	edit.putString("PREF_USER_ID", userId);
    	edit.putString("PREF_USER_LOGIN", login);
    	edit.putString("PREF_USER_EMAIL", email);
    	edit.putString("PREF_USER_APIKEY", apiKey);
    	edit.commit();
    }
    
}
