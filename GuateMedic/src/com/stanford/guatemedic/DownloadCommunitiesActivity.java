package com.stanford.guatemedic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadCommunitiesActivity extends ActionBarActivity {
	
	static DownloadCommunitiesFragment frag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_download_communities);
		
		final DownloadCommunitiesFragment fragment = DownloadCommunitiesFragment.newInstance();
		frag = fragment;
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, fragment).commit();
		}
		
		final EditText search = (EditText)findViewById(R.id.download_communities_search);
		search.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) fragment.update("");
				else fragment.update(s.toString());
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
		});
		
		Button button = (Button)findViewById(R.id.download_communities_button);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BasicRecordsStore.get().DowloadData(DownloadCommunitiesActivity.this, getApplication());
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.download_communities_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		int id = menuItem.getItemId();
		if (id == R.id.action_selectall) {
			BasicRecordsStore.get().checkAllCommunities();
			frag.update("");
		}
		if (id == R.id.action_unselectall) {
			BasicRecordsStore.get().uncheckAllCommunities();
			frag.update("");
			
		}
		return super.onOptionsItemSelected(menuItem);
	}
	
	public static class DownloadCommunitiesFragment extends ListFragment {
		
		private ArrayList<BasicCommunity> communities;
		
		public DownloadCommunitiesFragment() {
			
		}
		
		public void update(String str) {
			((CommunityAdapter)getListAdapter()).clear();
			communities = BasicRecordsStore.get().getMatchingCommunities(str);
			((CommunityAdapter)getListAdapter()).addAll(communities);
			((CommunityAdapter)getListAdapter()).notifyDataSetChanged();
		}
		
		public static DownloadCommunitiesFragment newInstance() {
			DownloadCommunitiesFragment fragment = new DownloadCommunitiesFragment();
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
			communities = BasicRecordsStore.get().getCommunities();
			CommunityAdapter adapter = new CommunityAdapter(communities);
			setListAdapter(adapter);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {	
			CheckBox checkbox = (CheckBox)v.findViewById(R.id.list_item_checkbox);
			checkbox.performClick();
		}
		
		private class CommunityAdapter extends ArrayAdapter<BasicCommunity> {
			
			public CommunityAdapter(ArrayList<BasicCommunity> communities) {
				super(getActivity(), 0 , communities);
			}
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment2, null);
				}
				
				final BasicCommunity community = getItem(position);
				String name = community.getName();
				
				TextView title = (TextView)convertView.findViewById(R.id.list_item_title);
				title.setText(name);
				
				TextView subtitle = (TextView)convertView.findViewById(R.id.list_item_subtitle);
				String subtitle_string = community.getNum_children() + " Niños de " + community.getNum_families() + " Familias";
				subtitle.setText(subtitle_string);
				
				final CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
				checkbox.setChecked(community.isCheckbox_selected());
				checkbox.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						boolean isChecked = checkbox.isChecked();
						community.setCheckbox_selected(isChecked);
					}
				});
				
				return convertView;
			}
		}
	}
	

}
