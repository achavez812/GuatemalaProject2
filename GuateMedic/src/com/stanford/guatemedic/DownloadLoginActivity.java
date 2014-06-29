package com.stanford.guatemedic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
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
			Toast.makeText(getApplicationContext(), "Settings Click", Toast.LENGTH_LONG).show();
			return true;
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
			
			private ProgressDialog dialog;

			
			private boolean showLoading;
			private String auth_key;
			
			public HandleDownloadLoginTask(boolean showLoading) {
				super();
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
						if (json_response.getString("status").equals("success"))
							auth_key = json_response.getString("auth_key");
					} catch (JSONException e) {
						e.printStackTrace();
					}
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
				if (auth_key != null) { //Success
					Toast.makeText(getActivity(), "Success: " + auth_key, Toast.LENGTH_LONG).show();
					BasicRecordsStore.load(auth_key);
					Intent intent = new Intent(getActivity().getApplication(), DownloadVillageListActivity.class);
					startActivity(intent);
				} else { //Failure
					Toast.makeText(getActivity(), "Failure", Toast.LENGTH_LONG).show();
				}
			}
		
		}
	}	
}