package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class ViewProfilesActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_view_profiles);
		getActionBar().setHomeButtonEnabled(true);
		String community = getIntent().getStringExtra("community");
		setTitle(community);
		
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
		
		ArrayList<String> families;
		Map<String, ArrayList<String>> children;
		ExpandableListView expListView;
		
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
			String community = args.getString("community");
			ArrayList<DetailedFamily> the_families = DetailedRecordsStore.get(getActivity().getApplication()).getFamilies(community);
			for (DetailedFamily family : the_families) {
				String family_id = family.getFamily_id();
				families.add(family_id);
				ArrayList<String> children_array = new ArrayList<String>();
				children.put(family_id, children_array);
				ArrayList<DetailedChild> the_children = DetailedRecordsStore.get(getActivity().getApplication()).getChildren(family_id);
				for (DetailedChild child : the_children)
					children_array.add(child.getChild_id());
				children_array.add("+Add Child");
			}
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_view_profiles_all, container,false);
			
			expListView = (ExpandableListView)rootView.findViewById(R.id.profiles_all_list);
			final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(getActivity(), families, children);
			expListView.setAdapter(expListAdapter);
			return rootView;
		}
	}

}
