package com.stanford.guatemedic;

public class ChildVisit {
	//child_id and visit_date form unique key
	private String mChild_id;
	private String mVisit_date;
	
	private String mNewVaccinations; //information about vaccinations
	private boolean mFed_only_by_breast;
	private boolean mCurrently_fed_supplementary_food;
	private int mNum_sibilings_older_than_5;
	private boolean mNew_child; //What is this?
	private Double mWeight_in_kilos;
	private Double mHeight_in_centimeters;
	private boolean mCurrently_breastfed;
	private boolean mReceiving_nutritional_supplement;
	private int mNum_times_vegetables_past_week;
	private int mNum_times_herbs_past_week;
	private int mNum_times_diarrhea_past_week;
	private int mNum_times_vomit_past_week;
	private int mNum_times_cough_past_week;
	private int mNum_times_fever_past_week;
	private String mOther_illness;
	private String mIllness_description;
	private String mOther_medicine_provided;
	private Double mMalnutrition_grade_guatemala_scale;
	private Double mMalnutrition_grade_world_scale;
	private Double mMalnutrition_grade_height_for_age;
	private boolean mReceiving_supplements;
	private String mWhy_receiving_supplements;
	
	public ChildVisit() {
		
	}

	public String getChild_id() {
		return mChild_id;
	}

	public void setChild_id(String child_id) {
		mChild_id = child_id;
	}

	public String getVisit_date() {
		return mVisit_date;
	}

	public void setVisit_date(String visit_date) {
		mVisit_date = visit_date;
	}

	public String getNewVaccinations() {
		return mNewVaccinations;
	}

	public void setNewVaccinations(String vaccinations) {
		mNewVaccinations = vaccinations;
	}

	public boolean isFed_only_by_breast() {
		return mFed_only_by_breast;
	}

	public void setFed_only_by_breast(boolean fed_only_by_breast) {
		mFed_only_by_breast = fed_only_by_breast;
	}

	public boolean isCurrently_fed_supplementary_food() {
		return mCurrently_fed_supplementary_food;
	}

	public void setCurrently_fed_supplementary_food(
			boolean currently_fed_supplementary_food) {
		mCurrently_fed_supplementary_food = currently_fed_supplementary_food;
	}

	public int getNum_sibilings_older_than_5() {
		return mNum_sibilings_older_than_5;
	}

	public void setNum_sibilings_older_than_5(int num_sibilings_older_than_5) {
		mNum_sibilings_older_than_5 = num_sibilings_older_than_5;
	}

	public boolean isNew_child() {
		return mNew_child;
	}

	public void setNew_child(boolean new_child) {
		mNew_child = new_child;
	}

	public Double getWeight_in_kilos() {
		return mWeight_in_kilos;
	}

	public void setWeight_in_kilos(Double weight_in_kilos) {
		mWeight_in_kilos = weight_in_kilos;
	}

	public Double getHeight_in_centimeters() {
		return mHeight_in_centimeters;
	}

	public void setHeight_in_centimeters(Double height_in_centimeters) {
		mHeight_in_centimeters = height_in_centimeters;
	}

	public boolean isCurrently_breastfed() {
		return mCurrently_breastfed;
	}

	public void setCurrently_breastfed(boolean currently_breastfed) {
		mCurrently_breastfed = currently_breastfed;
	}

	public boolean isReceiving_nutritional_supplement() {
		return mReceiving_nutritional_supplement;
	}

	public void setReceiving_nutritional_supplement(
			boolean receiving_nutritional_supplement) {
		mReceiving_nutritional_supplement = receiving_nutritional_supplement;
	}

	public int getNum_times_vegetables_past_week() {
		return mNum_times_vegetables_past_week;
	}

	public void setNum_times_vegetables_past_week(int num_times_vegetables_past_week) {
		mNum_times_vegetables_past_week = num_times_vegetables_past_week;
	}

	public int getNum_times_herbs_past_week() {
		return mNum_times_herbs_past_week;
	}

	public void setNum_times_herbs_past_week(int num_times_herbs_past_week) {
		mNum_times_herbs_past_week = num_times_herbs_past_week;
	}

	public int getNum_times_diarrhea_past_week() {
		return mNum_times_diarrhea_past_week;
	}

	public void setNum_times_diarrhea_past_week(int num_times_diarrhea_past_week) {
		mNum_times_diarrhea_past_week = num_times_diarrhea_past_week;
	}

	public int getNum_times_vomit_past_week() {
		return mNum_times_vomit_past_week;
	}

	public void setNum_times_vomit_past_week(int num_times_vomit_past_week) {
		mNum_times_vomit_past_week = num_times_vomit_past_week;
	}

	public int getNum_times_cougt_past_week() {
		return mNum_times_cough_past_week;
	}

	public void setNum_times_cough_past_week(int num_times_cought_past_week) {
		mNum_times_cough_past_week = num_times_cought_past_week;
	}

	public int getNum_times_fever_past_week() {
		return mNum_times_fever_past_week;
	}

	public void setNum_times_fever_past_week(int num_times_fever_past_week) {
		mNum_times_fever_past_week = num_times_fever_past_week;
	}

	public String getOther_illness() {
		return mOther_illness;
	}

	public void setOther_illness(String other_illness) {
		mOther_illness = other_illness;
	}

	public String getIllness_description() {
		return mIllness_description;
	}

	public void setIllness_description(String illness_description) {
		mIllness_description = illness_description;
	}

	public String getOther_medicine_provided() {
		return mOther_medicine_provided;
	}

	public void setOther_medicine_provided(String other_medicine_provided) {
		mOther_medicine_provided = other_medicine_provided;
	}

	public Double getMalnutrition_grade_guatemala_scale() {
		return mMalnutrition_grade_guatemala_scale;
	}

	public void setMalnutrition_grade_guatemala_scale(
			Double malnutrition_grade_guatemala_scale) {
		mMalnutrition_grade_guatemala_scale = malnutrition_grade_guatemala_scale;
	}

	public Double getMalnutrition_grade_world_scale() {
		return mMalnutrition_grade_world_scale;
	}

	public void setMalnutrition_grade_world_scale(
			Double malnutrition_grade_world_scale) {
		mMalnutrition_grade_world_scale = malnutrition_grade_world_scale;
	}

	public Double getMalnutrition_grade_height_for_age() {
		return mMalnutrition_grade_height_for_age;
	}

	public void setMalnutrition_grade_height_for_age(
			Double malnutrition_grade_height_for_age) {
		mMalnutrition_grade_height_for_age = malnutrition_grade_height_for_age;
	}

	public boolean isReceiving_supplements() {
		return mReceiving_supplements;
	}

	public void setReceiving_supplements(boolean receiving_supplements) {
		mReceiving_supplements = receiving_supplements;
	}

	public String getWhy_receiving_supplements() {
		return mWhy_receiving_supplements;
	}

	public void setWhy_receiving_supplements(String why_receiving_supplements) {
		mWhy_receiving_supplements = why_receiving_supplements;
	}
	
	
}
