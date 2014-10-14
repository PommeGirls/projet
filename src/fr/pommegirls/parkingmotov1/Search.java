package fr.pommegirls.parkingmotov1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.pommegirls.parkingmotov1.webservice.ParkingRequest;

public class Search extends Fragment {
	private static View view;

	private TextView searchField;
	private GoogleMap googleMap;
	private HashMap<String, Integer> cache = new HashMap<String, Integer>();
	private ParkingRequest pr;
	private List<Marker> markers;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pr = new ParkingRequest(getActivity().getApplicationContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null)
			return null;
		
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try{
			view = LayoutInflater.from(getActivity()).inflate(R.layout.search, null);
		}catch(InflateException e){
			System.out.println(e);
		}
		
		getActivity().getActionBar().setTitle("Rechercher un parking");
		markers = new ArrayList<Marker>();
		
		searchField = (TextView) view.findViewById(R.id.searchField);

		view.findViewById(R.id.searchGo).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						String searchTerm = searchField.getText().toString();
						if (!searchTerm.equals("")) {
							doSearch(searchTerm, true);
						} else {
							Toast.makeText(
									getActivity(),
									"Veuillez remplir le champs pour effectuer une recherche",
									Toast.LENGTH_LONG).show();
						}
					}
				});
		view.findViewById(R.id.aroundMe).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LocationManager locationManager = (LocationManager) getActivity()
								.getApplicationContext()
								.getSystemService(
										getActivity().getApplicationContext().LOCATION_SERVICE);
						Criteria criteria = new Criteria();
						String provider = locationManager.getBestProvider(
								criteria, true);
						Location location = locationManager
								.getLastKnownLocation(provider);
						double lat = location.getLatitude();
						double lng = location.getLongitude();

						doSearch(lat + "," + lng, false);
					}
				});
		return view;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getBaseContext());
		
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are available
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(),
					requestCode);
			dialog.show();
		} else { 
			LocationManager lm = (LocationManager) getActivity()
					.getApplicationContext()
					.getSystemService(
							getActivity().getApplicationContext().LOCATION_SERVICE);
			boolean gps_enabled = false, network_enabled = false;
			try {
				gps_enabled = lm
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
			} catch (Exception ex) {
			}
			try {
				network_enabled = lm
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			} catch (Exception ex) {
				System.out.println(ex);
			}
			 
			if (!gps_enabled && !network_enabled) {
				Intent gpsOptionsIntent = new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(gpsOptionsIntent);
			} else {
				initMap();
			}
		}
	}
	
	//Check if Google Play Services is available on device to display Google Maps
    public static boolean isGooglePlayServicesAvailable(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void doSearch(String address, boolean isAddress) {

		Listener<String> suListener = new Listener<String>() {
			@Override
			public void onResponse(String json) {
				try {
					JSONArray obj = new JSONArray(json);
					
					// Suppression des markers existants
					if(markers.size() > 0){
						googleMap.clear();
					}
					for (int i = 0; i < obj.length(); i++) {

						JSONObject parking = (JSONObject) obj.get(i);

						double longitude = parking.getDouble("longitude");
						double latitude = parking.getDouble("latitude");
						String name = parking.getString("name");
						int id = parking.getInt("id");
						String snippet = parking.getString("destination");

						MarkerOptions m = new MarkerOptions()
								.position(new LatLng(latitude, longitude))
								.title(name).snippet(snippet);
						
						
						Marker marker = googleMap.addMarker(m);
						
						markers.add(marker);
						cache.put(name, id);
						
						if (i == 0) {
							LatLng coordinate = new LatLng(latitude, longitude);
							CameraUpdate yourLocation = CameraUpdateFactory
									.newLatLngZoom(coordinate, 12);
							googleMap.animateCamera(yourLocation);

						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

		ErrorListener erListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
				Toast.makeText(
						getActivity(),
						"L'adresse n'a pas été reconnu ou il n'existe pas de parking proche de cette adresse, veuillez réessayer",
						Toast.LENGTH_LONG).show();
			}
		};
		if (isAddress)
			address = address.replaceAll(",", " ");
		pr.getParkingsFromAddress(suListener, erListener, address);
	}

	/* -------- Fonctions pour la Map -------- */
	private void initMap() {
		if (MyApp.isGooglePlayServicesAvailable(getActivity()
				.getApplicationContext())) {
			if (googleMap == null) {
				googleMap = ((SupportMapFragment) getActivity()
						.getSupportFragmentManager().findFragmentById(
								R.id.map_fragment2)).getMap();

				// Check if map is created successfully or not
				if (googleMap != null) {
					setupMap();
				} else
					Toast.makeText(getActivity(),
							"Il est impossible de créer la google map",
							Toast.LENGTH_SHORT).show();
			}
		} else {
			view.findViewById(R.id.map_fragment2).setVisibility(View.GONE);
		}
	}

	private void setupMap() {
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setAllGesturesEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setZoomGesturesEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		// Zoom la carte vers là où on est
		LocationManager locationManager = (LocationManager) getActivity()
				.getApplicationContext().getSystemService(
						getActivity().getApplicationContext().LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			LatLng coordinate = new LatLng(lat, lng);
			CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(
					coordinate, 12);
			googleMap.animateCamera(yourLocation);
		}
		// Clic sur les info bulle -> affiche la vue d'un parking
		googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				MainActivity ma = (MainActivity) getActivity();
				
				Intent parkingDetailIntent = new Intent(getActivity(), ParkingDetailActivity.class);
				parkingDetailIntent.putExtra("idParking", cache.get(marker.getTitle()));
				startActivity(parkingDetailIntent);
			}

		});
	}
}
