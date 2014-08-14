package com.stanford.guatemedic;

import java.util.ArrayList;

public class DetailedFamily {
	
	private boolean in_progress;
	
	private String village_name;
	private String family_id;
	private String temp_family_id; //Read only, set in constructor
	
	private String parent1_name;
	private String parent1_dob;
	
	private String parent2_name;
	private String parent2_dob;
	
	private String promoter_id;
	private String date_created;
	private String date_last_modified;
	
	//Declare data structure for storing family visits
	private ArrayList<DetailedFamilyVisit> family_visits;

	public DetailedFamily(String village_name, String family_id) {
		this.village_name = village_name;
		this.family_id = family_id;
		this.temp_family_id = family_id;
		
		this.in_progress = true;
		
		family_visits = new ArrayList<DetailedFamilyVisit>();
	}
	
	public boolean isIn_progress() {
		return in_progress;
	}

	public void setIn_progress(boolean in_progress) {
		this.in_progress = in_progress;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public String getFamily_id() {
		return family_id;
	}

	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}

	public String getTemp_family_id() {
		return temp_family_id;
	}

	public String getParent1_name() {
		return parent1_name;
	}
	
	public void setParent1_name(String parent1_name) {
		this.parent1_name = parent1_name;
	}

	public String getParent1_dob() {
		return parent1_dob;
	}

	public void setParent1_dob(String parent1_dob) {
		this.parent1_dob = parent1_dob;
	}

	public String getParent2_name() {
		return parent2_name;
	}
	
	public void setParent2_name(String parent2_name) {
		this.parent2_name = parent2_name;
	}

	public String getParent2_dob() {
		return parent2_dob;
	}

	public void setParent2_dob(String parent2_dob) {
		this.parent2_dob = parent2_dob;
	}
	
	public String getPromoter_id() {
		return promoter_id;
	}
	
	public void setPromoter_id(String promoter_id) {
		this.promoter_id = promoter_id;
	}

	public String getDate_created() {
		return date_created;
	}

	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}

	public String getDate_last_modified() {
		return date_last_modified;
	}

	public void setDate_last_modified(String date_last_modified) {
		this.date_last_modified = date_last_modified;
	}
	
	//Keeps visits ordered by date
	//Index 0: Most recent visit
	public void addFamily_visit(DetailedFamilyVisit family_visit) {
		String visit_date = family_visit.getVisit_date(); //Date of visit being added
		for (int i = 0; i < family_visits.size(); i++) {
			int value = visit_date.compareTo(family_visits.get(i).getVisit_date());
			if (value < 0) {
				family_visits.add(i, family_visit); //Shifts everything over
				return;
			} else if (value == 0) //Should never happen
				return;
		}
		family_visits.add(family_visit);
	}
	
	public boolean hasExisiting_Visit(String visit_date) {
		for (DetailedFamilyVisit dfv : family_visits) {
			if (visit_date.compareTo(dfv.getVisit_date()) == 0)
				return true;
		}
		return false;
	}
	
	public ArrayList<DetailedFamilyVisit> getFamily_visits() {
		return family_visits;
	}
	
}
