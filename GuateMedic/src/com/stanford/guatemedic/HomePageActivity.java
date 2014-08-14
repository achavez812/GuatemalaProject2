package com.stanford.guatemedic;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class HomePageActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_empty);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new HomePageFragment()).commit();
		}
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
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_home_page, container,false);
			
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
				//Maybe only gray out sync when no data is stored
				//Should we allow synchronization when there stuff downloaded, but nothing to upload?
					//Way to ensure local data is current
					//Can add a 3rd subtitle
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
					if (uploadNeeded) {
						new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "Hay registros que necesitan ser sincronizado. Puedes descargar despues de sincronizando.", null);
					} else {		
						final Dialog login = new Dialog(getActivity());
						login.setCancelable(false);
						login.setCanceledOnTouchOutside(false);
			            login.setContentView(R.layout.login_dialog);
			            login.setTitle("Autorizaci—n");
			            final Button login_button = (Button)login.findViewById(R.id.login_dialog_submit_button);
			            final Button cancel_button = (Button)login.findViewById(R.id.login_dialog_cancel_button);
			            EditText editText = (EditText)login.findViewById(R.id.login_dialog_username);
			            EditText password_edittext = (EditText)login.findViewById(R.id.login_dialog_password);
			            password_edittext.setOnEditorActionListener(new OnEditorActionListener() {
			                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
			                    	login_button.setSoundEffectsEnabled(false);
			                    	login_button.performClick();
			                    }    
			                    return false;
			                }
			            });
			            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			                @Override
			                public void onFocusChange(View v, boolean hasFocus) {
			                    if (hasFocus) {
			                        login.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			                    }
			                }
			            });
			            editText.requestFocus();
			            login_button.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								 String username = ((EditText)login.findViewById(R.id.login_dialog_username)).getText().toString();
						         String password = ((EditText)login.findViewById(R.id.login_dialog_password)).getText().toString();
						         if (username.isEmpty() || password.isEmpty())
						        	 ((TextView)login.findViewById(R.id.login_dialog_error)).setVisibility(View.VISIBLE);
						         else {
						        	 Intent i = new Intent(getActivity(), ViewCommunitiesActivity.class);
						        	 startActivity(i);
						         }
							}
						});
			            
			            cancel_button.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								login.dismiss();
								
							}
						});
			            
			            login.show();	
			            int total_width_dp = 370;
			            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, total_width_dp, getResources().getDisplayMetrics());
			            login.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
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
							new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay una conexi—n a Internet. Cuando hay una conexi—n puedes sincronizar.", null);
						else {
							final Dialog login = new Dialog(getActivity());
							login.setCancelable(false);
							login.setCanceledOnTouchOutside(false);
				            login.setContentView(R.layout.login_dialog);
				            login.setTitle("Autorizaci—n");
				            final Button login_button = (Button)login.findViewById(R.id.login_dialog_submit_button);
				            final Button cancel_button = (Button)login.findViewById(R.id.login_dialog_cancel_button);
				            EditText editText = (EditText)login.findViewById(R.id.login_dialog_username);
				            EditText password_edittext = (EditText)login.findViewById(R.id.login_dialog_password);
				            password_edittext.setOnEditorActionListener(new OnEditorActionListener() {
				                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
				                    	login_button.setSoundEffectsEnabled(false);
				                    	login_button.performClick();
				                    }    
				                    return false;
				                }
				            });
				            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				                @Override
				                public void onFocusChange(View v, boolean hasFocus) {
				                    if (hasFocus) {
				                        login.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				                    }
				                }
				            });
				            editText.requestFocus();
				            login_button.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									 String username = ((EditText)login.findViewById(R.id.login_dialog_username)).getText().toString();
							         String password = ((EditText)login.findViewById(R.id.login_dialog_password)).getText().toString();
							         if (username.isEmpty() || password.isEmpty())
							        	 ((TextView)login.findViewById(R.id.login_dialog_error)).setVisibility(View.VISIBLE);
							         else {
							        	 
							         }
								}
							});
				            
				            cancel_button.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									login.dismiss();
									
								}
							});
				            
				            login.show();	
				            int total_width_dp = 370;
				            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, total_width_dp, getResources().getDisplayMetrics());
				            login.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
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
							new AlertDialogManager().showAlertDialog(getActivity(), "Alerta", "No hay una conexi—n a Internet. Cuando hay una conexi—n puedes descargar.", null);
						else {
							final Dialog login = new Dialog(getActivity());
							login.setCancelable(false);
							login.setCanceledOnTouchOutside(false);
				            login.setContentView(R.layout.login_dialog);
				            login.setTitle("Autorizaci—n");
				            final Button login_button = (Button)login.findViewById(R.id.login_dialog_submit_button);
				            final Button cancel_button = (Button)login.findViewById(R.id.login_dialog_cancel_button);
				            EditText editText = (EditText)login.findViewById(R.id.login_dialog_username);
				            EditText password_edittext = (EditText)login.findViewById(R.id.login_dialog_password);
				            password_edittext.setOnEditorActionListener(new OnEditorActionListener() {
				                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
				                    	login_button.setSoundEffectsEnabled(false);
				                    	login_button.performClick();
				                    }    
				                    return false;
				                }
				            });
				            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				                @Override
				                public void onFocusChange(View v, boolean hasFocus) {
				                    if (hasFocus) {
				                        login.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				                    }
				                }
				            });
				            editText.requestFocus();
				            login_button.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									 String username = ((EditText)login.findViewById(R.id.login_dialog_username)).getText().toString();
							         String password = ((EditText)login.findViewById(R.id.login_dialog_password)).getText().toString();
							         if (username.isEmpty() || password.isEmpty())
							        	 ((TextView)login.findViewById(R.id.login_dialog_error)).setVisibility(View.VISIBLE);
							         else {
							        	 try {
							        		 JSONObject json = new JSONObject();
							        		 json.put("username", username);
							        		 json.put("password", password);
							        		 new HandleDownloadAuthorizationTask(true, (TextView)login.findViewById(R.id.login_dialog_error)).execute(json.toString());
							        	 } catch (JSONException e) {
							        		 e.printStackTrace();
							        	 }
							         }
								}
							});
				            
				            cancel_button.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									login.dismiss();
									
								}
							});
				            
				            login.show();	
				            int total_width_dp = 370;
				            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, total_width_dp, getResources().getDisplayMetrics());
				            login.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
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
			TextView error_view;
			
			public HandleDownloadAuthorizationTask(boolean showLoading, TextView error_view) {
				super();
				this.showLoading = showLoading;
				this.error_view = error_view;
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
		        	error_view.setVisibility(View.VISIBLE);
					error_view.setText("No hay Internet.");
				} else if (auth_key == null) {
					error_view.setVisibility(View.VISIBLE);
					error_view.setText("Incorrecto. Trata otra vez.");
				} else if (!success) {
					error_view.setVisibility(View.VISIBLE);
					error_view.setText("Occuri— un problema. Trata otra vez.");
				} else { //Success
					Intent i = new Intent(getActivity(), DownloadCommunitiesActivity.class);
					startActivity(i);
				} 
			}		
		}
	}
}
