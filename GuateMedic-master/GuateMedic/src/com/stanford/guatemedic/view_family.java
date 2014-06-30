package com.stanford.guatemedic;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class view_family extends Activity{
	
	// All static variables
    static final String KEY_EXAMDATE = "exam_date";
    static final String REC_ID = "id";
    final Context context = this;
	Intent intent;
	

    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_family);
		
		Intent objIntent = getIntent();
		final String familyId = objIntent.getStringExtra("family_id");
		DetailedFamily family = DetailedRecordsStore.get(getApplication()).getFamily(familyId);
		

		
		
		
// Creating object of all text views		
		TextView father_name = (TextView)findViewById(R.id.father_name);
		TextView father_dob = (TextView)findViewById(R.id.father_dob);
		TextView mother_name = (TextView)findViewById(R.id.mother_name);
		TextView mother_dob=(TextView)findViewById(R.id.mother_dob);

		
		
		
//Populate Fields with Information
		/*String patient_rec_no_string=child.getChild_id()+"";
		patient_rec_no.setText(patient_rec_no_string);
		if(patient_rec_no_string.toString().trim().equals("") || patient_rec_no_string.toString().trim().equals("null")){
			patient_rec_no.setText("Not Set");
		}
		*/		
		Log.d("Rec No","ID="+family.getFamily_id());
		
		
		
		//Parent 1 Father Information
		String father_name_string=family.getParent1_name()+"";
		father_name.setText(father_name_string);
		if(father_name_string.toString().trim().equals("") || father_name_string.toString().trim().equals("null")){
			father_name.setText("Not Set");
		}
		String father_dob_string=family.getParent1_dob()+"";
		if(father_dob_string.toString().trim().equals("") || father_dob_string.toString().trim().equals("null")){
			father_dob.setText("Not Set");
		}else {
			father_dob.setText(father_dob_string);
		}
		
		
		
		
		
		//Parent 2--Mother Information
		String mother_name_string=family.getParent1_name()+"";
		mother_name.setText(mother_name_string);
		if(mother_name_string.toString().trim().equals("") || mother_name_string.toString().trim().equals("null")){
			mother_name.setText("Not Set");
		}
		String mother_dob_string=family.getParent1_dob()+"";
		if(mother_dob_string.toString().trim().equals("") || mother_dob_string.toString().trim().equals("null")){
			mother_dob.setText("Not Set");
		}else {
			mother_dob.setText(mother_dob_string);
		}
	
		
		
		
		
		
		
		
//Click Listeners		
		Button add_ex_info = (Button)findViewById(R.id.add_exam_info_family);
		add_ex_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(getApplication(), AddNewChildVisitActivity.class);
				//intent.putExtra("family_id", patientId);
				//startActivity(intent);
				Log.d("click","add_exam_info_family clicked");
			}
		});		
		
		
		Button makeedit = (Button)findViewById(R.id.edit_family_detail);
		makeedit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final ProgressDialog ringProgressDialog = ProgressDialog.show(view_family.this, "",	"Loading ...", true);
				ringProgressDialog.setCancelable(true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							/*	Intent objIntent = getIntent();
								String patientId = objIntent.getStringExtra("familyId");
								// Opening edit patient info activity				
								
								objIndent.putExtra("patientId", patientId);*/
								Intent  objIndent = new Intent(getApplicationContext(),MainActivity.class);	
								Log.d("familyId",familyId);
								startActivity(objIndent); 
							
							} catch (Exception e) {
						}
						ringProgressDialog.dismiss();
					}
				}).start();
			}
		});
	}


}
