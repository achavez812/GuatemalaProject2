package com.stanford.guatemedic;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadChildListActivity extends ActionBarActivity {
	
	private static String family_id;
	private static String village;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_village_list);
		getActionBar().setHomeButtonEnabled(true);

		family_id = getIntent().getStringExtra("family_id");
		village = getIntent().getStringExtra("village");
		String parent1_name =  BasicRecordsStore.get().getFamily(family_id).getParent1_name();
		if (parent1_name == null) BasicRecordsStore.get().getFamily(family_id).setParent1_name("");
		setTitle("Niños de " + BasicRecordsStore.get().getFamily(family_id).getParent1_name());
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, DownloadChildListFragment.newInstance(family_id)).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download_button, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			return true;
		}
		if (id == R.id.action_download) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
			BasicRecordsStore.get().DownloadData(DownloadChildListActivity.this, getApplication());
			return true;
		}
		if (id == R.id.action_selectall) {
			BasicRecordsStore.get().getVillage(village).setCheckboxSelected(true);
			BasicRecordsStore.get().getFamily(family_id).setCheckboxSelected(true);
			for (BasicChild bc : BasicRecordsStore.get().getChildren(family_id))
				bc.setCheckboxSelected(true);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, DownloadChildListFragment.newInstance(family_id)).commit();

		}
		if (id == R.id.action_unselectall) {
			for (BasicChild bc : BasicRecordsStore.get().getChildren(family_id))
				bc.setCheckboxSelected(false);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, DownloadChildListFragment.newInstance(family_id)).commit();

		}
		return super.onOptionsItemSelected(item);
	}
	


	public static class DownloadChildListFragment extends ListFragment {
		
		private String family_id;
		
		public DownloadChildListFragment() {

		}
		
		public static DownloadChildListFragment newInstance(String family_id) {
		    DownloadChildListFragment f = new DownloadChildListFragment();
		    // Supply index input as an argument.
		    Bundle args = new Bundle();
		    args.putString("family_id", family_id);
		    f.setArguments(args);
		    return f;
		}

		private ArrayList<BasicChild> children;
		
		@Override
		public void onResume() {
			super.onResume();
			((ChildAdapter)getListAdapter()).notifyDataSetChanged();
			//getActivity().setTitle(R.string.villages_title);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//getActivity().setTitle(R.string.villages_title);
			Bundle args = getArguments();
			family_id = args.getString("family_id");
			
			children = BasicRecordsStore.get().getChildren(family_id);
			ChildAdapter adapter = new ChildAdapter(children);
			setListAdapter(adapter);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

		}
		
		private class ChildAdapter extends ArrayAdapter<BasicChild>  {
			public ChildAdapter(ArrayList<BasicChild> children) {
				super(getActivity(), 0, children);
			}
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment, null);
				}
				
				BasicChild child = getItem(position);
				String child_id = child.getChild_id();
				
				TextView childTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				childTitle.setText(child.getName());
				
				CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
				checkbox.setChecked(child.isCheckboxSelected());
				checkbox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View buttonView) {
						boolean isChecked = ((CheckBox)buttonView).isChecked();
						if (isChecked) {
							BasicRecordsStore.get().getVillage(village).setCheckboxSelected(true);
							BasicRecordsStore.get().getFamily(family_id).setCheckboxSelected(true);
						}
						View v = (View)buttonView.getParent();
						BasicChild theChild = children.get(position);
						String child_id = theChild.getChild_id();
						theChild.setCheckboxSelected(isChecked);
					}
				});

				return convertView;
				
			}
		}

		
	}	


}
