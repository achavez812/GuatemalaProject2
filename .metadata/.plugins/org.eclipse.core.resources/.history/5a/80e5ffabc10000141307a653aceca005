package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadChildListActivity extends ActionBarActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_village_list);
		String family_id = getIntent().getStringExtra("family_id");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, DownloadChildListFragment.newInstance(family_id)).commit();
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
				Toast.makeText(getApplication(), "Success", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplication(), "Failure", Toast.LENGTH_LONG).show();

			}
		}
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
			Intent intent = new Intent(getActivity().getApplication(), DownloadFamilyListActivity.class);
			startActivity(intent);
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
				childTitle.setText(child_id);
				
				CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
				checkbox.setVisibility(View.GONE);
				

				return convertView;
				
			}
		}

		
	}	


}
