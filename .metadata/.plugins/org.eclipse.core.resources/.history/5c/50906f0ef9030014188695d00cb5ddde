package com.stanford.guatemedic;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UploadRecordsStore {
	
	private static UploadRecordsStore sUploadRecordsStore;
	
	private ArrayList<UploadFamily> families;
	private ArrayList<UploadFamilyVisit> family_visits;
	private ArrayList<UploadChild> children;
	private ArrayList<UploadChildVisit> child_visits;
	
	private Context context;
	
	private GuatemedicReader gmr;
	private GuatemedicWriter gmw;
	
	private UploadRecordsStore(Context c) {
		context = c;
		
		families= new ArrayList<UploadFamily>();
		family_visits = new ArrayList<UploadFamilyVisit>();
		children = new ArrayList<UploadChild>();
		child_visits = new ArrayList<UploadChildVisit>();
		gmr = new GuatemedicReader(context);
		gmw = new GuatemedicWriter(context);
		
		processChildVisits();
		processFamilyVisits();
		processChildren();
		processFamilies();
		
	}
	
	public static void load(Context c) {
		sUploadRecordsStore = new UploadRecordsStore(c);
	}
	
	public void clear() {
		families = null;
		family_visits = null;
		children = null;
		child_visits = null;
	}
	
	public static UploadRecordsStore get(Context c) {
		if (sUploadRecordsStore == null)
			sUploadRecordsStore = new UploadRecordsStore(c);
		return sUploadRecordsStore;
	}
	
	
	
	public ArrayList<String> getChildVisits(String child_id) {
		ArrayList<String> ids = new ArrayList<String>();
		for (UploadChildVisit ucv : child_visits) {
			if (ucv.getTemp_child_id().equals(child_id))
					ids.add(ucv.getVisit_id());
		}
		return ids;
	}
	
	public ArrayList<String> getFamilyVisits(String family_id) {
		ArrayList<String> ids = new ArrayList<String>();
		for (UploadFamilyVisit ufv : family_visits) {
			if (ufv.getTemp_family_id().equals(family_id))
					ids.add(ufv.getVisit_id());
		}
		return ids;
	}
	
	public ArrayList<String> getChildren(String family_id) {
		ArrayList<String> ids = new ArrayList<String>();
		for (UploadChild uc : children) {
			if (uc.getTemp_family_id().equals(family_id))
				ids.add(uc.getTemp_child_id());
		}
		return ids;
	}
	
	public UploadFamily getFamily(String family_id) {
		for (UploadFamily uf : families) {
			if (uf.getTemp_family_id().equals(family_id))
				return uf;
		}
		return null;
	}
	
	public ArrayList<UploadFamily> getFamilies() {
		return families;
	}
	
	public ArrayList<UploadChild> getChildren() {
		return children;
	}
	
	public ArrayList<UploadFamilyVisit> getFamilyVisits() {
		return family_visits;
	}
	
	public ArrayList<UploadChildVisit> getChildVisits() {
		return child_visits;
	}
	
	public UploadChild getChild(String child_id) {
		for (UploadChild uc : children) {
			if (uc.getTemp_child_id().equals(child_id))
				return uc;
		}
		return null;
	}
	
	public UploadChildVisit getChildVisit(String visit_id) {
		for (UploadChildVisit ucv : child_visits) {
			if (ucv.getVisit_id().equals(visit_id))
				return ucv;
		}
		return null;
	}
	
	public UploadFamilyVisit getFamilyVisit(String visit_id) {
		for (UploadFamilyVisit ufv : family_visits) {
			if (ufv.getVisit_id().equals(visit_id))
				return ufv;
		}
		return null;
	}
	
	
	private void processChildVisits() {
		for (File f : gmr.getNewChildVisitFiles()) {
			String data = gmr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				String visit_id = obj.getString("visit_id");
				String child_id = obj.getString("child_id");
				String temp_child_id = obj.getString("temp_child_id");
				if (child_id.equals(temp_child_id)) { //HAS NOT BEEN UPLOADED YET
					obj.remove("visit_id");
					obj.remove("temp_child_id");
					UploadChildVisit ucv = new UploadChildVisit(visit_id, child_id, obj.toString());
					child_visits.add(ucv);
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processFamilyVisits() {
		for (File f : gmr.getNewFamilyVisitFiles()) {
			String data = gmr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				String visit_id = obj.getString("visit_id");
				String family_id = obj.getString("family_id");
				String temp_family_id = obj.getString("temp_family_id");
				if (family_id.equals(temp_family_id)) { //HAS NOT BEEN UPLOADED YET
					obj.remove("visit_id");
					obj.remove("temp_family_id");
					UploadFamilyVisit ufv = new UploadFamilyVisit(visit_id, family_id, obj.toString());
					family_visits.add(ufv);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processChildren() {
		for (File f : gmr.getNewChildFiles()) {
			String data = gmr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				String child_id = obj.getString("child_id");
				String temp_child_id = obj.getString("temp_child_id");
				String family_id = obj.getString("family_id");
				String temp_family_id = obj.getString("temp_family_id");
				if (child_id.equals(temp_child_id)) { //HAS NOT BEEN UPLOADED YET
					obj.remove("child_id");
					obj.remove("temp_family_id");
					UploadChild uc = new UploadChild(temp_family_id, child_id, obj.toString());
					children.add(uc);
					uc.setVisit_id(getChildVisits(temp_family_id));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void processFamilies() {
		for (File f : gmr.getNewFamilyFiles()) {
			String data = gmr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				String family_id = obj.getString("family_id");
				String temp_family_id = obj.getString("temp_family_id");
				if (family_id.equals(temp_family_id)) {
					obj.remove("family_id");
					UploadFamily uf = new UploadFamily(family_id, obj.toString());
					families.add(uf);
					uf.setVisit_ids(getFamilyVisits(temp_family_id));
					uf.setChild_ids(getChildren(temp_family_id));
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
