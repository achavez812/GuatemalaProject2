package com.example.guatemal;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nativecss.NativeCSS;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import static com.nativecss.enums.RemoteContentRefreshPeriod.*;
public class MainActivity extends Activity {
	AlertDialogManager alert = new AlertDialogManager();
	//SessionManagement session;
	ProgressDialog barProgressDialog;
	Intent intent;
	TextView animalId;
	DBController controller = new DBController(this);
	// Onload it will create database stuff
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Calendar c = Calendar.getInstance();
		
		/*Date date1 = new Date();
		try {
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25");
			long differenceInMillis = date1.getTime() - date2.getTime();
			
			Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse(Integer.toString(differenceInMillis));
			Log.d("date",date3.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		/*session = new SessionManagement(getApplicationContext());
		HashMap<String, String> user = session.getUserDetails();
        String u_id = user.get(SessionManagement.KEY_ID);
        int newid=0;
        if(u_id==null){
        	newid = 0;
        }else{
        	newid =Integer.parseInt(u_id);
        }
		if(newid > 0){
        	Intent intent = new Intent(MainActivity.this, home.class);
			startActivity(intent);
			finish();
        }*/
		
		Button makelogin = (Button)findViewById(R.id.login);
		//Login button to call login acttivity
		
		makelogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "",	"Loading ...", true);
				ringProgressDialog.setCancelable(true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {*/
								EditText uname = (EditText)findViewById(R.id.username);
								EditText password = (EditText)findViewById(R.id.password);
// Taking user name from user
								String username = uname.getText().toString();
// Taking password from user
								String pwd = password.getText().toString();
								if(username.equals("") || username.trim().equals("")){
									alert.showAlertDialog(MainActivity.this, "Problem Occured", "Please enter username.", false);
								}else if(pwd.equals("") || pwd.trim().equals("")){
									alert.showAlertDialog(MainActivity.this, "Problem Occured", "Please enter password.", false);
								}else{
// calling Login function written in DB controller file and passing username and password arguments provided by user 
									int data = controller.MakeLogin(username,pwd);

									if(Integer.toString(data).equals("1")){
										Intent objIntent = new Intent(getApplicationContext(), home.class);
										startActivity(objIntent);
									}else{
										alert.showAlertDialog(MainActivity.this, "Problem Occured", "Please provide correct username and password.", false);
									}
								}
						/*} catch (Exception e) {

						}
						ringProgressDialog.dismiss();
					}
				}).start();*/
				
				//Log.e("text",Integer.toString(data));
			}
		});
	
        URL css;
		try {
			css = new URL("http://10.0.2.2:8000/styles.css");
			NativeCSS.styleWithCSS("styles.css",css,Never);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
}
