package parkingmotov1;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.ListViewAdapter;
import util.ListViewAdapterParkings;
import webservice.ParkingRequest;
import webservice.UserRequest;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.parkingmotov1.R;

public class Favorites extends Fragment {
	
	private MyApp myApp;
	private ListView lvListe;
	private ParkingRequest pr;
	private ListViewAdapterParkings lviewAdapter;
	private static final int ACTION_SEE_DETAILS = 0;
	private static final int ACTION_GO_THERE = 1;
	
	private boolean isLoad = false;

	private ArrayList<String> nameAL;
	private ArrayList<String> addressAL;
	private ArrayList<String> latlngAL;
	private ArrayList<Integer> idAL;
	
	private String [] name;
	private String [] address;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   	 	myApp = ((MyApp)getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View v = LayoutInflater.from(getActivity()).inflate(R.layout.favorites,
                null);
		getActivity().getActionBar().setTitle("Mes favoris");
		
		pr = new ParkingRequest(getActivity().getApplicationContext());
		lvListe = (ListView)  v.findViewById(R.id.listFavorites);

		nameAL = new ArrayList<String>();
		addressAL = new ArrayList<String>();
		latlngAL = new ArrayList<String>();
		idAL = new ArrayList<Integer>();
		
		if(!isLoad)
			loadFavorites();
		else{
			showList();
		}
		
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
	public void loadFavorites() {
		Listener<JSONArray> suListener = new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray json) {
				System.out.println(json.toString());
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
						latlngAL.add(longitude + "," + latitude);

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
			}
		};
		// Appel au ws
		pr.getFavoritesParkings(suListener, erListener, 1);
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
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.realtabcontent,
						new ParkingDetail(idAL.get(info.position)));
				ft.addToBackStack(null);
				ft.commit();
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