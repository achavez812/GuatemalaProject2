package com.stanford.guatemedic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddNewGeneralFormActivity extends ActionBarActivity {
	
	private static AddNewGeneralFormFragment the_fragment;
	private static String child_id;
	private static String community;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_empty);
		
		child_id = getIntent().getStringExtra("child_id");
		community = getIntent().getStringExtra("community");
		
		if (savedInstanceState == null) {
			the_fragment = AddNewGeneralFormFragment.newInstance(child_id);
			getFragmentManager().beginTransaction()
				.add(R.id.container, the_fragment).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		View v = the_fragment.the_view;
		
		DetailedChild dc = DetailedRecordsStore.get(getApplication()).getChild(child_id);
		DetailedChildVisit dcv = dc.getLastVisit();
		
		EditText child_name_edittext = (EditText)v.findViewById(R.id.general_form_child_name_edittext);
		dc.setName(child_name_edittext.getText().toString());
		
		EditText child_weight_edittext = (EditText)v.findViewById(R.id.general_form_weight_edittext);
		String child_weight_str = child_weight_edittext.getText().toString();
		if (child_weight_str != null && !child_weight_str.isEmpty())
			dcv.setWeight_in_pounds(Float.parseFloat(child_weight_str));
		
		EditText child_height_edittext = (EditText)v.findViewById(R.id.general_form_height_edittext);
		String child_height_str = child_height_edittext.getText().toString();
		if (child_height_str != null && !child_height_str.isEmpty())
			dcv.setHeight_in_centimeters(Float.parseFloat(child_height_str));
		
		Intent i = new Intent(getApplication(), ViewProfilesActivity.class);
		i.putExtra("community", community);
		startActivity(i);
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		//MenuInflater inflater = getMenuInflater();
		//inflate.inflate(R.menu.menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	public static class AddNewGeneralFormFragment extends Fragment {
		
		private View the_view;
		private String child_id;
		private static int createFirstTime1;
		private static int createFirstTime2;
		
		public AddNewGeneralFormFragment() {
			createFirstTime1 = 1;
			createFirstTime2 = 1;
		}
		
		public static AddNewGeneralFormFragment newInstance(String child_id) {
			AddNewGeneralFormFragment f = new AddNewGeneralFormFragment();
			Bundle args =  new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_add_new_general_form, container,false);
			the_view = rootView;
			child_id = getArguments().getString("child_id");
			final DetailedChild child = DetailedRecordsStore.get(getActivity().getApplication()).getChild(child_id);
			DetailedChildVisit visit = child.getLastVisit();
			boolean child_in_progress = child.isIn_progress();
			boolean child_visit_in_progress = child.hasVisit_in_progress();
			
			LinearLayout basic_child_layout = (LinearLayout)rootView.findViewById(R.id.general_form_basic_child_layout);
			LinearLayout family_layout = (LinearLayout)rootView.findViewById(R.id.general_form_family_layout);
			LinearLayout detailed_child_layout = (LinearLayout)rootView.findViewById(R.id.general_form_child_layout);
			LinearLayout child_visit_layout = (LinearLayout)rootView.findViewById(R.id.general_form_child_visit_layout);
			
			family_layout.setVisibility(View.GONE);
			if (!child_in_progress) {
				detailed_child_layout.setVisibility(View.GONE);
			}
			
			
			Spinner parent1_marital_status_spinner = (Spinner)rootView.findViewById(R.id.general_form_parent1_marital_status_spinner);
			ArrayAdapter<String> adapter_marital_status = new ArrayAdapter<String>(getActivity(),
					R.layout.spinner_item, getResources().getStringArray(R.array.marital_status_array));
			adapter_marital_status.setDropDownViewResource(R.layout.spinner_dropdown_item);
			parent1_marital_status_spinner.setAdapter(adapter_marital_status);
			parent1_marital_status_spinner.setSelection(adapter_marital_status.getCount() - 1);
			
			Spinner father_job_spinner = (Spinner)rootView.findViewById(R.id.general_form_father_job_spinner);
			ArrayAdapter<String> adapter_father_job = new ArrayAdapter<String>(getActivity(),
					R.layout.spinner_item, getResources().getStringArray(R.array.father_job_array));
			adapter_father_job.setDropDownViewResource(R.layout.spinner_dropdown_item);
			father_job_spinner.setAdapter(adapter_father_job);
			father_job_spinner.setSelection(adapter_father_job.getCount() - 1);
			
			Spinner type_of_pregnancy_spinner = (Spinner)rootView.findViewById(R.id.general_form_type_of_pregnancy);
			ArrayAdapter<String> adapter_type_of_pregnancy = new ArrayAdapter<String>(getActivity(),
					R.layout.spinner_item, getResources().getStringArray(R.array.type_of_pregnancy_array));
			adapter_type_of_pregnancy.setDropDownViewResource(R.layout.spinner_dropdown_item);
			type_of_pregnancy_spinner.setAdapter(adapter_type_of_pregnancy);
			type_of_pregnancy_spinner.setSelection(adapter_type_of_pregnancy.getCount() - 1);
			
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
			
			rootView.findViewById(R.id.yes_breastfed).setOnClickListener(new View.OnClickListener() {
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

			rootView.findViewById(R.id.yes_only_breastfed).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.GONE);
					rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.GONE);
				}
			});
			
			rootView.findViewById(R.id.no_only_breastfed).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_textview).setVisibility(View.VISIBLE);
					rootView.findViewById(R.id.child_visit_child_how_long_only_breastfed_edittext).setVisibility(View.VISIBLE);
					createFirstTime2=1;
				}
			});
			
			rootView.findViewById(R.id.no_breastfed).setOnClickListener(new View.OnClickListener() {
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

			EditText child_name_edittext = (EditText)rootView.findViewById(R.id.general_form_child_name_edittext);
			child_name_edittext.setText(child.getName());
			
			final EditText child_weight_edittext = (EditText)rootView.findViewById(R.id.general_form_weight_edittext);
			if (visit.getWeight_in_pounds() != 0)
				child_weight_edittext.setText("" + visit.getWeight_in_pounds());
			
			final EditText child_height_edittext = (EditText)rootView.findViewById(R.id.general_form_height_edittext);
			if (visit.getHeight_in_centimeters() != 0)
				child_height_edittext.setText("" + visit.getHeight_in_centimeters());
			
			
			
			Button submit_button = (Button)rootView.findViewById(R.id.general_form_submit_button);
			submit_button.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String weight = child_weight_edittext.getText().toString();
					String height = child_height_edittext.getText().toString();
					if (weight != null && height != null && !weight.isEmpty() && !height.isEmpty()) {
						child.getLastVisit().setWeight_in_pounds(Float.parseFloat(weight));
						child.getLastVisit().setHeight_in_centimeters(Float.parseFloat(height));
						Intent i = new Intent(getActivity(), GraphActivity.class);
						i.putExtra("child_id", child_id);
						i.putExtra("weight", weight);
						i.putExtra("height", height);
						startActivity(i);
					}		
				}
				
			});
			
			return rootView;
		}
	}

}
