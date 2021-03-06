package com.stanford.guatemedic;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ViewFamilyListActivity extends ActionBarActivity {
	
	private static String village;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_family_list);
		getActionBar().setHomeButtonEnabled(true);

		String village_name = getIntent().getStringExtra("village_name");
		setTitle("Familias en " + village_name);
		village = village_name;
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, ViewFamilyListFragment.newInstance(village_name)).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), ViewVillageListActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.addfamily_button, menu);
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
		if (id == R.id.action_addfamily) {
			Intent i = new Intent(getApplication(), AddNewFamilyActivity.class);
			i.putExtra("village", village);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ViewFamilyListFragment extends ListFragment {
		
		private String village_name;
		private FamilyAdapter adapter;
		private ArrayList<DetailedFamily> families;
		private ArrayList<DetailedFamily> data;
		private ArrayList<DetailedFamily> filtered;
		public ViewFamilyListFragment() {
			
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	

			
			EditText inputSearch=(EditText)getActivity().findViewById(R.id.search_bar_families);
			inputSearch.addTextChangedListener(new TextWatcher() {
			     
			    @Override
			    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			        // When user changed the Text
			        	ViewFamilyListFragment.this.adapter.getFilter().filter(cs);   
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
		public static ViewFamilyListFragment newInstance(String village_name) {
		    ViewFamilyListFragment f = new ViewFamilyListFragment();
		    // Supply index input as an argument.
		    Bundle args = new Bundle();
		    args.putString("village_name", village_name);
		    f.setArguments(args);
		    return f;
		}


		
		@Override
		public void onResume() {
			super.onResume();
			((FamilyAdapter)getListAdapter()).notifyDataSetChanged();
			//getActivity().setTitle(R.string.villages_title);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Intent intent = new Intent(getActivity().getApplication(), ViewFamilyActivity.class);
			intent.putExtra("family_id", families.get(position).getTemp_family_id());
			intent.putExtra("village", village);
			startActivity(intent);
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			Bundle args = getArguments();
			village_name = args.getString("village_name");
			families = DetailedRecordsStore.get(getActivity()).getFamilies(village_name);
			adapter = new FamilyAdapter(families);
			setListAdapter(adapter);
			ListView lv = ((ListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).getListView();
			lv.setTextFilterEnabled(true);
			super.onActivityCreated(savedInstanceState);
		}
		
		private class FamilyAdapter extends ArrayAdapter<DetailedFamily> implements Filterable  {
			public FamilyAdapter(ArrayList<DetailedFamily> families) {
				super(getActivity(), 0, families);
				data=families;
				filtered=families;
			}
			

			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_fragment, null);
					convertView.findViewById(R.id.list_item_checkbox).setVisibility(View.GONE);
				}
				
				DetailedFamily family = getItem(position);
				String family_id = family.getFamily_id();
				int num_children = DetailedRecordsStore.get(getActivity().getApplication()).getChildren(family_id).size();
				
				TextView familyTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				familyTitle.setText(family.getParent1_name());
				TextView subtitle = (TextView)convertView.findViewById(R.id.list_item_subtitle);
				if (num_children == 1)
					subtitle.setText(num_children + " Niño");
				else
					subtitle.setText(num_children + " Niños");
				
				return convertView;
				
			}
			public void notifyDataSetChanged() { 
		        super.notifyDataSetChanged(); 
		    } 
			public Filter getFilter() {
		        return new Filter() {

		            @SuppressWarnings("unchecked")
		                protected void publishResults(CharSequence constraint, FilterResults results) {
		                //Log.d(TAG, "**** PUBLISHING RESULTS for: " + constraint);
		            	
		                data = (ArrayList<DetailedFamily>)results.values;
		                notifyDataSetChanged();
		            	}
		            
		            protected FilterResults performFiltering(CharSequence constraint) {
		               // Log.d(TAG, "**** PERFORM FILTERING for: " + constraint);
		                constraint = constraint.toString().toLowerCase();
		               
		 
		                FilterResults results = new FilterResults();
		                if(constraint != null && constraint.toString().length() > 0) {
		                       
		                        ArrayList<DetailedFamily> filt = new ArrayList<DetailedFamily>();
		                        ArrayList<DetailedFamily> lData = new ArrayList<DetailedFamily>();
		                       
		                        synchronized (this) {
		                                lData.addAll(data);
		                    }
		                    filt.clear();
		                    for(int i = 0; i < lData.size(); i++) {
		                        DetailedFamily m = lData.get(i);
		                        if((m.getParent1_name().toLowerCase()).contains(constraint))
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
		                    }
		                }
		                
		                return results;
		            }
		        };
		    } 
			@Override
			public int getCount() {
				return data.size();
			}
			@Override
			public DetailedFamily getItem(int position){
				return data.get(position);
			}

		 
			@Override
		    public long getItemId(int position) {
		        return position;
		    }
		}

	}	

}
