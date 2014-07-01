package com.stanford.guatemedic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class DetailedRecordsStore {
	
	private static DetailedRecordsStore sDetailedRecordsDatabase;
	
	//Declare data structures
	private ArrayList<DetailedVillage> villages;
	private Map<String, ArrayList<DetailedFamily>> families;
	private Map<String, ArrayList<DetailedChild>> children;
	
	private Context context;
	
	private GuatemedicReader gfr;

	private DetailedRecordsStore(Context c) {
		context = c;
		//Initialize data structures
		villages = new ArrayList<DetailedVillage>();
		families = new HashMap<String, ArrayList<DetailedFamily>>();
		children = new HashMap<String, ArrayList<DetailedChild>>();
		gfr = new GuatemedicReader(c);
		processDownloadedFiles();
		processNewFiles();
	}
	
	public static void load(Context c) {
		sDetailedRecordsDatabase = new DetailedRecordsStore(c);
	}
	
	public static DetailedRecordsStore get(Context c) {
		if (sDetailedRecordsDatabase == null)
			sDetailedRecordsDatabase = new DetailedRecordsStore(c);
		return sDetailedRecordsDatabase;
	}
	
	public ArrayList<DetailedVillage> getVillages() {
		return villages;
	}
	
	public DetailedVillage getVillage(String village_name) {
		for (DetailedVillage dv : villages) {
			if (dv.getVillage_name().equals(village_name))
				return dv;
		}
		return null;
	}	
	
	public ArrayList<DetailedFamily> getFamilies(String village_name) {
		return families.get(village_name);
	}
	
	public DetailedFamily getFamily(String village_name, String family_id) {
		for (DetailedFamily df : families.get(village_name)) {
			if (df.getTemp_family_id().equals(family_id)) //Or getFamily_id()
				return df;
		}
		return null;
	}
	
	public DetailedFamily getFamily(String family_id) {
		for (String village_name : families.keySet()) {
			for (DetailedFamily df : families.get(village_name)) {
				if (df.getTemp_family_id().equals(family_id)) //Or getFamily_id()
					return df;
			}
		}
		return null;
	}
	
	public ArrayList<DetailedChild> getChildren(String family_id) {
		return children.get(family_id);
	}
	
	public DetailedChild getChild(String family_id, String child_id) {
		for (DetailedChild dc : children.get(family_id)) {
			if (dc.getTemp_child_id().equals(child_id)) //Or getChild_id()
				return dc;
		}
		return null;
	}
	
	public DetailedChild getChild(String child_id) {
		for (String family_id : children.keySet()) {
			for (DetailedChild dc : children.get(family_id)) {
				if (dc.getTemp_child_id().equals(child_id)) //Or getChild_id()
					return dc;
			}
		}
		return null;
	}
	
	private String generateRandomId() {
		return UUID.randomUUID().toString();
	}
	
	//Creates a temporary id for family
	//Writes it to local storage
	//Adds it to DetailedRecordsStore
	public void addNewFamily(JSONObject obj) {
		try {
			String village = obj.getString("village");
			String random_id = generateRandomId();
			String current_date = Utilities.getTodayString();
			
			DetailedFamily df = new DetailedFamily(village, random_id);
			families.get(village).add(df);
			children.put(random_id, new ArrayList<DetailedChild>());
			
			obj.put("family_id", random_id);
			obj.put("temp_family_id", random_id);
			obj.put("date_created", current_date);
			obj.put("date_last_modified", current_date);
			obj.put("promoter_id", "NULL"); //Figure this out
			
			GuatemedicWriter gw = new GuatemedicWriter(context);
			if (gw.saveNewFamily(obj.toString()))
				Log.i("WTF", "Family save successful");
			
			parseFamily(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewChild(JSONObject obj) {
		try {
			String family_id = obj.getString("family_id");
			String random_id = generateRandomId();
			String current_date = Utilities.getTodayString();
			
			DetailedChild dc = new DetailedChild(family_id, random_id);
			children.get(family_id).add(dc);
			
			obj.put("child_id", random_id);
			obj.put("temp_child_id", random_id);
			obj.put("temp_family_id", family_id);
			obj.put("date_created", current_date);
			obj.put("date_last_modified", current_date);
			obj.put("promoter_id", "NULL"); //Figure this out
			
			GuatemedicWriter gw = new GuatemedicWriter(context);
			if (gw.saveNewChild(obj.toString()))
				Log.i("WTF", "Child save successful");
			
			parseChild(family_id, obj);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewChildVisit(JSONObject obj) {
		try {
			String child_id = obj.getString("child_id");
			String random_id = generateRandomId();
			String current_date = Utilities.getTodayString();
			
			DetailedChildVisit dcv = new DetailedChildVisit(child_id);
			
			obj.put("temp_child_id", child_id);
			obj.put("visit_id", random_id);
			obj.put("visit_date", current_date);
			obj.put("promoter_id", "NULL"); //Figure this out
			
			GuatemedicWriter gw = new GuatemedicWriter(context);
			if (gw.saveNewChildVisit(obj.toString()))
				Log.i("WTF", "Child Visit save successful");
			parseChildVisit(dcv, obj);
			getChild(child_id).addChild_visit(dcv);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void addNewFamilyVisit(JSONObject obj) {
		try {
			String family_id = obj.getString("child_id");
			String random_id = generateRandomId();
			String current_date = Utilities.getTodayString();
			
			DetailedFamilyVisit dfv = new DetailedFamilyVisit(family_id);
			
			obj.put("temp_family_id", family_id);
			obj.put("visit_id", random_id);
			obj.put("visit_date", current_date);
			obj.put("promoter_id", "NULL");
			
			GuatemedicWriter gw = new GuatemedicWriter(context);
			if (gw.saveNewFamilyVisit(obj.toString()))
				Log.i("WTF", "Family Visit save successful");
			parseFamilyVisit(dfv, obj);
			getFamily(family_id).addFamily_visit(dfv);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void parseFamily(JSONObject record) {
		try {
			String village_name = record.getString("village");
			String family_id = record.getString("family_id");
			DetailedFamily df = getFamily(village_name, family_id);
			if (df == null) //Error
				return;
			df.setPromoter_id(record.getString("promoter_id")); //Required Field
			df.setDate_created(record.getString("date_created")); //Required Field
			df.setDate_last_modified(record.getString("date_last_modified")); //Required Field
			if (record.has("parent1_name"))
				df.setParent1_name(record.getString("parent1_name"));
			if (record.has("parent1_dob"))
				df.setParent1_dob(record.getString("parent1_dob"));
			if (record.has("parent2_name"))
				df.setParent2_name(record.getString("parent2_name"));
			if (record.has("parent2_dob"))
				df.setParent2_dob(record.getString("parent2_dob"));
			if (record.has("promoter_id"))
				df.setPromoter_id(record.getString("promoter_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void addFamily(JSONObject record) {
		try {
			String village_name = record.getString("village");
			String family_id = record.getString("family_id");
			if (!familyRecordExists(village_name, family_id)) {
				families.get(village_name).add(new DetailedFamily(village_name, family_id));
				children.put(family_id, new ArrayList<DetailedChild>());
			}
			parseFamily(record);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void parseFamilyVisit(DetailedFamilyVisit dfv, JSONObject json) {
		try {
			dfv.setVisit_date(json.getString("visit_date"));
			if (json.has("parent1_marital_status")) 
				dfv.setParent1_marital_status(Integer.parseInt(json.getString("parent1_marital_status")));
			if (json.has("father_lives_with")) 
				dfv.setFather_lives_with(Integer.parseInt(json.getString("father_lives_with")));
			if (json.has("num_pregnancies")) 
				dfv.setNum_pregnancies(Integer.parseInt(json.getString("num_pregnancies")));
			if (json.has("num_children_alive"))
				dfv.setNum_children_alive(Integer.parseInt(json.getString("num_children_alive")));
			if (json.has("num_children_dead"))
				dfv.setNum_children_dead(Integer.parseInt(json.getString("num_children_dead")));
			if (json.has("how_died")) 
				dfv.setChildren_death_information(json.getString("how_died"));
			if (json.has("num_children_under_5"))
				dfv.setNum_children_under_5(Integer.parseInt(json.getString("num_children_under_5")));
			if (json.has("num_people_in_household"))
				dfv.setNum_people_in_household(Integer.parseInt(json.getString("num_people_in_household")));
			if (json.has("fathers_job"))
				dfv.setFathers_job(Integer.parseInt(json.getString("fathers_job")));
			if (json.has("igss"))
				dfv.setIGSS(Integer.parseInt(json.getString("igss")));
			if (json.has("promoter_id"))
				dfv.setPromoter_id(json.getString("promoter_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void addFamilyVisit(DetailedFamily family, JSONObject json_family_visit) {
		DetailedFamilyVisit dfv = new DetailedFamilyVisit(family.getTemp_family_id());
		parseFamilyVisit(dfv, json_family_visit);
		family.addFamily_visit(dfv);
	}
	
	//Stuff is commented out because currently using EditText for everything
	//Need to make sure its the given type or it will throw an error
	//For boolean type of question (0 for no answer, 1 for false, 2 for true)
	//Using int for things with fixed options (Need to figure out what integer will stand for)
	private void parseChild(String family_id, JSONObject json_child) {
		try {
			String child_id = json_child.getString("child_id");
			DetailedChild dc = getChild(family_id, child_id);
			if (dc == null) //Error
				return;
			if (json_child.has("name"))
				dc.setName(json_child.getString("name"));
			if (json_child.has("gender"))
				//dc.setGender(Integer.parseInt(json_child.getString("gender")));
			if (json_child.has("dob"))
				dc.setDob(json_child.getString("dob"));
			if (json_child.has("type_of_birth"))
				//dc.setType_of_birth(Integer.parseInt(json_child.getString("type_of_birth")));
			if (json_child.has("num_children_in_same_pregnancy"))
				//dc.setNum_children_in_same_pregnancy(Integer.parseInt(json_child.getString("num_children_in_same_pregnancy")));
			if (json_child.has("months_gestated"))
				//dc.setMonths_gestated(Float.parseFloat(json_child.getString("months_gestated")));
			if (json_child.has("prenatal_care")) 
				//dc.setPrenatal_care(Integer.parseInt(json_child.getString("prenatal_care")));
			if (json_child.has("birth_weight"))
				//dc.setBirth_weight(Float.parseFloat(json_child.getString("birth_weight")));
			if (json_child.has("birth_height"))
				//dc.setBirth_height(Float.parseFloat(json_child.getString("birth_height")));
			if (json_child.has("youngest_sibling_dob"))
				dc.setYoungest_sibling_dob(json_child.getString("youngest_sibling_dob"));
			if (json_child.has("date_created"))
				dc.setDate_created(json_child.getString("date_created"));
			if (json_child.has("date_last_modified"))
				dc.setDate_last_modified(json_child.getString("date_last_modified"));
			if (json_child.has("promoter_id"))
				dc.setPromoter_id(json_child.getString("promoter_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
	
	private void addChild(String family_id, JSONObject json_child) {
		try {
			String child_id = json_child.getString("child_id");
			if (!childRecordExists(family_id, child_id)) {
				children.get(family_id).add(new DetailedChild(family_id, child_id));
			}
			parseChild(family_id, json_child);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void parseChildVisit(DetailedChildVisit dcv, JSONObject json) {		
		try {
			dcv.setVisit_date(json.getString("visit_date"));
			if (json.has("received_all_vaccines"))
				dcv.setDid_receive_vaccinations(Integer.parseInt(json.getString("received_all_vaccines")));
			if (json.has("type_of_vaccines_received"))
				dcv.setVaccination_information(json.getString("types_of_vaccines_received"));
			if (json.has("chronic_disease_or_disability"))
				dcv.setHas_chronic_disease_or_disability(Integer.parseInt(json.getString("chronic_disease_or_disability")));
			if (json.has("type_of_chronic_disease_or_disability"))
				dcv.setChronic_disease_or_disability_information(json.getString("type_of_chronic_disease_or_disability"));
			if (json.has("currently_breastfed"))
				dcv.setIs_currently_breastfed(Integer.parseInt("currently_breastfed"));
			if (json.has("only_breastfed"))
				dcv.setIs_only_breastfed(Integer.parseInt(json.getString("only_breastfed")));
			if (json.has("how_long_only_breastfed"))
				dcv.setHow_long_only_breastfed(Float.parseFloat(json.getString("how_long_only_breastfed")));
			if (json.has("child_age_when_stoppped_breastfeeding"))
				dcv.setChild_age_when_stopped_breastfeeding(Float.parseFloat("child_age_when_stoppped_breastfeeding"));
			if (json.has("weight_in_kilos"))
				dcv.setWeight(Float.parseFloat("weight_in_kilos"));
			if (json.has("height_in_centimeters"))
				dcv.setHeight(Float.parseFloat("height_in_centimeters"));
			if (json.has("num_times_incaparina_past_week"))
				dcv.setNum_times_incaparina_past_week(Integer.parseInt(json.getString("num_times_incaparina_past_week")));
			if (json.has("num_times_vegetables_or_fruits_past_week"))
				dcv.setNum_times_vegetables_or_fruits_past_week(Integer.parseInt(json.getString("num_times_vegetables_or_fruits_past_week")));
			if (json.has("num_times_herbs_past_week"))
				dcv.setNum_times_herbs_past_week(Integer.parseInt(json.getString("num_times_herbs_past_week")));
			if (json.has("num_times_diarrhea_past_week"))
				dcv.setNum_times_diarrhea_past_week(Integer.parseInt(json.getString("num_times_diarrhea_past_week")));
			if (json.has("num_times_vomit_past_week"))
				dcv.setNum_times_vomit_past_week(Integer.parseInt(json.getString("num_times_vomit_past_week")));
			if (json.has("num_times_cough_past_week"))
				dcv.setNum_times_cough_past_week(Integer.parseInt(json.getString("num_times_cough_past_week")));
			if (json.has("num_times_fever_past_week"))
				dcv.setNum_times_fever_past_week(Integer.parseInt(json.getString("num_times_fever_past_week")));
			if (json.has("num_times_other_illness_past_week"))
				dcv.setNum_times_other_illness_past_week(Integer.parseInt(json.getString("num_times_other_illness_past_week")));
			if (json.has("illness_description")) //FIGURE THIS OUT
				dcv.setIllness_description(json.getString("illness_description"));
			if (json.has("age_last_received_deparasiting_medicine"))
				dcv.setAge_last_received_deparasiting_medicine(Float.parseFloat(json.getString("age_last_received_deparasiting_medicine")));
			//All the malnutrition grade scales
			//siblings older than 5
			if (json.has("receiving_supplements"))
				dcv.setReceiving_supplements(Integer.parseInt(json.getString("receiving_supplements")));
			if (json.has("why_receiving_supplements"))
				dcv.setWhy_receiving_supplements(Integer.parseInt("why_receiving_supplements"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void addChildVisit(DetailedChild child, JSONObject json_child_visit) {
		DetailedChildVisit dcv = new DetailedChildVisit(child.getTemp_child_id());
		parseChildVisit(dcv, json_child_visit);
		child.addChild_visit(dcv);
	}
	
	//village_name guaranteed to exist because of previous call to processVillage() 
		//Checks if there is a current family record
		// Returns false if one does not exist
	//If there is an existing record
		// Returns false if existing record date_last_modified is older than new_date
		// Returns true if existing record date_last_modified is the same or newer than new_date
	private boolean currentFamilyRecordExists(String village_name, String family_id, String new_date) {
		if (familyRecordExists(village_name, family_id)) {
			String existing_date = getFamily(village_name, family_id).getDate_last_modified();
			return (existing_date.compareTo(new_date) >= 0) ? true : false;
		}
		return false;
	}
	
	//Checks if there is a current family record
		// Returns true if there is an existing record
		// Returns false if there is not an existing record
	private boolean familyRecordExists(String village_name, String family_id) {
		for (DetailedFamily family : families.get(village_name)) {
			if (family.getFamily_id().equals(family_id)) 
				return true;
		}
		return false;
	}
	
	private boolean currentChildRecordExists(String family_id, String child_id, String new_date) {
		if (childRecordExists(family_id, child_id)) {
			String existing_date = getChild(family_id, child_id).getDate_last_modified();
			return (existing_date.compareTo(new_date) >= 0) ? true : false;
		}
		return false;
	}
	
	private boolean childRecordExists(String family_id, String child_id) {
		for (DetailedChild child : children.get(family_id)) {
			if (child.getChild_id().equals(child_id)) 
				return true;
		}
		return false;
	}

	private void processVillage(String village_name) {
		for (DetailedVillage village : villages) {
			if (village.getVillage_name().equals(village_name)) 
				return;
		}
		villages.add(new DetailedVillage(village_name));
		families.put(village_name, new ArrayList<DetailedFamily>());
	}
	
	private void processFamily(JSONObject record) {
		try {
			String village_name = record.getString("village");
			String family_id = record.getString("family_id");
			String date_last_modified = record.getString("date_last_modified");
			if (!currentFamilyRecordExists(village_name, family_id, date_last_modified)) {
				addFamily(record);
			}
			processFamilyVisits(getFamily(village_name, family_id), record.getJSONArray("visits"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void processFamilyVisits(DetailedFamily family, JSONArray json_family_visits) {
		if (family.getFamily_visits().size() < json_family_visits.length()) {
			try {
				for (int i = 0; i < json_family_visits.length(); i++) {
					JSONObject family_visit = json_family_visits.getJSONObject(i);
					String visit_date = family_visit.getString("visit_date");
					if (!family.hasExisiting_Visit(visit_date)) {
						addFamilyVisit(family, family_visit);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	//possibly don't need family_id
	private void processChild(String family_id, JSONObject json_child) {
		try {
			String child_id = json_child.getString("child_id");
			String date_last_modified = json_child.getString("date_last_modified");
			if (!currentChildRecordExists(family_id, child_id, date_last_modified)) {
				addChild(family_id, json_child);
			}
			processChildVisits(getChild(family_id, child_id), json_child.getJSONArray("visits"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void processChildVisits(DetailedChild child, JSONArray json_child_visits) {
		if (child.getChild_visits().size() < json_child_visits.length()) {
			try {
				for (int i = 0; i < json_child_visits.length(); i++) {
					JSONObject child_visit = json_child_visits.getJSONObject(i);
					String visit_date = child_visit.getString("visit_date");
					if (!child.hasExisiting_Visit(visit_date)) {
						addChildVisit(child, child_visit);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processChildren(String family_id, JSONArray json_children) {
		try {
			for (int child_cnt = 0; child_cnt < json_children.length(); child_cnt++) {
				JSONObject json_child = json_children.getJSONObject(child_cnt);
				processChild(family_id, json_child);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void processNewFamilyFiles() {
		Log.i("WTF", "Processing new family files");
		ArrayList<File> files = gfr.getNewFamilyFiles();
		for (File f : files) {
			Log.i("WTF", "	Process a file");
			String data = gfr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				addFamily(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void processNewChildFiles() {
		ArrayList<File> files = gfr.getNewChildFiles();
		for (File f : files) {
			String data = gfr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				addChild(obj.getString("family_id"), obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processNewFamilyVisitFiles() {
		ArrayList<File> files = gfr.getNewFamilyVisitFiles();
		for (File f : files) {
			String data = gfr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				addFamilyVisit(getFamily(obj.getString("family_id")), obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processNewChildVisitFiles() {
		ArrayList<File> files = gfr.getNewChildVisitFiles();
		for (File f : files) {
			String data = gfr.getStringData(f);
			try {
				JSONObject obj = new JSONObject(data);
				addChildVisit(getChild(obj.getString("child_id")), obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processNewFamilyModificationFiles() {
		
	}
	
	private void processNewChildModificationFiles() {
		
	}
	
	private void processNewFiles() {
		processNewFamilyFiles();
		processNewChildFiles();
		processNewFamilyVisitFiles();
		processNewChildVisitFiles();
		processNewFamilyModificationFiles();
		processNewChildModificationFiles();
	}
	
	private ArrayList<File> getDummyData() {
		ArrayList<File> files = new ArrayList<File>();
		files.add(new File("hi"));
		return files;
	}
	
	private String getDummyStringData(File f1) {
        try {
        	File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        	File the_file = null;
        	for (File file : f.listFiles()) {
        		if (file.getName().equals("dummy.txt")) 
        			the_file = file;
        	}
            BufferedReader br = new BufferedReader(new FileReader(the_file));
            String data = "";
            String line;
            while ((line = br.readLine()) != null)
            	data += line.trim();
      
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
	}
	
	private void processDownloadedFiles() {
		ArrayList<File> files = getDummyData(); //REMOVE LATER
		//ArrayList<File> files = gfr.getDownloadedFiles();
		for (File f : files) {
			String data = getDummyStringData(f); //REMOVE LATER
			//String data = gfr.getStringData(f);
			try {
				JSONObject json_data = new JSONObject(data);
				JSONArray records = json_data.getJSONArray("records");
				for (int family_cnt = 0; family_cnt < records.length(); family_cnt++) {
					JSONObject record = records.getJSONObject(family_cnt);
					processVillage(record.getString("village"));
					processFamily(record);
					processChildren(record.getString("family_id"), record.getJSONArray("children"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
