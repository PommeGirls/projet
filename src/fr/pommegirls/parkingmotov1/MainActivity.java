package fr.pommegirls.parkingmotov1;

import fr.pommegirls.parkingmotov1.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	public FragmentTabHost mTabHost;
	private MyApp myApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myApp = ((MyApp) getApplicationContext());

		SharedPreferences sp = getSharedPreferences("USER", 0);
		
		if (sp.getString("APIKEY", "none") != "none") {
			myApp.setIsConnected(true);
		}

		setContentView(R.layout.bottom_tabs);

		Bundle b;
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		b = new Bundle();
		b.putString("key", "Rechercher");
		mTabHost.addTab(
				mTabHost.newTabSpec("search").setIndicator("",
						getResources().getDrawable(R.drawable.search)),
				Search.class, b);

		b = new Bundle();
		b.putString("key", "Ajouter un parking");
		mTabHost.addTab(
				mTabHost.newTabSpec("addParking").setIndicator("",
						getResources().getDrawable(R.drawable.add_parking)),
				AddParking.class, b);

		b = new Bundle();
		b.putString("key", "Favoris");
		mTabHost.addTab(
				mTabHost.newTabSpec("favorites").setIndicator("",
						getResources().getDrawable(R.drawable.favorites)),
				Favorites.class, b);

		b = new Bundle();
		b.putString("key", "Classement");
		mTabHost.addTab(
				mTabHost.newTabSpec("ranking").setIndicator("",
						getResources().getDrawable(R.drawable.ranking)),
				Ranking.class, b);

		b = new Bundle();
		b.putString("key", "Mon Compte");
		mTabHost.addTab(
				mTabHost.newTabSpec("myAccount").setIndicator("",
						getResources().getDrawable(R.drawable.account)),
				MyAccount.class, b);
		
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
