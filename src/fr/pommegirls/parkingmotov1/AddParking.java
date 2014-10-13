package fr.pommegirls.parkingmotov1;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import fr.pommegirls.parkingmotov1.R;

import fr.pommegirls.parkingmotov1.webservice.ParkingRequest;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class AddParking extends Fragment {

	private ParkingRequest pr;
	public View v;
	public double lat;
	public double lng;
	EditText nom;
	EditText number;
	EditText nbp;
	Switch inside;
	Switch publico;
	Switch attache;
	Switch payant;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pr = new ParkingRequest(getActivity().getApplicationContext());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = LayoutInflater.from(getActivity()).inflate(
				R.layout.add_parking, null);

		getActivity().getActionBar().setTitle("Ajouter un parking");

		Button btnaddParking = (Button) v.findViewById(R.id.parkingAdd);
		nom = (EditText) v.findViewById(R.id.parkingName);
		number = (EditText) v.findViewById(R.id.parkingPhone);
		nbp = (EditText) v.findViewById(R.id.parkingPlaces);
		inside = (Switch) v.findViewById(R.id.parkingOutside);
		publico = (Switch) v.findViewById(R.id.parkingPublic);
		attache = (Switch) v.findViewById(R.id.parkingAttached);
		payant = (Switch) v.findViewById(R.id.parkingFree);

		btnaddParking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LocationManager lm = (LocationManager) getActivity()
					.getApplicationContext()
					.getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);
				
				boolean gps_enabled = false, network_enabled = false;
				try {
					gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
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
					Criteria criteria = new Criteria();
					String provider = lm.getBestProvider(criteria,
							true);
					Location location = lm
							.getLastKnownLocation(provider);
					lat = location.getLatitude();
					lng = location.getLongitude();

					boolean error = false;
					String errorMessage = "";

					String name = nom.getText().toString();

					if (name == null || name.isEmpty()) {
						error = true;
						errorMessage = "Veuillez renseigner le nom du parking";
					}

					if (!error) {
						String tel = number.getText().toString();

						String nb = nbp.getText().toString();
						int places;
						if (nb != null && !nb.isEmpty()) {
							places = Integer.parseInt(nb);
						} else {
							places = 0;
						}

						Boolean ins = inside.isChecked();
						int outside = (ins) ? 1 : 0;

						Boolean pub = publico.isChecked();
						int prive = (pub) ? 1 : 0;

						Boolean at = attache.isChecked();
						int attached = (at) ? 1 : 0;

						Boolean fr = payant.isChecked();
						int free = (fr) ? 1 : 0;

						doAddParking(lat, lng, name, tel, places, outside,
								prive, attached, free);
					} else {
						Toast.makeText(getActivity(), errorMessage,
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		return v;
	}

	private void doAddParking(double lat2, double lng2, String name,
			String tel, int places, int outside, int prive, int attached,
			int free) {
		Listener<JSONObject> suListener = new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject json) {
				try {
					boolean error = json.getBoolean("error");
					if (error) {
						String message = json.getString("message");
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_LONG).show();
					} else {
						// TODO : changer de vue !
						Toast.makeText(getActivity(), "Ajout en cours ...",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

		ErrorListener erListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), error.toString(),
						Toast.LENGTH_LONG).show();
			}
		};

		pr.addNewParking(suListener, erListener, name, lat2, lng2, places,
				outside, free, prive, attached, tel);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
