package com.example.guatemal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class about_us extends Activity{
	SessionManagement session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_des);
		
		session = new SessionManagement(getApplicationContext());
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_logout:
	    	makelogout();
	        return true;
	    case R.id.action_home:
	    	Openhome_page();
	        return true;
	    case R.id.action_about:
	    	Openabout_page();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void makelogout(){
		final ProgressDialog ringProgressDialog = ProgressDialog.show(about_us.this, "",	"Loading ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						session.logoutUser();
					} catch (Exception e) {
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}
	
	public void Openabout_page(){
		Toast.makeText(getApplicationContext(), "You are on About Us page....", Toast.LENGTH_SHORT).show();
	}
	
	public void Openhome_page(){
		final ProgressDialog ringProgressDialog = ProgressDialog.show(about_us.this, "",	"Loading ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						Intent intent = new Intent(about_us.this, home.class);
						startActivity(intent);
					} catch (Exception e) {
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}

}
