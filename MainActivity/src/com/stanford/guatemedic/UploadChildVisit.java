package com.stanford.guatemedic;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadChildVisit {
	String visit_id;
	String child_id;
	String temp_child_id;
	String data;
	
	public UploadChildVisit(String visit_id, String child_id, String data) {
		this.visit_id = visit_id;
		this.child_id = child_id;
		temp_child_id = child_id;
		this.data = data;
	}
	
	public String getVisit_id() {
		return visit_id;
	}
	
	public void setChild_id(String child_id) {
		this.child_id = child_id;
		try {
			JSONObject obj = new JSONObject(data);
			obj.put("child_id", child_id);
			data = obj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getChild_id() {
		return child_id;
	}
	
	//Can only get temp_child_id (not set)
	public String getTemp_child_id() {
		return temp_child_id;
	}
	
	public String getData() {
		return data;
	}
}
