package parkingmotov1;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingmotov1.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class Search extends Fragment {
	private static View view;
	
	private TextView searchField;
	 private GoogleMap googleMap;
    public Search() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	if(container == null)
    		return null;
    	
    	if(view != null){
    		ViewGroup parent = (ViewGroup) view.getParent();
    		if(parent != null)
    			parent.removeView(view);
    	}
    	
    	try {
    		view = LayoutInflater.from(getActivity()).inflate(R.layout.search,
   	             null);
    	}catch(InflateException e){
    		System.out.println(e);
    	}
    	/*
	   	View v = LayoutInflater.from(getActivity()).inflate(R.layout.search,
	             null);
	     */
	    getActivity().getActionBar().setTitle("Rechercher un parking");
	    
	    searchField = (TextView) view.findViewById(R.id.searchField);
	    /*
	    v.findViewById(R.id.searchGo).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// TODO : search
					}
				});
       */
	    return view;

    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	initMap();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
    private void initMap() {
        if (MyApp.isGooglePlayServicesAvailable(getActivity().getApplicationContext())) {
            if (googleMap == null) {
            	googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_fragment2)).getMap();

                //Check if map is created successfully or not
                if (googleMap != null) {
                    setupMap();
                } else
                    Toast.makeText(getActivity(), "Sorry! unable to create Google Maps", Toast.LENGTH_SHORT).show();
            }
        } else {
            getView().findViewById(R.id.map_fragment2).setVisibility(View.GONE);
        }
    }
    
    private void setupMap() {
        //Change settings Google Maps
    	googleMap.setMyLocationEnabled(true);
    	googleMap.getUiSettings().setAllGesturesEnabled(true);
    	googleMap.getUiSettings().setZoomControlsEnabled(true);
    	googleMap.getUiSettings().setZoomGesturesEnabled(true);
    	googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
}
