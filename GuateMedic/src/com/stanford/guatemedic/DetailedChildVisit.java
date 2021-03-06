package com.stanford.guatemedic;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedChildVisit {
	
	private boolean in_progress;
	
	private String child_id;
	private String temp_child_id;
	private String visit_date;
	private String promoter_id;
	
	private int received_all_vaccines; //received_all_vaccinations is backend field name
	private String types_of_vaccines_received; //types_of_vaccines_received
	private int has_chronic_disease_or_disability; //chronic_disease_or_disability
	private String type_of_chronic_disease_or_disability; //type_of_chronic_disease_or_disability
	
	private int is_currently_breastfed;
	private int is_only_breastfed;
	private float how_long_only_breastfed;
	private float child_age_when_stopped_breastfeeding; //Could be String date_when_stopped breastfeeding
	
	private float weight_in_pounds; //in kilograms
	private float height_in_centimeters; //in centimeters
	private int num_times_incaparina_past_week;
	
	private int nan_1_baby_formula; //Is the child taking nan? It's a supplement like incaparina (HANDLE THIS!!!!!!!!)
	
	private int num_times_vegetables_or_fruits_past_week;
	private int num_times_herbs_past_week;
	private int num_times_diarrhea_past_week;
	private int num_times_vomit_past_week;
	private int num_times_cough_past_week;
	private int num_times_fever_past_week;
	private int num_times_other_illness_past_week; 
	private String illness_description;
	
	private float age_last_received_deparasiting_medicine; //Could be String date_when_last_received_deparasiting_medicine 
	
	//REMOVE THESE
	private int receiving_supplements; 
	private int why_receiving_supplements;
	
	public DetailedChildVisit(String child_id) {
		this.child_id = child_id;
		this.temp_child_id = child_id;
		
		this.in_progress = false;
	}
	
	public boolean isIn_progress() {
		return in_progress;
	}

	public void setIn_progress(boolean in_progress) {
		this.in_progress = in_progress;
	}

	public String getChild_id() {
		return child_id;
	}

	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public String getPromoter_id() {
		return promoter_id;
	}

	public void setPromoter_id(String promoter_id) {
		this.promoter_id = promoter_id;
	}

	public int getReceived_all_vaccines() {
		return received_all_vaccines;
	}

	public void setReceived_all_vaccines(int received_all_vaccines) {
		this.received_all_vaccines = received_all_vaccines;
	}

	public String getType_of_vaccines_received() {
		return types_of_vaccines_received;
	}

	public void setType_of_vaccines_received(String types_of_vaccines_received) {
		this.types_of_vaccines_received = types_of_vaccines_received;
	}

	public int getHas_chronic_disease_or_disability() {
		return has_chronic_disease_or_disability;
	}

	public void setHas_chronic_disease_or_disability(
			int has_chronic_disease_or_disability) {
		this.has_chronic_disease_or_disability = has_chronic_disease_or_disability;
	}

	public String getType_of_chronic_disease_or_disability() {
		return type_of_chronic_disease_or_disability;
	}

	public void setType_of_chronic_disease_or_disability(String type_of_chronic_disease_or_disability) {
		this.type_of_chronic_disease_or_disability = type_of_chronic_disease_or_disability;
	}

	public int getIs_currently_breastfed() {
		return is_currently_breastfed;
	}

	public void setIs_currently_breastfed(int is_currently_breastfed) {
		this.is_currently_breastfed = is_currently_breastfed;
	}

	public int getIs_only_breastfed() {
		return is_only_breastfed;
	}

	public void setIs_only_breastfed(int is_only_breastfed) {
		this.is_only_breastfed = is_only_breastfed;
	}

	public float getHow_long_only_breastfed() {
		return how_long_only_breastfed;
	}

	public void setHow_long_only_breastfed(float how_long_only_breastfed) {
		this.how_long_only_breastfed = how_long_only_breastfed;
	}

	public float getChild_age_when_stopped_breastfeeding() {
		return child_age_when_stopped_breastfeeding;
	}

	public void setChild_age_when_stopped_breastfeeding(float child_age_when_stopped_breastfeeding) {
		this.child_age_when_stopped_breastfeeding = child_age_when_stopped_breastfeeding;
	}

	public float getWeight_in_pounds() {
		return weight_in_pounds;
	}

	public void setWeight_in_pounds(float weight_in_pounds) {
		this.weight_in_pounds = weight_in_pounds;
	}
	
	public float getHeight_in_centimeters() {
		return height_in_centimeters;
	}
	
	public void setHeight_in_centimeters(float height_in_centimeters) {
		this.height_in_centimeters = height_in_centimeters;
	}

	public int getNum_times_incaparina_past_week() {
		return num_times_incaparina_past_week;
	}

	public void setNum_times_incaparina_past_week(int num_times_incaparina_past_week) {
		this.num_times_incaparina_past_week = num_times_incaparina_past_week;
	}

	public int getNum_times_vegetables_or_fruits_past_week() {
		return num_times_vegetables_or_fruits_past_week;
	}

	public void setNum_times_vegetables_or_fruits_past_week(
			int num_times_vegetables_or_fruits_past_week) {
		this.num_times_vegetables_or_fruits_past_week = num_times_vegetables_or_fruits_past_week;
	}

	public int getNum_times_herbs_past_week() {
		return num_times_herbs_past_week;
	}

	public void setNum_times_herbs_past_week(int num_times_herbs_past_week) {
		this.num_times_herbs_past_week = num_times_herbs_past_week;
	}

	public int getNum_times_diarrhea_past_week() {
		return num_times_diarrhea_past_week;
	}

	public void setNum_times_diarrhea_past_week(int num_times_diarrhea_past_week) {
		this.num_times_diarrhea_past_week = num_times_diarrhea_past_week;
	}

	public int getNum_times_vomit_past_week() {
		return num_times_vomit_past_week;
	}

	public void setNum_times_vomit_past_week(int num_times_vomit_past_week) {
		this.num_times_vomit_past_week = num_times_vomit_past_week;
	}

	public int getNum_times_cough_past_week() {
		return num_times_cough_past_week;
	}

	public void setNum_times_cough_past_week(int num_times_cough_past_week) {
		this.num_times_cough_past_week = num_times_cough_past_week;
	}

	public int getNum_times_fever_past_week() {
		return num_times_fever_past_week;
	}

	public void setNum_times_fever_past_week(int num_times_fever_past_week) {
		this.num_times_fever_past_week = num_times_fever_past_week;
	}

	public int getNum_times_other_illness_past_week() {
		return num_times_other_illness_past_week;
	}

	public void setNum_times_other_illness_past_week(
			int num_times_other_illness_past_week) {
		this.num_times_other_illness_past_week = num_times_other_illness_past_week;
	}

	public String getIllness_description() {
		return illness_description;
	}

	public void setIllness_description(String illness_description) {
		this.illness_description = illness_description;
	}

	public float getAge_last_received_deparasiting_medicine() {
		return age_last_received_deparasiting_medicine;
	}

	public void setAge_last_received_deparasiting_medicine(
			float age_last_received_deparasiting_medicine) {
		this.age_last_received_deparasiting_medicine = age_last_received_deparasiting_medicine;
	}

	public int getReceiving_supplements() {
		return receiving_supplements;
	}

	public void setReceiving_supplements(int receiving_supplements) {
		this.receiving_supplements = receiving_supplements;
	}

	public int getWhy_receiving_supplements() {
		return why_receiving_supplements;
	}

	public void setWhy_receiving_supplements(int why_receiving_supplements) {
		this.why_receiving_supplements = why_receiving_supplements;
	}

	public String getTemp_child_id() {
		return temp_child_id;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			Field[] fields = DetailedChildVisit.class.getDeclaredFields();
			for (Field field : fields) {
				if (field.get(this) != null)
					obj.put(field.getName(), field.get(this).toString());
			}
		} catch (JSONException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
