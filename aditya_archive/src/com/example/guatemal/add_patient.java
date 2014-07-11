package com.example.guatemal;
import java.util.Calendar;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class add_patient extends android.support.v4.app.Fragment{
	private EditText mDateDisplay;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;
	String find_click ="";
	SessionManagement session;
	
	AlertDialogManager alert = new AlertDialogManager();
	private static int RESULT_LOAD_IMAGE = 1;
	Intent intent;
// On load it will define or include all database stuff
	DBController controller;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View patientListFragView = inflater.inflate(R.layout.add_patient, container, false);


		return patientListFragView;
	}
	
	
	
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	/*	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.spinner_dropdown_item) {

		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {

		        View v = super.getView(position, convertView, parent);
		        if (position == getCount()) {
		            ((TextView)v.findViewById(android.R.id.text1)).setText("");
		            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
		        }

		        return v;
		    }       

		    @Override
		    public int getCount() {
		        return super.getCount()-1; // you dont display last item. It is used as hint.
		    }

		};*/

		/*adapter.setDropDownViewResource(android.R.layout.spinner_dropdown_item);
		adapter.add("Item 1");
		adapter.add("Item 2");
		adapter.add("Hint to be displayed")*/

		//spinner.setSelection(adapter.getCount()); //display hint		
		
		
		
		
		
		Spinner spinnerOccu = (Spinner) this.getView().findViewById(R.id.edit_patient_father_occu);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapterOccu = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,
				getResources().getStringArray(R.array.employment_array)){
				    @Override
				    public int getCount() {
				        return super.getCount()-1;
				    }
			};
		// Specify the layout to use when the list of choices appears
		//two spinner_item res docs automatically were added before hand
		adapterOccu.setDropDownViewResource(R.layout.spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerOccu.setAdapter(adapterOccu);
		spinnerOccu.setSelection(adapterOccu.getCount());
		Spinner spinnerFeeding = (Spinner) this.getView().findViewById(R.id.edit_patient_breastefeeding_info);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapterFeeding = new ArrayAdapter<String>(getActivity(),
		       R.layout.spinner_item,getResources().getStringArray(R.array.br_array)){
			    @Override
			    public int getCount() {
			        return super.getCount()-1;
			    }
		};
	
		// Specify the layout to use when the list of choices appears
		//two spinner_item res docs automatically were added before hand
		adapterFeeding.setDropDownViewResource(R.layout.spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerFeeding.setAdapter(adapterFeeding);
		spinnerFeeding.setSelection(adapterFeeding.getCount());
		
		
		//create Sibling Information options
		String[] sib_array=new String[12];
		for (int sibIterate=0; sibIterate<11; sibIterate++){
			sib_array[sibIterate]=sibIterate+" siblings";
		}
		sib_array[11]="Sibling Information";
		Spinner spinnerSibInfo = (Spinner) this.getView().findViewById(R.id.edit_patient_sib_info);
		// Create an ArrayAdapter using the string array and a default spinner layout
		 ArrayAdapter<String> adapterSib = new ArrayAdapter<String>
		  (getActivity(),R.layout.spinner_item, sib_array){
			    @Override
			    public int getCount() {
			        return super.getCount()-1;
			    }
			};
		
			
		// Specify the layout to use when the list of choices appears
		//two spinner_item res docs automatically were added before hand
		adapterSib.setDropDownViewResource(R.layout.spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSibInfo.setAdapter(adapterSib);
		spinnerSibInfo.setSelection(adapterSib.getCount());
		//create No. in Household Information options
		String[] nohouse_array=new String[16];
		for (int houseIterate=0; houseIterate<15; houseIterate++){
			nohouse_array[houseIterate]="No. "+(houseIterate+1);
		}	
		nohouse_array[15]="Number in Household";
		Spinner spinnerHouseInfo = (Spinner) this.getView().findViewById(R.id.edit_patient_household_no);
		// Create an ArrayAdapter using the string array and a default spinner layout
		 ArrayAdapter<String> adapterHouse = new ArrayAdapter<String>
		  (getActivity(),R.layout.spinner_item, nohouse_array){
				    @Override
				    public int getCount() {
				        return super.getCount()-1;
				    }
		 };
		// Specify the layout to use when the list of choices appears
		//two spinner_item res docs automatically were added before hand
		adapterHouse.setDropDownViewResource(R.layout.spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerHouseInfo.setAdapter(adapterHouse);		
		spinnerHouseInfo.setSelection(adapterHouse.getCount());
		//create Gestation Information options
		String[] gest_array=new String[11];
		for (int gestIterate=0; gestIterate<10; gestIterate++){
			gest_array[gestIterate]=(gestIterate+33)+" weeks";
		}		
		gest_array[10]="Gestation Time";
		Spinner spinnerGestInfo = (Spinner) this.getView().findViewById(R.id.edit_patient_gestation_time);
		// Create an ArrayAdapter using the string array and a default spinner layout
		 ArrayAdapter<String> adapterGest = new ArrayAdapter<String>
		  (getActivity(),R.layout.spinner_item, gest_array){
			 
				    @Override
				    public int getCount() {
				        return super.getCount()-1;
				    }
				
		 };
		// Specify the layout to use when the list of choices appears
		//two spinner_item res docs automatically were added before hand
		adapterGest.setDropDownViewResource(R.layout.spinner_dropdown_item);
		// Apply the adapter to the spinner
		
		spinnerGestInfo.setAdapter(adapterGest);	
		spinnerGestInfo.setSelection(adapterGest.getCount());
		controller=new DBController(getActivity());
	
		
	
// On click of this button it will add new patient
		Button add_patient = (Button)this.getView().findViewById(R.id.edit_new_patient);

//add patient image
		ImageView add_patient_image = (ImageView)this.getView().findViewById(R.id.edit_patient_image);

// Opening image gallery
		add_patient_image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		

//display date dialog selector
			mDateDisplay = (EditText)this.getView().findViewById(R.id.edit_patient_dob);
			mDateDisplay.setOnClickListener(new View.OnClickListener() {
	            @SuppressWarnings("deprecation")
				public void onClick(View v) {
	            	find_click = "1";
	                getActivity().showDialog(DATE_DIALOG_ID);
	            }
	        });
			


	        final Calendar c = Calendar.getInstance();
	        mYear = c.get(Calendar.YEAR);
	        mMonth = c.get(Calendar.MONTH);
	        mDay = c.get(Calendar.DAY_OF_MONTH);
//	        updateDisplay();


		add_patient.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
//error validation process
				EditText patient_name = (EditText)getView().findViewById(R.id.view_patient_name); 
				String patient_name_val = patient_name.getText().toString();
				EditText patient_dob = (EditText)getView().findViewById(R.id.edit_patient_dob);
				String patient_dob_val = patient_dob.getText().toString();
				Spinner patient_gestation = (Spinner)getView().findViewById(R.id.edit_patient_gestation_time);
				String patient_gestation_val = patient_gestation.getSelectedItem().toString();
				Spinner patient_household = (Spinner)getView().findViewById(R.id.edit_patient_household_no);
				String patient_household_val = patient_household.getSelectedItem().toString();
				Spinner patient_sib_info = (Spinner)getView().findViewById(R.id.edit_patient_sib_info);
				String patient_sib_info_val = patient_sib_info.getSelectedItem().toString();
				Spinner patient_breastfeeding = (Spinner)getView().findViewById(R.id.edit_patient_breastefeeding_info);
				String patient_breastfeeding_val = patient_breastfeeding.getSelectedItem().toString();
				Spinner patient_father_occu = (Spinner)getView().findViewById(R.id.edit_patient_father_occu);
				String patient_father_occu_val = patient_father_occu.getSelectedItem().toString();
				EditText patient_image = (EditText)getView().findViewById(R.id.imagename);
				String patient_image_val = patient_image.getText().toString();			
				if(patient_name_val.equals("") || patient_name_val.trim().equals("")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient name.", false);
				}else if(patient_dob_val.equals("") || patient_dob_val.trim().equals("")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient date of birth.", false);
				}else if(patient_gestation_val.equals("Gestation Time") || patient_gestation_val.trim().equals("Gestation Time")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient gestation time.", false);
				}else if(patient_household_val.equals("Number in Household") || patient_household_val.trim().equals("Number in Household")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient house hold number.", false);
				}else if(patient_sib_info_val.equals("Sibling Information") || patient_sib_info_val.trim().equals("Sibling Information")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient sibling information.", false);
				}else if(patient_breastfeeding_val.equals("Breastfeeding Information") || patient_breastfeeding_val.trim().equals("Breastfeeding Information")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient breastfeeding information.", false);
				}else if(patient_father_occu_val.equals("Father Occupation") || patient_father_occu_val.trim().equals("Father Occupation")){
					alert.showAlertDialog(add_patient.this.getActivity(), "Problem Occured", "Please enter patient father's occupation.", false);
				}else{
					final ProgressDialog ringProgressDialog = ProgressDialog.show(add_patient.this.getActivity(), "",	"Loading ...", true);
					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
									EditText patient_name = (EditText)getView().findViewById(R.id.view_patient_name); 
									String patient_name_val = patient_name.getText().toString();
									EditText patient_dob = (EditText)getView().findViewById(R.id.edit_patient_dob);
									String patient_dob_val = patient_dob.getText().toString();
									Spinner patient_gestation = (Spinner)getView().findViewById(R.id.edit_patient_gestation_time);
									String patient_gestation_val = patient_gestation.getSelectedItem().toString();
									Spinner patient_household = (Spinner)getView().findViewById(R.id.edit_patient_household_no);
									String patient_household_val = patient_household.getSelectedItem().toString();
									Spinner patient_sib_info = (Spinner)getView().findViewById(R.id.edit_patient_sib_info);
									String patient_sib_info_val = patient_sib_info.getSelectedItem().toString();
									Spinner patient_breastfeeding = (Spinner)getView().findViewById(R.id.edit_patient_breastefeeding_info);
									String patient_breastfeeding_val = patient_breastfeeding.getSelectedItem().toString();
									Spinner patient_father_occu = (Spinner)getView().findViewById(R.id.edit_patient_father_occu);
									String patient_father_occu_val = patient_father_occu.getSelectedItem().toString();
									EditText patient_image = (EditText)getView().findViewById(R.id.imagename);
									String patient_image_val = patient_image.getText().toString();
									
									HashMap<String, String> queryValues =  new  HashMap<String, String>();
									queryValues.put("patient_name", patient_name_val);
									RadioGroup radioSexGroup = (RadioGroup) getView().findViewById(R.id.radioSex);
// get selected radio button from radioGroup
									int selectedId = radioSexGroup.getCheckedRadioButtonId();
// find the radio button by returned id
									RadioButton radioSexButton = (RadioButton) getView().findViewById(selectedId);
									queryValues.put("patient_gender", radioSexButton.getText().toString());
									queryValues.put("patient_dob", patient_dob_val);
									queryValues.put("patient_gestation", patient_gestation_val);
									queryValues.put("patient_household", patient_household_val);
									queryValues.put("patient_sibinfo", patient_sib_info_val);
									queryValues.put("patient_breastinfo", patient_breastfeeding_val);
									queryValues.put("patient_fatheroccu", patient_father_occu_val);
									queryValues.put("patient_image", patient_image_val);
// Calling insert function and passing all the values to that function
									controller.insertpatient(queryValues);
									home homey=(home)getActivity();
									homey.onSwitchView();
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
//Image uploading process has to include 

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	
			if (requestCode == RESULT_LOAD_IMAGE && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getActivity().getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				
				ImageView imageView = (ImageView) getView().findViewById(R.id.edit_patient_image);
				imageView.setImageBitmap( decodeSampledBitmapFromResource(picturePath, 100, 100));
				
				Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
				double size=bitmap.getRowBytes()* bitmap.getHeight();
				for (int iterate=0; iterate<20; iterate++) {
					Log.d("yoyo",size+"yo");
				}
				
				//imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
				EditText image_name = (EditText)getView().findViewById(R.id.imagename);
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
		 Log.d("find text",find_click);
		 if(find_click.equals("1")){
	        mDateDisplay.setText(
	            new StringBuilder()
	                    // Month is 0 based so add 1
	            		.append(mYear).append("-")
	            		.append(mDay).append("-")
	                    .append((mMonth +1<10?("0"+ (mMonth +1)):(mMonth +1))));
		 }else{
			 mDateDisplay.setText(
			            new StringBuilder()
			                    // Month is 0 based so add 1
			            		.append(mYear).append("-")
			            		.append(mDay).append("-")
			                    .append((mMonth +1<10?("0"+ (mMonth +1)):(mMonth +1))));
		 }
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

	
	    @SuppressLint("ValidFragment")
		public class MyDialogFragment extends DialogFragment {
		    public Dialog onCreateDialog(int id) {
		        switch (id) {
		        case DATE_DIALOG_ID:
		            return new DatePickerDialog(this.getActivity(),
		                        mDateSetListener,
		                        mYear, mMonth, mDay);
		        }
		        return null;
		    }
	}

}

