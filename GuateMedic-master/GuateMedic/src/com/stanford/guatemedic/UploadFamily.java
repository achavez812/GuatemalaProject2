package com.stanford.guatemedic;

import java.util.ArrayList;

public class UploadFamily {
	
	String family_id;
	String temp_family_id;
	ArrayList<String> visit_ids;
	ArrayList<String> child_ids;
	String data;
	
	public UploadFamily(String family_id, String data) {
		this.family_id = family_id;
		temp_family_id = family_id;
		this.data = data;
	}
	
	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}
	
	public String getFamily_id() {
		return family_id;
	}
	
	//Can only get temp_family_id (not set)
	public String getTemp_family_id() {
		return temp_family_id;
	}
	
	public void setVisit_id(ArrayList<String> visit_ids) {
		this.visit_ids = visit_ids;
	}
	
	public ArrayList<String> getVisit_ids() {
		return visit_ids;
	}
	
	public void setChild_ids(ArrayList<String> child_ids) {
		this.visit_ids = child_ids;
	}
	
	public ArrayList<String> getChild_ids() {
		return child_ids;
	}
	
	public String getData() {
		return data;
	}

}
