package fr.pommegirls.parkingmotov1;


import java.util.ArrayList;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import fr.pommegirls.parkingmotov1.R;

import fr.pommegirls.parkingmotov1.util.ListViewAdapterParkings;
import fr.pommegirls.parkingmotov1.util.PrefsManager;
import fr.pommegirls.parkingmotov1.webservice.ParkingRequest;

public class Favorites extends Fragment {
	
	private MyApp myApp;
	private ListView lvListe;
	private ParkingRequest pr;
	private ListViewAdapterParkings lviewAdapter;
	private static final int ACTION_SEE_DETAILS = 0;
	private static final int ACTION_GO_THERE = 1;
	private View v;
	private boolean isLoad;
	private boolean isConnection;
	private ArrayList<String> nameAL;
	private ArrayList<String> addressAL;
	private ArrayList<String> latlngAL;
	private ArrayList<Integer> idAL;
	
	private String [] name;
	private String [] address;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameAL = new ArrayList<String>();
		addressAL = new ArrayList<String>();
		latlngAL = new ArrayList<String>();
		idAL = new ArrayList<Integer>();
    }

    @Override
	public void onResume() {
		super.onResume();
		
		if(myApp.getIsConnected() && isConnection){
			showNormalFavoritesView();
			isConnection = false;
   	 	}
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		myApp = ((MyApp)getActivity().getApplicationContext());
		System.out.println("onCreateView");

		v = LayoutInflater.from(getActivity()).inflate(R.layout.favorites,
                null);
		if(!myApp.getIsConnected()){
			isConnection = true;
			Intent intent = new Intent(getActivity(), LoginActivity.class);
   	 		startActivity(intent);
   	 	}else{
   	 		showNormalFavoritesView();
			isConnection = false;
   	 	}
		
        return v;
    }
	
	public void showNormalFavoritesView(){
		pr = new ParkingRequest(getActivity().getApplicationContext());
		getActivity().getActionBar().setTitle("Mes favoris");
	 	lvListe = (ListView)  v.findViewById(R.id.listFavorites);
   	 	
		if(!isLoad)
			loadFavorites();
		else{
			showList();
		}
	}
	
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
	public void loadFavorites() {
		Listener<JSONArray> suListener = new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray json) {
				for (int i = 0; i < json.length(); i++) {
					JSONObject c;
					try {
						c = json.getJSONObject(i);
						String name = c.getString("name");
						String address = c.getString("address");
						double longitude = c.getDouble("longitude");
						double latitude = c.getDouble("latitude");
						int id = c.getInt("id");
						
						nameAL.add(name);
						addressAL.add(address);
						idAL.add(id);
						latlngAL.add( latitude + "," + longitude);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				name = new String[nameAL.size()];
				nameAL.toArray(name);
				address = new String[addressAL.size()];
				addressAL.toArray(address);
				
				showList();
			}
		};

		ErrorListener erListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.toString());
				Toast.makeText(getActivity(), "Vous n'avez aucun favoris", Toast.LENGTH_LONG).show();
			}
		};
		// Appel au ws
		pr.getFavoritesParkings(suListener, erListener);
		isLoad = true;
	}
	
	public void showList() {
		lviewAdapter = new ListViewAdapterParkings(getActivity(), name, address);
		lvListe.setAdapter(lviewAdapter);
		lvListe.isEnabled();
		lvListe.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view,
                            ContextMenu.ContextMenuInfo contextMenuInfo) {

                    contextMenu.add(0, ACTION_SEE_DETAILS, 0,
                   		 "Voir la fiche du parking"
                                    );
                    contextMenu.add(0, ACTION_GO_THERE, 1,
                      		 "S'y rendre"
                                       );

            }

		});
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {
			case ACTION_SEE_DETAILS:
				Intent parkingDetailIntent = new Intent(getActivity(), ParkingDetailActivity.class);
				parkingDetailIntent.putExtra("idParking", idAL.get(info.position));
				startActivity(parkingDetailIntent);
		        break;
		        
			case ACTION_GO_THERE:
				Intent intent = new Intent(
					android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?saddr=&daddr=" + latlngAL.get(info.position)));
				startActivity(intent);
		        break;
		}
		return super.onContextItemSelected(item);
	
	}
}