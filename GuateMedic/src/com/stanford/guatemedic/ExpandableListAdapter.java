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
	private Map<String, ArrayList<String>> children;
	private ArrayList<String> families;
	static private int family_focused = 0;
	static private boolean keyboard = false;
	private static String village;
	
	public ExpandableListAdapter(Activity context, String village, ArrayList<String> families, Map<String, ArrayList<String>> children) {
		this.context = context;
		this.children = children;
		this.families = families;
		this.village = village;
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
		final String child_id = (String)getChild(familyPosition, childPosition);
		final String family_id = (String)getGroup(familyPosition);
		if (convertView == null) {
			Log.i("WTF", "Inflating new child_all_item layout.");
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.child_all_item, null);
		}
		final TextView textview = (TextView)convertView.findViewById(R.id.child_all_item_textview);
		final EditText edittext = (EditText)convertView.findViewById(R.id.child_all_item_edittext);
		final Button button = (Button)convertView.findViewById(R.id.child_all_item_button);
		
		if (child_id.equals("+Añadir un Nuevo Niño")) {
			if (textview.getVisibility() == View.VISIBLE) {
				edittext.setVisibility(View.GONE);
				button.setVisibility(View.INVISIBLE);
				textview.setText("+Añadir un Nuevo Niño");
				
				textview.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (textview.getVisibility() == View.VISIBLE && textview.getText().toString().equals("+Añadir un Nuevo Niño")) {
							if (!keyboard) {
								InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
								keyboard = true;
							}
							textview.setVisibility(View.GONE);
							edittext.setVisibility(View.VISIBLE);
							button.setVisibility(View.VISIBLE);
							button.setText("Añadir");
							edittext.clearFocus();
							edittext.requestFocus();
						} else {
							
						}
					}
				});
			}
			
			button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (button.getText().toString().equals("Añadir")) {
						String new_child = edittext.getText().toString();
						DetailedChild dc = DetailedRecordsStore.get(context).startNewChild(village, family_id);
						dc.setName(new_child);
						setChild(familyPosition, childPosition, dc.getTemp_child_id());
						edittext.setVisibility(View.GONE);
						textview.setVisibility(View.VISIBLE);
						textview.setText(new_child);
						button.setText("Forma");
					} else if (button.getText().toString().equals("Forma")) {
						Intent i = new Intent(context, AddNewGeneralFormActivity.class);
						i.putExtra("child_id", child_id);
						context.startActivity(i);
					}
				}
			});
		} else {
			DetailedChild child = DetailedRecordsStore.get(context).getChild(child_id);
			final String child_name = child.getName();
			textview.setVisibility(View.VISIBLE);
			textview.setText(child_name);
			edittext.setVisibility(View.GONE);
			button.setVisibility(View.VISIBLE);
			button.setText("Forma");
			textview.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (child_id.substring(0,1).equals("-")) {
						Toast.makeText(context, "New Child Click", Toast.LENGTH_LONG).show();
					} else {
						Intent i = new Intent(context, GraphActivity.class);
						i.putExtra("child_id", child_id);
						context.startActivity(i);
					}
				}
			});
			
			button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(context, AddNewGeneralFormActivity.class);
					i.putExtra("child_id", child_id);
					context.startActivity(i);
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
