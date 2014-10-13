package fr.pommegirls.parkingmotov1;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import fr.pommegirls.parkingmotov1.R;

import fr.pommegirls.parkingmotov1.util.ListViewAdapter;
import fr.pommegirls.parkingmotov1.webservice.UserRequest;

public class Ranking extends Fragment {

	private View v;
	private ListView lvListe;
	private UserRequest ur;
	private ListViewAdapter lviewAdapter;
	
	private boolean isLoad = false;

	private ArrayList<String> usersAL;
	private ArrayList<String> pointsAL;
	private ArrayList<String> placesAL;
	private String [] users;
	private String [] points;
	private String [] places;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		v = LayoutInflater.from(getActivity()).inflate(R.layout.ranking, null);
		getActivity().getActionBar().setTitle("Classement");
		
		ur = new UserRequest(getActivity().getApplicationContext());
		lvListe = (ListView)  v.findViewById(R.id.lvListe);

		usersAL = new ArrayList<String>();
		pointsAL = new ArrayList<String>();
		placesAL = new ArrayList<String>();
		
		if(!isLoad)
			loadRanking();
		else{
			showList();
		}
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void loadRanking() {
		Listener<JSONArray> suListener = new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray json) {
				for (int i = 0; i < json.length(); i++) {
					JSONObject c;
					try {
						c = json.getJSONObject(i);
						String name = c.getString("login");
						String points = c.getString("totaux");

						usersAL.add(name);
						pointsAL.add(points + " points");
						placesAL.add((i+1) +" - ");

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				users = new String[usersAL.size()];
				usersAL.toArray(users);
				points = new String[pointsAL.size()];
				pointsAL.toArray(points);
				places = new String[placesAL.size()];
				placesAL.toArray(places);
				
				showList();
				
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
		ur.getRanking(suListener, erListener);
		isLoad = true;
	}
	
	public void showList() {
		lviewAdapter = new ListViewAdapter(getActivity(), users, points, places);
		lvListe.setAdapter(lviewAdapter);
	}
}