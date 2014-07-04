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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewFamilyActivity extends ActionBarActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_family);
		getActionBar().setHomeButtonEnabled(true);

		String village = getIntent().getStringExtra("village");
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container,
							AddNewFamilyFragment.newInstance(village)).commit();
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
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class AddNewFamilyFragment extends Fragment {

		public AddNewFamilyFragment() {

		}

		public static class DatePickerFragmentFather extends DialogFragment implements DatePickerDialog.OnDateSetListener {

			String val = "00-00-00";
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				month = month + 1;
				val = month + "-" + day + "-" + year;
				EditText dob_field = (EditText) getActivity().findViewById(R.id.add_new_family_parent2_dob);
				dob_field.setText(val);
			}

		}
		
		public static class DatePickerFragmentMother extends DialogFragment implements DatePickerDialog.OnDateSetListener {

			String val = "00-00-00";
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				// Create a new instance of DatePickerDialog and return it
				return new DatePickerDialog(getActivity(), this, year, month, day);
			}

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				month = month + 1;
				val = month + "-" + day + "-" + year;
				EditText dob_field = (EditText) getActivity().findViewById(R.id.add_new_family_parent1_dob);
				dob_field.setText(val);
			}

		}
		public static AddNewFamilyFragment newInstance(String village) {
			AddNewFamilyFragment f = new AddNewFamilyFragment();
			Bundle args = new Bundle();
			args.putString("village", village);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_family,
					container, false);
			final String village = getArguments().getString("village");
			
			TextView textview = (TextView)rootView.findViewById(R.id.add_new_family_textview);
			textview.setText("Village: " + village);


			
			EditText mother_dob_field=(EditText)rootView.findViewById(R.id.add_new_family_parent1_dob);
					mother_dob_field.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							DatePickerFragmentMother newdobFragment = new DatePickerFragmentMother();
							newdobFragment.show(getActivity().getFragmentManager(),
									"datePicker");

						}
					});

					EditText father_dob_field=(EditText)rootView.findViewById(R.id.add_new_family_parent2_dob);
					father_dob_field.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							DatePickerFragmentFather newdobFragment = new DatePickerFragmentFather();
							newdobFragment.show(getActivity().getFragmentManager(),
									"datePicker");

						}
					});
			
					
					
			Button button = (Button) rootView
					.findViewById(R.id.add_new_family_button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					TextView parent1_name_field = (TextView) getActivity()
							.findViewById(R.id.add_new_family_parent1_name);
					String parent1_name = parent1_name_field.getText()
							.toString();

					TextView parent1_dob_field = (TextView) getActivity()
							.findViewById(R.id.add_new_family_parent1_dob);
					String parent1_dob = parent1_dob_field.getText().toString();

					TextView parent2_name_field = (TextView) getActivity()
							.findViewById(R.id.add_new_family_parent2_name);
					String parent2_name = parent2_name_field.getText()
							.toString();

					TextView parent2_dob_field = (TextView) getActivity()
							.findViewById(R.id.add_new_family_parent2_dob);
					String parent2_dob = parent2_dob_field.getText().toString();

					try {
						JSONObject obj = new JSONObject();
						obj.put("village", village);
						obj.put("parent1_name", parent1_name);
						obj.put("parent1_dob", parent1_dob);
						obj.put("parent2_name", parent2_name);
						obj.put("parent2_dob", parent2_dob);
						DetailedRecordsStore
								.get(getActivity().getApplication())
								.addNewFamily(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}

					// Go to activity of this family
					Intent i = new Intent(getActivity(),
							ViewFamilyListActivity.class);
					i.putExtra("village_name", village);
					startActivity(i);
				}
			});

			return rootView;
		}
		
	}
}
