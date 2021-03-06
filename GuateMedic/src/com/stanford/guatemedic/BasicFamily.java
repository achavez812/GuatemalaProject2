package com.stanford.guatemedic;



public class BasicFamily {
	
	private String family_id;
	private String parent1_name;
	
	private boolean checkboxSelected;
	
	public BasicFamily(String family_id) {
		this.family_id = family_id;
		checkboxSelected = false;
	}

	public String getFamily_id() {
		return family_id;
	}

	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}

	public String getParent1_name() {
		return parent1_name;
	}

	public void setParent1_name(String parent1_name) {
		this.parent1_name = parent1_name;
	}

	public boolean isCheckboxSelected() {
		return checkboxSelected;
	}

	public void setCheckboxSelected(boolean checkboxSelected) {
		this.checkboxSelected = checkboxSelected;
	}

}
