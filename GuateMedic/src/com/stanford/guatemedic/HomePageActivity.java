package com.stanford.guatemedic;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomePageActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_empty);
		
		String username = getIntent().getStringExtra("username");
		String password = getIntent().getStringExtra("password");
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, HomePageFragment.newInstance(username, password)).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), MainAuthorizationActivity.class);
		startActivity(i);
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
	
	public static class HomePageFragment extends Fragment {
		
		public HomePageFragment() {
			
		}
		
		public static Fragment newInstance(String username, String password) {
			HomePageFragment fragment = new HomePageFragment();
			
			Bundle args = new Bundle();
			args.putString("username", username);
			args.putString("password", password);
			fragment.setArguments(args);
			
			return fragment;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_home_page, container,false);
			
			//Check if there is a valid internet connection to the server and if any files need to be uploaded
			//We will only ask once all data for that day has been completed
			//If there is, have a dialog pop up, asking 
			
			final String username = getArguments().getString("username");
			final String password = getArguments().getString("password");
			//This could be changed to be the actual promoter name or it can just be grabbed from DetailedRecordsDatabase
			
			TextView title_textview = (TextView)rootView.findViewById(R.id.home_page_title_textview);
			title_textview.setText("Bienvenido " + username);
			
			TextView subtitle1_textview = (TextView)rootView.findViewById(R.id.home_page_subtitle1_textview);		
			TextView subtitle2_textview = (TextView)rootView.findViewById(R.id.home_page_subtitle2_textview);
			
			Button view_button = (Button)rootView.findViewById(R.id.home_page_view_button);
			Button sync_button = (Button)rootView.findViewById(R.id.home_page_sync_button);
			Button download_button = (Button)rootView.findViewById(R.id.home_page_download_button);
			
			final GuatemedicFileReader fr = new GuatemedicFileReader(getActivity().getApplication());
			final boolean uploadNeeded = fr.isUploadNeeded();
			final boolean dataStored = fr.hasDownloadedData();
			if (uploadNeeded) {
				download_button.setTextColor(Color.GRAY);
				subtitle1_textview.setText("Registros han sido creado."); //Records have been created.
				subtitle2_textview.setText("Sincronizar cuando hay Internet."); //Upload when there is Internet
			} else {
				sync_button.setTextColor(Color.GRAY);
				if (dataStored) {
					subtitle1_textview.setText("Hay registros descargado.");
					subtitle2_textview.setText("Puedes descargar nuevos cuando hay Internet.");
				} else {
					view_button.setTextColor(Color.GRAY);
					subtitle1_textview.setText("No hay registros descargado.");
					subtitle2_textview.setText("Puedes descargar cuando hay Internet.");
				}
			}
			
			view_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (!dataStored) {
						new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay registros descargado. Puedes descargar cuando hay Internet.", null);
					} else {
						Intent i = new Intent(getActivity(), ViewCommunitiesActivity.class);
						startActivity(i);
							
					}	
				}
			});
			
			sync_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (!uploadNeeded) {
						new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay registros para sincronizar.", null);
					} else {
						if (!HttpUtilities.hasInternetConnection(getActivity()))
							new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay una conexión a Internet. Cuando hay una conexión puedes sincronizar.", null);
						else {
							//Synchronize
						}
					}	
				}
			});
			
			download_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (uploadNeeded) {
						new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "Hay registros que necesitan ser sincronizado. Puedes descargar despues de sincronizando.", null);
					} else {
						if (!HttpUtilities.hasInternetConnection(getActivity()))
							new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay una conexión a Internet. Cuando hay una conexión puedes descargar.", null);
						else {
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
				dialog.setCancelable(false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setMessage("Los Datos Son De Carga");
			}
			
			@Override
			protected Void doInBackground(String... params) {
				String json_body = params[0];
				String response = HttpUtilities.postRequest("https://guatemedic.herokuapp.com/login", null, json_body);
				if (response != null && !response.equals("-1")) {
					try {
						JSONObject promoter_info = new JSONObject(json_body);
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
							else {
								BasicRecordsStore.load(auth_key, data);
								BasicRecordsStore.get().setUsername(promoter_info.getString("username"));
								BasicRecordsStore.get().setPassword(promoter_info.getString("password"));
							}
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
				if (showLoading) {
					dialog.show();
				}
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if (showLoading)
					dialog.dismiss();
				
				if (!has_internet) {
					new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay una conexión a Internet.", false);
				} else if (auth_key == null) {
					showAlertDialog(getActivity(), "Alerta", "La información ingresada es incorrecto. Por favor identificarse de nuevo.", false);
				} else if (!success) {
					new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "Occurió un problema. Trata otra vez.", false);
				} else { //Success
					Intent i = new Intent(getActivity(), DownloadCommunitiesActivity.class);
					startActivity(i);
				} 
			}		
		}
		
		public void showAlertDialog(Context context, String title, String message,
	            Boolean status) {
	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle(title);
			alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(getActivity(), MainAuthorizationActivity.class);
						startActivity(i);
					}
				});
			if (status != null)
				alertDialogBuilder.setIcon((status) ? R.drawable.success : R.drawable.denied);
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	    }
	}
}
