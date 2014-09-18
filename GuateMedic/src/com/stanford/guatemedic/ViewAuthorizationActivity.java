package com.stanford.guatemedic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ViewAuthorizationActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		getActionBar().setHomeButtonEnabled(true);
		
		setContentView(R.layout.activity_empty);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new MainAuthorizationFragment()).commit();
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
	
	public static class MainAuthorizationFragment extends Fragment {
		
		public MainAuthorizationFragment() {
			
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
						//Need to check if valid
						//Store the promoter's information somewhere
						Intent i = new Intent(getActivity(), HomePageActivity.class);
						i.putExtra("username", username); //Can include this or just grab it from wherever i store it
						i.putExtra("password", password);
						startActivity(i);
					}
				}
			});

			return rootView;
		}
	}
}
