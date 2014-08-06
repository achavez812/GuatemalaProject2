package com.stanford.guatemedic;

import java.util.ArrayList;

public class BasicCommunity {
	
	private String name;
	private int num_families;
	private int num_children;
	private boolean checkbox_selected;
	private ArrayList<String> child_ids;
	
	public BasicCommunity(String name) {
		this.name = name;
		checkbox_selected = false;
		child_ids = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}

	public int getNum_families() {
		return num_families;
	}

	public void setNum_families(int num_families) {
		this.num_families = num_families;
	}

	public int getNum_children() {
		return num_children;
	}

	public void setNum_children(int num_children) {
		this.num_children = num_children;
	}

	public boolean isCheckbox_selected() {
		return checkbox_selected;
	}

	public void setCheckbox_selected(boolean checkbox_selected) {
		this.checkbox_selected = checkbox_selected;
	}
	
	public void addChild(String child_id) {
		child_ids.add(child_id);
	}
	
	public ArrayList<String> getChildren() {
		return child_ids;
	}

}
