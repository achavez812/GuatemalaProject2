package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UploadLoginActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_login);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new UploadLoginFragment()).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_download) {

		}
		return super.onOptionsItemSelected(item);
	}
	
	public class UploadLoginFragment extends Fragment {
		
		public UploadLoginFragment() {
			
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_upload_login, container,false);
			
			Button submit_button = (Button)rootView.findViewById(R.id.upload_login_submit_button);
			submit_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
					
					EditText username_input = (EditText)getActivity().findViewById(R.id.upload_login_username_edittext);
					String username = username_input.getText().toString();
					
					EditText password_input = (EditText)getActivity().findViewById(R.id.upload_login_password_edittext);
					String password = password_input.getText().toString();
					
					try {
						JSONObject json_body = new JSONObject();
						json_body.put("username", username);
						json_body.put("password", password);
						new HandleUploadLoginTask(true).execute(json_body.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			
			return rootView;
		}
		
		private class HandleUploadLoginTask extends AsyncTask<String, Void, Void> {

			private boolean showLoading;
			private String auth_key;
			private boolean success;
			
			ProgressDialog dialog;
			
			public HandleUploadLoginTask(boolean showLoading) {
				super();
				success = true;
				this.showLoading = showLoading;
				dialog = new ProgressDialog(getActivity());
				dialog.setMessage("Loading");
		
			}
			
			private void postFamilies() {
				try {
					JSONArray json_arr = new JSONArray();
					for (UploadFamily uf : UploadRecordsStore.get(getApplication()).getFamilies())  {
						json_arr.put(new JSONObject(uf.getData()));
					}
					if (json_arr.length() > 0) {
						Map<String, String> headerMap = new HashMap<String, String>();
						headerMap.put("Authorization", auth_key);
						String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/createFamily", headerMap, json_arr.toString());
						if (response != null) {
							JSONArray response_array = new JSONArray(response);
							for (int i = 0; i < response_array.length(); i++) {
								JSONObject obj = response_array.getJSONObject(i);
								String family_id = obj.getString("family_id");
								String temp_family_id = obj.getString("temp_family_id");
								UploadFamily uf = UploadRecordsStore.get(getApplication()).getFamily(temp_family_id);
								uf.setFamily_id(family_id);
								ArrayList<String> child_ids = UploadRecordsStore.get(getApplication()).getFamily(temp_family_id).getChild_ids();
								for (String child_id : child_ids) {
									UploadChild uc = UploadRecordsStore.get(getApplication()).getChild(child_id);
									uc.setFamily_id(family_id);
								}
								ArrayList<String> visit_ids = UploadRecordsStore.get(getApplication()).getFamily(temp_family_id).getVisit_ids();
								for (String visit_id : visit_ids) {
									UploadFamilyVisit ufv = UploadRecordsStore.get(getApplication()).getFamilyVisit(visit_id);
									ufv.setFamily_id(family_id);
								}
							}
						} else {
							success = false;
						}
					} 
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			private void postChildren() {
				try {
					JSONArray json_arr = new JSONArray();
					for (UploadChild uc : UploadRecordsStore.get(getApplication()).getChildren()) 
						json_arr.put(new JSONObject(uc.getData()));
					if (json_arr.length() > 0) {
						Map<String, String> headerMap = new HashMap<String, String>();
						headerMap.put("Authorization", auth_key);
						String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/createChild", headerMap, json_arr.toString());
						if (response != null) {
							JSONArray response_array = new JSONArray(response);
							for (int i = 0; i < response_array.length(); i++) {
								JSONObject obj = response_array.getJSONObject(i);
								String child_id = obj.getString("child_id");
								String temp_child_id = obj.getString("temp_child_id");
								UploadChild uc = UploadRecordsStore.get(getApplication()).getChild(temp_child_id);
								uc.setChild_id(child_id);
								ArrayList<String> visit_ids = UploadRecordsStore.get(getApplication()).getChildVisits(temp_child_id);
								for (String visit_id : visit_ids) {
									UploadChildVisit ucv = UploadRecordsStore.get(getApplication()).getChildVisit(visit_id);
									ucv.setChild_id(child_id);
								}
							} 
						} else {
							success = false;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			private void postVisits() {
				try {
					JSONArray json_arr = new JSONArray();
					for (UploadFamilyVisit ufv : UploadRecordsStore.get(getApplication()).getFamilyVisits())
						json_arr.put(new JSONObject(ufv.getData()));
					for (UploadChildVisit ucv : UploadRecordsStore.get(getApplication()).getChildVisits())
						json_arr.put(new JSONObject(ucv.getData()));
					if (json_arr.length() > 0) {
						Map<String, String> headerMap = new HashMap<String, String>();
						headerMap.put("Authorization", auth_key);
						String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/addVisits", headerMap, json_arr.toString());
						if (response != null) {
							//Success
							JSONObject obj = new JSONObject(response);
						} else {
							success = false;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			private void processUploads() {
				UploadRecordsStore.load(getActivity().getApplication());
				postFamilies();
				postChildren();
				postVisits();
			}

			@Override
			protected Void doInBackground(String... params) {
				String json_body = params[0];
				String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/login", null, json_body);
				if (response != null) {
					try {
						JSONObject json_response = new JSONObject(response);
						if (json_response.getString("status").equals("success")) {
							auth_key = json_response.getString("auth_key");
							processUploads();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
			
			@Override
			protected void onPreExecute() {
				if (showLoading) {
					dialog.show();
				}
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if (showLoading) {
					dialog.dismiss();
				}
				if (auth_key == null) { //Success
					Toast.makeText(getActivity(), "Invalid login", Toast.LENGTH_LONG).show();
				} else if (!success){ //Failure
					Toast.makeText(getActivity(), "Upload Failure", Toast.LENGTH_LONG).show();
				} else {
					Intent i = new Intent(getActivity(), UploadResultsActivity.class);
					startActivity(i);
				}
			}
		
		}
		
	}

}
