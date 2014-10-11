package parkingmotov1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.parkingmotov1.R;

public class Favorites extends Fragment {
	
	private MyApp myApp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   	 	myApp = ((MyApp)getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	if(myApp.getIsConnected()){
    	}else{
    	}
    	
    	
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout,
                null);
        getActivity().getActionBar().setTitle("Mes favoris");
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}