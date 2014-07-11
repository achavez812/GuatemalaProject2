package com.stanford.guatemedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NFCActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		getActionBar().setHomeButtonEnabled(true);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new NFCFragment(getIntent().getStringExtra("child_id"))).commit();
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
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class NFCFragment extends Fragment {
		
		private static String child_id;

		public NFCFragment(String child_id) {
			this.child_id = child_id;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_nfc, container,false);
			
			TextView child_id_title = (TextView)rootView.findViewById(R.id.nfc_page_child_id_title);
			child_id_title.setText(child_id);
			
			Button button = (Button)rootView.findViewById(R.id.nfc_page_button);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// Do something on a button click if you want
					//
					
				}
				/*Code to enter view_patient
				 * Intent i = new Intent(getActivity(), view_patient.class);
				 * i.putExtra("child_id", child_id);
				 * startActivity(i);
				 */
				
			});
			
			
			
			return rootView;
		}
	}
}