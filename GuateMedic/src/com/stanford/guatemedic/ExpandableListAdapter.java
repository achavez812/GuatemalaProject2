package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
	
	private Activity context;
	private Map<String, ArrayList<String>> children;
	private ArrayList<String> families;
	
	public ExpandableListAdapter(Activity context, ArrayList<String> families, Map<String, ArrayList<String>> children) {
		this.context = context;
		this.children = children;
		this.families = families;
	}
	
	public Object getChild(int familyPosition, int childPosition) {
		return children.get(families.get(familyPosition)).get(childPosition);
	}
	
	public long getChildId(int familyPosition, int childPosition) {
		return childPosition;
	}
	
	public View getChildView(final int familyPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final String child_id = (String)getChild(familyPosition, childPosition);
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.child_all_item, null);
		}
		TextView child_title = (TextView)convertView.findViewById(R.id.child_all_item_title);
		Button form_button = (Button)convertView.findViewById(R.id.child_all_item_button);
		RelativeLayout layout = (RelativeLayout)convertView.findViewById(R.id.child_all_item_layout);
		if (child_id.equals("+Add Child")) {
			form_button.setVisibility(View.GONE);
			child_title.setText(child_id);
			
			layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Toast.makeText(context, child_id, Toast.LENGTH_LONG).show();
				}
			});
		} else {
			form_button.setVisibility(View.VISIBLE);
			DetailedChild child = DetailedRecordsStore.get(context).getChild(child_id);
			child_title.setText(child.getName());
			
			layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(context, GraphActivity.class);
					i.putExtra("child_id", child_id);
					context.startActivity(i);
				}
			});
			
			form_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Toast.makeText(context, "Button Click", Toast.LENGTH_LONG).show();
					
				}
			});
		}
		return convertView;
	}
	
	public int getChildrenCount(int familyPosition) {
		return children.get(families.get(familyPosition)).size();
	}
	
	public Object getGroup(int familyPosition) {
		return families.get(familyPosition);
	}
	
	public int getGroupCount() {
		return families.size();
	}
	
	public long getGroupId(int familyPosition) {
		return familyPosition;
	}
	
	public View getGroupView(int familyPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final String family_id = (String)getGroup(familyPosition);
		DetailedFamily family = DetailedRecordsStore.get(context).getFamily(family_id);
		if (convertView == null) {
			//LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.group_all_item, null);
		}
		
		TextView family_title = (TextView)convertView.findViewById(R.id.group_all_item_title);
		family_title.setTypeface(null, Typeface.BOLD);
		family_title.setText(family.getParent1_name());
		
		Button family_button = (Button)convertView.findViewById(R.id.group_all_item_button);
		family_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(context, ViewFamilyActivity.class);
				i.putExtra("family_id", family_id);
				context.startActivity(i);
				
			}
		});
		return convertView;
	}
	
	public boolean hasStableIds() {
		return true;
	}
	
	public boolean isChildSelectable(int familyPosition, int childPosition) {
		return true;
	}
	
}
