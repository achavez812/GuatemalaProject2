package com.stanford.guatemedic;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DownloadAuthorizationActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_empty);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new DownloadAuthorizationFragment()).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		//MenuInflater inflater = getMenuInflater();
		//inflate.inflate(R.menu.menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	public static class DownloadAuthorizationFragment extends Fragment {
		
		public DownloadAuthorizationFragment() {
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_authorization, container,false);
			
			Button button = (Button)rootView.findViewById(R.id.authorization_button);
			button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					EditText username_field = (EditText)rootView.findViewById(R.id.authorization_username_edittext);
					String username = username_field.getText().toString();

					EditText password_field = (EditText)rootView.findViewById(R.id.authorization_password_edittext);
					String password = password_field.getText().toString();
					
					if (username.isEmpty() || password.isEmpty()) {
						new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "Por favor llene toda la información.", false);
					} else {
						//Check if valid login with backend
						//Go to new page
						try {
							JSONObject obj = new JSONObject();
							obj.put("username", username);
							obj.put("password", password);
							new HandleDownloadAuthorizationTask(true).execute(obj.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			});

			return rootView;
		}
		
		private class HandleDownloadAuthorizationTask extends AsyncTask<String, Void, Void> {
			
			private boolean showLoading;
			private boolean has_internet;
			private boolean success;
			private String auth_key;
			ProgressDialog dialog;
			
			public HandleDownloadAuthorizationTask(boolean showLoading) {
				super();
				this.showLoading = showLoading;
				has_internet = true;
				success = true;
				dialog = new ProgressDialog(getActivity());
				dialog.setMessage("Los Datos Son De Carga");
			}
			
			@Override
			protected Void doInBackground(String... params) {
				String json_body = params[0];
				String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/lgoin", null, json_body);
				if (response != null && !response.equals("-1")) {
					try {
						JSONObject json_response = new JSONObject(response);
						if (json_response.getString("status").equals("success")) {
							auth_key = json_response.getString("auth_key");
							Map<String, String> headerMap = new HashMap<String, String>();
							headerMap.put("Authorization", auth_key);
							String data = HttpUtilities.getRequest("https://guatemedic.herokuapp.com/profiles", headerMap);
							if (data == null) 
								has_internet = false;
							else if (data.equals("-1")) 
								success = false;
							else
								BasicRecordsStore.load(auth_key, data);
						} else {
							success = false;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (response == null) {
					success = false;
					has_internet = false;
				} else { //result is "-1"
					success = false;
				}
				return null;
			}
			
			@Override
			protected void onPreExecute() {
				if (showLoading)
					dialog.show();
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if (showLoading)
					dialog.dismiss();
				
				if (!has_internet) {
					new AlertDialogManager().showAlertDialog(getActivity(), "Alert", "No hay una conexión a Internet.", false);
				} else if (auth_key == null) {
					new AlertDialogManager().showAlertDialog(getActivity(), "Alert", "La información ingresada es incorrecto.", false);
				} else if (!success) {
					new AlertDialogManager().showAlertDialog(getActivity(), "Alert", "Occurió un problema. Trata otra vez.", false);
				} else { //Success
					Intent i = new Intent(getActivity(), DownloadCommunitiesActivity.class);
					startActivity(i);
				} 
			}
			
		}
	}
	
}
