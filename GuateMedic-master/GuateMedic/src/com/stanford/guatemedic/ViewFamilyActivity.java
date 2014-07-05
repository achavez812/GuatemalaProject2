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
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
				.add(R.id.child_list_container, ViewChildListFragment.newInstance(family_id)).commit();
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
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment, null);
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
