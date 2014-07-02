package com.stanford.guatemedic;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DownloadLoginActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_login);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DownloadLoginFragment()).commit();
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DownloadLoginFragment extends Fragment {

		public DownloadLoginFragment() {
			
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_download_login, container,false);
			
			Button submit_button = (Button)rootView.findViewById(R.id.download_login_submit_button);
			submit_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText username_input = (EditText)getActivity().findViewById(R.id.download_login_username_edittext);
					String username = username_input.getText().toString();
					
					EditText password_input = (EditText)getActivity().findViewById(R.id.download_login_password_edittext);
					String password = password_input.getText().toString();
					
					try {
						JSONObject json_body = new JSONObject();
						json_body.put("username", username);
						json_body.put("password", password);
						new HandleDownloadLoginTask(true).execute(json_body.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			
			return rootView;
		}
		
		private class HandleDownloadLoginTask extends AsyncTask<String, Void, Void> {

			private boolean showLoading;
			private String auth_key;
			private boolean success;
			ProgressDialog dialog;
			
			public HandleDownloadLoginTask(boolean showLoading) {
				super();
				success = true;
				this.showLoading = showLoading;
				dialog = new ProgressDialog(getActivity());
				dialog.setMessage("Loading");
			}

			@Override
			protected Void doInBackground(String... params) {
				String json_body = params[0];
				String response = Utilities.postRequest("https://guatemedic.herokuapp.com/login", null, json_body);
				if (response != null) {
					try {
						JSONObject json_response = new JSONObject(response);
						if (json_response.getString("status").equals("success")) {
							auth_key = json_response.getString("auth_key");
							Map<String, String> headerMap = new HashMap<String, String>();
							headerMap.put("Authorization", auth_key);
							String data = Utilities.getRequest("https://guatemedic.herokuapp.com/profiles", headerMap);
							if (data == null) 
								success = false;
							else
								BasicRecordsStore.load(auth_key, data);
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
				if (auth_key == null) {
					Toast.makeText(getActivity(), "Invalid Login", Toast.LENGTH_LONG).show();
				} else if (!success) {
					Toast.makeText(getActivity(), "Download Failure", Toast.LENGTH_LONG).show();
				} else { //Success
					Intent intent = new Intent(getActivity(), DownloadVillageListActivity.class);
					startActivity(intent);
				} 
			}
		
		}
	}	
}
