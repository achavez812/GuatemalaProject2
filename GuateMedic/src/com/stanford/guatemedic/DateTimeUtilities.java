package com.stanford.guatemedic;

import java.util.Calendar;

public class DateTimeUtilities {
	
	private static final double days_in_week = 7;
	private static final double days_in_month = 30.4167;
	private static final double days_in_year = 365.2425;
	
	//This is for backend storage
	//Used for things such as visit_date, date_last_modified, date_created
	public static String getCurrentDateTimeString() {
		//Include argument for TimeZone or Locale when calling getInstance()
		Calendar calendar = Calendar.getInstance(); 
		
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		
		String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		if (month.length() == 1) month = 0 + month;
		
		String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) day = 0 + day;
		
		String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		if (hour.length() == 1) hour = 0 + hour;
		
		String minute = Integer.toString(calendar.get(Calendar.MINUTE));
		if (minute.length() == 1) minute = 0 + minute;
		
		String second = Integer.toString(calendar.get(Calendar.SECOND));
		if (second.length() == 1) second = 0 + second;
		
		return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
	}
	
	public static String getBirthDateFromAge(int age) {
		String current_date = getCurrentDateTimeString();
		
		String current_year = current_date.substring(0, 4);
		String birth_year = Integer.toString(Integer.valueOf(current_year) - age);
		
		return current_date.replace(current_year, birth_year);
	}
	
	//Assumes format passed in is yyyy-mm-dd
	//Removes anything after first 10 characters
	//returns it as dd/mm/yyyy 
	public static String formatDateForDisplay(String date) {
		return date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);
	}
	
	//This format is also used for calculations such as timeBetween
	//Assumes format is display format (dd/mm/yyyy)
	//Returns it as yyyy-mm-ddT00:00:00
	public static String formatDateForBackend(String date) {
		return date.substring(6,10) + "-" + date.substring(3,5) + "-" + date.substring(0,2) + "T00:00:00";
	}
	
	//Expects birth_date in backend format
	public static double getCurrentAgeInDays(String birth_date) {
		return timeBetweenInDays(birth_date, getCurrentDateTimeString());
	}
	
	//Expects date1 and date2 in backend format (yyyy-MM-dd)
	//It ignores the time if it is present
	//date1 should be the older date
	//date2 should be the newer date
	public static double timeBetweenInDays(String date1, String date2) {
		int year1 = Integer.parseInt(date1.substring(0,4));
		int month1 = Integer.parseInt(date1.substring(5,7));
		int day1 = Integer.parseInt(date1.substring(8,10));
		
		int year2 = Integer.parseInt(date2.substring(0,4));
		int month2 = Integer.parseInt(date2.substring(5,7));
		int day2 = Integer.parseInt(date2.substring(8,10));
		
		int years = year2 - year1;
		int months = month2 - month1;
		int days = day2 - day1;
		
		return years * days_in_year + months * days_in_month + days;
	}
	
	public static double convertDaysToWeeks(int days) {
		return days / days_in_week;
	}
	
	public static double convertDaysToMonths(int days) {
		return days / days_in_month;
	}
	
	public static double convertDaysToYears(int days) {
		return days / days_in_year;
	}

}
