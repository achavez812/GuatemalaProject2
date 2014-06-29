package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

public class DownloadVillageListActivity extends ActionBarActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_village_list);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DownloadVillageListFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_download) {
			try {
				new HandleDownloadRecordsTask(true).execute().get();
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class HandleDownloadRecordsTask extends AsyncTask<Void, Void, Void> {
		
		private boolean showLoading;
		private boolean success;
		
		public HandleDownloadRecordsTask(boolean showLoading) {
			super();
			this.showLoading = showLoading;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				JSONArray json_array = new JSONArray();
				for (BasicVillage village : BasicRecordsStore.get().getVillages()) {
					if (village.isCheckboxSelected()) {
						for (BasicFamily family : BasicRecordsStore.get().getFamilies(village.getName())) {
							if (family.isCheckboxSelected()) {
								for (BasicChild child :BasicRecordsStore.get().getChildren(family.getFamily_id())) {
									if (child.isCheckboxSelected()) {
										json_array.put(child.getChild_id());
									}
								}
							}
						}
					}
				}
				JSONObject obj = new JSONObject();
				obj.put("child_ids", json_array);
				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("Authorization", BasicRecordsStore.get().getAuthKey());
				String response = Utilities.postRequest("https://guatemedic.herokuapp.com/records", headerMap, obj.toString());
				if (response == null) {
					success = false;
				} else {
					GuatemedicWriter gfw = new GuatemedicWriter(getApplication());
					if (gfw.saveDownloads(response)) {
						DetailedRecordsStore.load(getApplication());
						success = true;
					} else {

						success = false;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			if (showLoading) {
				//Display loading bar
			}
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (showLoading) {
				//Dismiss loading bar
			}
			if (success) {
			} 
		}

	
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DownloadVillageListFragment extends ListFragment {

		public DownloadVillageListFragment() {
			
		}

		private ArrayList<BasicVillage> villages;
		
		@Override
		public void onResume() {
			super.onResume();
			((VillageAdapter)getListAdapter()).notifyDataSetChanged();
			//getActivity().setTitle(R.string.villages_title);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//getActivity().setTitle(R.string.villages_title);
			
			villages = BasicRecordsStore.get().getVillages();
			VillageAdapter adapter = new VillageAdapter(villages);
			setListAdapter(adapter);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			BasicVillage village = villages.get(position);
			Intent intent = new Intent(getActivity().getApplication(), DownloadFamilyListActivity.class);
			intent.putExtra("village_name", village.getName());
			startActivity(intent);
		}
		
		private class VillageAdapter extends ArrayAdapter<BasicVillage>  {
			public VillageAdapter(ArrayList<BasicVillage> villages) {
				super(getActivity(), 0, villages);
			}
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment, null);
				}
				
				BasicVillage village = getItem(position);
				String villageName = village.getName();
				
				TextView villageTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				villageTitle.setText(villageName);
				
				int numChildren = 0;
				int totalChildren = 0;
				int numFamilies = 0;
				for (BasicFamily aFamily : BasicRecordsStore.get().getFamilies(villageName)) {
					if (aFamily.isCheckboxSelected()) {
						numFamilies++;
						String familyId = aFamily.getFamily_id();
						for (BasicChild aChild : BasicRecordsStore.get().getChildren(familyId)) {
							if (aChild.isCheckboxSelected())
								numChildren++;
						}
					}
					totalChildren += BasicRecordsStore.get().getChildren(aFamily.getFamily_id()).size();
				}
				village.setCheckboxSelected(numFamilies != 0);
				
				TextView villageSubtitle = (TextView)convertView.findViewById(R.id.list_item_subtitle);
				String subtitleText = numChildren + "/" + totalChildren + " Children from " + numFamilies + " Families";
				villageSubtitle.setText(subtitleText);
				
				CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
				checkbox.setChecked(village.isCheckboxSelected());
				checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						View v = (View)buttonView.getParent();
						BasicVillage theVillage = villages.get(position);
						String villageName = theVillage.getName();
						theVillage.setCheckboxSelected(isChecked);
						
						int numChildren = 0;
						int numFamilies = 0;
						for (BasicFamily aFamily : BasicRecordsStore.get().getFamilies(villageName)) {
							int c = BasicRecordsStore.get().getChildren(aFamily.getFamily_id()).size();
							if (c > 0) {
								aFamily.setCheckboxSelected(isChecked);
								numFamilies++;
								numChildren += c;
								String familyId = aFamily.getFamily_id();
								for (BasicChild aChild : BasicRecordsStore.get().getChildren(familyId)) {
									aChild.setCheckboxSelected(isChecked);
								}
							}
						}
						
						if (isChecked) {
							String subtitleText = numChildren + "/" + numChildren + " Children from " + numFamilies + " Families";
							((TextView)v.findViewById(R.id.list_item_subtitle)).setText(subtitleText);
							
						} else {
							String subtitleText = 0 + "/" + numChildren + " Children from 0 Families";
							((TextView)v.findViewById(R.id.list_item_subtitle)).setText(subtitleText);
						}

					}
				});
				
				
				return convertView;
				
			}
		}

		
	}	
}