package com.stanford.guatemedic;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadChild {
	
	private String family_id;
	private String temp_family_id;
	private String child_id;
	private String temp_child_id;
	private ArrayList<String> visit_ids;
	private String data;
	
	public UploadChild(String family_id, String child_id, String data) {
		this.family_id = family_id;
		temp_family_id = family_id;
		this.child_id = child_id;
		temp_child_id = child_id;
		this.data = data;
	}
	
	public void setFamily_id(String family_id) {
		this.family_id = family_id;
		try {
			JSONObject obj = new JSONObject(data);
			obj.put("family_id", family_id);
			data = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getFamily_id() {
		return family_id;
	}
	
	//Can only get temp_family_id (not set)
	public String getTemp_family_id() {
		return temp_family_id;
	}
	
	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}
	
	public String getChild_id() {
		return child_id;
	}
	
	//Can only get temp_child_id (not set)
	public String getTemp_child_id() {
		return temp_child_id;
	}
	
	public void setVisit_id(ArrayList<String> visit_ids) {
		this.visit_ids = visit_ids;
	}
	
	public ArrayList<String> getVisit_ids() {
		return visit_ids;
	}
	
	public String getData() {
		return data;
	}

}
