package com.example.guatemal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController  extends SQLiteOpenHelper {

	public DBController(Context applicationcontext) {
        super(applicationcontext, "doctor.db", null, 2);
    }

	
	
// This function will be called on load and will create database and its tables
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE users ( id INTEGER PRIMARY KEY, name TEXT, password TEXT, type INTEGER)";
        database.execSQL(query);
        
        query = "CREATE TABLE patients ( id INTEGER PRIMARY KEY, rec_no INTEGER, name TEXT, dob TEXT, gestation_time TEXT, household_count TEXT, breastfeeding_info TEXT, fathers_occupation TEXT, sex TEXT, image TEXT, status TEXT, community TEXT, brothers TEXT, sisters TEXT, mom_births TEXT, surviving_children TEXT, moms_name TEXT, dads_name TEXT, bith_weight TEXT, mom_care TEXT, birth_type TEXT, birth_location TEXT, chronic_illness TEXT, sibling_info TEXT)";
        database.execSQL(query);
        
        query = "CREATE TABLE exam ( id INTEGER PRIMARY KEY, patient_id INTEGER, height TEXT, weight TEXT, exam_date TEXT)";
        database.execSQL(query);
        //Log.d(LOGCAT,"users Created");
        
        query = "INSERT INTO users (name,password,type)values('admin','admin','1')";
        database.execSQL(query);
        //Log.d(LOGCAT,"users inserted");
	}
	
	
	
	
// If database and tables already exist it will delete all the stuff
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS users";
		database.execSQL(query);
		query = "DROP TABLE IF EXISTS patients";
		database.execSQL(query);
		query = "DROP TABLE IF EXISTS exam";
		database.execSQL(query);
        onCreate(database);
	}
	
	
	
// This function will authenticate user on the basis of information provided by user if credential are coreect it will pass 1 else it will pass 0
	public int MakeLogin(String name, String password) {
		SQLiteDatabase database = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM users where name='"+name+"' AND password='"+password+"'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		int make_login =0;
		if (cursor.getCount()>0) {
			make_login = 1;
	    }
		return make_login;
	}

	
	
// This will select all the patients from database
	public ArrayList<HashMap<String, String>> getAllpatients() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM patients";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	//Log.e("text",cursor.getString(cursor.getColumnIndex("name")));
	        	//map.put("id", cursor.getString('name'));
	        	map.put("id", cursor.getString(cursor.getColumnIndex("id")));
	        	map.put("name", cursor.getString(cursor.getColumnIndex("name")));
	        	map.put("image", cursor.getString(cursor.getColumnIndex("image")));
	        	
	        	//Log.d("imagename",cursor.getString(cursor.getColumnIndex("image")));
	        	
	        	String newdate = cursor.getString(cursor.getColumnIndex("dob"));
				String[] expdate = newdate.split("-"); 
				int day = Integer.parseInt(expdate[1].trim());
		        int month = Integer.parseInt(expdate[2].trim());
		        int year = Integer.parseInt(expdate[0].trim());

		        //Log.d("Age",String.valueOf(view_patient.this.));
		        String Age = getAge(year, month, day);

	        	
	        	map.put("dob", Age);
	        	//map.put("3wz0", "http://10.0.2.2/appimage/thumb1.jpg");
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return wordList;
	}
	
	
//	This will select all the exam info from database
	public ArrayList<HashMap<String, String>> getpatientexaminfo(String pid) {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM exam where patient_id='" + pid + "'";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("id", cursor.getString(cursor.getColumnIndex("id")));
	        	map.put("patient_id", cursor.getString(cursor.getColumnIndex("patient_id")));
	        	map.put("height", cursor.getString(cursor.getColumnIndex("height")) + " CM");
	        	map.put("weight", cursor.getString(cursor.getColumnIndex("weight"))  + " KG");
	        	map.put("exam_date", cursor.getString(cursor.getColumnIndex("exam_date")));
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    return wordList;
	}

	
	
// This function will add new patient
	public void insertpatient(HashMap<String, String> queryValues) {
		
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", queryValues.get("patient_name"));
		//values.put("rec_no", queryValues.get("patient_rec_no"));
		values.put("sex", queryValues.get("patient_gender"));
		values.put("dob", queryValues.get("patient_dob"));
		values.put("gestation_time", queryValues.get("patient_gestation"));
		values.put("household_count", queryValues.get("patient_household"));
		values.put("sibling_info", queryValues.get("patient_sibinfo"));
		values.put("breastfeeding_info", queryValues.get("patient_breastinfo"));
		values.put("fathers_occupation", queryValues.get("patient_fatheroccu"));
		values.put("image", queryValues.get("patient_image"));
		database.insert("patients", null, values);
		database.close();
	}

	
//delete patient from table
	public void deletepatient(String pid){
		SQLiteDatabase database=this.getWritableDatabase();
		String deleterExamQuery="DELETE FROM exam where patient_id='"+pid+"'";
		database.execSQL(deleterExamQuery);
		String deleterQuery = "DELETE FROM  patients where id='"+pid+"'";
		database.execSQL(deleterQuery);
		database.close();
	}
	
	
// This function will add new patient
	public void insertpatientexam(HashMap<String, String> queryValues) {
		
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int Last_id = Lastpatientid();
		values.put("exam_date", queryValues.get("exam_date"));
		values.put("weight", queryValues.get("weight"));
		values.put("height", queryValues.get("height"));
		values.put("patient_id", Last_id);
		database.insert("exam", null, values);
		database.close();
	}

	
	
	
// This function will add new patient
	public void insert_specific_patient_exam_info(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("exam_date", queryValues.get("exam_date"));
		values.put("weight", queryValues.get("weight"));
		values.put("height", queryValues.get("height"));
		values.put("patient_id", queryValues.get("patient_id"));
		database.insert("exam", null, values);
		database.close();
	}
	
	
	
	
// This function return last patient id
	public int Lastpatientid() {
			SQLiteDatabase database = this.getReadableDatabase();
			String selectQuery = "SELECT MAX(id) FROM patients";
			Cursor cursor = database.rawQuery(selectQuery, null);
			String maxid ="";
			
			if (cursor.moveToFirst()) {
		        do {
		        	maxid = cursor.getString(cursor.getColumnIndex("MAX(id)"));
		        } while (cursor.moveToNext());
			
			}
			return Integer.parseInt(maxid);
		}


	
// This function will select patient info for editing on patient 
	public HashMap<String, String> get_specific_exam_info(String id) {
			HashMap<String, String> wordList = new HashMap<String, String>();
			SQLiteDatabase database = this.getReadableDatabase();
			String selectQuery = "SELECT * FROM exam where id='"+id+"'";
			Cursor cursor = database.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
		        do {
		        	wordList.put("exam_date", cursor.getString(cursor.getColumnIndex("exam_date")));
		        	wordList.put("weight", cursor.getString(cursor.getColumnIndex("weight")));
		        	wordList.put("height", cursor.getString(cursor.getColumnIndex("height")));
		        		
		        } while (cursor.moveToNext());
		    }				    
		return wordList;
		}
		
		
	
	
//This function will use to update specific patient info	
	public int updateexam_info(HashMap<String, String> queryValues) {
			SQLiteDatabase database = this.getWritableDatabase();	 
		    ContentValues values = new ContentValues();
		    values.put("exam_date", queryValues.get("exam_date"));
			values.put("weight", queryValues.get("weight"));
			values.put("height", queryValues.get("height"));
		    return database.update("exam", values, "id" + " = ?", new String[] { queryValues.get("id") });
		}

	
	
// This function will select patient info for editing on patient 
	public HashMap<String, String> getPatientInfo(String id) {
		HashMap<String, String> wordList = new HashMap<String, String>();
		SQLiteDatabase database = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM patients where id='"+id+"'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
	        do {
	        	wordList.put("patientname", cursor.getString(cursor.getColumnIndex("name")));
	        	wordList.put("record_no", cursor.getString(cursor.getColumnIndex("rec_no")));
	        	wordList.put("gender", cursor.getString(cursor.getColumnIndex("sex")));
	        	wordList.put("id", cursor.getString(cursor.getColumnIndex("id")));
	        	wordList.put("dob", cursor.getString(cursor.getColumnIndex("dob")));
	        	wordList.put("gestation", cursor.getString(cursor.getColumnIndex("gestation_time")));
	        	wordList.put("household_no", cursor.getString(cursor.getColumnIndex("household_count")));
	        	wordList.put("sib_info", cursor.getString(cursor.getColumnIndex("sibling_info")));
	        	wordList.put("breast_info", cursor.getString(cursor.getColumnIndex("breastfeeding_info")));
	        	wordList.put("father_occu", cursor.getString(cursor.getColumnIndex("fathers_occupation")));
	        	wordList.put("image", cursor.getString(cursor.getColumnIndex("image")));
	        		
	        } while (cursor.moveToNext());
	    }				    
	return wordList;
	}
	
	
		
	public ArrayList<HashMap<String, String>> getPatientExam_Info_graph(String id) {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		
		String selectQuery = "SELECT * FROM exam where patient_id='"+id+"'";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("weight", cursor.getString(cursor.getColumnIndex("weight")));
	        	map.put("height", cursor.getString(cursor.getColumnIndex("height")));
	        	map.put("exam_date", cursor.getString(cursor.getColumnIndex("exam_date")));
	        	String examdate=cursor.getString(cursor.getColumnIndex("exam_date"));
	        	String[] expdate = examdate.split("-"); 
				int day = Integer.parseInt(expdate[1].trim());
		        int month = Integer.parseInt(expdate[2].trim());
		        int year = Integer.parseInt(expdate[0].trim());
	            map.put("time_graph", getTime(year, month, day));
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return wordList;
	    
	}
		
		
// This function will select patient exam info for Graph 
		public int getPatientExam_Info_graph_count(String id) {
			SQLiteDatabase database = this.getReadableDatabase();
			String selectQuery = "SELECT * FROM exam where patient_id='"+id+"'";
			Cursor cursor = database.rawQuery(selectQuery, null);
			return cursor.getCount();
		}
	
	
		
		public void delete_exam_info(String id) {
			SQLiteDatabase database = this.getWritableDatabase();	 
			String deleteQuery = "DELETE FROM  exam where id='"+ id +"'";
			database.execSQL(deleteQuery);
		}
		
	
	
	
	
	
//This function will use to update specific patient info	
	public int updatePatient(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();	 
	    ContentValues values = new ContentValues();
	    
	    values.put("name", queryValues.get("patient_name"));
		values.put("rec_no", queryValues.get("patient_rec_no"));
		values.put("sex", queryValues.get("patient_gender"));
		values.put("dob", queryValues.get("patient_dob"));
		values.put("gestation_time", queryValues.get("patient_gestation"));
		values.put("household_count", queryValues.get("patient_household"));
		values.put("sibling_info", queryValues.get("patient_sibinfo"));
		values.put("breastfeeding_info", queryValues.get("patient_breastinfo"));
		values.put("fathers_occupation", queryValues.get("patient_fatheroccu"));
		values.put("image", queryValues.get("patient_image"));
	    return database.update("patients", values, "id" + " = ?", new String[] { queryValues.get("patientid") });
	}
	
	public String getTime(int _year, int _month, int _day){
        String dayAmount=(_year*365+_month*30+_day*1)+"";
		return dayAmount;
	}
	
		
	/* Function for calculating date of birth*/
	public String getAge (int _year, int _month, int _day) {

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
       // Log.d("Month",Integer.toString(b));
        
       // if(a < 0)
              //  throw new IllegalArgumentException("Age < 0");
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
       return myage;
        
    }
}

