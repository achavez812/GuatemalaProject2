package com.stanford.guatemedic;

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

public class UploadResultsActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_login);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new UploadResultsFragment()).commit();
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

		}
		return super.onOptionsItemSelected(item);
	}
	
	public class UploadResultsFragment extends Fragment {
		
		public UploadResultsFragment() {
					
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_upload_results, container,false);
			
			int num_new_families = UploadRecordsStore.get(getApplication()).getFamilies().size();
			int num_new_children = UploadRecordsStore.get(getApplication()).getChildren().size();
			int num_new_family_visits = UploadRecordsStore.get(getApplication()).getFamilyVisits().size();
			int num_new_child_visits = UploadRecordsStore.get(getApplication()).getChildVisits().size();
			UploadRecordsStore.get(getApplication()).clear();
			new GuatemedicFileWriter(getApplication()).eraseAll();

			
			TextView textview1 = (TextView)rootView.findViewById(R.id.upload_results_textview1);
			textview1.setText("Enviar Exitosa");
			
			TextView textview2 = (TextView)rootView.findViewById(R.id.upload_results_textview2);
			textview2.setText("Familias Nuevas: " + num_new_families);
			
			TextView textview3 = (TextView)rootView.findViewById(R.id.upload_results_textview3);
			textview3.setText("Ni" + num_new_children);
			
			TextView textview4 = (TextView)rootView.findViewById(R.id.upload_results_textview4);
			textview4.setText("New Family Visits: " + num_new_family_visits);
			
			TextView textview5 = (TextView)rootView.findViewById(R.id.upload_results_textview5);
			textview5.setText("New Child Visits: " + num_new_child_visits);
			
			TextView textview6 = (TextView)rootView.findViewById(R.id.upload_results_textview6);
			textview6.setText("All records have been cleared from the device. Download new records if necessary.");
			
			Button button = (Button)rootView.findViewById(R.id.upload_results_home_button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), MainActivity.class);
					startActivity(i);
				}
			});
			
			
			return rootView;
		}
		
	}

	

}
