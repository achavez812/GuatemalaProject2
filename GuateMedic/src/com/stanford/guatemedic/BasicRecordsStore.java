package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
	
	private static BasicRecordsStore sBasicRecordsStore;
	
	private static ArrayList<BasicCommunity> communities;
	
	private String username;
	private String password;
	
	private String authKey;
	
	static Context context;
	
	private BasicRecordsStore(String auth_key, String data) throws InterruptedException, ExecutionException {
		this.authKey = auth_key;
		communities = new ArrayList<BasicCommunity>();
		loadData(data);
	}
	
	public static void load(String auth_key, String data) {
		try {
			sBasicRecordsStore = new BasicRecordsStore(auth_key, data);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static BasicRecordsStore get() {
		return sBasicRecordsStore;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public String getAuthKey() {
		return authKey;
	}

	public ArrayList<BasicCommunity> getCommunities() {
		return (ArrayList<BasicCommunity>)communities.clone();
	}
	
	public void checkAllCommunities() {
		for (BasicCommunity community : communities)
			community.setCheckbox_selected(true);
	}
	
	public void uncheckAllCommunities() {
		for (BasicCommunity community : communities)
			community.setCheckbox_selected(false);
	}
	
	public ArrayList<BasicCommunity> getMatchingCommunities(String str) {
		if (str.isEmpty())
			return communities;
		ArrayList<BasicCommunity> matchingCommunities = new ArrayList<BasicCommunity>();
		for (BasicCommunity community : communities) {
			if (community.getName().toLowerCase(Locale.getDefault()).contains(str.toLowerCase(Locale.getDefault())))
				matchingCommunities.add(community);
		}
		return matchingCommunities;
	}
	
	public ArrayList<BasicCommunity> getCheckedCommunities() {
		ArrayList<BasicCommunity> checkedCommunities = new ArrayList<BasicCommunity>();
		for (BasicCommunity community : communities) {
			if (community.isCheckbox_selected())
				checkedCommunities.add(community);
		}
		return checkedCommunities;
	}

	public BasicCommunity getCommunity(String name) {
		for (BasicCommunity community : communities) {
			if (community.getName().equals(name))
				return community;
		}
		return null;
	}
	
	private boolean loadData(String data) {
		try {
			JSONArray json_villages = new JSONArray(data);
			for (int i = 0; i < json_villages.length(); i++) {
				JSONObject json_village = json_villages.getJSONObject(i);
				String name = json_village.getString("city");
				BasicCommunity c = new BasicCommunity(name);
				communities.add(c);
				JSONArray json_families = json_village.getJSONArray("families");
				c.setNum_families(json_families.length());
				int num_children = 0;
				for (int j = 0; j < json_families.length(); j++) {
					JSONArray json_children = json_families.getJSONObject(j).getJSONArray("children");
					num_children += json_children.length();
					for (int k = 0; k < json_children.length(); k++)
						c.addChild(((JSONObject)json_children.get(k)).getString("child_id"));
				}
				c.setNum_children(num_children);
			}
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void DowloadData(Context activity_context, Context application_context) {
		new HandleDownloadRecordsTask(true, activity_context, application_context).execute();
	}
	
	private class HandleDownloadRecordsTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		
		private boolean showLoading;
		private boolean success;
		
		private Context activity_context;
		private Context application_context;
		
		public HandleDownloadRecordsTask(boolean showLoading, Context activity_context, Context application_context) {
			super();
			this.showLoading = showLoading;
			this.activity_context = activity_context;
			this.application_context = application_context;
			dialog = new ProgressDialog(activity_context);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setMessage("Los Datos Son De Carga");
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Map<String, String> headerMap = new HashMap<String, String>();
				headerMap.put("Authorization", BasicRecordsStore.get().getAuthKey());
				
				JSONArray arr = new JSONArray();
				for (BasicCommunity community : BasicRecordsStore.get().getCheckedCommunities()) {
					for (String child_id : community.getChildren()) 
						arr.put(child_id);
				}
				JSONObject obj = new JSONObject();
				obj.put("child_ids", arr);
				Log.i("WTF", obj.toString());
				String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/records", headerMap, obj.toString());
				if (response == null) {
					success = false;
				} else {
					GuatemedicFileWriter fw = new GuatemedicFileWriter(application_context);
					fw.eraseAll(); //Stuff needs to be uploaded first
					if (fw.saveDownloads(response)) {
						//load data in DetailedRecordsStore
						DetailedRecordsStore.load(application_context);

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
		
		@Override protected void onPreExecute() {
			if (showLoading) {
				dialog.show();
			}
		}
		
		@Override protected void onPostExecute(Void result) {
			if (showLoading) {
				dialog.dismiss();
			}
			
			if (success) {
				Toast.makeText(activity_context,  "DESCARGAR EXITOSA", Toast.LENGTH_LONG).show();
				Intent i = new Intent(activity_context, HomePageActivity.class);
				i.putExtra("username", BasicRecordsStore.get().getUsername());
				i.putExtra("password", BasicRecordsStore.get().getPassword());
				activity_context.startActivity(i);
				
			} else {
				Toast.makeText(activity_context,  "FAILURE", Toast.LENGTH_LONG).show();
			}
		}
	}

}
