package com.stanford.guatemedic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewChildVisitSinglePageActivity extends ActionBarActivity {

	private static String child_id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setHomeButtonEnabled(true);
		child_id = getIntent().getStringExtra("child_id");
		DetailedChild child = DetailedRecordsStore.get(getApplication())
				.getChild(child_id);
		setTitle(child.getName());
		setContentView(R.layout.activity_add_new_child_visit_single_page);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container,
							new AddNewChildVisitSinglePageFragment()).commit();
		}
	}

	public void onBackPressed() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
		// set title
		alertDialogBuilder.setTitle("Alerta");
 
			// set dialog message
		alertDialogBuilder
			.setMessage("Vas a perder este informaci—n si dejes este pagina.")
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
		//getMenuInflater().inflate(R.menu.add_child_visit_menu, menu);
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

	public static class AddNewChildVisitSinglePageFragment extends Fragment {
		private static int createFirstTime1;
		private static int createFirstTime2;

		public AddNewChildVisitSinglePageFragment() {
			createFirstTime1=1;
			createFirstTime2=1;
		}
		
		public static AddNewChildVisitSinglePageFragment newInstance() {
			AddNewChildVisitSinglePageFragment f = new AddNewChildVisitSinglePageFragment();
			return f;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_add_new_child_visit_single_page, container, false);

			DetailedChild child = DetailedRecordsStore.get(getActivity()).getChild(child_id);

			/*
			 * String child_name = child.getName(); TextView child_name_field =
			 * (TextView)rootView.findViewById(R.id.
			 * single_child_visit_child_name_textview);
			 * child_name_field.setText("Nombre del Ni–o: " + child_name);
			 */
			
			if(createFirstTime1==1){
				rootView.findViewById(R.id.child_visit_child_is_only_breastfed_textview).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit_child_is_only_breastfed_radio).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_textview).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_edittext).setVisibility(View.GONE);
			}
			
			
			if(createFirstTime1==0){
				rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_textview).setVisibility(View.VISIBLE);
				rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_edittext).setVisibility(View.VISIBLE);
			}
			if(createFirstTime2==1){
				rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.GONE);
				rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.GONE);
			}
			
			rootView.findViewById(R.id.yes_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							rootView.findViewById(R.id.child_visit_child_is_only_breastfed_textview).setVisibility(View.VISIBLE);
							rootView.findViewById(R.id.child_visit_child_is_only_breastfed_radio).setVisibility(View.VISIBLE);
							
							rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_textview).setVisibility(View.GONE);
							rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_edittext).setVisibility(View.GONE);
							if(createFirstTime2==0){
								rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.VISIBLE);
								rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.VISIBLE);
							}
							createFirstTime1=0;
						}
					});

			rootView.findViewById(R.id.yes_only_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.GONE);
							rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.GONE);
						}
					});
			rootView.findViewById(R.id.no_only_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.VISIBLE);
							rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.VISIBLE);
							createFirstTime2=1;
						}
					});
			rootView.findViewById(R.id.no_breastfed).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							rootView.findViewById(R.id.child_visit_child_is_only_breastfed_textview).setVisibility(View.GONE);
							rootView.findViewById(R.id.child_visit_child_is_only_breastfed_radio).setVisibility(View.GONE);
							rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.GONE);
							rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.GONE);
							rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_textview).setVisibility(View.VISIBLE);
							rootView.findViewById(R.id.child_visit_child_what_age_stopped_breastfeeding_edittext).setVisibility(View.VISIBLE);
							
							createFirstTime1=1;
						
						}
					});

			double child_age = GeneralUtilities.round(DateTimeUtilities.convertDaysToMonths((int)DateTimeUtilities.getCurrentAgeInDays(child.getDob())), 1);
			TextView child_age_field = (TextView) rootView.findViewById(R.id.single_child_visit_child_age_textview);
			child_age_field.setText("Edad del Ni–o: " + child_age + " meses");
			
			Button button = (Button)rootView.findViewById(R.id.single_child_visit_submit_button);
			button.setOnClickListener(new View.OnClickListener() {
			//hello	
				@Override
				public void onClick(View arg0) {
					try {
						JSONObject obj = new JSONObject();
						
						double weight = 0;
						try {
							String weight_string = ((EditText)rootView.findViewById(R.id.single_child_visit_child_weight_edittext)).getText().toString();
							if (weight_string != null || ! weight_string.equals("")) {
								weight = Double.parseDouble(weight_string);
								obj.put("weight_in_pounds", weight);
							} else {
								Toast.makeText(getActivity(), "Necesitas Incluir Peso", Toast.LENGTH_LONG).show();
							}
						} catch (NullPointerException | NumberFormatException e) {
							Toast.makeText(getActivity(), "Necesitas Incluir Peso", Toast.LENGTH_LONG).show();
						}
						double height = 0;
						try {
							String height_string = ((EditText)rootView.findViewById(R.id.single_child_visit_child_height_edittext)).getText().toString();
							if (height_string != null || ! height_string.equals("")) {
								height = Double.parseDouble(height_string);
								obj.put("height_in_centimeters", height);

							} else {
								Toast.makeText(getActivity(), "Necesitas Incluir Talla", Toast.LENGTH_LONG).show();
							}
						} catch (NullPointerException | NumberFormatException e) {
							Toast.makeText(getActivity(), "Necesitas Incluir Talla", Toast.LENGTH_LONG).show();
						}
						if (height > 0 && weight > 0) {
							obj.put("child_id", child_id);
							DetailedRecordsStore.get(getActivity()).addNewChildVisit(obj);
							Intent i = new Intent(getActivity(), GraphActivity.class);
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