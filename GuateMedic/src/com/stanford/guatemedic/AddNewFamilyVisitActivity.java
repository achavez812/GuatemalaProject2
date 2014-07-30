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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddNewFamilyVisitActivity extends ActionBarActivity {

	DetailedFamilyVisit dfv;
	private static String family_id;
	private AddNewFamilyVisitFragment1 frag1_instance;
	private AddNewFamilyVisitFragment2 frag2_instance;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_family_visit);
		getActionBar().setHomeButtonEnabled(true);
		family_id = getIntent().getStringExtra("family_id");
		frag1_instance = AddNewFamilyVisitFragment1.newInstance(family_id);
		frag2_instance = AddNewFamilyVisitFragment2.newInstance(family_id);

		dfv = new DetailedFamilyVisit(family_id);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, frag1_instance).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container2, frag2_instance).commit();
		Button clicker = (Button) findViewById(R.id.save_family_visit);
		clicker.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				frag1_instance.convertValuesFrag1();
				frag2_instance.convertValuesFrag2();
				JSONObject obj = new JSONObject();
				try {
					obj.put("family_id", family_id);
					obj.put("does_father_live_with",
							AddNewFamilyVisitFragment1.father_lives_with_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment1.father_lives_with_put);
					obj.put("parent1_marital_status",
							AddNewFamilyVisitFragment1.parent1_marital_status_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment1.parent1_marital_status_put);
					obj.put("fathers_job",
							AddNewFamilyVisitFragment1.father_occu_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment1.father_occu_put);
					obj.put("has_igss",
							AddNewFamilyVisitFragment1.IGSS_family_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment1.IGSS_family_put);
					obj.put("num_children_alive",
							AddNewFamilyVisitFragment2.num_children_alive_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment2.num_children_alive_put);
					obj.put("num_children_dead",
							AddNewFamilyVisitFragment2.num_children_dead_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment2.num_children_dead_put);
					obj.put("num_children_under_5",
							AddNewFamilyVisitFragment2.num_children_under_5_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment2.num_children_under_5_put);
					obj.put("num_people_in_household",
							AddNewFamilyVisitFragment2.num_people_in_household_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment2.num_people_in_household_put);
					obj.put("num_pregnancies",
							AddNewFamilyVisitFragment2.num_pregnancies_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment2.num_pregnancies_put);
					obj.put("how_children_died",
							AddNewFamilyVisitFragment2.children_death_information_put);
					// Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment2.children_death_information_put);
					Log.i("WTF", "CHECK");
					DetailedRecordsStore.get(getApplication())
							.addNewFamilyVisit(obj);

					Intent i = new Intent(getApplication(),
							ViewFamilyActivity.class);
					i.putExtra("family_id", family_id);
					startActivity(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle("Alerta");

		// set dialog message
		alertDialogBuilder
				.setMessage(
						"Vas a perder este información si dejes este pagina.")
				.setCancelable(false)
				.setPositiveButton("Dejar Esta Pagina",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

								Intent i = new Intent(getApplication(),
										ViewFamilyActivity.class);
								i.putExtra("family_id", family_id);
								startActivity(i);
							}
						})
				.setNegativeButton("Quedar en Esta Pagina",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
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

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_family_visit_menu, menu);
		return true;
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { int id =
	 * item.getItemId(); if (id == android.R.id.home) { Intent i = new
	 * Intent(getApplication(), MainActivity.class); startActivity(i); return
	 * true; } if (id == R.id.action_addfamilyvisit_submit) {
	 * 
	 * frag1_instance.convertValuesFrag1(); frag2_instance.convertValuesFrag2();
	 * JSONObject obj = new JSONObject(); try { obj.put("family_id", family_id);
	 * obj.put("does_father_live_with",
	 * AddNewFamilyVisitFragment1.father_lives_with_put); //
	 * Log.d("Working","WorkingTag"
	 * +AddNewFamilyVisitFragment1.father_lives_with_put);
	 * obj.put("parent1_marital_status",
	 * AddNewFamilyVisitFragment1.parent1_marital_status_put); //
	 * Log.d("Working"
	 * ,"WorkingTag"+AddNewFamilyVisitFragment1.parent1_marital_status_put);
	 * obj.put("fathers_job", AddNewFamilyVisitFragment1.father_occu_put); //
	 * Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment1.father_occu_put);
	 * obj.put("has_igss", AddNewFamilyVisitFragment1.IGSS_family_put); //
	 * Log.d("Working","WorkingTag"+AddNewFamilyVisitFragment1.IGSS_family_put);
	 * obj.put("num_children_alive",
	 * AddNewFamilyVisitFragment2.num_children_alive_put); //
	 * Log.d("Working","WorkingTag"
	 * +AddNewFamilyVisitFragment2.num_children_alive_put);
	 * obj.put("num_children_dead",
	 * AddNewFamilyVisitFragment2.num_children_dead_put); //
	 * Log.d("Working","WorkingTag"
	 * +AddNewFamilyVisitFragment2.num_children_dead_put);
	 * obj.put("num_children_under_5",
	 * AddNewFamilyVisitFragment2.num_children_under_5_put); //
	 * Log.d("Working","WorkingTag"
	 * +AddNewFamilyVisitFragment2.num_children_under_5_put);
	 * obj.put("num_people_in_household",
	 * AddNewFamilyVisitFragment2.num_people_in_household_put); //
	 * Log.d("Working"
	 * ,"WorkingTag"+AddNewFamilyVisitFragment2.num_people_in_household_put);
	 * obj.put("num_pregnancies",
	 * AddNewFamilyVisitFragment2.num_pregnancies_put); //
	 * Log.d("Working","WorkingTag"
	 * +AddNewFamilyVisitFragment2.num_pregnancies_put);
	 * obj.put("how_children_died",
	 * AddNewFamilyVisitFragment2.children_death_information_put); //
	 * Log.d("Working"
	 * ,"WorkingTag"+AddNewFamilyVisitFragment2.children_death_information_put);
	 * Log.i("WTF", "CHECK");
	 * DetailedRecordsStore.get(getApplication()).addNewFamilyVisit( obj);
	 * 
	 * Intent i = new Intent(getApplication(), ViewFamilyActivity.class);
	 * i.putExtra("family_id", family_id); startActivity(i); } catch
	 * (JSONException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * // DetailedRecordsStore.get(getApplication()).addNewFamilyVisit(obj); //
	 * DetailedRecordsStore
	 * .get(getApplication()).addNewChildVisit(dfv.toJSONObject()); return true;
	 * } return super.onOptionsItemSelected(item); }
	 */
	public static class AddNewFamilyVisitFragment1 extends Fragment {
		private static int parent1_marital_status_put;
		private static int father_lives_with_put;
		private static int father_occu_put;
		private static int IGSS_family_put;
		private static View rootView;

		public AddNewFamilyVisitFragment1() {

		}

		public static AddNewFamilyVisitFragment1 newInstance(String family_id) {
			AddNewFamilyVisitFragment1 f = new AddNewFamilyVisitFragment1();
			Bundle args = new Bundle();
			args.putString("family_id", family_id);
			f.setArguments(args);
			return f;
		}

		public void onCreate(Bundle savedInstanceState) {
			//Utilities utilityObj = new Utilities();
			//utilityObj.loadNativeCSS();
			super.onCreate(savedInstanceState);
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//Utilities utilityObj = new Utilities();
			//utilityObj.loadNativeCSS();
			rootView = inflater.inflate(
					R.layout.fragment_add_new_family_visit1, container, false);

			final DetailedFamilyVisit dfv = ((AddNewFamilyVisitActivity) getActivity()).dfv;

			Spinner parent1_marital_status_field = (Spinner) rootView
					.findViewById(R.id.family_visit1_parent1_marital_status);
			ArrayAdapter<String> adapter_marital = new ArrayAdapter<String>(
					getActivity(), R.layout.spinner_item, getResources()
							.getStringArray(R.array.marital_status_array));
			adapter_marital
					.setDropDownViewResource(R.layout.spinner_dropdown_item);

			parent1_marital_status_field.setAdapter(adapter_marital);

			parent1_marital_status_field.setSelection(adapter_marital
					.getCount() - 1);

			// EditText father_lives_with_field =
			// (EditText)rootView.findViewById(R.id.family_visit1_father_lives_with);

			Spinner fathers_job_field = (Spinner) rootView
					.findViewById(R.id.family_visit1_fathers_job);
			ArrayAdapter<String> adapter_father_job = new ArrayAdapter<String>(
					getActivity(), R.layout.spinner_item, getResources()
							.getStringArray(R.array.fathers_job));
			adapter_father_job
					.setDropDownViewResource(R.layout.spinner_dropdown_item);
			fathers_job_field.setAdapter(adapter_father_job);
			fathers_job_field.setSelection(adapter_father_job.getCount() - 1);

			// EditText igss_field =
			// (EditText)rootView.findViewById(R.id.family_visit1_igss);

			return rootView;
		}

		public void convertValuesFrag1() {

			// Marital Status
			String parent1_marital_status_string = ((Spinner) (rootView
					.findViewById(R.id.family_visit1_parent1_marital_status)))
					.getSelectedItem().toString();

			if (parent1_marital_status_string.trim().equals("Unida")) {
				parent1_marital_status_put = 1;
			} else if (parent1_marital_status_string.trim().equals("Casada")) {
				parent1_marital_status_put = 2;
			} else if (parent1_marital_status_string.trim().equals("Viuda")) {
				parent1_marital_status_put = 3;
			} else if (parent1_marital_status_string.trim().equalsIgnoreCase(
					"Soltera")) {
				parent1_marital_status_put = 4;
			} else {
				parent1_marital_status_put = 0;
			}

			// Father Occupation
			String father_occu_string = ((Spinner) (rootView
					.findViewById(R.id.family_visit1_fathers_job)))
					.getSelectedItem().toString();

			if (father_occu_string.trim().equals("Fijo")) {
				father_occu_put = 1;
			} else if (father_occu_string.trim().equals("Comerciante")) {
				father_occu_put = 2;
			} else if (father_occu_string.trim().equals("Agricultor (Propio)")) {
				father_occu_put = 3;
			} else if (father_occu_string.trim().equals("Agricultor (Ajeno)")) {
				father_occu_put = 4;
			} else if (father_occu_string.trim().equals("Otro")) {
				father_occu_put = 5;
			} else {
				father_occu_put = 0;
			}

			// Lives With Father?
			RadioGroup radioFatherLivesWithGroup = (RadioGroup) rootView
					.findViewById(R.id.family_visit1_father_lives_with);
			int selectedId_father = radioFatherLivesWithGroup
					.getCheckedRadioButtonId();
			if (selectedId_father == -1) {
				father_lives_with_put = 0;
			} else {
				RadioButton father_lives_with = (RadioButton) rootView
						.findViewById(selectedId_father);
				String father_lives_with_string = father_lives_with.getText()
						.toString();

				if (father_lives_with_string.trim().equals("Só")) {
					father_lives_with_put = 2;
				} else if (father_lives_with_string.trim().equals("No")) {
					father_lives_with_put = 1;
				} else {
					father_lives_with_put = 0;
				}
			}

			// IGSS?
			RadioGroup radioFamilyIGSSGroup = (RadioGroup) rootView
					.findViewById(R.id.family_visit1_igss);
			int selectedId_IGSS = radioFamilyIGSSGroup
					.getCheckedRadioButtonId();
			if (selectedId_IGSS == -1) {
				IGSS_family_put = 0;
			} else {
				RadioButton IGSS_family = (RadioButton) rootView
						.findViewById(selectedId_IGSS);
				String IGSS_family_string = IGSS_family.getText().toString();

				if (IGSS_family_string.trim().equals("Só")) {
					IGSS_family_put = 2;
				} else if (IGSS_family_string.trim().equals("No")) {
					IGSS_family_put = 1;
				} else {
					IGSS_family_put = 0;
				}
			}

		}
	}

	public static class AddNewFamilyVisitFragment2 extends Fragment {
		private static int num_children_dead_put;
		private static String children_death_information_put;
		private static int num_children_under_5_put;
		private static int num_people_in_household_put;
		private static int num_pregnancies_put;
		private static int num_children_alive_put;
		private static View rootView;

		public AddNewFamilyVisitFragment2() {

		}

		public void onCreate(Bundle savedInstanceState) {
		//	Utilities utilityObj = new Utilities();
			//utilityObj.loadNativeCSS();
			super.onCreate(savedInstanceState);
		}

		public static AddNewFamilyVisitFragment2 newInstance(String family_id) {
			AddNewFamilyVisitFragment2 f = new AddNewFamilyVisitFragment2();
			Bundle args = new Bundle();
			args.putString("family_id", family_id);
			f.setArguments(args);
			return f;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		//	Utilities utilityObj = new Utilities();
			//utilityObj.loadNativeCSS();
			rootView = inflater.inflate(
					R.layout.fragment_add_new_family_visit2, container, false);
			final String family_id = getArguments().getString("family_id");

			final DetailedFamilyVisit dfv = ((AddNewFamilyVisitActivity) getActivity()).dfv;

			EditText num_pregnancies_field = (EditText) rootView
					.findViewById(R.id.family_visit2_num_pregnancies);
			if (dfv.getNum_pregnancies() != 0)
				num_pregnancies_field.setText("" + dfv.getNum_pregnancies());
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

			EditText num_children_alive_field = (EditText) rootView
					.findViewById(R.id.family_visit2_num_children_alive);
			if (dfv.getNum_children_alive() != 0)
				num_children_alive_field.setText(""
						+ dfv.getNum_children_alive());
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

			EditText num_children_dead_field = (EditText) rootView
					.findViewById(R.id.family_visit2_num_children_dead);
			if (dfv.getNum_children_dead() != 0)
				num_children_dead_field
						.setText("" + dfv.getNum_children_dead());
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

			EditText death_information_field = (EditText) rootView
					.findViewById(R.id.family_visit2_death_information);
			if (dfv.getHow_children_died() != null)
				death_information_field
						.setText("" + dfv.getHow_children_died());
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
						dfv.setHow_children_died(s.toString());
					else
						dfv.setHow_children_died(null);

				}

			});

			EditText num_children_under_5_field = (EditText) rootView
					.findViewById(R.id.family_visit2_num_children_under_5);
			if (dfv.getNum_children_under_5() != 0)
				num_children_under_5_field.setText(""
						+ dfv.getNum_children_under_5());
			num_children_under_5_field
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
								dfv.setNum_children_under_5(Integer.parseInt(s
										.toString()));
							else
								dfv.setNum_children_under_5(0);

						}

					});

			EditText num_people_in_household_field = (EditText) rootView
					.findViewById(R.id.family_visit2_num_people_in_household);
			if (dfv.getNum_people_in_household() != 0)
				num_people_in_household_field.setText(""
						+ dfv.getNum_people_in_household());
			num_people_in_household_field
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
								dfv.setNum_people_in_household(Integer
										.parseInt(s.toString()));
							else
								dfv.setNum_people_in_household(0);

						}

					});

			return rootView;
		}

		public void convertValuesFrag2() {
			// Number of Pregnancies
			try {
				String num_pregnancies_string = ((EditText) rootView
						.findViewById(R.id.family_visit2_num_pregnancies))
						.getText().toString();
				num_pregnancies_put = Integer.parseInt(num_pregnancies_string);
			} catch (NumberFormatException | NullPointerException e) {
				num_pregnancies_put = -1;
			}

			try {
				String num_children_alive_string = ((EditText) (rootView
						.findViewById(R.id.family_visit2_num_children_alive)))
						.getText().toString();
				num_children_alive_put = Integer
						.parseInt(num_children_alive_string);
			} catch (NumberFormatException | NullPointerException e) {
				num_children_alive_put = -1;
			}

			try {
				String num_children_dead_string = ((EditText) (rootView
						.findViewById(R.id.family_visit2_num_children_dead)))
						.getText().toString();
				num_children_dead_put = Integer
						.parseInt(num_children_dead_string);
			} catch (NumberFormatException | NullPointerException e) {
				num_children_dead_put = -1;
			}

			// Death Information
			try {
				String children_death_information_string = ((EditText) (rootView
						.findViewById(R.id.family_visit2_death_information)))
						.getText().toString();
				children_death_information_put = children_death_information_string;
			} catch (NullPointerException e) {
				children_death_information_put = "";
			}

			try {
				String num_children_under_5_string = ((EditText) (rootView
						.findViewById(R.id.family_visit2_num_children_under_5)))
						.getText().toString();
				num_children_under_5_put = Integer
						.parseInt(num_children_under_5_string);
			} catch (NumberFormatException | NullPointerException e) {
				num_children_under_5_put = -1;
			}

			try {
				String num_people_in_household_string = ((EditText) (rootView
						.findViewById(R.id.family_visit2_num_people_in_household)))
						.getText().toString();
				num_people_in_household_put = Integer
						.parseInt(num_people_in_household_string);
			} catch (NumberFormatException | NullPointerException e) {
				num_people_in_household_put = -1;
			}

		}
	}

}
