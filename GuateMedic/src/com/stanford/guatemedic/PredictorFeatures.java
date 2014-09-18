package com.stanford.guatemedic;

public class PredictorFeatures {

	// Mean values for predictors. Default to these in case of missing data.
	
	private static final int MONTHS_IN_YEAR = 12;
	
	private static final double WAZ_CURRENT_DEFAULT = -1.01;
	private static final double WAZ_DRIFT_DEFAULT = -0.03;
	private static final double HAZ_CURRENT_DEFAULT = -2.24;
	private static final double HAZ_DRIFT_DEFAULT = -0.04;
	private static final double AGE_CENT_DEFAULT = 0;
	private static final double ITT_MULTIPLIER_DEFAULT = 144;
	private static final double SIN_DEFAULT = -0.01;
	private static final double COS_DEFAULT = -0.08;	
	
	// Useful constants
	private static final double AGE_MEAN = 873.6239;
	private static final double TWO_YEARS = 730;

	public double WAZCurrent = Double.NaN;
	public double WAZDrift = Double.NaN;
	public double HAZCurrent = Double.NaN;
	public double HAZDrift = Double.NaN;
	
	public double ageCentered = Double.NaN;
	
	public double sinMonth = Double.NaN;
	public double cosMonth = Double.NaN;
	
	public boolean ITT = false;
	// Age-dependent multiplier for ITT effect
	// (currently decays linearly from 0 to 2 y and then plateaus)
	public double ITTMultiplier = Double.NaN;
	
	public PredictorFeatures(double WAZCurrent,
										   double WAZPrev,
										   double HAZCurrent,
										   double HAZPrev,
										   double ageInDays,
										   double visitMonth,
										   boolean ITT) {
										   
		if(!Double.isNaN(WAZCurrent)) {
			this.WAZCurrent = WAZCurrent;
			if(!Double.isNaN(WAZPrev)) {
				this.WAZDrift = WAZCurrent - WAZPrev;
			} else {
				this.WAZDrift = WAZ_DRIFT_DEFAULT;
			}
		} else {
			this.WAZCurrent = WAZ_CURRENT_DEFAULT;
			this.WAZDrift = WAZ_DRIFT_DEFAULT;
		}
		
		if(!Double.isNaN(HAZCurrent)) {
			this.HAZCurrent = HAZCurrent;
			if(!Double.isNaN(HAZPrev)) {
				this.HAZDrift = HAZCurrent - HAZPrev;
			} else {
				this.HAZDrift = HAZ_DRIFT_DEFAULT;
			}
		} else {
			this.HAZCurrent = HAZ_CURRENT_DEFAULT;
			this.HAZDrift = HAZ_DRIFT_DEFAULT;
		}
		
		if(!Double.isNaN(ageInDays)) {
			this.ageCentered = ageInDays - AGE_MEAN;
			this.ITTMultiplier = -Math.min(0, ageInDays - TWO_YEARS);
		} else {
			this.ageCentered = AGE_CENT_DEFAULT;
			this.ITTMultiplier = ITT_MULTIPLIER_DEFAULT;
		}
		
		if(!Double.isNaN(visitMonth)) {
			this.sinMonth = Math.sin(2 * Math.PI * visitMonth / MONTHS_IN_YEAR);
			this.cosMonth = Math.sin(2 * Math.PI * visitMonth / MONTHS_IN_YEAR);
		} else {
			this.sinMonth = SIN_DEFAULT;
			this.cosMonth = COS_DEFAULT;
		}
		
		this.ITT = ITT;
	}
}
