package com.stanford.guatemedic;

import org.json.JSONException;
import org.json.JSONObject;

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

public class AddNewChildActivity extends ActionBarActivity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_child);
		String family_id = getIntent().getStringExtra("family_id");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, AddNewChildFragment.newInstance(family_id)).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
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
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_new_child, container,false);
			final String family_id = getArguments().getString("family_id");
			
			TextView textview = (TextView)rootView.findViewById(R.id.add_new_child_textview1);
			textview.setText(family_id);
			
			Button button = (Button)rootView.findViewById(R.id.add_new_child_button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText name_field = (EditText)getActivity().findViewById(R.id.add_new_child_name);
					String name = name_field.getText().toString();
					
					EditText dob_field = (EditText)getActivity().findViewById(R.id.add_new_child_dob);
					String dob = dob_field.getText().toString();
					
					EditText gender_field = (EditText)getActivity().findViewById(R.id.add_new_child_gender);
					String gender = gender_field.getText().toString();
					
					EditText type_of_birth_field = (EditText)getActivity().findViewById(R.id.add_new_child_type_of_birth);
					String type_of_birth = type_of_birth_field.getText().toString();
					
					EditText num_children_in_same_pregnancy_field = (EditText)getActivity().findViewById(R.id.add_new_child_num_children_in_same_pregnancy);
					String num_children_in_same_pregnancy = num_children_in_same_pregnancy_field.getText().toString();
					
					EditText months_gestated_field = (EditText)getActivity().findViewById(R.id.add_new_child_months_gestated);
					String months_gestated = months_gestated_field.getText().toString();
					
					EditText prenatal_care_field = (EditText)getActivity().findViewById(R.id.add_new_child_prenatal_care);
					String prenatal_care = prenatal_care_field.getText().toString();
					
					EditText birth_weight_field = (EditText)getActivity().findViewById(R.id.add_new_child_birth_weight);
					String birth_weight = birth_weight_field.getText().toString();
					
					EditText birth_height_field = (EditText)getActivity().findViewById(R.id.add_new_child_birth_height);
					String birth_height = birth_height_field.getText().toString();
					
					EditText youngest_sibling_dob_field = (EditText)getActivity().findViewById(R.id.add_new_child_youngest_sibling_dob);
					String youngest_sibling_dob = youngest_sibling_dob_field.getText().toString();
					
					try {
						JSONObject obj = new JSONObject();
						obj.put("family_id", family_id);
						obj.put("name", name);
						obj.put("dob", dob);
						obj.put("gender", gender);
						obj.put("type_of_birth", type_of_birth);
						obj.put("num_children_in_same_pregnancy", num_children_in_same_pregnancy);
						obj.put("months_gestated", months_gestated);
						obj.put("prenatal_care", prenatal_care);
						obj.put("birth_weight", birth_weight);
						obj.put("birth_height", birth_height);
						obj.put("youngest_sibling_dob", youngest_sibling_dob);
						DetailedRecordsStore.get(getActivity().getApplication()).addNewChild(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					//Go to activity of this family
					Intent i = new Intent(getActivity(), ViewChildListActivity.class);
					i.putExtra("family_id", family_id);
					startActivity(i);
				
				}
			});
			
			return rootView;
		}
		
		
		
		
	}
}