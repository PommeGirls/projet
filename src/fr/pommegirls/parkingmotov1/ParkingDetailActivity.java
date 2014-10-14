package fr.pommegirls.parkingmotov1;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import fr.pommegirls.parkingmotov1.webservice.ParkingRequest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ParkingDetailActivity extends Activity {

	private ParkingRequest pr;
	public int parkingId;

	public double lat;
	public double lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_parking_detail);
		
		Bundle b = getIntent().getExtras();
		parkingId = b.getInt("idParking");
		
		pr = new ParkingRequest(getApplicationContext());
		
		doGetParking();
		findViewById(R.id.goThere).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(
								android.content.Intent.ACTION_VIEW,
								Uri.parse("http://maps.google.com/maps?saddr=&daddr=" + lat +"," + lng));
						startActivity(intent);
					}
				});
				
	}
	private void doGetParking() {

		Listener<JSONObject> suListener = new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject json) {
				doFillForm(json);
			}
		};

		ErrorListener erListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getApplicationContext(), error.toString(),
						Toast.LENGTH_LONG).show();
			}
		};

		pr.getParkingsFromId(suListener, erListener, parkingId);
	}

	private void doFillForm(JSONObject suListener) {

		TextView name = (TextView) findViewById(R.id.parkingName);
		TextView tel = (TextView) findViewById(R.id.parkingPhone);
		TextView ad = (TextView) findViewById(R.id.parkingAddresse);
		TextView nb = (TextView) findViewById(R.id.parkingPlaces);
		TextView out = (TextView) findViewById(R.id.parkingInside);
		TextView pub = (TextView) findViewById(R.id.parkingClose);
		TextView at = (TextView) findViewById(R.id.parkingAttached);
		TextView f = (TextView) findViewById(R.id.parkingFree);

		try {
			lng = suListener.getDouble("longitude");
			lat = suListener.getDouble("latitude");
			if (suListener.get("name") != null) {
				name.setText(suListener.get("name").toString());
			}
			if (suListener.get("address") != null) {
				ad.setText(suListener.get("address").toString());
			}
			if (suListener.get("phone") != null
					&& !suListener.get("phone").equals(null)) {
				tel.setText(suListener.get("phone").toString());
			}
			if (suListener.get("nbSpaces") != null) {
				nb.setText(suListener.get("nbSpaces").toString());
			}
			if (suListener.get("outside") != null
					&& suListener.get("outside").equals(1)) {
				out.setText("Extérieur");
			} else if (suListener.get("outside") != null
					&& suListener.get("outside").equals(0)) {
				out.setText("Intérieur");
			}
			if (suListener.get("private") != null
					&& suListener.get("private").equals(1)) {
				pub.setText("Privé");
			} else if (suListener.get("private") != null
					&& suListener.get("private").equals(0)) {
				pub.setText("Public");
			}
			if (suListener.get("attache") != null
					&& suListener.get("attache").equals(1)) {
				at.setText("Avec attache");
			} else if (suListener.get("attache") != null
					&& suListener.get("attache").equals(0)) {
				at.setText("Sans attache");
			}
			if (suListener.get("free") != null
					&& suListener.get("free").equals(1)) {
				f.setText("Gratuit");
			} else if (suListener.get("free") != null
					&& suListener.get("free").equals(0)) {
				f.setText("Payant");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
