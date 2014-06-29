package com.example.guatemal;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class patient_list  extends android.support.v4.app.Fragment{
	// All static variables
    static final String KEY_NAME = "name";
    static final String KEY_DOB = "dob";
    static final String KEY_THUMB_URL = "http://10.0.2.2/appimage/thumb1.jpg";
    static final String REC_ID = "id";
    static final String IMAGE_URL="image";
    Context context;
    
    Intent intent;
	TextView patientId;
	TextView deleteId;
//Defining all database stuff
	DBController controller;
	ListView list;
    LazyAdapter adapter;
    public void onActivityCreated(Bundle savedInstanceState){
    	super.onActivityCreated(savedInstanceState);
    	context=getActivity();
    	controller= new DBController(context);
    	// Selecting all patient from database		
    			ArrayList<HashMap<String, String>> patientList =  controller.getAllpatients();
    			if(patientList.size()!=0) {
    				list = (ListView)this.getView().findViewById(R.id.patient_list);
    		        adapter=new LazyAdapter(this, patientList);
    	// Adding all data in list view	        
    		        list.setAdapter(adapter);
    		        
    		      //enables filtering for the contents of the given ListView
    		        list.setTextFilterEnabled(true);
    	// Trigger list item click event	        
    				list.setOnItemClickListener(new OnItemClickListener() {
    					  @Override 
    					  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
    						  patientId = (TextView) view.findViewById(R.id.patient_id);
    						  String valPatientId = patientId.getText().toString();
    	// Open activity to view patient detail					  
    						  Intent  objIndent = new Intent(getActivity().getApplicationContext(),view_patient.class);
    						  objIndent.putExtra("patientId", valPatientId); 
    						  startActivity(objIndent); 
    					  }
    				});
    				
    				list.setOnItemLongClickListener(new OnItemLongClickListener() {
    		

    					@Override
    					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    						// TODO Auto-generated method stub
    						final Dialog dialog = new Dialog(context);
    						dialog.setContentView(R.layout.patient_options);
    						dialog.setTitle("Patient Options");
    						dialog.show();
    						patientId = (TextView) view.findViewById(R.id.patient_id);
    						final String pos=patientId.getText().toString();
    						dialog.findViewById(R.id.patientdeleter).setOnClickListener(new View.OnClickListener() {
    							
    							@Override
    							public void onClick(View v) {
    							    controller.deletepatient(pos);
    						
    							    dialog.dismiss();
    							    getActivity().finish();
    							    startActivity(getActivity().getIntent());
    							
    								// TODO Auto-generated method stub
    								
    							}
    						});
    						
    						return false;
    					}
    					
    				});
    				
    			}
    			
    			
    	// Search from list		
    			EditText search_text = (EditText)this.getView().findViewById(R.id.view_patient_name);
    			search_text.addTextChangedListener(new TextWatcher(){
    				@Override
    		        public void onTextChanged(CharSequence s, int start, int before, int count) {
    					//(adapter).getFilter().filter(s.toString());
    					patient_list.this.adapter.getFilter().filter(s); 
    		        }

    				@Override
    				public void afterTextChanged(Editable arg0) {
    				}

    				@Override
    				public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {
    				}
    			});
    			
    }
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.patient_list, container, false);

	}
}
