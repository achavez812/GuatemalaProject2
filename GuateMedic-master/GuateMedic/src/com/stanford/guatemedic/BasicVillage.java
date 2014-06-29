package com.stanford.guatemedic;


public class BasicVillage {
	
	private String name;
	
	private boolean checkboxSelected;
	
	public BasicVillage(String name) {
		this.name = name;
		checkboxSelected = false;
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
