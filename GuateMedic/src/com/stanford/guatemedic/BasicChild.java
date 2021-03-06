package com.stanford.guatemedic;


public class BasicChild {
	
	private String child_id;
	private String name;
	
	private boolean checkboxSelected;
	
	public BasicChild(String child_id) {
		this.child_id = child_id;
		checkboxSelected = false;
	}

	public String getChild_id() {
		return child_id;
	}

	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheckboxSelected() {
		return checkboxSelected;
	}

	public void setCheckboxSelected(boolean checkboxSelected) {
		this.checkboxSelected = checkboxSelected;
	}

}
