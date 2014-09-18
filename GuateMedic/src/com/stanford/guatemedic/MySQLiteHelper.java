package com.stanford.guatemedic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_COMMUNITIES = "communities";
	public static final String COMMUNITIES_COLUMN_ID = "_id"; //referred to as community_id in other tables
	public static final String COMMUNITIES_COLUMN_NAME = "name";


	public static final String TABLE_FAMILIES = "families";
	public static final String FAMILIES_COLUMN_ID = "_id"; //reffered to as family_id in other tables
	public static final String FAMILIES_COLUMN_COMMUNITY_ID = "community_id";
	public static final String FAMILIES_COLUMN_NEW = "new"; //boolean
	public static final String FAMILIES_COLUMN_IN_PROGRESS = "in_progress"; //boolean
	public static final String FAMILIES_COLUMN_PARENT1_NAME = "parent1_name";
	public static final String FAMILIES_COLUMN_PARENT1_DOB = "parent1_dob";
	public static final String FAMILIES_COLUMN_PARENT2_NAME = "parent2_name";
	public static final String FAMILIES_COLUMN_PARENT2_DOB = "parent2_dob";
	public static final String FAMILIES_COLUMN_PARENT1_MARITAL_STATUS = "parent1_marital_status";
	public static final String FAMILIES_COLUMN_DOES_FATHER_LIVE_WITH_FAMILY = "does_father_live_with_family"; //boolean
	public static final String FAMILIES_COLUMN_NUM_PREGNANCIES = "num_pregnancies";
	public static final String FAMILIES_COLUMN_NUM_CHILDREN_ALIVE = "num_children_alive";
	public static final String FAMILIES_COLUMN_NUM_CHILDREN_DEAD = "num_children_dead";
	public static final String FAMILIES_COLUMN_NUM_CHILDREN_UNDER_5 = "num_children_under_5";
	public static final String FAMILIES_COLUMN_NUM_PEOPLE_IN_HOUSEHOLD = "num_people_in_household";
	public static final String FAMILIES_COLUMN_FATHERS_JOB = "fathers_job";
	public static final String FAMILIES_COLUMN_HAS_IGSS = "has_igss"; //boolean
	public static final String FAMILIES_COLUMN_PROMOTER_ID = "promoter_id";
	public static final String FAMILIES_COLUMN_DATE_CREATED = "date_created";
	public static final String FAMILIES_COLUMN_DATE_LAST_MODIFIED = "date_last_modified";


	//Should I also include the community_id?
	public static final String TABLE_CHILDREN = "children";
	public static final String CHILDREN_COLUMN_ID = "_id"; //referred to as child_id in other tables
	public static final String CHILDREN_COLUMN_FAMILY_ID = "family_id";
	public static final String CHILDREN_COLUMN_NAME = "name";
	public static final String CHILDREN_COLUMN_DOB = "dob";
	public static final String CHILDREN_COLUMN_GENDER = "gender";
	public static final String CHILDREN_COLUMN_TYPE_OF_BIRTH = "type_of_birth";
	public static final String CHILDREN_COLUMN_NUM_CHILDREN_IN_SAME_PREGNANCY = "num_children_in_pregnancy";
	public static final String CHILDREN_COLUMN_MONTHS_GESTATED = "months_gestated";
	public static final String CHILDREN_COLUMN_RECEIVED_PRENATAL_CARE = "received_prenatal_care"; //boolean
	public static final String CHILDREN_COLUMN_BIRTH_WEIGHT = "birth_weight"; //pounds
	public static final String CHILDREN_COLUMN_BIRTH_HEIGHT = "birth_height"; //centimeters
	public static final String CHILDREN_COLUMN_YOUNGEST_SIBLING_DOB = "youngest_sibling_dob";
	public static final String CHILDREN_COLUMN_PROMOTER_ID = "promoter_id";
	public static final String CHILDREN_COLUMN_DATE_CREATED = "date_created";
	public static final String CHILDREN_COLUMN_DATE_LAST_MODIFIED = "date_last_modified";


	//Should I also include the family_id?
	//Should I also include the community_id?
	//nan1_baby_formula field?
	public static final String TABLE_VISITS = "visits";
	public static final String DETAILED_VISITS_COLUMN_ID = "_id";
	public static final String DETAILED_VISITS_COLUMN_CHILD_ID = "child_id";
	public static final String DETAILED_VISITS_COLUMN_DID_RECEIVE_ALL_VACCINES = "did_receive_all_vaccines";
	public static final String DETAILED_VISITS_COLUMN_TYPES_OF_VACCINES_RECEIVED = "types_of_vaccines_received";
	public static final String DETAILED_VISITS_COLUMN_HAS_CHRONIC_DISEASE_OR_DISABILITY = "has_chronic_disease_or_disability";
	public static final String DETAILED_VISITS_COLUMN_TYPE_OF_CHRONIC_DISEASE_OR_DISABILITY = "type_of_chronic_disease_or_disability";
	public static final String DETAILED_VISITS_COLUMN_IS_CURRENTLY_BREASTFED = "is_currently_breastfed";
	public static final String DETAILED_VISITS_COLUMN_AGE_WHEN_STOPPED_BEING_BREASTFED = "child_age_when_stopped_breastfeeding";
	public static final String DETAILED_VISITS_COLUMN_IS_ONLY_BREASTFED = "is_only_breastfed";
	public static final String DETAILED_VISITS_COLUMN_LENGTH_ONLY_BREASTFED = "length_only_breastfed";
	public static final String DETAILED_VISITS_COLUMN_WEIGHT_IN_POUNDS = "weight_in_pounds";
	public static final String DETAILED_VISITS_COLUMN_HEIGHT_IN_CENTIMETERS = "heights_in_centimeters";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_INCAPARINA_PAST_WEEK = "num_times_incaparina_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_VEGETABLES_OR_FRUITS_PAST_WEEK = "num_times_vegetables_or_fruits_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_HERBS_PAST_WEEK = "num_times_herbs_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_DIARRHEA_PAST_WEEK = "num_times_diarrhea_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_VOMIT_PAST_WEEK = "num_times_vomit_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_COUGH_PAST_WEEK = "num_times_cough_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_FEVER_PAST_WEEK = "num_times_fever_past_week";
	public static final String DETAILED_VISITS_COLUMN_NUM_TIMES_OTHER_ILLNESS_PAST_WEEK = "num_times_other_illness_past_week";
	public static final String DETAILED_VISITS_COLUMN_ILLNESS_DESCRIPTION = "illness_description";
	public static final String DETAILED_VISITS_COLUMN_AGE_LAST_RECEIVED_DEPARASITING_MEDICINE = "age_last_received_deparasiting_medicine";
	public static final String DETAILED_VISITS_COLUMN_PROMOTER_ID = "promoter_id";
	public static final String DETAILED_VISITS_COLUMN_DATETIME = "datetime";

	

	//May not need all these fields
	public static final String TABLE_MODIFICATIONS = "modifications";
	public static final String MODIFICATIONS_COLUMN_ID = "_id";
	public static final String MODIFICATIONS_COLUMN_TABLE = "table"; //families or children
	public static final String MODIFICATIONS_COLUMN_TABLE_ROW = "table_row"; //the identifier for specific row(family_id or child_id)
	public static final String MODIFICATIONS_COLUMN_COLUMN = "column"; //the column that is being modified
	public static final String MODIFICATIONS_COLUMN_PREVIOUS_VALUE = "previous_value";
	public static final String MODIFICATIONS_COLUMN_NEW_VALUE = "new_value";
	public static final String MODIFICATIONS_COLUMN_PROMOTER_ID = "promoter_id";
	public static final String MODIFICATIONS_COLUMN_DATETIME = "datetime";


	//Still not sure this is the best way to go about doing local authorization
	public static final String TABLE_PROMOTERS = "promoters";
	public static final String PROMOTERS_COLUMN_ID = "_id"; //referred to as promoter_id in other tables
	public static final String PROMOTERS_COLUMN_NAME = "promoter_name";
	public static final String PROMOTERS_COLUMN_USERNAME = "promoter_username";
	public static final String PROMOTER_COLUMN_PASSWORD_HASH = "promoter_password_hash";


	//Database Information
	private static final String DATABASE_NAME = "tijax.db";
	private static final int DATABASE_VERSION = 1;

	//Database Table creations
	private static final String TABLE_COMMUNITIES_CREATE = "CREATE TABLE " + TABLE_COMMUNITIES + "("
		+ COMMUNITIES_COLUMN_ID + " integer primary key not null, " 
		+ COMMUNITIES_COLUMN_NAME + " string not null);";

	private static final String TABLE_FAMILIES_CREATE = "CREATE TABLE " + TABLE_FAMILIES + "("
		+ FAMILIES_COLUMN_ID + " integer primary key not null, "
		+ FAMILIES_COLUMN_COMMUNITY_ID + " integer not null, "
		+ FAMILIES_COLUMN_NEW + " integer not null, "
		+ FAMILIES_COLUMN_IN_PROGRESS + " integer not null, "
		+ FAMILIES_COLUMN_PARENT1_NAME + " string not null, "
		+ FAMILIES_COLUMN_PARENT1_DOB + " string not null, "
		+ FAMILIES_COLUMN_PARENT2_NAME + " string not null, "
		+ FAMILIES_COLUMN_PARENT2_DOB + " string not null, "
		+ FAMILIES_COLUMN_PARENT1_MARITAL_STATUS + " integer not null, "
		+ FAMILIES_COLUMN_DOES_FATHER_LIVE_WITH_FAMILY + " integer not null, "
		+ FAMILIES_COLUMN_NUM_PREGNANCIES + " integer not null, "
		+ FAMILIES_COLUMN_NUM_CHILDREN_ALIVE + " integer not null, "
		+ FAMILIES_COLUMN_NUM_CHILDREN_DEAD + " integer not null, "
		+ FAMILIES_COLUMN_NUM_CHILDREN_UNDER_5 + " integer not null, "
		+ FAMILIES_COLUMN_NUM_PEOPLE_IN_HOUSEHOLD + " integer not null, "
		+ FAMILIES_COLUMN_FATHERS_JOB + " integer not null, "
		+ FAMILIES_COLUMN_HAS_IGSS + " integer not null, "
		+ FAMILIES_COLUMN_PROMOTER_ID + " integer not null, "
		+ FAMILIES_COLUMN_DATE_CREATED + " string not null, "
		+ FAMILIES_COLUMN_DATE_LAST_MODIFIED + " string not null);";
	
	private static final String TABLE_CHILDREN_CREATE = "CREATE TABLE " + TABLE_CHILDREN + "("
		+ CHILDREN_COLUMN_ID + " integer primary key not null, "
		+ CHILDREN_COLUMN_FAMILY_ID + " integer not null, "
		+ CHILDREN_COLUMN_NAME + " string not null, "
		+ CHILDREN_COLUMN_DOB + " string not null, "
		+ CHILDREN_COLUMN_GENDER + " integer not null, "
		+ CHILDREN_COLUMN_TYPE_OF_BIRTH + " integer not null, "
		+ CHILDREN_COLUMN_NUM_CHILDREN_IN_SAME_PREGNANCY + " integer not null, "
		+ CHILDREN_COLUMN_MONTHS_GESTATED + " integer not null, "
		+ CHILDREN_COLUMN_RECEIVED_PRENATAL_CARE + " integer not null, "
		+ CHILDREN_COLUMN_BIRTH_WEIGHT + " integer not null, "
		+ CHILDREN_COLUMN_BIRTH_HEIGHT + " integer not null, "
		+ CHILDREN_COLUMN_YOUNGEST_SIBLING_DOB + " string not null, "
		+ CHILDREN_COLUMN_PROMOTER_ID + " integer not null, "
		+ CHILDREN_COLUMN_DATE_CREATED + " string not null, "
		+ CHILDREN_COLUMN_DATE_LAST_MODIFIED + " string not null);";
	
	private static final String TABLE_VISITS_CREATE = "CREATE TABLE " + TABLE_VISITS + "("
		+ DETAILED_VISITS_COLUMN_ID + " integer primary key not null, "
		+ DETAILED_VISITS_COLUMN_CHILD_ID + " integer not null, "
		+ DETAILED_VISITS_COLUMN_DID_RECEIVE_ALL_VACCINES + " integer not null, "
		+ DETAILED_VISITS_COLUMN_TYPES_OF_VACCINES_RECEIVED + " string not null, "
		+ DETAILED_VISITS_COLUMN_HAS_CHRONIC_DISEASE_OR_DISABILITY + " integer not null, "
		+ DETAILED_VISITS_COLUMN_TYPE_OF_CHRONIC_DISEASE_OR_DISABILITY + " string not null, "
		+ DETAILED_VISITS_COLUMN_IS_CURRENTLY_BREASTFED + " integer not null, "
		+ DETAILED_VISITS_COLUMN_AGE_WHEN_STOPPED_BEING_BREASTFED + " integer not null, "
		+ DETAILED_VISITS_COLUMN_IS_ONLY_BREASTFED + " integer not null, "
		+ DETAILED_VISITS_COLUMN_LENGTH_ONLY_BREASTFED + " integer not null, "
		+ DETAILED_VISITS_COLUMN_WEIGHT_IN_POUNDS + " integer not null, "
		+ DETAILED_VISITS_COLUMN_HEIGHT_IN_CENTIMETERS + " height in centimeters, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_INCAPARINA_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_VEGETABLES_OR_FRUITS_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_HERBS_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_DIARRHEA_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_VOMIT_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_COUGH_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_FEVER_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_NUM_TIMES_OTHER_ILLNESS_PAST_WEEK + " integer not null, "
		+ DETAILED_VISITS_COLUMN_ILLNESS_DESCRIPTION + " string not null, "
		+ DETAILED_VISITS_COLUMN_AGE_LAST_RECEIVED_DEPARASITING_MEDICINE + " integer not null, "
		+ DETAILED_VISITS_COLUMN_PROMOTER_ID + " integer not null, "
		+ DETAILED_VISITS_COLUMN_DATETIME + " string not null);";
	
	private static final String TABLE_MODIFICATIONS_CREATE = "CREATE TABLE " + TABLE_MODIFICATIONS + "("
		+ MODIFICATIONS_COLUMN_ID + " integer primary key not null, "
		+ MODIFICATIONS_COLUMN_TABLE + " string not null, "
		+ MODIFICATIONS_COLUMN_TABLE_ROW + " integer not null, "
		+ MODIFICATIONS_COLUMN_COLUMN + " string not null, "
		+ MODIFICATIONS_COLUMN_PREVIOUS_VALUE + " string not null, "
		+ MODIFICATIONS_COLUMN_NEW_VALUE + " string not null, "
		+ MODIFICATIONS_COLUMN_PROMOTER_ID + " integer not null, "
		+ MODIFICATIONS_COLUMN_DATETIME + " string not null);";
	
	private static final String TABLE_PROMOTERS_CREATE = "CREATE TABLE " + TABLE_PROMOTERS + "("
		+ PROMOTERS_COLUMN_ID + " integer primary key not null, "
		+ PROMOTERS_COLUMN_NAME + " string not null, "
		+ PROMOTERS_COLUMN_USERNAME + " string not null, "
		+ PROMOTER_COLUMN_PASSWORD_HASH + " string not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_COMMUNITIES_CREATE);
		database.execSQL(TABLE_FAMILIES_CREATE);
		database.execSQL(TABLE_CHILDREN_CREATE);
		database.execSQL(TABLE_VISITS_CREATE);
		database.execSQL(TABLE_MODIFICATIONS_CREATE);
		database.execSQL(TABLE_PROMOTERS_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(), 
			"Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMUNITIES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILIES);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILDREN);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODIFICATIONS);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTERS);
		    onCreate(db);
	}
}
