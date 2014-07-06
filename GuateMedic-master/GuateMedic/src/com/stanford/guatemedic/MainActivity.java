package com.stanford.guatemedic;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nativecss.NativeCSS;
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
	public void onBackPressed() {
		
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
			
			final GuatemedicReader gmr = new GuatemedicReader(getActivity().getApplication());
			
			TextView notes = (TextView)rootView.findViewById(R.id.main_page_notes);
						
			Button view_button = (Button)rootView.findViewById(R.id.main_view_button);
			view_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (gmr.hasDownloadedData()) {
						Intent intent = new Intent(getActivity().getApplication(), ViewVillageListActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "NO HAY REGISTROS", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			final Button upload_download_button = (Button)rootView.findViewById(R.id.main_upload_download_button);
			if (gmr.hasDownloadedData()) {
				if (gmr.isUploadNeeded()) {
					upload_download_button.setText("CARGAR");
					notes.setText("Registros han sido creado. Por favor carga cuando hay un conexión al Internet.");
				} else {
					upload_download_button.setText("DESCARGAR");
					notes.setText("Hay registros descargados. Puedes descargar mas cuando hay un conexión al Internet.");
				}
			} else {
				upload_download_button.setText("DESCARGAR");
				notes.setText("No hay registros descargados. Puedes descargar cuando hay un conexión al Internet.");
			}
			
			upload_download_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Utilities.hasInternetConnection(getActivity().getApplication())) {
						if (upload_download_button.getText().equals("DESCARGAR")) {
							Intent intent = new Intent(getActivity().getApplication(), DownloadLoginActivity.class);
							startActivity(intent);
						} else {
							Intent intent = new Intent(getActivity().getApplication(), UploadLoginActivity.class);
							startActivity(intent);
						}
					} else {
						Toast.makeText(getActivity(), "NO CONEXIÓN AL INTERNET", Toast.LENGTH_LONG).show();
					}
				}
			});
			
			return rootView;
		}
	}

}
