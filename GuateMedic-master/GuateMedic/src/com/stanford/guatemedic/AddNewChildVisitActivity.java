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

public class AddNewChildVisitActivity extends ActionBarActivity {

	DetailedChildVisit dcv;
	private AddNewChildVisitFragment1 frag1_childvisit;
	private AddNewChildVisitFragment2 frag2_childvisit;
	private AddNewChildVisitFragment3 frag3_childvisit;
	private AddNewChildVisitFragment4 frag4_childvisit;
	private AddNewChildVisitFragment5 frag5_childvisit;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_child_visit);

		Spinner spinner = (Spinner) findViewById(R.id.child_visit_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.child_visit_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		final String child_id = getIntent().getStringExtra("child_id");
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
				if (item.equals("Category 1")) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, frag1_childvisit).commit();
				} else if (item.equals("Category 2")) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, frag2_childvisit).commit();
				} else if (item.equals("Category 3")) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, frag3_childvisit).commit();
				} else if (item.equals("Category 4")) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, frag4_childvisit).commit();
				} else if (item.equals("Category 5")) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, frag5_childvisit).commit();
				} else {
					Toast.makeText(getApplication(), "Error", Toast.LENGTH_LONG)
							.show();
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
			DetailedRecordsStore.get(getApplication()).addNewChildVisit(
					dcv.toJSONObject());
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
			if (dcv.getWeight() != 0)
				weight_field.setText("" + dcv.getWeight());
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

			EditText height_field = (EditText) rootView
					.findViewById(R.id.child_visit1_height);
			if (dcv.getHeight() != 0)
				height_field.setText("" + dcv.getHeight());
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
			
			if(createFirstTime1==1){
				rootView.findViewById(R.id.child_visit2_is_only_breastfed).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit2_is_only_breastfed_text).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit2_how_long_only_breastfed).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit2_age_when_stopped_breastfeeding).setVisibility(View.GONE);
			}
			
			
			if(createFirstTime1==0){
				rootView.findViewById(R.id.child_visit2_age_when_stopped_breastfeeding).setVisibility(View.VISIBLE);
			}
			if(createFirstTime2==1){
				rootView.findViewById(R.id.child_visit2_how_long_only_breastfed).setVisibility(View.GONE);
			}
			
			rootView.findViewById(R.id.yes_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							getView().findViewById(
									R.id.child_visit2_is_only_breastfed)
									.setVisibility(View.VISIBLE);
							getView().findViewById(
									R.id.child_visit2_is_only_breastfed_text)
									.setVisibility(View.VISIBLE);
							getView().findViewById(R.id.child_visit2_age_when_stopped_breastfeeding).setVisibility(View.GONE);
							if(createFirstTime2==0){
								rootView.findViewById(R.id.child_visit2_how_long_only_breastfed).setVisibility(View.VISIBLE);
							}
							createFirstTime1=0;
						}
					});

			rootView.findViewById(R.id.yes_only_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							getView().findViewById(
									R.id.child_visit2_how_long_only_breastfed)
									.setVisibility(View.VISIBLE);
							createFirstTime2=0;
						}
					});
			rootView.findViewById(R.id.no_only_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							getView().findViewById(
									R.id.child_visit2_how_long_only_breastfed)
									.setVisibility(View.GONE);
							createFirstTime2=1;
						}
					});
			rootView.findViewById(R.id.no_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							getView().findViewById(
									R.id.child_visit2_is_only_breastfed)
									.setVisibility(View.GONE);
							getView().findViewById(
									R.id.child_visit2_is_only_breastfed_text)
									.setVisibility(View.GONE);
							getView().findViewById(
									R.id.child_visit2_how_long_only_breastfed)
									.setVisibility(View.GONE);
							getView()
									.findViewById(
											R.id.child_visit2_age_when_stopped_breastfeeding)
									.setVisibility(View.VISIBLE);
							
							createFirstTime1=1;
						
						}
					});
			
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

	public static class AddNewChildVisitFragment5 extends Fragment {
		private static View rootView;

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

			return rootView;
		}
	}

}