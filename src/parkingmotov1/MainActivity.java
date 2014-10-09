package parkingmotov1;

import java.util.Properties;

import com.example.parkingmotov1.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

	private FragmentTabHost mTabHost;
	public boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
 		 Properties properties = new Properties();
 		 
    	 if( properties.getProperty("PREF_USER_APIKEY", "none") != "none"){
    		 connected = true;
    	 }
    	 
         setContentView(R.layout.bottom_tabs);

          mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
          mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

          Bundle b = new Bundle();
          b.putString("key", "Rechercher");
          mTabHost.addTab(mTabHost.newTabSpec("search").setIndicator("", getResources().getDrawable(R.drawable.search))
                              ,Search.class
                              ,b);

          b = new Bundle();
          b.putString("key", "Ajouter un parking");
          mTabHost.addTab(mTabHost.newTabSpec("addParking").setIndicator("", getResources().getDrawable(R.drawable.add_parking))
                              ,AddParking.class
                              ,b);

          b = new Bundle();
          b.putString("key", "Favoris");
          mTabHost.addTab(mTabHost.newTabSpec("favorites").setIndicator("", getResources().getDrawable(R.drawable.favorites))
                              ,Favorites.class
                              ,b);

          b = new Bundle();
          b.putString("key", "Classement");
          mTabHost.addTab(mTabHost.newTabSpec("ranking").setIndicator("", getResources().getDrawable(R.drawable.ranking))
                              ,Ranking.class
                              ,b);

          b = new Bundle();
          b.putString("key", "Mon Compte");
          mTabHost.addTab(mTabHost.newTabSpec("myAccount").setIndicator("", getResources().getDrawable(R.drawable.account))
                              ,MyAccount.class
                              ,b);
         
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 getMenuInflater().inflate(R.menu.main, menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
