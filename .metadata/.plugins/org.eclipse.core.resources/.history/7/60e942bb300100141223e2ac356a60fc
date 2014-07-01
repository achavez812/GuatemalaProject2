package com.stanford.guatemedic;

import java.util.ArrayList;

public class UploadChild {
	
	String child_id;
	String temp_child_id;
	ArrayList<String> visit_ids;
	String data;
	
	public UploadChild(String child_id, String data) {
		this.child_id = child_id;
		temp_child_id = child_id;
		visit_ids = new ArrayList<String>();
		this.data = data;
	}
	
	public void setchild_id(String child_id) {
		this.child_id = child_id;
	}
	
	public String getchild_id() {
		return child_id;
	}
	
	//Can only get temp_child_id (not set)
	public String getTemp_child_id() {
		return temp_child_id;
	}
	
	public void addVisit_id(String visit_id) {
		visit_ids.add(visit_id);
	}
	
	public ArrayList<String> getVisit_ids() {
		return visit_ids;
	}
	
	public String getData() {
		return data;
	}

}
