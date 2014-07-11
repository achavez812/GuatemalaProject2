package com.example.guatemal;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class edit_patient extends Activity{
	
/*Date picker Variables*/
	private EditText mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
/*Date picker Variables ends here*/
    SessionManagement session;
	private static int RESULT_LOAD_IMAGE = 1;
	AlertDialogManager alert = new AlertDialogManager();
	Intent intent;
// Defining all the data base stuff
	DBController controller = new DBController(this);
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_patient);
		
		session = new SessionManagement(getApplicationContext());
		
		Intent objIntent = getIntent();
		String patientId = objIntent.getStringExtra("patientId");
		ImageView edit_patient_image = (ImageView)findViewById(R.id.edit_patient_image);
		
// Opening image gallery
		edit_patient_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
// Selecting all the patient information
		HashMap<String, String> patientdata = controller.getPatientInfo(patientId);
		
// Creating object for all text fields		
		EditText patient_name = (EditText)findViewById(R.id.view_patient_name); 
		EditText patient_dob = (EditText)findViewById(R.id.edit_patient_dob);
		EditText patient_gestation = (EditText)findViewById(R.id.edit_patient_gestation_time);
		EditText patient_household = (EditText)findViewById(R.id.edit_patient_household_no);
		EditText patient_sib_info = (EditText)findViewById(R.id.edit_patient_sib_info);
		EditText patient_breastfeeding = (EditText)findViewById(R.id.edit_patient_breastefeeding_info);
		EditText patient_father_occu = (EditText)findViewById(R.id.edit_patient_father_occu);
		EditText patient_image_edited1 = (EditText)findViewById(R.id.imagename);
		ImageView edit_pati_image = (ImageView)findViewById(R.id.edit_patient_image);
		mDateDisplay = (EditText)findViewById(R.id.edit_patient_dob);
		mDateDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
// Adding ptient info in that text fields
		if(patientdata.size()!=0) {
			patient_name.setText(patientdata.get("patientname"));
			RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
			if(patientdata.get("gender").equals("Male")){
				((RadioButton) findViewById(R.id.radiomale)).setChecked(true);
			}
			if(patientdata.get("gender").equals("Female")){
				((RadioButton) findViewById(R.id.radiofemale)).setChecked(true);
			}
			
// get selected radio button from radioGroup
			int selectedId = radioSexGroup.getCheckedRadioButtonId();
// find the radiobutton by returned id
			RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
			patient_dob.setText(patientdata.get("dob"));
			patient_gestation.setText(patientdata.get("gestation"));
			patient_household.setText(patientdata.get("household_no"));
			patient_sib_info.setText(patientdata.get("sib_info"));
			patient_breastfeeding.setText(patientdata.get("breast_info"));
			patient_father_occu.setText(patientdata.get("father_occu"));
			patient_image_edited1.setText(patientdata.get("image"));
			edit_pati_image.setImageBitmap(BitmapFactory.decodeFile(patientdata.get("image")));
		}
		
// creating update button object
		Button edit_patient = (Button)findViewById(R.id.edit_new_patient);
		edit_patient.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				
// Creating object of textfields				
				EditText patient_name = (EditText)findViewById(R.id.view_patient_name); 
				String patient_name_val = patient_name.getText().toString();
				EditText patient_dob = (EditText)findViewById(R.id.edit_patient_dob);
				String patient_dob_val = patient_dob.getText().toString();
				EditText patient_gestation = (EditText)findViewById(R.id.edit_patient_gestation_time);
				String patient_gestation_val = patient_gestation.getText().toString();
				EditText patient_household = (EditText)findViewById(R.id.edit_patient_household_no);
				String patient_household_val = patient_household.getText().toString();
				EditText patient_sib_info = (EditText)findViewById(R.id.edit_patient_sib_info);
				String patient_sib_info_val = patient_sib_info.getText().toString();
				EditText patient_breastfeeding = (EditText)findViewById(R.id.edit_patient_breastefeeding_info);
				String patient_breastfeeding_val = patient_breastfeeding.getText().toString();
				EditText patient_father_occu = (EditText)findViewById(R.id.edit_patient_father_occu);
				String patient_father_occu_val = patient_father_occu.getText().toString();
				
				//EditText patient_image_edited = (EditText)findViewById(R.id.imagename);
				//String patient_image_edited_val = patient_image_edited.getText().toString();
				
// Validating text fields if blank than it will alert user				
				if(patient_name_val.equals("") || patient_name_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient name.", false);
				}else if(patient_dob_val.equals("") || patient_dob_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient date of birth.", false);
				}else if(patient_gestation_val.equals("") || patient_gestation_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient gestation time.", false);
				}else if(patient_household_val.equals("") || patient_household_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient house hold number.", false);
				}else if(patient_sib_info_val.equals("") || patient_sib_info_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient sibling information.", false);
				}else if(patient_breastfeeding_val.equals("") || patient_breastfeeding_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient breastfeeding information.", false);
				}else if(patient_father_occu_val.equals("") || patient_father_occu_val.trim().equals("")){
					alert.showAlertDialog(edit_patient.this, "Problem Occured", "Please enter patient father's occupation.", false);
				}else{
					final ProgressDialog ringProgressDialog = ProgressDialog.show(edit_patient.this, "",	"Loading ...", true);
					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
									EditText patient_name = (EditText)findViewById(R.id.view_patient_name); 
									String patient_name_val = patient_name.getText().toString();
									EditText patient_dob = (EditText)findViewById(R.id.edit_patient_dob);
									String patient_dob_val = patient_dob.getText().toString();
									EditText patient_gestation = (EditText)findViewById(R.id.edit_patient_gestation_time);
									String patient_gestation_val = patient_gestation.getText().toString();
									EditText patient_household = (EditText)findViewById(R.id.edit_patient_household_no);
									String patient_household_val = patient_household.getText().toString();
									EditText patient_sib_info = (EditText)findViewById(R.id.edit_patient_sib_info);
									String patient_sib_info_val = patient_sib_info.getText().toString();
									EditText patient_breastfeeding = (EditText)findViewById(R.id.edit_patient_breastefeeding_info);
									String patient_breastfeeding_val = patient_breastfeeding.getText().toString();
									EditText patient_father_occu = (EditText)findViewById(R.id.edit_patient_father_occu);
									String patient_father_occu_val = patient_father_occu.getText().toString();
									EditText patient_image_edited = (EditText)findViewById(R.id.imagename);
									String patient_image_edited_val = patient_image_edited.getText().toString();
									
									HashMap<String, String> queryValues =  new  HashMap<String, String>();
									Intent objIntent = getIntent();
									String patientId = objIntent.getStringExtra("patientId");
									queryValues.put("patient_name", patient_name_val);
									RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
				// get selected radio button from radioGroup
									int selectedId = radioSexGroup.getCheckedRadioButtonId();
				// find the radiobutton by returned id
									RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
									queryValues.put("patient_gender", radioSexButton.getText().toString());
									queryValues.put("patient_dob", patient_dob_val);
									queryValues.put("patient_gestation", patient_gestation_val);
									queryValues.put("patient_household", patient_household_val);
									queryValues.put("patient_sibinfo", patient_sib_info_val);
									queryValues.put("patient_breastinfo", patient_breastfeeding_val);
									queryValues.put("patient_fatheroccu", patient_father_occu_val);
									queryValues.put("patient_image", patient_image_edited_val);
									queryValues.put("patientid", patientId);
				// Calling update function and passing all the vlues to that function					
									controller.updatePatient(queryValues);
									Intent intent = new Intent(edit_patient.this, home.class);
					    			startActivity(intent);
								} catch (Exception e) {
							}
							ringProgressDialog.dismiss();
						}
					}).start();
				}
			}
		});
	}
/*Image uploading code starts here*/	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			
			ImageView imageView = (ImageView)findViewById(R.id.edit_patient_image);
			imageView.setImageBitmap( decodeSampledBitmapFromResource(picturePath, 100, 100));
			
			Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
			double size=bitmap.getRowBytes()* bitmap.getHeight();
			for (int iterate=0; iterate<20; iterate++) {
				Log.d("yoyo",size+"yo");
			}
			
			//imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			EditText image_name = (EditText)findViewById(R.id.imagename);
			image_name.setText(picturePath);
		}
    
    
    }

 public static Bitmap decodeSampledBitmapFromResource(String picturePath,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(picturePath, options);
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
		Bitmap bitmap=BitmapFactory.decodeFile(picturePath,options);
		
		double size=bitmap.getRowBytes()* bitmap.getHeight();
		for (int iterate=0; iterate<20; iterate++) {
			Log.d("yoyo",size+"yoooooooloooooo");
		}
	    return BitmapFactory.decodeFile(picturePath,options);
	}
 public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
/*Image uploading code Ends here*/	
	private void updateDisplay() {
	        mDateDisplay.setText(
	            new StringBuilder()
	            		.append(mYear).append("-")
	            		.append(mDay).append("-")
	                    .append((mMonth +1<10?("0"+ (mMonth +1)):(mMonth +1))));
	    }
	private DatePickerDialog.OnDateSetListener mDateSetListener =
	    new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        };
	    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
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
			final ProgressDialog ringProgressDialog = ProgressDialog.show(edit_patient.this, "",	"Loading ...", true);
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
			final ProgressDialog ringProgressDialog = ProgressDialog.show(edit_patient.this, "",	"Loading ...", true);
			ringProgressDialog.setCancelable(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
							Intent intent = new Intent(edit_patient.this, about_us.class);
							startActivity(intent);
						} catch (Exception e) {
					}
					ringProgressDialog.dismiss();
				}
			}).start();
		}
		
		public void Openhome_page(){
			final ProgressDialog ringProgressDialog = ProgressDialog.show(edit_patient.this, "",	"Loading ...", true);
			ringProgressDialog.setCancelable(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
							Intent intent = new Intent(edit_patient.this, home.class);
							startActivity(intent);
						} catch (Exception e) {
					}
					ringProgressDialog.dismiss();
				}
			}).start();
		}

}
