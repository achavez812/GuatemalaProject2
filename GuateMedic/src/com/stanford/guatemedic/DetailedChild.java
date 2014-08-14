package com.stanford.guatemedic;

import java.util.ArrayList;

import android.util.Log;

public class DetailedChild {
	
	private boolean in_progress;
	
	private String family_id;
	private String temp_family_id;
	private String child_id;
	private String temp_child_id;
	
	private String name;
	private String dob; //date of birth
	private int gender;
	
	private int type_of_birth;
	private int num_children_in_same_pregnancy;
	private float months_gestated;
	private int received_prenatal_care;
	private float birth_weight;
	private float birth_height; //length
	private String youngest_sibling_dob;
	
	private String promoter_id;
	private String date_created;
	private String date_last_modified;
	
	private ArrayList<DetailedChildVisit> child_visits;
	
	public DetailedChild(String family_id, String child_id) {
		this.family_id = family_id;
		this.temp_family_id = family_id;
		this.child_id = child_id;
		this.temp_child_id = child_id;
		
		this.in_progress = false;
				
		child_visits = new ArrayList<DetailedChildVisit>();
	}	

	public boolean isIn_progress() {
		return in_progress;
	}

	public void setIn_progress(boolean in_progress) {
		this.in_progress = in_progress;
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

	public void setTemp_family_id(String temp_family_id) {
		this.temp_family_id = temp_family_id;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getType_of_birth() {
		return type_of_birth;
	}

	public void setType_of_birth(int type_of_birth) {
		this.type_of_birth = type_of_birth;
	}

	public int getNum_children_in_same_pregnancy() {
		return num_children_in_same_pregnancy;
	}

	public void setNum_children_in_same_pregnancy(int num_children_in_same_pregnancy) {
		this.num_children_in_same_pregnancy = num_children_in_same_pregnancy;
	}

	public float getMonths_gestated() {
		return months_gestated;
	}

	public void setMonths_gestated(float months_gestated) {
		this.months_gestated = months_gestated;
	}

	public int getReceived_prenatal_care() {
		return received_prenatal_care;
	}

	public void setReceived_prenatal_care(int received_prenatal_care) {
		this.received_prenatal_care = received_prenatal_care;
	}

	public float getBirth_weight() {
		return birth_weight;
	}

	public void setBirth_weight(float birth_weight) {
		this.birth_weight = birth_weight;
	}

	public float getBirth_height() {
		return birth_height;
	}

	public void setBirth_height(float birth_height) {
		this.birth_height = birth_height;
	}

	public String getYoungest_sibling_dob() {
		return youngest_sibling_dob;
	}

	public void setYoungest_sibling_dob(String youngest_sibling_dob) {
		this.youngest_sibling_dob = youngest_sibling_dob;
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

	public String getTemp_child_id() {
		return temp_child_id;
	}
	
	//Keeps visits ordered by date
	//Index 0: Most recent visit
	public void addChild_visit(DetailedChildVisit child_visit) {
		String visit_date = child_visit.getVisit_date(); //Date of visit being added
		Log.i("WTF", "New Visit Date: " + visit_date);
		for (int i = 0; i < child_visits.size(); i++) {
			Log.i("WTF", "  Visit Date: " + child_visits.get(i).getVisit_date());
			int value = visit_date.compareTo(child_visits.get(i).getVisit_date());
			if (value < 0) {
				child_visits.add(i, child_visit); //Shifts everything over
				return;
			} else if (value == 0) //Should never happen
				return;
		}
		child_visits.add(child_visit);
	}
	
	public boolean hasExisiting_Visit(String visit_date) {
		for (DetailedChildVisit dcv : child_visits) {
			if (visit_date.compareTo(dcv.getVisit_date()) == 0)
				return true;
		}
		return false;
	}
	
	public boolean hasVisit_in_progress() {
		ArrayList<DetailedChildVisit> visits = getChild_visits();
		if (!visits.isEmpty()) {
			DetailedChildVisit visit = visits.get(visits.size() - 1);
			return visit.isIn_progress();
		}
		return false;
	}
	
	public DetailedChildVisit getLastVisit() {
		ArrayList<DetailedChildVisit> visits = getChild_visits();
		return visits.get(visits.size() - 1);
	}
	
	public ArrayList<DetailedChildVisit> getChild_visits() {
		return child_visits;
	}

}
