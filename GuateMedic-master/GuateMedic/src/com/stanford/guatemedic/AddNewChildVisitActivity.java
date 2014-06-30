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

public class AddNewChildVisitActivity extends ActionBarActivity{
	
	DetailedChildVisit dcv;

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
		dcv = new DetailedChildVisit(child_id);

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
			DetailedRecordsStore.get(getApplication()).addNewChildVisit(dcv.toJSONObject());
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
			
			final DetailedChildVisit dcv = ((AddNewChildVisitActivity)getActivity()).dcv;
			
			EditText weight_field = (EditText)rootView.findViewById(R.id.child_visit1_weight);
			if (dcv.getWeight() != 0) weight_field.setText("" + dcv.getWeight());
			weight_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setWeight(Float.parseFloat(s.toString()));
					else
						dcv.setWeight(0);
					
				}
				
			});
			
			EditText height_field = (EditText)rootView.findViewById(R.id.child_visit1_height);
			if (dcv.getHeight() != 0) height_field.setText("" + dcv.getHeight());
			height_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setHeight(Float.parseFloat(s.toString()));
					else 
						dcv.setHeight(0);
					
				}
				
			});
			
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
			
			final DetailedChildVisit dcv = ((AddNewChildVisitActivity)getActivity()).dcv;
			
			EditText is_currently_breastfed_field = (EditText)rootView.findViewById(R.id.child_visit2_is_currently_breastfed);
			if (dcv.getIs_currently_breastfed() != 0) is_currently_breastfed_field.setText("" + dcv.getIs_currently_breastfed());
			is_currently_breastfed_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setIs_currently_breastfed(Integer.parseInt(s.toString()));
					else 
						dcv.setIs_currently_breastfed(0);
					
				}
				
			});
			
			EditText is_only_breastfed_field = (EditText)rootView.findViewById(R.id.child_visit2_is_only_breastfed);
			if (dcv.getIs_only_breastfed() != 0) is_only_breastfed_field.setText("" + dcv.getIs_only_breastfed());
			is_only_breastfed_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setIs_only_breastfed(Integer.parseInt(s.toString()));
					else
						dcv.setIs_only_breastfed(0);
					
				}
				
			});
			
			EditText how_long_only_breastfed_field = (EditText)rootView.findViewById(R.id.child_visit2_how_long_only_breastfed);
			if (dcv.getHow_long_only_breastfed() != 0) how_long_only_breastfed_field.setText("" + dcv.getHow_long_only_breastfed());
			how_long_only_breastfed_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setHow_long_only_breastfed(Float.parseFloat(s.toString()));
					else
						dcv.setHow_long_only_breastfed(0);
					
				}
				
			});
			
			EditText age_when_stopped_breastfed_field = (EditText)rootView.findViewById(R.id.child_visit2_age_when_stopped_breastfeeding);
			if (dcv.getChild_age_when_stopped_breastfeeding() != 0) age_when_stopped_breastfed_field.setText("" + dcv.getChild_age_when_stopped_breastfeeding());
			age_when_stopped_breastfed_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setChild_age_when_stopped_breastfeeding(Float.parseFloat(s.toString()));
					else
						dcv.setChild_age_when_stopped_breastfeeding(0);
					
				}
				
			});
			
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
			
			final DetailedChildVisit dcv = ((AddNewChildVisitActivity)getActivity()).dcv;
			
			EditText did_receive_vaccinations_field = (EditText)rootView.findViewById(R.id.child_visit3_did_receive_vaccinations);
			if (dcv.getDid_receive_vaccinations() != 0) did_receive_vaccinations_field.setText("" + dcv.getDid_receive_vaccinations());
			did_receive_vaccinations_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setDid_receive_vaccinations(Integer.parseInt(s.toString()));
					else
						dcv.setDid_receive_vaccinations(0);
					
				}
				
			});
			
			EditText vaccination_information_field = (EditText)rootView.findViewById(R.id.child_visit3_vaccination_information);
			if (dcv.getVaccination_information() != null) vaccination_information_field.setText("" + dcv.getVaccination_information());
			vaccination_information_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setVaccination_information(s.toString());
					else
						dcv.setVaccination_information(null);
					
				}
				
			});
			
			EditText has_chronic_disease_or_disability_field = (EditText)rootView.findViewById(R.id.child_visit3_has_chronic_disease_or_disability);
			if (dcv.getHas_chronic_disease_or_disability() != 0) has_chronic_disease_or_disability_field.setText("" + dcv.getHas_chronic_disease_or_disability());
			has_chronic_disease_or_disability_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setHas_chronic_disease_or_disability(Integer.parseInt(s.toString()));
					else
						dcv.setHas_chronic_disease_or_disability(0);
					
				}
				
			});
			
			EditText chronic_disease_or_disability_informatin_field = (EditText)rootView.findViewById(R.id.child_visit3_chronic_disease_or_disability_information);
			if (dcv.getChronic_disease_or_disability_information() != null) chronic_disease_or_disability_informatin_field.setText("" + dcv.getChronic_disease_or_disability_information());
			chronic_disease_or_disability_informatin_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setChronic_disease_or_disability_information(s.toString());
					else
						dcv.setChronic_disease_or_disability_information(null);
					
				}
				
			});
			
			EditText age_last_received_deparasiting_medicine_field = (EditText)rootView.findViewById(R.id.child_visit3_age_last_received_deparasiting_medicine);
			if (dcv.getAge_last_received_deparasiting_medicine() != 0) age_last_received_deparasiting_medicine_field.setText("" + dcv.getAge_last_received_deparasiting_medicine());
			age_last_received_deparasiting_medicine_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setAge_last_received_deparasiting_medicine(Float.parseFloat(toString()));
					else
						dcv.setAge_last_received_deparasiting_medicine(0);
					
				}
				
			});

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
			
			final DetailedChildVisit dcv = ((AddNewChildVisitActivity)getActivity()).dcv;
			
			EditText num_times_incaparina_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_incaparina_past_week);
			if (dcv.getNum_times_incaparina_past_week() != 0) num_times_incaparina_past_week_field.setText("" + dcv.getNum_times_incaparina_past_week());
			num_times_incaparina_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_incaparina_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_incaparina_past_week(0);
					
				}
				
			});
			
			EditText num_times_vegetables_or_fruits_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_vegetables_or_fruits_past_week);
			if (dcv.getNum_times_vegetables_or_fruits_past_week() != 0) num_times_vegetables_or_fruits_past_week_field.setText("" + dcv.getNum_times_vegetables_or_fruits_past_week());
			num_times_vegetables_or_fruits_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_vegetables_or_fruits_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_vegetables_or_fruits_past_week(0);
					
				}
				
			});
			
			EditText num_times_herbs_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_herbs_past_week);
			if (dcv.getNum_times_herbs_past_week() != 0) num_times_herbs_past_week_field.setText("" + dcv.getNum_times_herbs_past_week());
			num_times_herbs_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_herbs_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_herbs_past_week(0);
					
				}
				
			});
			
			EditText num_times_diarrhea_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_diarrhea_past_week);
			if (dcv.getNum_times_diarrhea_past_week() != 0) num_times_diarrhea_past_week_field.setText("" + dcv.getNum_times_diarrhea_past_week());
			num_times_diarrhea_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_diarrhea_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_diarrhea_past_week(0);
					
				}
				
			});
			
			EditText num_times_vomit_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_vomit_past_week);
			if (dcv.getNum_times_vomit_past_week() != 0) num_times_vomit_past_week_field.setText("" + dcv.getNum_times_vomit_past_week());
			num_times_vomit_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_vomit_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_vomit_past_week(0);
					
				}
				
			});
			
			EditText num_times_cough_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_cough_past_week);
			if (dcv.getNum_times_cough_past_week() != 0) num_times_cough_past_week_field.setText("" + dcv.getNum_times_cough_past_week());
			num_times_cough_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_cough_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_cough_past_week(0);
					
				}
				
			});
			
			EditText num_times_fever_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_fever_past_week);
			if (dcv.getNum_times_fever_past_week() != 0) num_times_fever_past_week_field.setText("" + dcv.getNum_times_fever_past_week());
			num_times_fever_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_fever_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_fever_past_week(0);
					
				}
				
			});
			
			EditText num_times_other_illness_past_week_field = (EditText)rootView.findViewById(R.id.child_visit4_num_times_other_illness_past_week);
			if (dcv.getNum_times_other_illness_past_week() != 0) num_times_other_illness_past_week_field.setText("" + dcv.getNum_times_other_illness_past_week());
			num_times_other_illness_past_week_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setNum_times_other_illness_past_week(Integer.parseInt(s.toString()));
					else
						dcv.setNum_times_other_illness_past_week(0);
					
				}
				
			});
			
			EditText other_illness_information_field = (EditText)rootView.findViewById(R.id.child_visit4_other_illness_information);
			if (dcv.getIllness_description() != null) other_illness_information_field.setText("" + dcv.getIllness_description());
			other_illness_information_field.addTextChangedListener(new TextWatcher() {

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
						dcv.setIllness_description(s.toString());
					else
						dcv.setIllness_description(null);
					
				}
				
			});

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