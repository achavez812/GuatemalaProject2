package com.stanford.guatemedic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewChildVisitActivity extends ActionBarActivity {

	DetailedChildVisit dcv;
	private AddNewChildVisitFragment1 frag1_childvisit;
	private AddNewChildVisitFragment2 frag2_childvisit;
	private AddNewChildVisitFragment3 frag3_childvisit;
	private AddNewChildVisitFragment4 frag4_childvisit;
	private AddNewChildVisitFragment5 frag5_childvisit;
	
	private static String child_id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_child_visit);
		getActionBar().setHomeButtonEnabled(true);
		Spinner spinner = (Spinner) findViewById(R.id.child_visit_spinner);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.child_visit_array, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		child_id = getIntent().getStringExtra("child_id");
		dcv = new DetailedChildVisit(child_id);
		frag1_childvisit = AddNewChildVisitFragment1.newInstance(child_id);
		frag2_childvisit = AddNewChildVisitFragment2.newInstance(child_id);
		frag3_childvisit = AddNewChildVisitFragment3.newInstance(child_id);
		frag4_childvisit = AddNewChildVisitFragment4.newInstance(child_id);
		frag5_childvisit = AddNewChildVisitFragment5.newInstance(child_id);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// An item was selected. You can retrieve the selected item
				// using
				String item = (String) parent.getItemAtPosition(pos);
				if (item.trim().equals("Categoróa 1")) {
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag1_childvisit).commit();
				} else if (item.trim().equals("Categoróa 2")) {
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag2_childvisit).commit();
				} else if (item.trim().equals("Categoróa 3")) {
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag3_childvisit).commit();
				} else if (item.trim().equals("Categoróa 4")) {
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag4_childvisit).commit();
				} else if (item.trim().equals("Categoróa 5")){
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag5_childvisit).commit();
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
	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
		// set title
		alertDialogBuilder.setTitle("Alerta");
 
			// set dialog message
		alertDialogBuilder
			.setMessage("Vas a perder este información si dejes este pagina.")
			.setCancelable(false)
			.setPositiveButton("Dejar Esta Pagina",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					
					Intent i = new Intent(getApplication(), GraphActivity.class);
					i.putExtra("child_id", child_id);
					startActivity(i);
				}
			  })
			.setNegativeButton("Quedar en Esta Pagina",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});
 
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
			alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_child_visit_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			return true;
		}
		if (id == R.id.action_addchildvisit_submit) {
			try {
				JSONObject obj = new JSONObject();
				obj.put("child_id", child_id);
				if (dcv.getWeight_in_pounds() == 0) {
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag1_childvisit).commit();
					Toast.makeText(getApplication(), "Necesitas Incluir Peso", Toast.LENGTH_LONG).show();
					return false;
				}
				if (dcv.getHeight_in_centimeters() == 0) {
					getSupportFragmentManager().beginTransaction().replace(R.id.container, frag1_childvisit).commit();
					Toast.makeText(getApplication(), "Necesitas Incluir Talla", Toast.LENGTH_LONG).show();
					return false;
				}
				obj.put("weight_in_pounds", dcv.getWeight_in_pounds());
				obj.put("height_in_centimeters", dcv.getHeight_in_centimeters());
				
				obj.put("received_all_vaccines", dcv.getReceived_all_vaccines());
				obj.put("types_of_vaccines_received", dcv.getType_of_vaccines_received());
				obj.put("has_chronic_disease_or_disability", dcv.getHas_chronic_disease_or_disability());
				obj.put("type_of_chronic_disease_or_disability", dcv.getType_of_chronic_disease_or_disability());
				obj.put("is_currently_breastfed", dcv.getIs_currently_breastfed());
				obj.put("is_only_breastfed", dcv.getIs_only_breastfed());
				obj.put("how_long_only_breastfed", dcv.getHow_long_only_breastfed());
				obj.put("child_age_when_stopped_breastfeeding", dcv.getChild_age_when_stopped_breastfeeding());
				
				obj.put("num_times_incaparina_past_week", dcv.getNum_times_incaparina_past_week());
				obj.put("num_times_vegetables_or_fruits_past_week", dcv.getNum_times_vegetables_or_fruits_past_week());
				obj.put("num_times_herbs_past_week", dcv.getNum_times_herbs_past_week());
				obj.put("num_times_diarrhea_past_week", dcv.getNum_times_herbs_past_week());
				obj.put("num_times_vomit_past_week", dcv.getNum_times_vomit_past_week());
				obj.put("num_times_cough_past_week", dcv.getNum_times_cough_past_week());
				obj.put("num_times_fever_past_week", dcv.getNum_times_fever_past_week());
				obj.put("num_times_other_illness_past_week", dcv.getNum_times_other_illness_past_week());
				obj.put("illness_description", dcv.getIllness_description());
				obj.put("age_last_received_deparasiting_medicine", dcv.getAge_last_received_deparasiting_medicine());
				DetailedRecordsStore.get(getApplication()).addNewChildVisit(obj);
				Intent i = new Intent(getApplication(), GraphActivity.class);
				i.putExtra("child_id", child_id);
				startActivity(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class AddNewChildVisitFragment1 extends Fragment {
		private static View rootView;

		public AddNewChildVisitFragment1() {

		}

		public static AddNewChildVisitFragment1 newInstance(String child_id) {
			AddNewChildVisitFragment1 f = new AddNewChildVisitFragment1();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_add_new_child_visit1,
					container, false);
			final String child_id = getArguments().getString("child_id");

			final DetailedChildVisit dcv = ((AddNewChildVisitActivity) getActivity()).dcv;

			EditText weight_field = (EditText) rootView
					.findViewById(R.id.child_visit1_weight);
			if (dcv.getWeight_in_pounds() != 0)
				weight_field.setText("" + dcv.getWeight_in_pounds());
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
						dcv.setWeight_in_pounds(Float.parseFloat(s.toString()));
					else
						dcv.setWeight_in_pounds(0);

				}

			});

			EditText height_field = (EditText) rootView
					.findViewById(R.id.child_visit1_height);
			if (dcv.getHeight_in_centimeters() != 0)
				height_field.setText("" + dcv.getHeight_in_centimeters());
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
						dcv.setHeight_in_centimeters(Float.parseFloat(s.toString()));
					else
						dcv.setHeight_in_centimeters(0);

				}

			});

			return rootView;
		}
	}

	public static class AddNewChildVisitFragment2 extends Fragment {
		private static View rootView;
		private static int createFirstTime1;
		private static int createFirstTime2;
		public AddNewChildVisitFragment2() {

		}

		public static AddNewChildVisitFragment2 newInstance(String child_id) {
			createFirstTime1=1;
			createFirstTime2=1;
			AddNewChildVisitFragment2 f = new AddNewChildVisitFragment2();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_add_new_child_visit2,
					container, false);
			final String child_id = getArguments().getString("child_id");

			final DetailedChildVisit dcv = ((AddNewChildVisitActivity) getActivity()).dcv;
			
			
			
			RadioGroup radiobreastfedGroup1 = (RadioGroup) rootView.findViewById(R.id.child_visit2_is_currently_breastfed);
			int selectedId_does_breastfeed = radiobreastfedGroup1.getCheckedRadioButtonId();
			if (selectedId_does_breastfeed != -1) {
				RadioButton is_currently_breastfed = (RadioButton) rootView.findViewById(selectedId_does_breastfeed);
				String is_currently_breastfed_string = is_currently_breastfed.getText().toString();
				
	
				if(is_currently_breastfed_string.trim().equals("Só")){
					dcv.setIs_currently_breastfed(2);
				}else if(is_currently_breastfed_string.trim().equals("No")){
					dcv.setIs_currently_breastfed(1);
				}else {
					dcv.setIs_only_breastfed(0);
				}
				
				RadioGroup radiobreastfedGroup2 = (RadioGroup) rootView.findViewById(R.id.child_visit2_is_only_breastfed);
				int selectedId_does_only_breastfeed = radiobreastfedGroup2.getCheckedRadioButtonId();
				RadioButton does_only_breastfeed = (RadioButton) rootView.findViewById(selectedId_does_only_breastfeed);
				String does_only_breastfeed_string = does_only_breastfeed.getText().toString();
				
	
				if(does_only_breastfeed_string.trim().equals("Só")){
					dcv.setIs_currently_breastfed(2);
				}else if(does_only_breastfeed_string.trim().equals("No")){
					dcv.setIs_currently_breastfed(1);
				}else {
					dcv.setIs_only_breastfed(0);
				}
				
				EditText how_long_only_breastfed_field = (EditText) rootView
						.findViewById(R.id.child_visit2_how_long_only_breastfed);
				if (dcv.getHow_long_only_breastfed() != 0)
					how_long_only_breastfed_field.setText("" + dcv.getHow_long_only_breastfed());
	
				
				EditText child_age_when_stopped_breastfeeding_field = (EditText) rootView
						.findViewById(R.id.child_visit2_how_long_only_breastfed);
				if (dcv.getChild_age_when_stopped_breastfeeding() != 0)
					child_age_when_stopped_breastfeeding_field.setText("" + dcv.getChild_age_when_stopped_breastfeeding());

			}
			
			
			
			return rootView;
		}
	}

	public static class AddNewChildVisitFragment3 extends Fragment {
		private static View rootView;
		private static int createFirstTime1;
		private static int createFirstTime2;
		public AddNewChildVisitFragment3() {
			
		}

		public static AddNewChildVisitFragment3 newInstance(String child_id) {
			AddNewChildVisitFragment3 f = new AddNewChildVisitFragment3();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			createFirstTime1=1;
			createFirstTime2=1;
			return f;
		}

		public void show_info_vaccinations() {
			getView().findViewById(R.id.child_visit3_vaccination_information)
					.setVisibility(View.VISIBLE);
		}

		public void disappear_info_vaccinations() {
			getView().findViewById(R.id.child_visit3_vaccination_information)
					.setVisibility(View.GONE);
		}

		public void show_info_disease() {
			getView()
					.findViewById(
							R.id.child_visit3_chronic_disease_or_disability_information)
					.setVisibility(View.VISIBLE);
		}

		public void disappear_info_disease() {
			getView()
					.findViewById(
							R.id.child_visit3_chronic_disease_or_disability_information)
					.setVisibility(View.GONE);
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			
			//Variable declarations
			rootView = inflater.inflate(R.layout.fragment_add_new_child_visit3,
					container, false);
			final String child_id = getArguments().getString("child_id");
			final DetailedChildVisit dcv = ((AddNewChildVisitActivity) getActivity()).dcv;
			
			
			
			
			if(createFirstTime1==1){
				rootView.findViewById(R.id.child_visit3_chronic_disease_or_disability_information).setVisibility(View.GONE);
			}
			
			//Disease click listeners
			rootView.findViewById(R.id.yes_has_chronic_disease)
			.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					show_info_disease();
					createFirstTime1=0;
				}
			});
			rootView.findViewById(R.id.no_has_chronic_disease)
			.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					disappear_info_disease();
					createFirstTime1=1;
				}
			});			
				
			//preset vaccination information if is vaccinated selected
			/*RadioGroup is_vaccinated = (RadioGroup) rootView.findViewById(R.id.child_visit3_has_chronic_disease_or_disability);
			Integer selectedId_vacc = is_vaccinated.getCheckedRadioButtonId();
			String selected_vacc;
			if(selectedId_vacc!=null){
				int selectedId_vacc_test=(Integer)selectedId_vacc;
				selected_vacc = ((RadioButton) (rootView.findViewById(selectedId_vacc_test))).getText().toString();
			}else {
				selected_vacc="none";
			}
			selected_vacc = selected_vacc + "";
			if (selected_vacc.equalsIgnoreCase("yes")) {
				rootView.findViewById(R.id.child_visit3_vaccination_information)
						.setVisibility(View.VISIBLE);
			} else {
				rootView.findViewById(R.id.child_visit3_vaccination_information)
						.setVisibility(View.GONE);
			}*/
			
			if(createFirstTime2==1){
				rootView.findViewById(R.id.child_visit3_vaccination_information).setVisibility(View.GONE);
			}
			
			
			
			//click listeners for vaccination yes or no
			rootView.findViewById(R.id.yes_vaccinations).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							show_info_vaccinations();
							createFirstTime2=0;
						}
					});
			rootView.findViewById(R.id.no_vaccinations).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							disappear_info_vaccinations();
							createFirstTime2=1;
						}
					});
			
			RadioGroup radioVaccinationGroup = (RadioGroup) rootView.findViewById(R.id.child_visit3_did_receive_vaccinations);
			int selectedId_did_receive_vaccinations = radioVaccinationGroup.getCheckedRadioButtonId();
			if (selectedId_did_receive_vaccinations != -1) {
				RadioButton did_receive_vaccinations = (RadioButton) rootView.findViewById(selectedId_did_receive_vaccinations);
				String did_receive_vaccinations_string = did_receive_vaccinations.getText().toString();
				
	
				if(did_receive_vaccinations_string.trim().equals("Só")){
					dcv.setReceived_all_vaccines(2);
				}else if(did_receive_vaccinations_string.trim().equals("No")){
					dcv.setReceived_all_vaccines(1);
				}else {
					dcv.setReceived_all_vaccines(0);
				}
				
				RadioGroup radioChronicGroup = (RadioGroup) rootView.findViewById(R.id.child_visit3_has_chronic_disease_or_disability_text);
				int selectedId_chronic_disease_or_disability = radioChronicGroup.getCheckedRadioButtonId();
				RadioButton has_chronic_disease_or_disability = (RadioButton) rootView.findViewById(selectedId_chronic_disease_or_disability);
				String has_chronic_disease_or_disability_string = has_chronic_disease_or_disability.getText().toString();
				
	
				if(has_chronic_disease_or_disability_string.trim().equals("Só")){
					dcv.setHas_chronic_disease_or_disability(2);
				}else if(has_chronic_disease_or_disability_string.trim().equals("No")){
					dcv.setHas_chronic_disease_or_disability(1);
				}else {
					dcv.setHas_chronic_disease_or_disability(0);
				}
				
				EditText vaccination_information_field = (EditText) rootView
						.findViewById(R.id.child_visit3_vaccination_information);
				if (dcv.getType_of_vaccines_received() != null)
					vaccination_information_field.setText("" + dcv.getType_of_vaccines_received());
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
							dcv.setType_of_vaccines_received(s.toString());
						else
							dcv.setType_of_vaccines_received(null);
	
					}
	
				});
				
				EditText chronic_information_field = (EditText) rootView
						.findViewById(R.id.child_visit3_chronic_disease_or_disability_information);
				if (dcv.getType_of_chronic_disease_or_disability() != null)
					chronic_information_field.setText("" + dcv.getType_of_chronic_disease_or_disability());
				chronic_information_field.addTextChangedListener(new TextWatcher() {
	
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
							dcv.setType_of_vaccines_received(s.toString());
						else
							dcv.setType_of_vaccines_received(null);
	
					}
	
				});
				
				EditText deparasiting_age_field = (EditText) rootView
						.findViewById(R.id.child_visit3_age_last_received_deparasiting_medicine);
				if (dcv.getAge_last_received_deparasiting_medicine() != 0)
					deparasiting_age_field.setText("" + dcv.getAge_last_received_deparasiting_medicine());
				deparasiting_age_field.addTextChangedListener(new TextWatcher() {
	
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
							dcv.setAge_last_received_deparasiting_medicine(Float.parseFloat(s.toString()));
						else
							dcv.setAge_last_received_deparasiting_medicine(0);
	
					}
	
				});
			}
			
			return rootView;
		}
	}

	public static class AddNewChildVisitFragment4 extends Fragment {
		private static View rootView;
		private static int createFirstTime1;
		private static int createFirstTime2;
		public AddNewChildVisitFragment4() {

		}

		public static AddNewChildVisitFragment4 newInstance(String child_id) {
			
			AddNewChildVisitFragment4 f = new AddNewChildVisitFragment4();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_add_new_child_visit4,
					container, false);
			final String child_id = getArguments().getString("child_id");

			final DetailedChildVisit dcv = ((AddNewChildVisitActivity) getActivity()).dcv;

			EditText num_times_incaparina_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_incaparina_past_week);
			if (dcv.getNum_times_incaparina_past_week() != 0)
				num_times_incaparina_past_week_field.setText(""
						+ dcv.getNum_times_incaparina_past_week());
			num_times_incaparina_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_incaparina_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_incaparina_past_week(0);

						}

					});

			EditText num_times_herbs_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_herbs_past_week);
			if (dcv.getNum_times_herbs_past_week() != 0)
				num_times_herbs_past_week_field.setText(""
						+ dcv.getNum_times_herbs_past_week());
			num_times_herbs_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_herbs_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_herbs_past_week(0);

						}

					});
			
			EditText num_times_vegetables_or_fruits_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_vegetables_or_fruits_past_week);
			if (dcv.getNum_times_vegetables_or_fruits_past_week() != 0)
				num_times_vegetables_or_fruits_week_field.setText(""
						+ dcv.getNum_times_vegetables_or_fruits_past_week());
			num_times_vegetables_or_fruits_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_vegetables_or_fruits_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_vegetables_or_fruits_past_week(0);

						}

					});

			return rootView;
		}
	}
	
	public static class AddNewChildVisitFragment5 extends Fragment {
		private static View rootView;
		private static int createFirstTime1;
		private static int createFirstTime2;
		public AddNewChildVisitFragment5() {

		}

		public static AddNewChildVisitFragment5 newInstance(String child_id) {
			
			AddNewChildVisitFragment5 f = new AddNewChildVisitFragment5();
			Bundle args = new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_add_new_child_visit5,
					container, false);
			final String child_id = getArguments().getString("child_id");

			final DetailedChildVisit dcv = ((AddNewChildVisitActivity) getActivity()).dcv;


			EditText num_times_diarrhea_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_diarrhea_past_week);
			if (dcv.getNum_times_diarrhea_past_week() != 0)
				num_times_diarrhea_past_week_field.setText(""
						+ dcv.getNum_times_diarrhea_past_week());
			num_times_diarrhea_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_diarrhea_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_diarrhea_past_week(0);

						}

					});

			EditText num_times_vomit_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_vomit_past_week);
			if (dcv.getNum_times_vomit_past_week() != 0)
				num_times_vomit_past_week_field.setText(""
						+ dcv.getNum_times_vomit_past_week());
			num_times_vomit_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_vomit_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_vomit_past_week(0);

						}

					});

			EditText num_times_cough_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_cough_past_week);
			if (dcv.getNum_times_cough_past_week() != 0)
				num_times_cough_past_week_field.setText(""
						+ dcv.getNum_times_cough_past_week());
			num_times_cough_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_cough_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_cough_past_week(0);

						}

					});

			EditText num_times_fever_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_fever_past_week);
			if (dcv.getNum_times_fever_past_week() != 0)
				num_times_fever_past_week_field.setText(""
						+ dcv.getNum_times_fever_past_week());
			num_times_fever_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_fever_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_fever_past_week(0);

						}

					});

			EditText num_times_other_illness_past_week_field = (EditText) rootView
					.findViewById(R.id.child_visit4_num_times_other_illness_past_week);
			if (dcv.getNum_times_other_illness_past_week() != 0)
				num_times_other_illness_past_week_field.setText(""
						+ dcv.getNum_times_other_illness_past_week());
			num_times_other_illness_past_week_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTextChanged(CharSequence s, int start,
								int before, int count) {
							// TODO Auto-generated method stub
							if (s.length() > 0)
								dcv.setNum_times_other_illness_past_week(Integer
										.parseInt(s.toString()));
							else
								dcv.setNum_times_other_illness_past_week(0);

						}

					});

			EditText other_illness_information_field = (EditText) rootView
					.findViewById(R.id.child_visit4_other_illness_information);
			if (dcv.getIllness_description() != null)
				other_illness_information_field.setText(""
						+ dcv.getIllness_description());
			other_illness_information_field
					.addTextChangedListener(new TextWatcher() {

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
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

}