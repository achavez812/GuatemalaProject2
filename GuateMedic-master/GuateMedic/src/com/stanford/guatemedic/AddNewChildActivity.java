package com.stanford.guatemedic;

import java.util.Calendar;

import org.json.JSONException;

import org.json.JSONObject;

import android.app.DatePickerDialog;

import android.app.Dialog;

import android.app.DialogFragment;

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBarActivity;

import android.view.LayoutInflater;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.DatePicker;

import android.widget.EditText;

import android.widget.RadioButton;

import android.widget.RadioGroup;

import android.widget.Spinner;

import android.widget.TextView;

public class AddNewChildActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_new_child);

		String family_id = getIntent().getStringExtra("family_id");

		if (savedInstanceState == null) {

			getSupportFragmentManager().beginTransaction()

			.add(R.id.container, AddNewChildFragment.newInstance(family_id))
					.commit();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// getMenuInflater().inflate(R.menu.main, menu);

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

	public static class AddNewChildFragment extends Fragment {

		public AddNewChildFragment() {

		}

		public static AddNewChildFragment newInstance(String family_id) {

			AddNewChildFragment f = new AddNewChildFragment();

			Bundle args = new Bundle();

			args.putString("family_id", family_id);

			f.setArguments(args);

			return f;

		}

		public static class DatePickerFragment extends DialogFragment implements
				DatePickerDialog.OnDateSetListener {

			String val = "00-00-00";

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {

				// Use the current date as the default date in the picker

				final Calendar c = Calendar.getInstance();

				int year = c.get(Calendar.YEAR);

				int month = c.get(Calendar.MONTH);

				int day = c.get(Calendar.DAY_OF_MONTH);

				// Create a new instance of DatePickerDialog and return it

				return new DatePickerDialog(getActivity(), this, year, month,
						day);

			}

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				month=month+1;
				val = month + "-" + day + "-" + year;
				EditText dob_field = (EditText) getActivity().findViewById(
						R.id.add_new_child_dob);
				dob_field.setText(val);

			}

		}

		public static class DatePickerFragmentYoungest extends DialogFragment
				implements DatePickerDialog.OnDateSetListener {

			String val = "00-00-00";

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month,
						day);

			}

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {

				// Do something with the date chosen by the user
				month=month+1;
				val = month + "-" + day + "-" + year;
				EditText youngest_sibling_dob_field = (EditText) getActivity()
						.findViewById(R.id.add_new_child_youngest_sibling_dob);
				youngest_sibling_dob_field.setText(val);

			}

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_add_new_child,
					container, false);

			final String family_id = getArguments().getString("family_id");

			// get basic information for fragment functionality

			TextView textview = (TextView) rootView
					.findViewById(R.id.add_new_child_textview1);
			textview.setText(family_id);
			Button button = (Button) rootView
					.findViewById(R.id.add_new_child_button);
			// click listeners for date dialogs
			EditText dob_field = (EditText) rootView
					.findViewById(R.id.add_new_child_dob);
			dob_field.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					DatePickerFragment newdobFragment = new DatePickerFragment();
					newdobFragment.show(getActivity().getFragmentManager(),
							"datePicker");

				}

			});

			EditText youngest_sibling_dob_field = (EditText) rootView
					.findViewById(R.id.add_new_child_youngest_sibling_dob);
			youngest_sibling_dob_field
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							DatePickerFragmentYoungest newdobFragmentYoung = new DatePickerFragmentYoungest();

							newdobFragmentYoung.show(getActivity()
									.getFragmentManager(), "datePicker");

						}

					});

			// Populating Spinners

			// Type of Birth

			Spinner type_of_birth_field = (Spinner) rootView
					.findViewById(R.id.add_new_child_type_of_birth);

			ArrayAdapter<String> adaptert_of_b = new ArrayAdapter<String>(
					getActivity(), R.layout.spinner_item,

					getResources().getStringArray(R.array.t_of_b_array));

			adaptert_of_b
					.setDropDownViewResource(R.layout.spinner_dropdown_item);

			type_of_birth_field.setAdapter(adaptert_of_b);

			type_of_birth_field.setSelection(adaptert_of_b.getCount()-1);

			// Prenatal Care

			Spinner prenatal_care_field = (Spinner) rootView
					.findViewById(R.id.add_new_child_prenatal_care);

			ArrayAdapter<String> adapter_prenatal = new ArrayAdapter<String>(
					getActivity(), R.layout.spinner_item,

					getResources().getStringArray(R.array.prenatal_array));

			adapter_prenatal
					.setDropDownViewResource(R.layout.spinner_dropdown_item);

			prenatal_care_field.setAdapter(adapter_prenatal);

			prenatal_care_field.setSelection(adapter_prenatal.getCount()-1);

			// In Same pregnancy

			Spinner num_children_in_same_pregrancy_field = (Spinner) rootView
					.findViewById(R.id.add_new_child_num_children_in_same_pregnancy);

			ArrayAdapter<String> adapter_preg = new ArrayAdapter<String>(
					getActivity(), R.layout.spinner_item,getResources().getStringArray(R.array.same_pregernating_array));

			adapter_preg
					.setDropDownViewResource(R.layout.spinner_dropdown_item);

			num_children_in_same_pregrancy_field.setAdapter(adapter_preg);

			num_children_in_same_pregrancy_field.setSelection(adapter_preg.getCount()-1);

			// On Click add

			button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					EditText name_field = (EditText) getActivity()
							.findViewById(R.id.add_new_child_name);

					String name = name_field.getText().toString();

					EditText dob_field = (EditText) getActivity().findViewById(
							R.id.add_new_child_dob);

					String dob = dob_field.getText().toString();

					RadioGroup radioGenderGroup = (RadioGroup) getView()
							.findViewById(R.id.add_new_child_gender);

					int selectedId = radioGenderGroup.getCheckedRadioButtonId();

					RadioButton gender_field = (RadioButton) getView()
							.findViewById(selectedId);

					// EditText gender_field =
					// (EditText)getActivity().findViewById(R.id.add_new_child_gender);

					String gender_string = gender_field.getText().toString();

					Spinner type_of_birth_field = (Spinner) getView()
							.findViewById(R.id.add_new_child_type_of_birth);

					// EditText type_of_birth_field =
					// (EditText)getActivity().findViewById(R.id.add_new_child_type_of_birth);

					String type_of_birth_string = type_of_birth_field
							.getSelectedItem().toString();

					Spinner num_children_in_same_pregrancy_field = (Spinner) getView()
							.findViewById(
									R.id.add_new_child_num_children_in_same_pregnancy);

					// EditText num_children_in_same_pregnancy_field =
					// (EditText)getActivity().findViewById(R.id.add_new_child_num_children_in_same_pregnancy);

					String num_children_in_same_pregnancy_string = num_children_in_same_pregrancy_field
							.getSelectedItem().toString();

					EditText months_gestated_field = (EditText) getActivity()
							.findViewById(R.id.add_new_child_months_gestated);

					String months_gestated_string = months_gestated_field
							.getText().toString();

					Spinner prenatal_care_field = (Spinner) getActivity()
							.findViewById(R.id.add_new_child_prenatal_care);

					// EditText prenatal_care_field =
					// (EditText)getActivity().findViewById(R.id.add_new_child_prenatal_care);

					String prenatal_care_string = prenatal_care_field
							.getSelectedItem().toString();

					EditText birth_weight_field = (EditText) getActivity()
							.findViewById(R.id.add_new_child_birth_weight);

					String birth_weight_string = birth_weight_field.getText()
							.toString();

					EditText birth_height_field = (EditText) getActivity()
							.findViewById(R.id.add_new_child_birth_height);

					String birth_height_string = birth_height_field.getText()
							.toString();

					EditText youngest_sibling_dob_field = (EditText) getActivity()
							.findViewById(
									R.id.add_new_child_youngest_sibling_dob);

					String youngest_sibling_dob = youngest_sibling_dob_field
							.getText().toString();

					try {

						JSONObject obj = new JSONObject();

						obj.put("family_id", family_id);

						obj.put("name", name);

						obj.put("dob", dob);

						int gender = 2;

						if (gender_string.trim().equalsIgnoreCase("male")) {

							gender = 0;

						} else if (gender_string.trim().equalsIgnoreCase(
								"female")) {

							gender = 1;

						}

						obj.put("gender", gender);

						int type_of_birth = 2;

						if (type_of_birth_string.trim().equalsIgnoreCase(
								"normal")) {

							type_of_birth = 0;

						} else if (type_of_birth_string.trim()
								.equalsIgnoreCase("cesarean")) {

							type_of_birth = 1;

						}

						obj.put("type_of_birth", type_of_birth);

						int num_children_in_same_pregnancy = 0;

						if (num_children_in_same_pregnancy_string
								.equalsIgnoreCase("single")) {

							num_children_in_same_pregnancy = 1;

						} else if (num_children_in_same_pregnancy_string
								.equalsIgnoreCase("twins")) {

							num_children_in_same_pregnancy = 2;

						} else if (num_children_in_same_pregnancy_string
								.equalsIgnoreCase("triplets")) {

							num_children_in_same_pregnancy = 3;

						}

						obj.put("num_children_in_same_pregnancy",
								num_children_in_same_pregnancy);

						float months_gestated = Float
								.parseFloat(months_gestated_string);

						obj.put("months_gestated", months_gestated);

						int prenatal_care = 2;

						if (prenatal_care_string.trim().equalsIgnoreCase("Yes")) {

							prenatal_care = 0;

						} else if (prenatal_care_string.trim()
								.equalsIgnoreCase("No")) {

							prenatal_care = 1;

						}

						obj.put("prenatal_care", prenatal_care);

						float birth_weight = Float
								.parseFloat(birth_weight_string);

						obj.put("birth_weight", birth_weight);

						float birth_height = Float
								.parseFloat(birth_height_string);

						obj.put("birth_height", birth_height);

						obj.put("youngest_sibling_dob", youngest_sibling_dob);

						DetailedRecordsStore
								.get(getActivity().getApplication())
								.addNewChild(obj);

					} catch (JSONException e) {

						e.printStackTrace();

					}

					// Go to activity of this family

					Intent i = new Intent(getActivity(),
							ViewChildListActivity.class);

					i.putExtra("family_id", family_id);

					startActivity(i);

				}

			});

			return rootView;

		}

	}

}