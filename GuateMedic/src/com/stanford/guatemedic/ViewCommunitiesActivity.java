package com.stanford.guatemedic;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewCommunitiesActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_view_communities);
		
		final ViewCommunitiesFragment fragment = ViewCommunitiesFragment.newInstance();
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, fragment).commit();
		}
		
		final EditText search = (EditText)findViewById(R.id.view_communities_search);
		search.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) fragment.update("");
				else fragment.update(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count){}
		});
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		// OMenuInflater inflater = getMenuInflater();
		//inflate.inflate(R.menu.menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	public static class ViewCommunitiesFragment extends ListFragment {
		
		private ArrayList<DetailedVillage> communities;
		
		public ViewCommunitiesFragment() {
			
		}
		
		public void update(String str) {
			((CommunityAdapter)getListAdapter()).clear();
			communities = DetailedRecordsStore.get(getActivity().getApplication()).getMatchingVillages(str);
			((CommunityAdapter)getListAdapter()).addAll(communities);
			((CommunityAdapter)getListAdapter()).notifyDataSetChanged();
		}
		
		public static ViewCommunitiesFragment newInstance() {
			ViewCommunitiesFragment fragment = new ViewCommunitiesFragment();
			return fragment;
		}
		
		@Override
		public void onResume() {
			super.onResume();
			((CommunityAdapter)getListAdapter()).notifyDataSetChanged();
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			communities = DetailedRecordsStore.get(getActivity().getApplication()).getVillages();
			CommunityAdapter adapter = new CommunityAdapter(communities);
			setListAdapter(adapter);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			//Start new activity
		}
		
		private class CommunityAdapter extends ArrayAdapter<DetailedVillage> {
			
			public CommunityAdapter(ArrayList<DetailedVillage> communities) {
				super(getActivity(), 0, communities);
			}
			
			@Override 
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment2, null);
				}
				
				final DetailedVillage community = getItem(position);
				String name = community.getName();
				
				TextView title = (TextView)convertView.findViewById(R.id.list_item_title);
				title.setText(name);
				
				TextView subtitle = (TextView)convertView.findViewById(R.id.list_item_subtitle);
				DetailedRecordsStore drs = DetailedRecordsStore.get(getActivity().getApplication());
				ArrayList<DetailedFamily> families = drs.getFamilies(name);
				int num_families = families.size();
				int num_children = 0;
				for (DetailedFamily family : families)
					num_children += drs.getChildren(family.getFamily_id()).size();
				String subtitle_string = num_children + " Niños de " + num_families + " Familias";
				subtitle.setText(subtitle_string);
				
				CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
				checkbox.setVisibility(View.GONE);
				return convertView;
			}
		}
	}
}
