package com.stanford.guatemedic;

public class DetailedVillage {
	
	private String village_name;
	
	public DetailedVillage(String village_name) {
		this.village_name = village_name;
	}

	public String getName() {
		return village_name;
	}

	public void setName(String village_name) {
		this.village_name = village_name;
	}

}
