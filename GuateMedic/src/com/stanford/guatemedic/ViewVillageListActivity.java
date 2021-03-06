package com.stanford.guatemedic;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

public class ViewVillageListActivity extends ActionBarActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_village_list);
		getActionBar().setHomeButtonEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ViewVillageListFragment()).commit();
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
		//getMenuInflater().inflate(R.menu.main, menu);
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
		return super.onOptionsItemSelected(item);
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ViewVillageListFragment extends ListFragment {

		public ViewVillageListFragment() {
			
		}
		public void onActivityCreated(Bundle savedInstanceState){
			villages = DetailedRecordsStore.get(getActivity()).getVillages();
			adapter = new VillageAdapter(villages);
			setListAdapter(adapter);
			((ListView)getListView()).setTextFilterEnabled(true);
			super.onActivityCreated(savedInstanceState);
		}

		private ArrayList<DetailedVillage> villages;
		private VillageAdapter adapter;
		@Override
		public void onResume() {
			super.onResume();
			((VillageAdapter)getListAdapter()).notifyDataSetChanged();
			//getActivity().setTitle(R.string.villages_title);
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	

			
			EditText inputSearch=(EditText)getActivity().findViewById(R.id.search_bar_villages);
			inputSearch.addTextChangedListener(new TextWatcher() {
			     
			    @Override
			    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			        // When user changed the Text
			        	ViewVillageListFragment.this.adapter.getFilter().filter(cs);   
			    }
			     
			    @Override
			    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			            int arg3) {
			        // TODO Auto-generated method stub
			         
			    }
			     
			    @Override
			    public void afterTextChanged(Editable arg0) {
			        // TODO Auto-generated method stub     
			    
			    }
			});

	
			return super.onCreateView(inflater, container, savedInstanceState);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//getActivity().setTitle(R.string.villages_title);

			
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			String village_name=((TextView)v.findViewById(R.id.list_item_title)).getText().toString();
			Intent intent = new Intent(getActivity().getApplication(), ViewFamilyListActivity.class);
			intent.putExtra("village_name", village_name);
			startActivity(intent);
		}
		
		private class VillageAdapter extends ArrayAdapter<DetailedVillage> implements Filterable {
			private ArrayList<DetailedVillage> data;
			private ArrayList<DetailedVillage> filtered;
			public VillageAdapter(ArrayList<DetailedVillage> villages) {
				super(getActivity(), 0, villages);
				data=villages;
				filtered=villages;
			}
			public void notifyDataSetChanged() { 
		        super.notifyDataSetChanged(); 
		    } 
			public Filter getFilter() {
		        return new Filter() {

		            @SuppressWarnings("unchecked")
		                protected void publishResults(CharSequence constraint, FilterResults results) {
		                //Log.d(TAG, "**** PUBLISHING RESULTS for: " + constraint);
		            	
		                data = (ArrayList<DetailedVillage>)results.values;
		                
		                notifyDataSetChanged();
		            	}

		            protected FilterResults performFiltering(CharSequence constraint) {
		               // Log.d(TAG, "**** PERFORM FILTERING for: " + constraint);
		                constraint = constraint.toString().toLowerCase();
		               
		 
		                FilterResults results = new FilterResults();
		                if(constraint != null && constraint.toString().length() > 0) {
		                       
		                        ArrayList<DetailedVillage> filt = new ArrayList<DetailedVillage>();
		                        ArrayList<DetailedVillage> lData = new ArrayList<DetailedVillage>();
		                       
		                        synchronized (this) {
		                                lData.addAll(data);
		                    }
		                    filt.clear();
		                    for(int i = 0; i < lData.size(); i++) {
		                        DetailedVillage m = lData.get(i);
		                        if((m.getName().toLowerCase()).contains(constraint))
		                        	{
		                            filt.add(m);
		                        	}
		                    }
		                    results.count = filt.size();
		                    results.values = filt;                 
		                }               
		                else {
		                       
		                    synchronized(this)
		                    {
		                        results.count = filtered.size();
		                        results.values = filtered;
		                        Log.d("Something","Something2");
		                    }
		                } 
		                return results;
		            }
		        };
		    } 
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment, null);
					convertView.findViewById(R.id.list_item_checkbox).setVisibility(View.GONE);
				}
				
				DetailedVillage village = (DetailedVillage) getItem(position);
				String village_name = village.getName();
				int num_families = DetailedRecordsStore.get(getActivity().getApplication()).getFamilies(village_name).size();
				
				TextView villageTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				villageTitle.setText(village_name);
				
				TextView subtitle = (TextView)convertView.findViewById(R.id.list_item_subtitle);
				subtitle.setText(num_families + " Familias");
				
				return convertView;
				
			}
			@Override
			public int getCount() {
				return data.size();
			}
			
			@Override
			   public DetailedVillage getItem(int position) {
		        return data.get(position);
		    }
		 
			@Override
		    public long getItemId(int position) {
		        return position;
		    }
		}

		
	}	


}
