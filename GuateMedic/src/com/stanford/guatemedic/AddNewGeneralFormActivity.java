package com.stanford.guatemedic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddNewGeneralFormActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_empty);
		
		
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, AddNewGeneralFormFragment.newInstance(getIntent().getStringExtra("child_id"))).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
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
			child_id = getArguments().getString("child_id");
			
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

			
			
			
			return rootView;
		}
	}

}
