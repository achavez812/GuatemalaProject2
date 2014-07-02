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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadFamilyListActivity extends ActionBarActivity {
	
	private static String village_name;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_village_list);
		village_name = getIntent().getStringExtra("village_name");
		setTitle("Families in " + village_name);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, DownloadFamilyListFragment.newInstance(village_name)).commit();
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
		if (id == R.id.action_download) {
			BasicRecordsStore.get().DownloadData(DownloadFamilyListActivity.this, getApplication());
			return true;
		}
		if (id == R.id.action_selectall) {
			BasicRecordsStore.get().getVillage(village_name).setCheckboxSelected(true);
			for (BasicFamily bf : BasicRecordsStore.get().getFamilies(village_name)) {
				bf.setCheckboxSelected(true);
				for (BasicChild bc : BasicRecordsStore.get().getChildren(bf.getFamily_id()))
					bc.setCheckboxSelected(true);
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.container, DownloadFamilyListFragment.newInstance(village_name)).commit();

		}
		if (id == R.id.action_unselectall) {

			for (BasicFamily bf : BasicRecordsStore.get().getFamilies(village_name)) {
				if (bf.isCheckboxSelected()) {
					bf.setCheckboxSelected(false);
					for (BasicChild bc : BasicRecordsStore.get().getChildren(bf.getFamily_id()))
						bc.setCheckboxSelected(false);
				}
			}
			getSupportFragmentManager().beginTransaction().replace(R.id.container, DownloadFamilyListFragment.newInstance(village_name)).commit();

		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class DownloadFamilyListFragment extends ListFragment {
		
		private String village_name;
		
		public DownloadFamilyListFragment() {

		}
		
		public static DownloadFamilyListFragment newInstance(String village_name) {
		    DownloadFamilyListFragment f = new DownloadFamilyListFragment();
		    // Supply index input as an argument.
		    Bundle args = new Bundle();
		    args.putString("village_name", village_name);
		    f.setArguments(args);
		    return f;
		}

		private ArrayList<BasicFamily> families;
		
		@Override
		public void onResume() {
			super.onResume();
			((FamilyAdapter)getListAdapter()).notifyDataSetChanged();
			//getActivity().setTitle(R.string.villages_title);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//getActivity().setTitle(R.string.villages_title);
			Bundle args = getArguments();
			village_name = args.getString("village_name");
			
			families = BasicRecordsStore.get().getFamilies(village_name);
			FamilyAdapter adapter = new FamilyAdapter(families);
			setListAdapter(adapter);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			BasicFamily family = families.get(position);
			Intent intent = new Intent(getActivity().getApplication(), DownloadChildListActivity.class);
			intent.putExtra("family_id", family.getFamily_id());
			intent.putExtra("village", village_name);
			startActivity(intent);
		}
		
		
		private class FamilyAdapter extends ArrayAdapter<BasicFamily>  {
			public FamilyAdapter(ArrayList<BasicFamily> families) {
				super(getActivity(), 0, families);
			}
			

			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment, null);
				}
				
				BasicFamily family = getItem(position);
				String family_id = family.getFamily_id();
				
				TextView familyTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				familyTitle.setText(family.getParent1_name());
				
				CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.list_item_checkbox);
				
				if (BasicRecordsStore.get().getFamilies(village_name).size() == 0) {
					checkbox.setVisibility(View.INVISIBLE);
					TextView numChildrenTextView = (TextView)convertView.findViewById(R.id.list_item_subtitle);
					numChildrenTextView.setText("0/0 Children");
				} else {
					int count = 0;
					for (BasicChild aChild : BasicRecordsStore.get().getChildren(family_id)) {
						if (aChild.isCheckboxSelected())
							count++;
					}
					family.setCheckboxSelected(count != 0);
					
					TextView numChildrenTextView = (TextView)convertView.findViewById(R.id.list_item_subtitle);
					numChildrenTextView.setText(count + "/" + BasicRecordsStore.get().getChildren(family_id).size() + " Children");
					
					
					checkbox.setChecked(family.isCheckboxSelected());
					checkbox.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View buttonView) {
							boolean isChecked = ((CheckBox)buttonView).isChecked();
							View v = (View)buttonView.getParent();
							BasicFamily theFamily = families.get(position);
							theFamily.setCheckboxSelected(isChecked);
							int numTotalChildren = BasicRecordsStore.get().getChildren(theFamily.getFamily_id()).size();
							if (isChecked) {
								((TextView)v.findViewById(R.id.list_item_subtitle)).setText(numTotalChildren + "/" + numTotalChildren + " Children");
								String villageName = village_name;
								BasicVillage village = BasicRecordsStore.get().getVillage(villageName);
								village.setCheckboxSelected(true);
							} else
								((TextView)v.findViewById(R.id.list_item_subtitle)).setText("0/" + numTotalChildren + " Children");
							
							
							String familyId = theFamily.getFamily_id();
							ArrayList<BasicChild> children = BasicRecordsStore.get().getChildren(familyId);
							for (BasicChild theChild : children) {
								theChild.setCheckboxSelected(isChecked);
							}
						}
					});
				}
				

				return convertView;
				
			}
		}

		
	}	

}
