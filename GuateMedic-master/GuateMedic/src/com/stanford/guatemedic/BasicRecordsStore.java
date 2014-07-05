package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class BasicRecordsStore {
	
	private static BasicRecordsStore sBasicRecordsDatabase;
	
	private static ArrayList<BasicVillage> villages;
	private Map<String, ArrayList<BasicFamily>> families;
	private Map<String, ArrayList<BasicChild>> children;
	
	private String authKey;
	
	static Context context;
	
	
	private BasicRecordsStore(String auth_key, String data) throws InterruptedException, ExecutionException {
		this.authKey = auth_key;
		villages = new ArrayList<BasicVillage>();
		families = new HashMap<String, ArrayList<BasicFamily>>();
		children = new HashMap<String, ArrayList<BasicChild>>();
		loadData(data);
	}
	
	
	/**
	 * Client method to load the BasicRecordsDatabase
	 * Must be called prior to get()
	 *
	 * @param authKey Authentication key must be passed in
	 */
	public static void load(String auth_key, String data) {
		try {
			sBasicRecordsDatabase = new BasicRecordsStore(auth_key, data);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public BasicFamily getFamily(String family_id) {
		for (String village :families.keySet()) {
			for(BasicFamily family : families.get(village)) {
				if (family.getFamily_id().equals(family_id))
					return family;
			}
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
	
	private boolean villageHasChildren(JSONObject village) {
		try {
			JSONArray families = village.getJSONArray("families");
			for (int i = 0; i < families.length(); i++) {
				if (families.getJSONObject(i).getJSONArray("children").length() > 0)
					return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean familyHasChildren(JSONObject family) {
		try {
			return (family.getJSONArray("children").length() > 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean loadData(String data) {
		try {
			JSONArray json_villages = new JSONArray(data);
			for (int i = 0; i < json_villages.length(); i++) {
				JSONObject json_village = json_villages.getJSONObject(i);
				//if (villageHasChildren(json_village)) {
					String village_name = json_village.getString("city");
					BasicVillage v = new BasicVillage(village_name);
					villages.add(v);
					ArrayList<BasicFamily> family_array = new ArrayList<BasicFamily>();
					families.put(village_name, family_array);
					JSONArray json_families = json_village.getJSONArray("families");
					for (int j = 0; j < json_families.length(); j++) {
						JSONObject json_family = json_families.getJSONObject(j);
						//if (familyHasChildren(json_family)) {
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
						//}
					}
			//	}
			}
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
		
	
	
	public void DownloadData(Context context1, Context context2) {
		new HandleDownloadRecordsTask(true, context1, context2).execute();
	}
	
	private class HandleDownloadRecordsTask extends AsyncTask<Void, Void, Void> {
		
		ProgressDialog dialog;
		
		private boolean showLoading;
		private boolean success;
		
		private Context context1;
		private Context context2;
		
		public HandleDownloadRecordsTask(boolean showLoading, Context context1, Context context2) {
			super();
			this.showLoading = showLoading;
			this.context1 = context1;
			this.context2 = context2;
			dialog = new ProgressDialog(context1);
			dialog.setMessage("Loading");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				JSONArray json_array = new JSONArray();
				for (BasicVillage village : BasicRecordsStore.get().getVillages()) {
					if (village.isCheckboxSelected()) {
						for (BasicFamily family : BasicRecordsStore.get().getFamilies(village.getName())) {
							if (family.isCheckboxSelected()) {
								for (BasicChild child :BasicRecordsStore.get().getChildren(family.getFamily_id())) {
									if (child.isCheckboxSelected()) {
										json_array.put(child.getChild_id());
									}
								}
							}
						}
					}
				}
				JSONObject obj = new JSONObject();
				obj.put("child_ids", json_array);
				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("Authorization", BasicRecordsStore.get().getAuthKey());
				String response = Utilities.postRequest("https://guatemedic.herokuapp.com/records", headerMap, obj.toString());
				if (response == null) {
					success = false;
				} else {
					GuatemedicWriter gfw = new GuatemedicWriter(context2);
					if (gfw.saveDownloads(response)) {
						DetailedRecordsStore.load(context2);
						success = true;
					} else {
						success = false;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			if (showLoading) {
				//Display loading bar
				dialog.show();
			}
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (showLoading) {
				//Dismiss loading bar
				dialog.dismiss();
			}
			if (success) {
				Intent i = new Intent(context1, MainActivity.class);
				Toast.makeText(context2, "Successful Download", Toast.LENGTH_LONG).show();
				context1.startActivity(i);
			} 
		}

	
	}
	
}
