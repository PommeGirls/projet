package parkingmotov1;

import com.example.parkingmotov1.R;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddParking extends Fragment {

	private TextView text;

	public AddParking() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = LayoutInflater.from(getActivity()).inflate(
				com.example.parkingmotov1.R.layout.add_parking, null);

		getActivity().getActionBar().setTitle("Ajouter un parking");

		v.findViewById(R.id.button1).setOnClickListener(
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
						System.out.println(lat + lng);
					}
				});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
