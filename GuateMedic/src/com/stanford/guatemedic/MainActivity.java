package com.stanford.guatemedic;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;

import com.nativecss.NativeCSS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import static com.nativecss.enums.RemoteContentRefreshPeriod.*;
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
        URL css;
		try {
			css = new URL("http://10.0.2.2:8000/styles.css");
			NativeCSS.styleWithCSS("styles.css",css,Never);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_download) {
			Toast.makeText(getApplicationContext(), "Settings Click", Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,false);
			
			//TextView info = (TextView)rootView.findViewById(R.id.main_page_textview);
			
			
			Button view_button = (Button)rootView.findViewById(R.id.main_view_button);
			view_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity().getApplication(), ViewVillageListActivity.class);
					startActivity(intent);
				}
			});
			
			Button download_button = (Button)rootView.findViewById(R.id.main_download_button);
			download_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity().getApplication(), DownloadLoginActivity.class);
					startActivity(intent);
				}
			});
			
			
			return rootView;
		}
	}

}
