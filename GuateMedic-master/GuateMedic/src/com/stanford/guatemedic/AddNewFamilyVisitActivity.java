package com.stanford.guatemedic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewFamilyVisitActivity extends ActionBarActivity {
	
	DetailedFamilyVisit dfv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_family_visit);
		
		Spinner spinner = (Spinner)findViewById(R.id.family_visit_spinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.family_visit_array, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		final String family_id = getIntent().getStringExtra("family_id");
		dfv = new DetailedFamilyVisit(family_id);
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
		        String item =  (String)parent.getItemAtPosition(pos);
		        if (item.equals("Category 1")) {
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewFamilyVisitFragment1.newInstance(family_id)).commit();
		        }
		        else if (item.equals("Category 2")) {
		        	getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewFamilyVisitFragment2.newInstance(family_id)).commit();
		        }
		        else if (item.equals("Category 3")) {
					getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, AddNewFamilyVisitFragment3.newInstance(family_id)).commit();    	
				}
		        else {
					Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG).show();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

				
			}
			
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_family_visit_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_addfamilyvisit_submit) {
			DetailedRecordsStore.get(getApplication()).addNewChildVisit(dfv.toJSONObject());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class AddNewFamilyVisitFragment1 extends Fragment {

		public AddNewFamilyVisitFragment1() {

		}

		public static AddNewFamilyVisitFragment1 newInstance(String family_id) {
			AddNewFamilyVisitFragment1 f = new AddNewFamilyVisitFragment1();
			Bundle args = new Bundle();
			args.putString("family_id", family_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_family_visit1, container, false);
			final String family_id = getArguments().getString("family_id");
			
			final DetailedFamilyVisit dfv = ((AddNewFamilyVisitActivity)getActivity()).dfv;
			
			EditText parent1_marital_status_field = (EditText)rootView.findViewById(R.id.family_visit1_parent1_marital_status);
			if (dfv.getParent1_marital_status() != 0) parent1_marital_status_field.setText("" + dfv.getParent1_marital_status());
			parent1_marital_status_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setParent1_marital_status(Integer.parseInt(s.toString()));
					else 
						dfv.setParent1_marital_status(0);
					
				}
				
			});
			
			EditText father_lives_with_field = (EditText)rootView.findViewById(R.id.family_visit1_father_lives_with);
			if (dfv.getFather_lives_with() != 0) father_lives_with_field.setText("" + dfv.getFather_lives_with());
			father_lives_with_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setFather_lives_with(Integer.parseInt(s.toString()));
					else 
						dfv.setFather_lives_with(0);
					
				}
				
			});
			
			EditText fathers_job_field = (EditText)rootView.findViewById(R.id.family_visit1_fathers_job);
			if (dfv.getFathers_job() != 0) fathers_job_field.setText("" + dfv.getFathers_job());
			fathers_job_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setFathers_job(Integer.parseInt(s.toString()));
					else 
						dfv.setFathers_job(0);
					
				}
				
			});
			
			EditText igss_field = (EditText)rootView.findViewById(R.id.family_visit1_igss);
			if (dfv.getIGSS() != 0) igss_field.setText("" + dfv.getIGSS());
			igss_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setIGSS(Integer.parseInt(s.toString()));
					else 
						dfv.setIGSS(0);
					
				}
				
			});
			
			
			
			return rootView;
		}
	}
	
	public static class AddNewFamilyVisitFragment2 extends Fragment {

		public AddNewFamilyVisitFragment2() {

		}

		public static AddNewFamilyVisitFragment2 newInstance(String family_id) {
			AddNewFamilyVisitFragment2 f = new AddNewFamilyVisitFragment2();
			Bundle args = new Bundle();
			args.putString("family_id", family_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_family_visit2, container, false);
			final String family_id = getArguments().getString("family_id");
			
			final DetailedFamilyVisit dfv = ((AddNewFamilyVisitActivity)getActivity()).dfv;
			
			EditText num_pregnancies_field = (EditText)rootView.findViewById(R.id.family_visit2_num_pregnancies);
			if (dfv.getNum_pregnancies() != 0) num_pregnancies_field.setText("" + dfv.getNum_pregnancies());
			num_pregnancies_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setNum_pregnancies(Integer.parseInt(s.toString()));
					else 
						dfv.setNum_pregnancies(0);
					
				}
				
			});
			
			EditText num_children_alive_field = (EditText)rootView.findViewById(R.id.family_visit2_num_children_alive);
			if (dfv.getNum_children_alive() != 0) num_children_alive_field.setText("" + dfv.getNum_children_alive());
			num_children_alive_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setNum_children_alive(Integer.parseInt(s.toString()));
					else 
						dfv.setNum_children_alive(0);
					
				}
				
			});
			
			EditText num_children_dead_field = (EditText)rootView.findViewById(R.id.family_visit2_num_children_dead);
			if (dfv.getNum_children_dead() != 0) num_children_dead_field.setText("" + dfv.getNum_children_dead());
			num_children_dead_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setNum_children_dead(Integer.parseInt(s.toString()));
					else 
						dfv.setNum_children_dead(0);
					
				}
				
			});
			
			EditText death_information_field = (EditText)rootView.findViewById(R.id.family_visit2_death_information);
			if (dfv.getChildren_death_information() != null) death_information_field.setText("" + dfv.getChildren_death_information());
			death_information_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setChildren_death_information(s.toString());
					else 
						dfv.setChildren_death_information(null);
					
				}
				
			});
			
			EditText num_children_under_5_field = (EditText)rootView.findViewById(R.id.family_visit2_num_children_under_5);
			if (dfv.getNum_children_under_5() != 0) num_children_under_5_field.setText("" + dfv.getNum_children_under_5());
			num_children_under_5_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setNum_children_under_5(Integer.parseInt(s.toString()));
					else 
						dfv.setNum_children_under_5(0);
					
				}
				
			});
			
			EditText num_people_in_household_field = (EditText)rootView.findViewById(R.id.family_visit2_num_people_in_household);
			if (dfv.getNum_people_in_household() != 0) num_people_in_household_field.setText("" + dfv.getNum_people_in_household());
			num_people_in_household_field.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0)
						dfv.setNum_people_in_household(Integer.parseInt(s.toString()));
					else 
						dfv.setNum_people_in_household(0);
					
				}
				
			});
			
			return rootView;
		}
	}
	
	public static class AddNewFamilyVisitFragment3 extends Fragment {

		public AddNewFamilyVisitFragment3() {

		}

		public static AddNewFamilyVisitFragment3 newInstance(String family_id) {
			AddNewFamilyVisitFragment3 f = new AddNewFamilyVisitFragment3();
			Bundle args = new Bundle();
			args.putString("family_id", family_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_family_visit3, container, false);
			final String family_id = getArguments().getString("family_id");
			
			final DetailedFamilyVisit dfv = ((AddNewFamilyVisitActivity)getActivity()).dfv;
			
			return rootView;
		}
	}
}