package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class BasicRecordsStore {
	
	private static BasicRecordsStore sBasicRecordsDatabase;
	
	private static ArrayList<BasicVillage> villages;
	private Map<String, ArrayList<BasicFamily>> families;
	private Map<String, ArrayList<BasicChild>> children;
	
	private String authKey;
	
	private BasicRecordsStore(String authKey) {
		this.authKey = authKey;
		villages = new ArrayList<BasicVillage>();
		families = new HashMap<String, ArrayList<BasicFamily>>();
		children = new HashMap<String, ArrayList<BasicChild>>();
		try {
			new FetchBasicRecordsTask(true).execute(authKey).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Client method to load the BasicRecordsDatabase
	 * Must be called prior to get()
	 *
	 * @param authKey Authentication key must be passed in
	 */
	public static void load(String authKey) {
		sBasicRecordsDatabase = new BasicRecordsStore(authKey);
	}
	
	/**
	 * Gets the BasicRecordsDatabase
	 * load() must have been previously called, or it will be null
	 *
	 * @return BasicRecordsStore 
	 */
	public static BasicRecordsStore get() {
		return sBasicRecordsDatabase;
	}
	
	public String getAuthKey() {
		return authKey;
	}
	
	public ArrayList<BasicVillage> getVillages() {
		return villages;
	}
	
	public BasicVillage getVillage(String village_name) {
		for (BasicVillage village : villages) {
			if (village.getName().equals(village_name))
				return village;
		}
		return null;
	}
	
	public ArrayList<BasicFamily> getFamilies(String village_name) {
		if (families.containsKey(village_name))
			return families.get(village_name);
		return null;
	}
	
	public BasicFamily getFamily(String village_name, String family_id) {
		for (BasicFamily family : getFamilies(village_name)) {
			if (family.getFamily_id().equals(family_id))
				return family;
		}
		return null;
	}
	
	public ArrayList<BasicChild> getChildren(String family_id) {
		if (children.containsKey(family_id))
			return children.get(family_id);
		return null;
	}
	
	public BasicChild getChild(String family_id, String child_id) {
		for (BasicChild child : getChildren(family_id)) {
			if (child.getChild_id().equals(child_id))
				return child;
		}
		return null;
	}
	
	//Handles performing the GET request and loading data into data structures
	private class FetchBasicRecordsTask extends AsyncTask<String, Void, Void> {
		
		private boolean showLoading;
		private boolean success;
		
		public FetchBasicRecordsTask(boolean showLoading) {
			super();
			this.showLoading = showLoading;
		}
		
		private void setFamilyInfo(BasicFamily f, JSONObject family) {
			try {
				if (family.has("parent1_name"))
					f.setParent1_name(family.getString("parent1_name"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		private void setChildInfo(BasicChild c, JSONObject child) {
			try {
				if (child.has("name"))
					c.setName(child.getString("name"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected Void doInBackground(String... params) {
			Map<String, String> headerMap = new HashMap<String, String>();
			headerMap.put("Authorization", params[0]); //Only one value
			String response = Utilities.getRequest("https://guatemedic.herokuapp.com/profiles", headerMap);
			if (response == null) {
				Log.i("WTF", "response is null");
				success = false;
			} else {
				Log.i("WTF", "response is not null");
				Log.i("WTF", response);
				try {
					JSONArray json_villages = new JSONArray(response);
					for (int i = 0; i < json_villages.length(); i++) {
						JSONObject json_village = json_villages.getJSONObject(i);
						String village_name = json_village.getString("city");
						BasicVillage v = new BasicVillage(village_name);
						villages.add(v);
						ArrayList<BasicFamily> family_array = new ArrayList<BasicFamily>();
						families.put(village_name, family_array);
						JSONArray json_families = json_village.getJSONArray("families");
						for (int j = 0; j < json_families.length(); j++) {
							JSONObject json_family = json_families.getJSONObject(j);
							String family_id = json_family.getString("family_id");
							BasicFamily f = new BasicFamily(family_id);
							setFamilyInfo(f, json_family);
							family_array.add(f);
							ArrayList<BasicChild> child_array = new ArrayList<BasicChild>();
							children.put(family_id, child_array);
							JSONArray json_children = json_family.getJSONArray("children");
							for (int k = 0; k < json_children.length(); k++) {
								JSONObject json_child = json_children.getJSONObject(k);
								String child_id = json_child.getString("child_id");
								BasicChild c = new BasicChild(child_id);
								setChildInfo(c, json_child);
								child_array.add(c);
							}
						}
					}
					success = true;
				} catch (JSONException e) {
					success = false;
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			if (showLoading) {
				//Display loading bar
			}
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (showLoading) {
				//Dismiss loading bar
			}
			if (!success) {
				
			}
		}
	}
	
}
