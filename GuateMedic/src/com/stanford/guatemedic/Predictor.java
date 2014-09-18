package com.stanford.guatemedic;



public class Predictor {

	// Current and Previous weight z-scores
	private double WAZCurrent = Double.NaN;
	private double WAZPrev = Double.NaN;
	
	// Current and Previous height z-scores
	private double HAZCurrent = Double.NaN;
	private double HAZPrev = Double.NaN;
	
	// Current age of child in days
	private double ageInDays = Double.NaN;
	
	// Month of Current visit
	private double visitMonth = Double.NaN;
	
	// Is the child supposed to get supplementary feeding ("intention to treat")
	private boolean ITT = false;
	
	// Passing in Double.NaN is allowed for either argument. This should be used,
	// for example, if the child is visiting for the first time.
	// (This applies to all setters.)
	
	public void setITT(boolean ITT) {
		this.ITT = ITT;
	}
	
	public void setWAZ(double WAZCurrent, double WAZPrev) {
		this.WAZCurrent = WAZCurrent;
		this.WAZPrev = WAZPrev;
	}
	
	public void setHAZ(double HAZCurrent, double HAZPrev) {
		this.HAZCurrent = HAZCurrent;
		this.HAZPrev = HAZPrev;
	}
	
	public void setAge(double ageInDays) {
		this.ageInDays = ageInDays;
	}
	
	public void setMonth(double visitMonth) {
		this.visitMonth = visitMonth;
	}
		
	public double predictWAZGain() {
		return predWAZ(new PredictorFeatures(WAZCurrent, WAZPrev, HAZCurrent, HAZPrev, ageInDays, visitMonth, ITT));
	}
	
	public double predictHAZGain() {
		return predHAZ(new PredictorFeatures(WAZCurrent, WAZPrev, HAZCurrent, HAZPrev, ageInDays, visitMonth, ITT));
	}
	
	// The private methods below encode the predictive model
	
	private double predWAZ(PredictorFeatures feat) {
		double WAZDriftAcute = Math.min(0, feat.WAZDrift + 0.5);
		
		double ITTEffect = 0;
		if (feat.ITT) {
			ITTEffect = -0.000099 * feat.ITTMultiplier;
		} else {
			ITTEffect = 0.00012 * feat.ITTMultiplier;
		}
		
		double pred = -0.054 +
								0.000093 * feat.ageCentered +
								-0.0767 * feat.WAZCurrent +
								0.0255 * feat.HAZCurrent +
								0.18 * feat.WAZDrift +
								-0.00587 * feat.HAZDrift +
								-0.021 * feat.sinMonth +
								-0.028 * feat.cosMonth +
								-0.0188 * feat.WAZCurrent * feat.WAZCurrent +
								0.000077 * feat.ageCentered * feat.WAZCurrent +
								-0.00027 * feat.ageCentered * feat.WAZDrift +
								-0.0068 * feat.WAZCurrent * feat.HAZCurrent +
								0.014 * feat.HAZCurrent * feat.WAZDrift +
								0.0032 * feat.WAZCurrent * feat.HAZDrift +
								0.027 * feat.HAZDrift * feat.WAZDrift +
								0.00038 * feat.ageCentered * WAZDriftAcute +
								ITTEffect;
								
		return pred;			
	}
	
	private double predHAZ(PredictorFeatures feat) {
	  return 0;
	}
}