package com.stanford.guatemedic;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedFamilyVisit {
	
	private String family_id;
	private String temp_family_id; //Read only, Set in constructor
	private String visit_date;
	private String promoter_id;
	
	private int parent1_marital_status; // Together, Married, Widowed, Single
	private int does_father_live_with; //boolean
	private int num_pregnancies;
	private int num_children_alive;
	private int num_children_dead;
	private String how_children_died; //Age and why died?
	private int num_children_under_5;
	private int num_people_in_household;
	
	private int fathers_job; //Should this be a String instead
	private int has_igss; //Should this be a String instead

	public DetailedFamilyVisit(String family_id) {
		this.family_id = family_id;
		this.temp_family_id = family_id;
	}

	public String getFamily_id() {
		return family_id;
	}

	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public int getParent1_marital_status() {
		return parent1_marital_status;
	}

	public void setParent1_marital_status(int parent1_marital_status) {
		this.parent1_marital_status = parent1_marital_status;
	}

	public int getDoes_father_lives_with() {
		return does_father_live_with;
	}

	public void setDoes_father_live_with(int does_father_live_with) {
		this.does_father_live_with = does_father_live_with;
	}

	public int getNum_pregnancies() {
		return num_pregnancies;
	}

	public void setNum_pregnancies(int num_pregnancies) {
		this.num_pregnancies = num_pregnancies;
	}

	public int getNum_children_alive() {
		return num_children_alive;
	}

	public void setNum_children_alive(int num_children_alive) {
		this.num_children_alive = num_children_alive;
	}

	public int getNum_children_dead() {
		return num_children_dead;
	}

	public void setNum_children_dead(int num_children_dead) {
		this.num_children_dead = num_children_dead;
	}

	public String getHow_children_died() {
		return how_children_died;
	}

	public void setHow_children_died(String how_children_died) {
		this.how_children_died = how_children_died;
	}

	public int getNum_children_under_5() {
		return num_children_under_5;
	}

	public void setNum_children_under_5(int num_children_under_5) {
		this.num_children_under_5 = num_children_under_5;
	}

	public int getNum_people_in_household() {
		return num_people_in_household;
	}

	public void setNum_people_in_household(int num_people_in_household) {
		this.num_people_in_household = num_people_in_household;
	}

	public int getFathers_job() {
		return fathers_job;
	}

	public void setFathers_job(int fathers_job) {
		this.fathers_job = fathers_job;
	}

	public int getHas_igss() {
		return has_igss;
	}

	public void setHas_igss(int igss) {
		this.has_igss = has_igss;
	}

	public String getTemp_family_id() {
		return temp_family_id;
	}

	public String getPromoter_id() {
		return promoter_id;
	}

	public void setPromoter_id(String promoter_id) {
		this.promoter_id = promoter_id;
	}
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			Field[] fields = DetailedFamilyVisit.class.getDeclaredFields();
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
