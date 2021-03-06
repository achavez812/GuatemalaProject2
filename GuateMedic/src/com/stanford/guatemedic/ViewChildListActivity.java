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
public class ViewChildListActivity extends ActionBarActivity {
	
	private static String family_id;
	private static String village;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_view_child_list);
		getActionBar().setHomeButtonEnabled(true);

		
		family_id = getIntent().getStringExtra("family_id");
		village = getIntent().getStringExtra("village");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, ViewChildListFragment.newInstance(family_id)).commit();
		}
	
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), ViewFamilyListActivity.class);
		i.putExtra("village_name", village);
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.addchild_button, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			return true;
		}
		if (id == R.id.action_addchild) {
			Intent i = new Intent(getApplication(), AddNewChildActivity.class);
			i.putExtra("family_id", family_id);
			startActivity(i);
			return true;
		}else if(id== R.id.viewfamilyinfor2) {
			Intent i= new Intent(getApplication(), view_family.class);
			i.putExtra("family_id", family_id);
			startActivity(i);
		} else if (id == R.id.familyvist) {
//			Intent i = new Intent(getApplication(), AddNewFamilyVisitActivity.class);
//			i.putExtra("family_id", family_id);
//			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
		*/
		return true;
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ViewChildListFragment extends ListFragment {
		
		private String family_id;
		private ChildAdapter adapter;
		public ViewChildListFragment() {
		}
		
		public static ViewChildListFragment newInstance(String family_id) {
			ViewChildListFragment f = new ViewChildListFragment();
		    // Supply index input as an argument.
		    Bundle args = new Bundle();
		    args.putString("family_id", family_id);
		    f.setArguments(args);

		    return f;
		}

		private ArrayList<DetailedChild> children;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	

			
			EditText inputSearch=(EditText)getActivity().findViewById(R.id.search_bar_children);
			inputSearch.addTextChangedListener(new TextWatcher() {
			     
			    @Override
			    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			        // When user changed the Text
			        	ViewChildListFragment.this.adapter.getFilter().filter(cs);   
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
		public void onResume() {
			super.onResume();
			((ChildAdapter)getListAdapter()).notifyDataSetChanged();
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			children = DetailedRecordsStore.get(getActivity()).getChildren(family_id);
			adapter = new ChildAdapter(children);
			setListAdapter(adapter);
			ListView lv = ((ListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).getListView();
			lv.setTextFilterEnabled(true);
			super.onActivityCreated(savedInstanceState);
		}
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//getActivity().setTitle(R.string.villages_title);
			
			Bundle args = getArguments();
			family_id = args.getString("family_id");
			
		
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			/*
			Intent intent=new Intent(getActivity(), view_patient.class);
			intent.putExtra("child_id", children.get(position).getChild_id());
			startActivity(intent);
			*/
		}
		
		private class ChildAdapter extends ArrayAdapter<DetailedChild> implements Filterable  {
			private ArrayList <DetailedChild>data;
			private ArrayList<DetailedChild>filtered;
			public ChildAdapter(ArrayList<DetailedChild> children) {
				super(getActivity(), 0, children);
				data=children;
				filtered=children;
			}
			public Filter getFilter() {
		        return new Filter() {

		            @SuppressWarnings("unchecked")
		                protected void publishResults(CharSequence constraint, FilterResults results) {
		                //Log.d(TAG, "**** PUBLISHING RESULTS for: " + constraint);
		            	
		                data = (ArrayList<DetailedChild>)results.values;
		                notifyDataSetChanged();
		            	}
		            
		            protected FilterResults performFiltering(CharSequence constraint) {
		               // Log.d(TAG, "**** PERFORM FILTERING for: " + constraint);
		                constraint = constraint.toString().toLowerCase();
		               
		 
		                FilterResults results = new FilterResults();
		                if(constraint != null && constraint.toString().length() > 0) {
		                       
		                        ArrayList<DetailedChild> filt = new ArrayList<DetailedChild>();
		                        ArrayList<DetailedChild> lData = new ArrayList<DetailedChild>();
		                       
		                        synchronized (this) {
		                                lData.addAll(data);
		                    }
		                    filt.clear();
		                    for(int i = 0; i < lData.size(); i++) {
		                        DetailedChild m = lData.get(i);
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
				DetailedChild child = getItem(position);
				String child_id = child.getChild_id();
				
				TextView childTitle = (TextView)convertView.findViewById(R.id.list_item_title);
				childTitle.setText(child.getName());
				return convertView;
				
			}
			@Override
			public int getCount() {
				return data.size();
			}
			
			@Override
			public DetailedChild getItem(int position){
				return data.get(position);
			}
		 
			@Override
		    public long getItemId(int position) {
		        return position;
		    }
		}
		
	}	


}
