package com.stanford.guatemedic;

public class DetailedChildVisit {
	
	private String child_id;
	private String temp_child_id;
	private String visit_date;
	private String promoter_id;
	
	private int did_receive_vaccinations;
	private String vaccination_information;
	private int has_chronic_disease_or_disability;
	private String chronic_disease_or_disability_information;
	
	private int is_currently_breastfed;
	private int is_only_breastfed;
	private float how_long_only_breastfed;
	private float child_age_when_stopped_breastfeeding; //Could be String date_when_stopped breastfeeding
	
	private float weight; //in kilogramss
	private float height; //in centimeters
	private int num_times_incaparina_past_week;
	private int num_times_vegetables_or_fruits_past_week;
	private int num_times_herbs_past_week;
	private int num_times_diarrhea_past_week;
	private int num_times_vomit_past_week;
	private int num_times_cough_past_week;
	private int num_times_fever_past_week;
	private int num_times_other_illness_past_week; 
	private String illness_description;
	
	private float age_last_received_deparasiting_medicine; //Could be String date_when_last_received_deparasiting_medicine 
	
	private int receiving_supplements;
	private int why_receiving_supplements;
	
	public DetailedChildVisit(String child_id) {
		this.child_id = child_id;
		this.child_id = child_id;
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

	public int getDid_receive_vaccinations() {
		return did_receive_vaccinations;
	}

	public void setDid_receive_vaccinations(int did_receive_vaccinations) {
		this.did_receive_vaccinations = did_receive_vaccinations;
	}

	public String getVaccination_information() {
		return vaccination_information;
	}

	public void setVaccination_information(String vaccination_information) {
		this.vaccination_information = vaccination_information;
	}

	public int getHas_chronic_disease_or_disability() {
		return has_chronic_disease_or_disability;
	}

	public void setHas_chronic_disease_or_disability(
			int has_chronic_disease_or_disability) {
		this.has_chronic_disease_or_disability = has_chronic_disease_or_disability;
	}

	public String getChronic_disease_or_disability_information() {
		return chronic_disease_or_disability_information;
	}

	public void setChronic_disease_or_disability_information(
			String chronic_disease_or_disability_information) {
		this.chronic_disease_or_disability_information = chronic_disease_or_disability_information;
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

	public void setChild_age_when_stopped_breastfeeding(
			float child_age_when_stopped_breastfeeding) {
		this.child_age_when_stopped_breastfeeding = child_age_when_stopped_breastfeeding;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
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

}