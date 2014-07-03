package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.stanford.guatemedic.DownloadFamilyListActivity.DownloadFamilyListFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
		getActionBar().setHomeButtonEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DownloadVillageListFragment()).commit();
		}
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), MainActivity.class);
		startActivity(i);
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
			BasicRecordsStore.get().DownloadData(DownloadVillageListActivity.this, getApplication());
			return true;
		}
		if (id == R.id.action_selectall) {
			for (BasicVillage bv : BasicRecordsStore.get().getVillages()) {
				bv.setCheckboxSelected(true);
				for (BasicFamily bf : BasicRecordsStore.get().getFamilies(bv.getName())) {
					bf.setCheckboxSelected(true);
					for (BasicChild bc : BasicRecordsStore.get().getChildren(bf.getFamily_id()))
						bc.setCheckboxSelected(true);
				}
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new DownloadVillageListFragment()).commit();

		}
		if (id == R.id.action_unselectall) {
			for (BasicVillage bv : BasicRecordsStore.get().getVillages()) {
				if (bv.isCheckboxSelected()) {
					bv.setCheckboxSelected(false);
					for (BasicFamily bf : BasicRecordsStore.get().getFamilies(bv.getName())) {
						if (bf.isCheckboxSelected()) {
							bf.setCheckboxSelected(false);
							for (BasicChild bc : BasicRecordsStore.get().getChildren(bf.getFamily_id()))
								bc.setCheckboxSelected(false);
						}
					}
				}
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new DownloadVillageListFragment()).commit();

		}
		
		return super.onOptionsItemSelected(item);
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
				checkbox.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View buttonView) {
						boolean isChecked = ((CheckBox)buttonView).isChecked();
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
