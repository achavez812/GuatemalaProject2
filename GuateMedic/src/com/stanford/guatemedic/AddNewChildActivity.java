package com.stanford.guatemedic;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

public class AddNewChildActivity extends ActionBarActivity {
	
	private static String family_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_new_child);
		getActionBar().setHomeButtonEnabled(true);
		family_id = getIntent().getStringExtra("family_id");
		if (savedInstanceState == null) {

			getSupportFragmentManager().beginTransaction()

			.add(R.id.container, AddNewChildFragment.newInstance(family_id))
					.commit();

		}

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
					
					Intent i = new Intent(getApplication(), ViewFamilyActivity.class);
					i.putExtra("family_id", family_id);
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
				String month_string = "" + month;
				if (month < 10) month_string = 0 + month_string;
				String day_string = "" + day;
				if (day < 10) day_string = 0 + day_string;
				val = year + "/" + month_string + "/" + day_string;
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			final View rootView = inflater.inflate(R.layout.fragment_add_new_child, container, false);

			final String family_id = getArguments().getString("family_id");
			
			// get basic information for fragment functionality

			TextView textview = (TextView) rootView.findViewById(R.id.add_new_child_family);
			textview.setText("Familia: " + DetailedRecordsStore.get(getActivity().getApplication()).getFamily(family_id).getParent1_name());
			
			EditText dob_field = (EditText) rootView.findViewById(R.id.add_new_child_dob);
			dob_field.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DatePickerFragment newdobFragment = new DatePickerFragment();
					newdobFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

					EditText youngest_sibling_dob_field = (EditText) rootView.findViewById(R.id.add_new_child_youngest_sibling_dob);
					youngest_sibling_dob_field.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							DatePickerFragmentYoungest newdobFragmentYoung = new DatePickerFragmentYoungest();
							newdobFragmentYoung.show(getActivity().getSupportFragmentManager(), "datePicker");
						}
					});
				}
			});

			// Populating Spinners

			// Type of Birth
			Spinner type_of_birth_field = (Spinner) rootView.findViewById(R.id.add_new_child_type_of_birth);
			ArrayAdapter<String> adaptert_of_b = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.t_of_b_array));
			adaptert_of_b.setDropDownViewResource(R.layout.spinner_dropdown_item);
			type_of_birth_field.setAdapter(adaptert_of_b);
			type_of_birth_field.setSelection(adaptert_of_b.getCount()-1);
//
//			// Prenatal Care
//			Spinner prenatal_care_field = (Spinner) rootView.findViewById(R.id.add_new_child_prenatal_care);
//			ArrayAdapter<String> adapter_prenatal = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.prenatal_array));
//			adapter_prenatal.setDropDownViewResource(R.layout.spinner_dropdown_item);
//			prenatal_care_field.setAdapter(adapter_prenatal);
//			prenatal_care_field.setSelection(adapter_prenatal.getCount()-1);
//
//			// In Same pregnancy
//			Spinner num_children_in_same_pregrancy_field = (Spinner) rootView.findViewById(R.id.add_new_child_num_children_in_same_pregnancy);
//			ArrayAdapter<String> adapter_preg = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,getResources().getStringArray(R.array.same_pregernating_array));
//			adapter_preg.setDropDownViewResource(R.layout.spinner_dropdown_item);
//			num_children_in_same_pregrancy_field.setAdapter(adapter_preg);
//			num_children_in_same_pregrancy_field.setSelection(adapter_preg.getCount()-1);

			// On Click add
			Button button = (Button) rootView.findViewById(R.id.add_new_child_button);
			button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					EditText name_field = (EditText)rootView.findViewById(R.id.add_new_child_name);
					String name = name_field.getText().toString();
					if (name.equals("")) {
						Toast.makeText(getActivity(), "Necesitas Incluir Nombre", Toast.LENGTH_LONG).show();
						return;
					}
					
					EditText dob_field = (EditText)rootView.findViewById(R.id.add_new_child_dob);
					String dob = dob_field.getText().toString();
					if (dob.equals("")) {
						Toast.makeText(getActivity(), "Necesitas Incluir Fecha de Nacimiento", Toast.LENGTH_LONG).show();
						return;
					}

					RadioGroup radioGenderGroup = (RadioGroup)rootView.findViewById(R.id.add_new_child_gender);
					int selectedId = radioGenderGroup.getCheckedRadioButtonId();
					int gender = 0;
					if (selectedId != -1) {
						RadioButton gender_field = (RadioButton)rootView.findViewById(selectedId);
						String gender_string = gender_field.getText().toString().trim();
						if (gender_string.equals("Niño")) gender = 1;
						else if (gender_string.equals("Ñina")) gender = 2;
					}

					Spinner type_of_birth_field = (Spinner)rootView.findViewById(R.id.add_new_child_type_of_birth);
					String type_of_birth_string = type_of_birth_field.getSelectedItem().toString();
					int type_of_birth = 0;
					if (type_of_birth_string.equals("Normal")) type_of_birth = 1;
					else if (type_of_birth_string.equals("Cesaria")) type_of_birth = 2;

					Spinner num_children_in_same_pregrancy_field = (Spinner)rootView.findViewById(R.id.add_new_child_num_children_in_same_pregnancy);
					String num_children_in_same_pregnancy_string = num_children_in_same_pregrancy_field.getSelectedItem().toString();
					int num_children_in_same_pregnancy = 0;
					if (num_children_in_same_pregnancy_string.equals("Solo Uno")) num_children_in_same_pregnancy = 1;
					else if (num_children_in_same_pregnancy_string.equals("Gemelos")) num_children_in_same_pregnancy = 2;
					else if (num_children_in_same_pregnancy_string.equals("Trillizos")) num_children_in_same_pregnancy = 3;

					String months_gestated_string = ((EditText) rootView.findViewById(R.id.add_new_child_months_gestated)).getText().toString();
					float months_gestated = 0;
					if (!months_gestated_string.equals("")) months_gestated = Float.parseFloat(months_gestated_string);

					Spinner prenatal_care_field = (Spinner) rootView.findViewById(R.id.add_new_child_prenatal_care);
					String received_prenatal_care_string = prenatal_care_field.getSelectedItem().toString();
					int received_prenatal_care = 0;
					if (received_prenatal_care_string.equals("Sí")) received_prenatal_care = 2;
					else if (received_prenatal_care_string.equals("No")) received_prenatal_care = 1;

					EditText birth_weight_field = (EditText) rootView.findViewById(R.id.add_new_child_birth_weight);
					String birth_weight_string = birth_weight_field.getText().toString();
					float birth_weight = 0;
					if (!birth_weight_string.equals("")) birth_weight = Float.parseFloat(birth_weight_string);

					EditText birth_height_field = (EditText)rootView.findViewById(R.id.add_new_child_birth_height);
					String birth_height_string = birth_height_field.getText().toString();
					float birth_height= 0;
					if (!birth_height_string.equals("")) birth_height = Float.parseFloat(birth_height_string);

					EditText youngest_sibling_dob_field = (EditText)rootView.findViewById(R.id.add_new_child_youngest_sibling_dob);
					String youngest_sibling_dob = youngest_sibling_dob_field.getText().toString();

					try {

						JSONObject obj = new JSONObject();

						obj.put("family_id", family_id);
						obj.put("name", name);
						obj.put("gender", gender);
						obj.put("dob", dob);
						obj.put("type_of_birth", type_of_birth);
						obj.put("num_children_in_same_pregnancy", num_children_in_same_pregnancy);
						obj.put("months_gestated", months_gestated);
						obj.put("received_prenatal_care", received_prenatal_care);
						obj.put("birth_weight", birth_weight);
						obj.put("birth_height", birth_height);
						obj.put("youngest_sibling_dob", youngest_sibling_dob);
						String child_id = DetailedRecordsStore.get(getActivity().getApplication()).addNewChild(obj);
						// Go to activity of this family
						if (child_id != null) {
							Intent i = new Intent(getActivity(), AddNewChildVisitSinglePageActivity.class);
							i.putExtra("child_id", child_id);
							startActivity(i);
						}
						
					} catch (JSONException e) {

						e.printStackTrace();

					}



				}

			});

			return rootView;

		}

	}
		

}