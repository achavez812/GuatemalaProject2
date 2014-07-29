package com.stanford.guatemedic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeneralUtilities {
	
	public static final double kilograms_in_pound = 2.2046;
	
	public static double kilogramsToPounds(double kilograms) {
		return kilograms * kilograms_in_pound;
	}
	
	public static double poundsToKilograms(double pounds) {
		return pounds / kilograms_in_pound;
	}
	
	//value is the number that is to be rounded
	//places is the number of decimal places desired
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
