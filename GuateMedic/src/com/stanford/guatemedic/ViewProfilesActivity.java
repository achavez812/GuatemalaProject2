package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ViewProfilesActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_view_profiles);
		getActionBar().setHomeButtonEnabled(true);
		final String community = getIntent().getStringExtra("community");
		setTitle(community);
		
		final TextView all_tab = (TextView)findViewById(R.id.view_profiles_all_tab);
		final TextView in_progress_tab = (TextView)findViewById(R.id.view_profiles_in_progress_tab);
		all_tab.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				all_tab.setBackgroundColor(getResources().getColor(R.color.selected_tab_background_color));
				all_tab.setTextColor(getResources().getColor(R.color.selected_tab_text_color));
				in_progress_tab.setBackgroundColor(getResources().getColor(R.color.unselected_tab_background_color));
				in_progress_tab.setTextColor(getResources().getColor(R.color.unselected_tab_text_color));
				getFragmentManager().beginTransaction().replace(R.id.container, ViewAllProfilesFragment.newInstance(community)).commit();
				
			}
		}); 
		in_progress_tab.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				in_progress_tab.setBackgroundColor(getResources().getColor(R.color.selected_tab_background_color));
				in_progress_tab.setTextColor(getResources().getColor(R.color.selected_tab_text_color));
				all_tab.setBackgroundColor(getResources().getColor(R.color.unselected_tab_background_color));
				all_tab.setTextColor(getResources().getColor(R.color.unselected_tab_text_color));
				getFragmentManager().beginTransaction().replace(R.id.container, ViewInProgressProfilesFragment.newInstance(community)).commit();
				
			}
		});
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, ViewAllProfilesFragment.newInstance(community)).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), ViewCommunitiesActivity.class);
		startActivity(i);
		
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
	
	public static class ViewAllProfilesFragment extends Fragment {
		
		private ArrayList<String> families;
		private Map<String, ArrayList<String>> children;
		private ExpandableListView expListView;
		private String community;
		
		public ViewAllProfilesFragment() {
			families = new ArrayList<String>();
			children = new HashMap<String, ArrayList<String>>();
		}
		
		public static ViewAllProfilesFragment newInstance(String community) {
			ViewAllProfilesFragment f = new ViewAllProfilesFragment();
			Bundle args =  new Bundle();
			args.putString("community", community);
			f.setArguments(args);
			return f;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle args = getArguments();
			community = args.getString("community");
			ArrayList<DetailedFamily> the_families = DetailedRecordsStore.get(getActivity().getApplication()).getFamilies(community);
			for (DetailedFamily family : the_families) {
				String family_id = family.getFamily_id();
				families.add(family_id);
				ArrayList<String> children_array = new ArrayList<String>();
				children.put(family_id, children_array);
				ArrayList<DetailedChild> the_children = DetailedRecordsStore.get(getActivity().getApplication()).getChildren(family_id);
				for (DetailedChild child : the_children)
					children_array.add(child.getChild_id());
				children_array.add("+Añadir un Nuevo Niño");
			}
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_view_profiles_all, container,false);
			
			expListView = (ExpandableListView)rootView.findViewById(R.id.profiles_all_list);
			final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(getActivity(), community, families, children);
			expListView.setAdapter(expListAdapter);
			return rootView;
		}
	}
	
	public static class ViewInProgressProfilesFragment extends Fragment {
		private ArrayList<String> families;
		private Map<String, ArrayList<String>> children;
		private ExpandableListView expListView;
		private String community;
		
		public ViewInProgressProfilesFragment() {
			families = new ArrayList<String>();
			children = new HashMap<String, ArrayList<String>>();
		}
		
		public static ViewInProgressProfilesFragment newInstance(String community) {
			ViewInProgressProfilesFragment f = new ViewInProgressProfilesFragment();
			Bundle args =  new Bundle();
			args.putString("community", community);
			f.setArguments(args);
			return f;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle args = getArguments();
			community = args.getString("community");
			
			Set<String> children_set = DetailedRecordsStore.get(getActivity().getApplication()).getChildrenInProgress(community);
			Log.i("WTF", "" + children_set.size());
			for (String child_id : children_set) {
				DetailedChild child = DetailedRecordsStore.get(getActivity().getApplication()).getChild(child_id);
				String family_id = child.getFamily_id();
				if (!families.contains(family_id)) {
					families.add(family_id);
					children.put(family_id, new ArrayList<String>());
					children.get(family_id).add(child_id);
				} else {
					children.get(family_id).add(child_id);
				}
			}
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_view_profiles_all, container,false);
			
			expListView = (ExpandableListView)rootView.findViewById(R.id.profiles_all_list);
			final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(getActivity(), community, families, children);
			expListView.setAdapter(expListAdapter);
			return rootView;
		}
	}
 
}
