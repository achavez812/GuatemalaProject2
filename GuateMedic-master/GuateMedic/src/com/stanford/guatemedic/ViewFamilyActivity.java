package com.stanford.guatemedic;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ViewFamilyActivity extends ActionBarActivity{
	
	private static String family_id;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_family1);
		getActionBar().setHomeButtonEnabled(true);
		
		family_id = getIntent().getStringExtra("family_id");
		
		Button view_visits_button = (Button)findViewById(R.id.view_family_view_past_visits);
		view_visits_button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button log_visit_button = (Button)findViewById(R.id.view_family_log_new_visit);
		log_visit_button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplication(), AddNewFamilyVisitActivity.class);
				i.putExtra("family_id", family_id);
				startActivity(i);
				
			}
		});
		
		Button add_child_button = (Button)findViewById(R.id.view_family_add_child_button);
		add_child_button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplication(), AddNewChildActivity.class);
				i.putExtra("family_id", family_id);
				startActivity(i);
			}
		});
		
		DetailedFamily family = DetailedRecordsStore.get(getApplication()).getFamily(family_id);
		
		TextView village_field = (TextView)findViewById(R.id.view_family_village_header);
		village_field.setText(family.getVillage_name());
		
		
		TextView mother_name_field = (TextView)findViewById(R.id.view_family_parent1_name_field);
		if (!family.getParent1_name().isEmpty())
			mother_name_field.setText(family.getParent1_name());
		else
			mother_name_field.setText("Nombre desconocido");
		
		TextView mother_dob_field = (TextView)findViewById(R.id.view_family_parent1_dob_field);
		if (!family.getParent1_dob().isEmpty()) {
			String formatted_mother_dob = Utilities.formatDate(family.getParent1_dob());
			mother_dob_field.setText(formatted_mother_dob + " (" + Utilities.getAge(formatted_mother_dob) + " años)");
		} else
			mother_dob_field.setText("Edad desconocida");
		
		TextView father_name_field = (TextView)findViewById(R.id.view_family_parent2_name_field);
		if (!family.getParent2_name().isEmpty())
			father_name_field.setText(family.getParent2_name());
		else
			father_name_field.setText("Nombre desconocido");
		
		TextView father_dob_field = (TextView)findViewById(R.id.view_family_parent2_dob_field);
		if (!family.getParent2_dob().isEmpty()) {
			String formatted_father_dob = Utilities.formatDate(family.getParent2_dob());
			father_dob_field.setText(formatted_father_dob + " (" + Utilities.getAge(formatted_father_dob) + " años)");
		} else
			father_dob_field.setText("Edad desconocida");
		
		int family_visits_size = family.getFamily_visits().size();
		if (family_visits_size > 0) {
			DetailedFamilyVisit visit = family.getFamily_visits().get(family_visits_size - 1);
			
			TextView father_lives_with_field = (TextView)findViewById(R.id.view_family_parent2_lives_with_family_field);
			int does_father_live_with = visit.getDoes_father_lives_with();
			if (does_father_live_with != 0) {
				if (does_father_live_with == 1) {
					father_lives_with_field.setText("No vive con familia");
				} else if (does_father_live_with == 2) {
					father_lives_with_field.setText("Vive con familia");
				} 
			} else {
				father_lives_with_field.setText("Información desconocida");
			}
			
			TextView father_occupation_field = (TextView)findViewById(R.id.view_family_parent2_occupation_field);
			int father_occupation = visit.getFathers_job();
			if (father_occupation != 0) {
				if (father_occupation == 1) {
					father_occupation_field.setText("Fijo");
				} else if (father_occupation == 2) {
					father_occupation_field.setText("Comerciante");
				} else if (father_occupation == 3) {
					father_occupation_field.setText("Agricultor (Propio)");
				} else if (father_occupation == 4) {
					father_occupation_field.setText("Agricultor (Ajeno)");
				} else if (father_occupation == 5) {
					
				}
			} else {
				father_occupation_field.setText("Información desconocida");
			}
			
			
			
		} else {
			TextView father_lives_with_field = (TextView)findViewById(R.id.view_family_parent2_lives_with_family_field);
			father_lives_with_field.setText("Información desconocida");
			
			TextView father_occupation_field = (TextView)findViewById(R.id.view_family_parent2_occupation_field);
			father_occupation_field.setText("Información desconocida");

		}
		
		
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
				.add(R.id.child_list_container, ViewChildListFragment.newInstance(family_id)).commit();
		}
		
		
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), ViewFamilyListActivity.class);
		i.putExtra("village_name", DetailedRecordsStore.get(getApplication()).getFamily(family_id).getVillage_name());
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
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
	
	public static class ViewChildListFragment extends ListFragment {
		
		private String family_id;
		private ArrayList<DetailedChild> children;
		
		public ViewChildListFragment() {
			
		}
		
		public static ViewChildListFragment newInstance(String family_id) {
			ViewChildListFragment f = new ViewChildListFragment();
			Bundle args = new Bundle();
			args.putString("family_id",  family_id);
			f.setArguments(args);
			return f;
		}
		
		@Override
		public void onResume() {
			super.onResume();
			((ChildAdapter)getListAdapter()).notifyDataSetChanged();
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			Bundle args = getArguments();
			family_id = args.getString("family_id");
			children = DetailedRecordsStore.get(getActivity().getApplication()).getChildren(family_id);
			ChildAdapter adapter = new ChildAdapter(children);
			setListAdapter(adapter);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			DetailedChild child = children.get(position);
			Intent intent = new Intent(getActivity(), GraphActivity.class);
			intent.putExtra("child_id", child.getChild_id());
			startActivity(intent);
		}
		
		private class ChildAdapter extends ArrayAdapter<DetailedChild> {
			public ChildAdapter(ArrayList<DetailedChild> children) {
				super(getActivity(), 0, children);
			}
			
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment2, null);
					CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
					checkbox.setVisibility(View.GONE);
				}
				
				DetailedChild child = getItem(position);
				String child_id = child.getChild_id();
				String name = child.getName();
				
				TextView childTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				childTitle.setText(name);

				return convertView;
			}
		}
		
		
		
	}
	

}
