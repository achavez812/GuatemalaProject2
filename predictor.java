
public class PredictorFeatures {

	# Mean values for predictors. Default to these in case of missing data.
	
	private static final double WAZ_CURRENT_DEFAULT = -1.01;
	private static final double WAZ_DRIFT_DEFAULT = -0.03;
	private static final double HAZ_CURRENT_DEFAULT = -2.24;
	private static final double HAZ_DRIFT_DEFAULT = -0.04;
	private static final double AGE_CENT_DEFAULT = 0;
	private static final double ITT_MULTIPLIER_DEFAULT = 144;
	private static final double SIN_DEFAULT = -0.01;
	private static final double COS_DEFAULT = -0.08;	
	
	# Useful constants
	private static final double AGE_MEAN = 873.6239;
	private static final double TWO_YEARS = 730;

	public double WAZCurrent = Double.NaN;
	public double WAZDrift = Double.NaN;
	public double HAZCurrent = Double.NaN;
	public double HAZDrift = Double.NaN;
	
	public double ageCentered = Double.NaN;
	
	public double sinMonth = Double.NaN;
	public double cosMonth = Double.NaN;
	
	public bool ITT = FALSE;
	# Age-dependent multiplier for ITT effect
	# (currently decays linearly from 0 to 2 y and then plateaus)
	public double ITTMultiplier = Double.NaN;
	
	public PredictorFeatures(double WAZCurrent,
										   double WAZPrev,
										   double HAZCurrent,
										   double HAZPrev,
										   double ageInDays,
										   double visitMonth,
										   bool ITT) {
										   
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
			this.sinMonth = Math.sin(2 * Math.PI * visitMonth / MONTHS_IN_YEAR)
			this.cosMonth = Math.sin(2 * Math.PI * visitMonth / MONTHS_IN_YEAR)
		} else {
			this.sinMonth = SIN_DEFAULT;
			this.cosMonth = COS_DEFAULT;
		}
		
		this.ITT = ITT;
	}
}

public class Predictor {

	# Current and Previous weight z-scores
	private double WAZCurrent = Double.NaN;
	private double WAZPrev = Double.NaN;
	
	# Current and Previous height z-scores
	private double HAZCurrent = Double.NaN;
	private double HAZPrev = Double.NaN;
	
	# Current age of child in days
	private double ageInDays = Double.NaN;
	
	# Month of Current visit
	private double visitMonth = Double.NaN;
	
	# Is the child supposed to get supplementary feeding ("intention to treat")
	private bool ITT = FALSE;
	
	# Passing in Double.NaN is allowed for either argument. This should be used,
	# for example, if the child is visiting for the first time.
	# (This applies to all setters.)
	
	public void setWAZ(double WAZCurrent, double WAZPrev) {
		this.WAZCurrent = WAZCurrent;
		this.WAZPred = WAZPrev;
	}
	
	public void setHAZ(double HAZCurrent, double HAZPrev) {
		this.HAZCurrent = HAZCurrent;
		this.HAZPred = HAZPrev;
	}
	
	public void setAge(double ageInDays) {
		this.ageInDays = ageInDays;
	}
	
	public void setMonth(double visitMonth) {
		this.visitMonth = visitMonth;
	}
		
	public double predictWAZGain() {
		return predWAZ(PredictorFeatures(WAZCurrent, WAZPrev, HAZCurrent, HAZPrev, ageInDays, visitMonth, ITT));
	}
	
	public double predictHAZGain() {
		return predHAZ(PredictorFeatures(WAZCurrent, WAZPrev, HAZCurrent, HAZPrev, ageInDays, visitMonth, ITT));
	}
	
	# The private methods below encode the predictive model
	
	private double predWAZ(PredictorFeatures feat) {
		double WAZDriftAcute = Math.min(0, feat.WAZDrfit + 0.5);
		
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
								0.18 * feat.WAZdrift +
								-0.00587 * feat.HAZdrift +
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
