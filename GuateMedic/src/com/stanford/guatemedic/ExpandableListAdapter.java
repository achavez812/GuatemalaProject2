package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter{
	
	private Activity context;
	private static String village;
	private ArrayList<String> families;
	private Map<String, ArrayList<String>> children;
	
	public ExpandableListAdapter(Activity context, String village, ArrayList<String> families, Map<String, ArrayList<String>> children) {
		this.context = context;
		this.children = children;
		this.families = families;
		ExpandableListAdapter.village = village;
	}
	
	public Object getChild(int familyPosition, int childPosition) {
		return children.get(families.get(familyPosition)).get(childPosition);
	}
	
	public void setChild(int familyPosition, int childPosition, String child) {
		children.get(families.get(familyPosition)).set(childPosition, child);
	}
	
	public long getChildId(int familyPosition, int childPosition) {
		return childPosition;
	}
	
	public View getChildView(final int familyPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		String child_id = (String)getChild(familyPosition, childPosition);
		String family_id = (String)getGroup(familyPosition);
		if (convertView == null) {
			Log.i("WTF", "Inflating new child_all_item layout.");
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.child_all_item, null);
		}
		
		final TextView textview = (TextView)convertView.findViewById(R.id.child_all_item_textview);
		final EditText edittext = (EditText)convertView.findViewById(R.id.child_all_item_edittext);
		final Button button = (Button)convertView.findViewById(R.id.child_all_item_button);
				
		if (child_id.equals("+Añadir un Nuevo Niño") && textview.getVisibility() == View.VISIBLE) {
			Log.i("WTF", "Initial 1  " + child_id);
			textview.setVisibility(View.VISIBLE);
			textview.setText("+Añadir un Nuevo Niño");
			edittext.setVisibility(View.GONE);
			button.setVisibility(View.INVISIBLE);
			textview.setBackgroundColor(context.getResources().getColor(R.color.white));
		} else if (child_id.equals("+Añadir un Nuevo Niño") && edittext.getVisibility() == View.VISIBLE && textview.getVisibility() == View.GONE) {
			Log.i("WTF", "Initial 2  " + child_id);
			/*
			edittext.setVisibility(View.VISIBLE);
			textview.setVisibility(View.GONE);
			button.setVisibility(View.VISIBLE);
			button.setText("Añadir");
			*/
			edittext.requestFocus();
		} else {
			Log.i("WTF", "Initial 3  " + child_id);
			DetailedChild child = DetailedRecordsStore.get(context).getChild(child_id);
			textview.setVisibility(View.VISIBLE);
			textview.setText(child.getName());
			edittext.setVisibility(View.GONE);
			button.setVisibility(View.VISIBLE);
			button.setText("Forma");
			if (child.hasVisit_in_progress()) {
				textview.setBackgroundColor(context.getResources().getColor(R.color.red));
			} else {
				textview.setBackgroundColor(context.getResources().getColor(R.color.white));
			}
		}
		
		textview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String child_id = (String)getChild(familyPosition, childPosition);
				String family_id = (String)getGroup(familyPosition);
				if (child_id.equals("+Añadir un Nuevo Niño")) {
					Log.i("WTF", "textview 1  " + child_id);
					edittext.setVisibility(View.VISIBLE);
					textview.setVisibility(View.GONE);
					button.setVisibility(View.VISIBLE);
					button.setText("Añadir");	
				} else {
					Log.i("WTF", "textview 2  " + child_id);
					DetailedChild dc = DetailedRecordsStore.get(context).getChild(child_id);
					if (!dc.hasVisit_in_progress()) {
						Intent i = new Intent(context, GraphActivity.class);
						i.putExtra("child_id", child_id);
						context.startActivity(i);
					} else {
						textview.setBackgroundColor(context.getResources().getColor(R.color.red));
					}
				}
			}
		});
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String child_id = (String)getChild(familyPosition, childPosition);
				String family_id = (String)getGroup(familyPosition);
				if (button.getText().toString().equals("Añadir")) {
					Log.i("WTF", "button 1");
					String new_child_name = edittext.getText().toString();
					DetailedChild dc = DetailedRecordsStore.get(context).startNewChild(village, family_id);
					dc.setName(new_child_name);
					setChild(familyPosition, childPosition, dc.getTemp_child_id());
					edittext.setVisibility(View.GONE);
					textview.setVisibility(View.VISIBLE);
					textview.setText(new_child_name);
					button.setText("Forma");
					if (dc.hasVisit_in_progress()) {
						textview.setBackgroundColor(context.getResources().getColor(R.color.red));
					}
				} else if (button.getText().toString().equals("Forma")) {
					Log.i("WTF", "button 2");
					DetailedChildVisit dcv = DetailedRecordsStore.get(context).startNewChildVisit(village, (String)getChild(familyPosition, childPosition));
					Intent i = new Intent(context, AddNewGeneralFormActivity.class);
					i.putExtra("child_id", dcv.getTemp_child_id());
					i.putExtra("community", village);
					context.startActivity(i);
				}
			}
		});
		
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
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.group_all_item, null);
		}
		
		TextView family_title = (TextView)convertView.findViewById(R.id.group_all_item_title);
		family_title.setTypeface(null, Typeface.BOLD);
		family_title.setText(family.getParent1_name());
		
		Button family_button = (Button)convertView.findViewById(R.id.group_all_item_button);
		family_button.setText("Ver");
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
