package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class view_patient extends ActionBarActivity{
	private TextView mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;
    private boolean isExpanded=false;
    
    static final int DATE_DIALOG_ID = 0;
    AlertDialogManager alert = new AlertDialogManager();
// All static variables
   //SessionManagement session;
    static final String KEY_EXAMDATE = "exam_date";
    static final String KEY_WEIGHT = "weight";
    static final String KEY_HEIGHT = "height";
    static final String REC_ID = "id";
    final Context context = this;
	Intent intent;
	
	
// Defining all database stuff
	DBController controller = new DBController(this);
	ListView list;
    LazyAdapterExam adapter;
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nfc_stuff_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_nfc_button) {
			Intent i = new Intent(getApplication(), NFCActivity.class);
			i.putExtra("child_id", getIntent().getStringExtra("child_id"));
			startActivity(i);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_patient_des);
		
		Intent objIntent = getIntent();
		final String patientId = objIntent.getStringExtra("child_id");

		plot_weight_graph();
		plot_height_graph();
		
		
		
		Button add_family_visit= (Button)findViewById(R.id.add_family_visits_cont);
		add_family_visit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DetailedChild child = DetailedRecordsStore.get(getApplication()).getChild(getIntent().getStringExtra("child_id"));
				Intent intent = new Intent(getApplication(), AddNewFamilyVisitActivity.class);
				intent.putExtra("family_id", child.getFamily_id());
				startActivity(intent);
				//Log.d("click","add_exam_info_family clicked");
			}
		});
		
		Button view_family_info= (Button)findViewById(R.id.view_family_info_cont);
		view_family_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DetailedChild child = DetailedRecordsStore.get(getApplication()).getChild(getIntent().getStringExtra("child_id"));
				Intent intent = new Intent(getApplication(), view_family.class);
				intent.putExtra("family_id", child.getFamily_id());
				startActivity(intent);
				//Log.d("click","add_exam_info_family clicked");
			}
		});
		
		
		Button add_ex_info = (Button)findViewById(R.id.add_exam_info);
		add_ex_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplication(), AddNewChildVisitActivity.class);
				intent.putExtra("child_id", patientId);
				startActivity(intent);
				/*
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.add_exam_det);
				dialog.setTitle("Add Exam Details");
				dialog.show();
				mDateDisplay = (TextView) dialog.findViewById(R.id.exam_date);
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
				Button dialogButton = (Button) dialog.findViewById(R.id.add_examinfo);
				
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText exam_date = (EditText)dialog.findViewById(R.id.exam_date);
						EditText exam_weight = (EditText)dialog.findViewById(R.id.patientweight);
						EditText exam_height = (EditText)dialog.findViewById(R.id.patientheight);
						if(exam_date.getText().toString().equals("") || exam_date.getText().toString().trim().equals("")){
							alert.showAlertDialog(view_patient.this, "Problem Occured", "Please enter exam date.", false);
						}else if(exam_weight.getText().toString().equals("") || exam_weight.getText().toString().trim().equals("")){
							alert.showAlertDialog(view_patient.this, "Problem Occured", "Please enter patient weight.", false);
						}else if(exam_height.getText().toString().equals("") || exam_height.getText().toString().trim().equals("")){
							alert.showAlertDialog(view_patient.this, "Problem Occured", "Please enter patient height.", false);
						}else{
							Intent objIntent = getIntent();
							String patientId = objIntent.getStringExtra("patientId");
							HashMap<String, String> queryValues =  new  HashMap<String, String>();
							queryValues.put("exam_date", exam_date.getText().toString());
							queryValues.put("weight", exam_weight.getText().toString());
							queryValues.put("height", exam_height.getText().toString());
							queryValues.put("patient_id", patientId);
							controller.insert_specific_patient_exam_info(queryValues);
							dialog.dismiss();
							finish();
							startActivity(getIntent());
						}
					}
				});
				*/
			}
		});
		
		
	
		// Selecting patient information
		//HashMap<String, String> patientdata = controller.getPatientInfo(patientId);
		
		// Creating object of all text views		
		TextView patient_name = (TextView)findViewById(R.id.view_patient_name);
		//TextView patient_rec_no = (TextView)findViewById(R.id.record_no);
		TextView patient_gender = (TextView)findViewById(R.id.gender);
		TextView patient_gestation = (TextView)findViewById(R.id.gestation_time);
		TextView patient_t_of_b=(TextView)findViewById(R.id.type_of_birth);
		//TextView patient_household = (TextView)findViewById(R.id.household_no);
		//TextView patient_sib_info = (TextView)findViewById(R.id.sibling_info);
		//TextView patient_breastfeeding = (TextView)findViewById(R.id.breastfeed_info);
		//TextView patient_father_occu = (TextView)findViewById(R.id.father_occupation);
		//ImageView view_pati_image = (ImageView)findViewById(R.id.view_patient_image);
        TextView patient_dob = (TextView)findViewById(R.id.dob);
        TextView no_siblings_birth=(TextView)findViewById(R.id.no_siblings_birth);
		TextView youngest_sibling_dob=(TextView)findViewById(R.id.youngest_sibling_dob);
		TextView patient_birth_weight= (TextView)findViewById(R.id.birth_weight);
		TextView patient_birth_height= (TextView)findViewById(R.id.birth_height);
		DetailedChild child = DetailedRecordsStore.get(getApplication()).getChild(getIntent().getStringExtra("child_id"));
		
		
		
		
		String patient_name_string=child.getName()+"";
		patient_name.setText(patient_name_string);
		if(patient_name_string.toString().trim().equals("") || patient_name_string.toString().trim().equals("null")){
			patient_name.setText("Not Set");
		}
		
		
		/*String patient_rec_no_string=child.getChild_id()+"";
		patient_rec_no.setText(patient_rec_no_string);
		if(patient_rec_no_string.toString().trim().equals("") || patient_rec_no_string.toString().trim().equals("null")){
			patient_rec_no.setText("Not Set");
		}
		*/
		
		
		String patient_gender_string=child.getGender()+"";
		//patient_gender.setText(patient_gender_string);
		if(patient_gender_string.toString().trim().equals("") || patient_gender_string.toString().trim().equals("null")){
			patient_gender.setText("Not Set");
		}else if(child.getGender()==0){
			patient_gender.setText("Male");
		}else if(child.getGender()==1){
			patient_gender.setText("Female");
		}else {
			patient_gender.setText("Not Set");
		}
		
		String patient_gestation_string=child.getMonths_gestated()+"";
		//patient_gestation.setText(patient_gestation_string);
		if(patient_gestation_string.toString().trim().equals("") || patient_gestation_string.toString().trim().equals("null")){
			patient_gestation.setText("Not Set");
		}else{
			patient_gestation.setText(patient_gestation_string+ " weeks");
		}
		
		String patient_t_of_b_string=child.getType_of_birth()+"";
		//patient_t_of_b.setText(patient_t_of_b_string);
		if(patient_t_of_b_string.toString().trim().equals("") || patient_t_of_b_string.toString().trim().equals("null")){
			patient_t_of_b.setText("Not Set");
		}else if (child.getType_of_birth()==0){
			patient_t_of_b.setText("Normal");
		}else if (child.getType_of_birth()==1){
			patient_t_of_b.setText("Cesarean");
		}else {
			patient_t_of_b.setText("Not Set");
		}
		
		
		String patient_dob_string=child.getDob()+"";
		patient_dob.setText(patient_dob_string);
		if(patient_dob_string.toString().trim().equals("") || patient_dob_string.toString().trim().equals("null")){
			patient_dob.setText("Not Set");
		}
		
		String patient_no_siblings_birth_string=child.getNum_children_in_same_pregnancy()+"";
		//no_siblings_birth.setText(patient_no_siblings_birth_string);
		if(patient_no_siblings_birth_string.toString().trim().equals("") || patient_no_siblings_birth_string.toString().trim().equals("null") || (child.getNum_children_in_same_pregnancy()==0)){
			no_siblings_birth.setText("Not Set");
		}else if(child.getNum_children_in_same_pregnancy()==1){
			no_siblings_birth.setText("Single");
		}else if(child.getNum_children_in_same_pregnancy()==2){
			no_siblings_birth.setText("Twins");
		}else if(child.getNum_children_in_same_pregnancy()==3){
			no_siblings_birth.setText("Triplets");
		}else{
			no_siblings_birth.setText("Not Set");
		}
		
		String YoungDOB=child.getYoungest_sibling_dob()+ "";
		youngest_sibling_dob.setText(YoungDOB);
		if(YoungDOB.toString().trim().equals("") || YoungDOB.toString().trim().equals("null")){
			((TextView)findViewById(R.id.youngest_sibling_dob)).setText("Not Set");
		}
		
		String patient_birth_weight_string=child.getBirth_weight()+"";
		if(patient_birth_weight_string.toString().trim().equals("") || patient_birth_weight_string.toString().trim().equals("null")){
			patient_birth_weight.setText("Not Set");
		}else {
			patient_birth_weight.setText(patient_birth_weight_string+" kg(s)");
		}
		
		String patient_birth_height_string=child.getBirth_height()+"";
		if(patient_birth_height_string.toString().trim().equals("") || patient_birth_height_string.toString().trim().equals("null")){
			patient_birth_height.setText("Not Set");
		}else {
			patient_birth_height.setText(patient_birth_height_string+" cm(s)");
		}		
	
		Button makeedit = (Button)findViewById(R.id.edit_patient_detail);
		makeedit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final ProgressDialog ringProgressDialog = ProgressDialog.show(view_patient.this, "",	"Loading ...", true);
				ringProgressDialog.setCancelable(true);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
								Intent objIntent = getIntent();
								String patientId = objIntent.getStringExtra("patientId");
								// Opening edit patient info activity				
								Intent  objIndent = new Intent(getApplicationContext(),MainActivity.class);
								objIndent.putExtra("patientId", patientId);
								startActivity(objIndent); 
							} catch (Exception e) {
						}
						ringProgressDialog.dismiss();
					}
				}).start();
			}
		});
		getexaminfo();
		if(!isExpanded)
			findViewById(R.id.collapse_info).setVisibility(View.INVISIBLE);
	}
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	public void getexaminfo(){
		Intent objIntent = getIntent();
		String patientId = objIntent.getStringExtra("patientId");
		ArrayList<HashMap<String, String>> patientexaminfo = controller.getpatientexaminfo(patientId);
		if(patientexaminfo.size()!=0) {
			list = (ListView)findViewById(R.id.examdetails);
			adapter=new LazyAdapterExam(this, patientexaminfo);
	        list.setAdapter(adapter);
	        
	        list.setOnItemClickListener(new OnItemClickListener() {
				  @Override 
				  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					  TextView patientId = (TextView) view.findViewById(R.id.viewexam_id);
					  final String valPatientId = patientId.getText().toString();
					  Log.d("idid",valPatientId);
					  final Dialog dialog2 = new Dialog(context);
					  dialog2.setContentView(R.layout.edit_exam_detail);
					  dialog2.setTitle("Edit Exam Details");
					  mDateDisplay = (TextView) dialog2.findViewById(R.id.edit_exam_date);
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
					  HashMap<String, String> patient_exam_det = controller.get_specific_exam_info(valPatientId);
					  EditText editexam_date =(EditText)dialog2.findViewById(R.id.edit_exam_date);
					  EditText editexam_weight =(EditText)dialog2.findViewById(R.id.edit_exam_weight);
					  EditText editexam_height =(EditText)dialog2.findViewById(R.id.edit_exam_height);
					  if(patient_exam_det.size()!=0) {
						  editexam_date.setText(patient_exam_det.get("exam_date"));
						  editexam_weight.setText(patient_exam_det.get("weight"));
						  editexam_height.setText(patient_exam_det.get("height"));
					  }
						Button dialogButton = (Button) dialog2.findViewById(R.id.save_exam_info);
						Button dialogdelButton = (Button) dialog2.findViewById(R.id.delete_exam_info);
						dialogdelButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertDialog.Builder builder = new Builder(view_patient.this);
								builder.setMessage("Are you sure you want to delete this?");
								builder.setTitle("Confirmation...");
								builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										controller.delete_exam_info(valPatientId);
										getexaminfo();
										plot_weight_graph();
										plot_height_graph();
										dialog2.dismiss();
										finish();
										startActivity(getIntent());
									}
								});	
								builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.cancel();
									}
								});	
								builder.create().show();	
							}
						});
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								EditText editexam_date =(EditText)dialog2.findViewById(R.id.edit_exam_date);
								EditText editexam_weight =(EditText)dialog2.findViewById(R.id.edit_exam_weight);
								EditText editexam_height =(EditText)dialog2.findViewById(R.id.edit_exam_height);
								if(editexam_date.getText().toString().equals("") || editexam_date.getText().toString().trim().equals("")){
									alert.showAlertDialog(view_patient.this, "Problem Occured", "Please enter exam date.", false);
								}else if(editexam_weight.getText().toString().equals("") || editexam_weight.getText().toString().trim().equals("")){
									alert.showAlertDialog(view_patient.this, "Problem Occured", "Please enter patient weight.", false);
								}else if(editexam_height.getText().toString().equals("") || editexam_height.getText().toString().trim().equals("")){
									alert.showAlertDialog(view_patient.this, "Problem Occured", "Please enter patient height.", false);
								}else{
									HashMap<String, String> queryValues =  new  HashMap<String, String>();
									queryValues.put("exam_date", editexam_date.getText().toString());
									queryValues.put("weight", editexam_weight.getText().toString());
									queryValues.put("height", editexam_height.getText().toString());
									queryValues.put("id", valPatientId);
									Log.d("idid",valPatientId);
									controller.updateexam_info(queryValues);
									getexaminfo();
									plot_weight_graph();
									plot_height_graph();
									dialog2.dismiss();
									finish();
									startActivity(getIntent());
								}
							}
						});
						dialog2.show();
				  }
			});
		}
	}
	
	public void toggle_contents(View v){
		
		TextView gestation_descy=(TextView)findViewById(R.id.textView4);
		TextView t_of_b_desc=(TextView)findViewById(R.id.textView5);
		TextView gestation_val=(TextView)findViewById(R.id.gestation_time);
		TextView t_of_b_val=(TextView)findViewById(R.id.type_of_birth);
		
		if (!isExpanded){
			isExpanded=true;
		}else{
			isExpanded=false;
		}
		if(isExpanded){
			
			findViewById(R.id.collapse_info).setVisibility(View.VISIBLE);
			findViewById(R.id.clickfordetails_text).setVisibility(View.GONE);
			expand(findViewById(R.id.collapse_info));
			//slide_down(this, findViewById(R.id.collapse_info));
			findViewById(android.R.id.content).invalidate();
		}else if(!isExpanded) {
			findViewById(R.id.collapse_info).setVisibility(View.GONE);
			findViewById(R.id.clickfordetails_text).setVisibility(View.VISIBLE);
			findViewById(android.R.id.content).invalidate();
			//expand(findViewById(R.id.collapse_info));
			
		}
		
	}
	
	public static void expand(final View v) {
	    v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    final int targtetHeight = v.getMeasuredHeight();

	    v.getLayoutParams().height = 0;
	    v.setVisibility(View.VISIBLE);
	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            v.getLayoutParams().height = interpolatedTime == 1
	                    ? LayoutParams.WRAP_CONTENT
	                    : (int)(targtetHeight * interpolatedTime);
	            v.requestLayout();
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
	}

	public static void collapse(final View v) {
	    final int initialHeight = v.getMeasuredHeight();

	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            if(interpolatedTime == 1){
	                v.setVisibility(View.GONE);
	            }else{
	                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
	                v.requestLayout();
	            }
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
	}

	 public static Bitmap decodeSampledBitmapFromResource(String picturePath, int reqWidth, int reqHeight) {

		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(picturePath, options);
		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
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
	private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
            		.append(mYear).append("-")
            		.append(mDay).append("-")
                    .append((mMonth +1<10?("0"+ (mMonth +1)):(mMonth +1))));
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
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
	public void plot_weight_graph(){
		Intent objIntent = getIntent();
		String patientId = objIntent.getStringExtra("patientId");
		int get_count = controller.getPatientExam_Info_graph_count(patientId);
		ArrayList<HashMap<String, String>> patientgraph_data = controller.getPatientExam_Info_graph(patientId);
		
		GraphViewData[] data = new GraphViewData[get_count];
		int i=0;
		if(get_count>0){
			/*
			for (int r=0; r<patientgraph_data.size();r++){
				int maxDate=0;
				int maxIndexer=0;
				HashMap<String, String> maxIndexObj; 
				for(int x=r; x<patientgraph_data.size();x++){
					int a=Integer.parseInt(patientgraph_data.get(x).get("time_graph").toString());
						if(a>maxDate){
							maxIndexer=x;
							maxDate=a;
						}
					
					}
				maxIndexObj=patientgraph_data.get(maxIndexer);
				patientgraph_data.set(maxIndexer, patientgraph_data.get(r));
				patientgraph_data.set(r, maxIndexObj);
				}*/

			for(Map<String, String> map : patientgraph_data)
		    {
				//Log.d("map","map"+map.get("time_graph"));
				String[] getdata = map.get("exam_date").split("-");
				int val = Integer.parseInt(getdata[2]);
				data[i] = new GraphViewData(val, Integer.parseInt(map.get("weight")));
				i++;
		    }
		}
			GraphView graphView = new LineGraphView(
			    this
			    , ""
			);
			graphView.addSeries(new GraphViewSeries(data));
			graphView.setScrollable(true);
	
	 		LinearLayout layout = (LinearLayout) findViewById(R.id.weight_chart);
	 		layout.addView(graphView);
		}
	
	
	public void plot_height_graph(){
		Intent objIntent = getIntent();
		String patientId = objIntent.getStringExtra("patientId");
		int get_count = controller.getPatientExam_Info_graph_count(patientId);
		ArrayList<HashMap<String, String>> patientgraph_data = controller.getPatientExam_Info_graph(patientId);
		GraphViewData[] data = new GraphViewData[get_count];
		
		int j=0;
		if(get_count>0){
			for(Map<String, String> map : patientgraph_data)
		    {
				String[] getdata = map.get("exam_date").split("-");
				int val = Integer.parseInt(getdata[2]);
				data[j] = new GraphViewData(val, Integer.parseInt(map.get("height")));
				j++;
		    }
			
			GraphView graphView = new LineGraphView(
			    this
			    , ""
			);
			// add data
			graphView.addSeries(new GraphViewSeries(data));
			graphView.setScrollable(true);
	
	 		LinearLayout layout = (LinearLayout) findViewById(R.id.height_chart);
	 		layout.addView(graphView);
		}
	}
	

	public void getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a, b;        
        String c ="";

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        b = m - cal.get(Calendar.MONTH);
        if ((m < cal.get(Calendar.MONTH))
                        || ((m == cal.get(Calendar.MONTH)) && (d < cal
                                        .get(Calendar.DAY_OF_MONTH)))) {
                --a;
                --b;
        }
        //if(a < 0)
                //throw new IllegalArgumentException("Age < 0");
        TextView patient_dob = (TextView)findViewById(R.id.dob);
        
        String mymon = " month";
        String mymons = " months";
        String myyear = " year ";
        String myyears = " years ";
        if(b<0){
        	c = (b + 12)==1?(b + 12) + mymon:(b + 12) + mymons;
        }else{
        	c = b==1?b + mymon:b + mymons;
        }
        
        String myage = (a>1?a + myyears:a + myyear) + c;
        
        patient_dob.setText(myage);
       // return a;
    }

/*
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
			final ProgressDialog ringProgressDialog = ProgressDialog.show(view_patient.this, "",	"Loading ...", true);
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
			final ProgressDialog ringProgressDialog = ProgressDialog.show(view_patient.this, "",	"Loading ...", true);
			ringProgressDialog.setCancelable(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
							Intent intent = new Intent(view_patient.this, about_us.class);
							startActivity(intent);
						} catch (Exception e) {
					}
					ringProgressDialog.dismiss();
				}
			}).start();
		}
		
		public void Openhome_page(){
			final ProgressDialog ringProgressDialog = ProgressDialog.show(view_patient.this, "",	"Loading ...", true);
			ringProgressDialog.setCancelable(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
							Intent intent = new Intent(view_patient.this, home.class);
							startActivity(intent);
						} catch (Exception e) {
					}
					ringProgressDialog.dismiss();
				}
			}).start();
		}
*/

}