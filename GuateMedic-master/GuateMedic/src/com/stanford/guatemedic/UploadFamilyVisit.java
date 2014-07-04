package com.stanford.guatemedic;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadFamilyVisit {
	
	private String visit_id;
	private String family_id;
	private String temp_family_id;
	private String data;
	
	public UploadFamilyVisit(String visit_id, String family_id, String data) {
		this.visit_id = visit_id;
		this.family_id = family_id;
		temp_family_id = family_id;
		this.data = data;
	}
	
	public String getVisit_id() {
		return visit_id;
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
	
	public String getData() {
		return data;
	}

}
