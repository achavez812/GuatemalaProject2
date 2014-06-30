package com.stanford.guatemedic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewChildVisitActivity extends ActionBarActivity{
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_child_visit);

		Spinner spinner = (Spinner) findViewById(R.id.child_visit_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.child_visit_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		final String child_id = getIntent().getStringExtra("child_id");

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        String item =  (String)parent.getItemAtPosition(pos);
		        if (item.equals("Category 1")) {
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewChildVisitFragment1.newInstance(child_id)).commit();
		        }
		        else if (item.equals("Category 2")) {
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewChildVisitFragment2.newInstance(child_id)).commit();
		        }
		        else if (item.equals("Category 3")) {
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewChildVisitFragment3.newInstance(child_id)).commit();    	
				}
		        else if (item.equals("Category 4")) {
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewChildVisitFragment4.newInstance(child_id)).commit();
				}
		        else if (item.equals("Category 5")) {
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewChildVisitFragment5.newInstance(child_id)).commit();
				} else {
					Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show();
				}


		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_child_visit_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_addchildvisit_submit) {
			
			Toast.makeText(getApplication(), "CHECK", Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class AddNewChildVisitFragment1 extends Fragment {

		public AddNewChildVisitFragment1() {

		}

		public static AddNewChildVisitFragment1 newInstance(String child_id) {
			AddNewChildVisitFragment1 f = new AddNewChildVisitFragment1();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_child_visit1, container, false);
			final String child_id = getArguments().getString("child_id");
			
			
			return rootView;
		}
	}

	public static class AddNewChildVisitFragment2 extends Fragment {

		public AddNewChildVisitFragment2() {

		}

		public static AddNewChildVisitFragment2 newInstance(String child_id) {
			AddNewChildVisitFragment2 f = new AddNewChildVisitFragment2();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_child_visit2, container, false);
			final String child_id = getArguments().getString("child_id");
			
			return rootView;
		}
	}

	public static class AddNewChildVisitFragment3 extends Fragment {

		public AddNewChildVisitFragment3() {

		}

		public static AddNewChildVisitFragment3 newInstance(String child_id) {
			AddNewChildVisitFragment3 f = new AddNewChildVisitFragment3();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_child_visit3, container, false);
			final String child_id = getArguments().getString("child_id");

			return rootView;
		}
	}

	public static class AddNewChildVisitFragment4 extends Fragment {

		public AddNewChildVisitFragment4() {

		}

		public static AddNewChildVisitFragment4 newInstance(String child_id) {
			AddNewChildVisitFragment4 f = new AddNewChildVisitFragment4();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_child_visit4, container, false);
			final String child_id = getArguments().getString("child_id");

			return rootView;
		}
	}

	public static class AddNewChildVisitFragment5 extends Fragment {

	public AddNewChildVisitFragment5() {

	}

	public static AddNewChildVisitFragment5 newInstance(String child_id) {
		AddNewChildVisitFragment5 f = new AddNewChildVisitFragment5();
		Bundle args = new Bundle();
		args.putString("child_id", child_id);
		f.setArguments(args);
		return f;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_new_child_visit5, container, false);
		final String child_id = getArguments().getString("child_id");

		return rootView;
	}
}



}