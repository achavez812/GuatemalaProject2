package com.stanford.guatemedic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.util.PixelUtils;
import com.androidplot.util.ValPixConverter;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.stanford.guatemedic.R.color;

public class GraphActivity extends ActionBarActivity {
	
    private static String child_id;
    private static float new_weight;
    private static float new_height;
    private static Number[] child_age_in_weeks_weight;
    private static Number[] child_age_in_weeks_height;
    private static Number[] child_weight;
    private static Number[] child_height;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		getActionBar().setHomeButtonEnabled(true);
		child_id = getIntent().getStringExtra("child_id");
		String weight = getIntent().getStringExtra("weight");
		if (weight != null && !weight.isEmpty())
			new_weight = Float.parseFloat(weight);
		String height = getIntent().getStringExtra("height");
		if (height != null && !height.isEmpty())
			new_height = Float.parseFloat(height);
		DetailedChild child = DetailedRecordsStore.get(getApplication()).getChild(child_id);
		processVisits(child);
		setTitle(child.getName());
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.left_content, GraphFragmentLeft.newInstance(child_id)).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.right_content, GraphFragmentRight.newInstance(child_id)).commit();
		}
	}
	
	private void processVisits(DetailedChild child) {
		ArrayList<DetailedChildVisit> visits = child.getChild_visits();
		String date_of_birth = child.getDob();
		int birth_weight_plus = 0;
		int birth_height_plus = 0;
		if (date_of_birth != null && !date_of_birth.isEmpty()) {
			double birth_weight = child.getBirth_weight();
			double birth_height = child.getBirth_height();
			if (birth_weight != 0) {
				child_age_in_weeks_weight = new Number[visits.size() + 1];
				child_weight = new Number[visits.size() + 1];
				child_age_in_weeks_weight[0] = 0;
				child_weight[0] = birth_weight;
				birth_weight_plus = 1;
			} else {
				child_age_in_weeks_weight = new Number[visits.size()];
				child_weight = new Number[visits.size()];
			}
			if (birth_height != 0) {
				child_age_in_weeks_height = new Number[visits.size() + 1];
				child_height = new Number[visits.size() + 1];
				child_age_in_weeks_height[0] = 0;
				child_height[0] = birth_height;
				birth_height_plus = 1;
			} else {
				child_height = new Number[visits.size()];
				child_age_in_weeks_height = new Number[visits.size()];
			}
		} else {
			double birth_weight = child.getBirth_weight();
			double birth_height = child.getBirth_height();
			if (birth_weight != 0) {
				child_age_in_weeks_weight = new Number[1];
				child_weight = new Number[1];
				child_age_in_weeks_weight[0] = 0;
				child_weight[0] = GeneralUtilities.round(birth_weight, 2);
				birth_weight_plus = 1;
			} else {
				child_age_in_weeks_weight = new Number[0];
				child_weight = new Number[0];
			}
			if (birth_height != 0) {
				child_age_in_weeks_height = new Number[1];
				child_height = new Number[1];
				child_age_in_weeks_height[0] = 0;
				child_height[0] = GeneralUtilities.round(birth_height, 2);
				birth_height_plus = 1;
			} else {
				child_height = new Number[0];
				child_age_in_weeks_height = new Number[0];
			}
		}
		for (int i = 0; i < visits.size(); i++) {
			DetailedChildVisit visit = visits.get(i);
			String visit_date = visit.getVisit_date();
			double weight = visit.getWeight_in_pounds();
			double height = visit.getHeight_in_centimeters();
			int time_between = (int)DateTimeUtilities.timeBetweenInDays(date_of_birth, visit_date);	
			double age_in_weeks = DateTimeUtilities.convertDaysToWeeks(time_between);
			child_age_in_weeks_weight[i + birth_weight_plus] = GeneralUtilities.round(age_in_weeks, 1);
			child_age_in_weeks_height[i + birth_height_plus] = GeneralUtilities.round(age_in_weeks, 1);
			child_weight[i + birth_weight_plus] = GeneralUtilities.round(weight, 2); //convert here
			child_height[i + birth_height_plus] = GeneralUtilities.round(height, 2); //convert here
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.graph_menu_bar, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplication(), ViewFamilyActivity.class);
		i.putExtra("family_id", DetailedRecordsStore.get(getApplication()).getChild(child_id).getFamily_id());
		startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			return true;
		}
		if (id == R.id.action_addvisit) {
			Intent i = new Intent(getApplication(), AddNewChildVisitSinglePageActivity.class);
			i.putExtra("child_id", child_id);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class NewChildGraphDialog extends DialogFragment {
	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    
		    // Get the layout inflater
		    LayoutInflater inflater = getActivity().getLayoutInflater();  
		    final View v = inflater.inflate(R.layout.fragment_graph_new_child_dialog, null);
	
		    // Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		    AlertDialog.Builder view = builder.setView(v);

			//Set stuff up
		    return builder.create();
		}
	}
	
	public static double calculate_weight_z_score(double weight, double age_days, int sex) {
		Number[] mean_data = null;
		Number[] standard_deviations = null;
		
		//This data is for every 3 days
		if (sex == 1) {
			mean_data = ChildDataStoreMean.male_weight_lb_daily_mean_data;
			standard_deviations = ChildDataStoreSD.male_weight_lb_daily_sd;
		} else { //girl or unknown
			mean_data = ChildDataStoreMean.female_weight_lb_daily_mean_data;
			standard_deviations = ChildDataStoreSD.female_weight_lb_daily_sd;
		}
		int index = (int)(age_days / 3); //every three days of data
		if (index > mean_data.length)
			return -10;
		double mean = mean_data[index].doubleValue();
		double sd = standard_deviations[index].doubleValue();
		return (weight - mean) / sd;
	}
	
	public static double calculate_height_z_score(double height, double age_days, int sex) {
		Number[] mean_data = null;
		Number[] standard_deviations = null;
		
		//This data is for every 3 days
		if (sex == 1) {
			mean_data = ChildDataStoreMean.male_height_cm_daily_mean_data;
			standard_deviations = ChildDataStoreSD.male_height_cm_daily_sd;
		} else { //girl or unknown
			mean_data = ChildDataStoreMean.female_height_cm_daily_mean_data;
			standard_deviations = ChildDataStoreSD.female_height_cm_daily_sd;
		}
		
		int index = (int)(age_days / 3);
		if (index > mean_data.length)
			return -10;
		double mean = mean_data[index].doubleValue();
		double sd = standard_deviations[index].doubleValue();
		
		return (height - mean) / sd;
	}
	
	public static double calculate_other_score() {
		return 0;
	}

	
	public static class GraphFragmentLeft extends Fragment {
		
		private static String child_id;

		public GraphFragmentLeft() {}
		
		public static GraphFragmentLeft newInstance(String child_id) {
			GraphFragmentLeft f = new GraphFragmentLeft();
			Bundle args =  new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle args = getArguments();
			this.child_id = args.getString("child_id");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_graph_left, container,false);
			
			DetailedChild child = DetailedRecordsStore.get(getActivity()).getChild(child_id);
			int gender = child.getGender();
			String dob = child.getDob();
			ArrayList<DetailedChildVisit> child_visits = child.getChild_visits();
			
			ImageView image_field = (ImageView)rootView.findViewById(R.id.graph_child_image);
			
			TextView sex_field = (TextView)rootView.findViewById(R.id.graph_child_sex);
			if (gender == 1) {
				sex_field.setText("Niño");
			} else if (gender == 2) {
				sex_field.setText("Niña");
			} else if (gender == 0) {
				sex_field.setText("No Sexo");
			}
			
			if (dob != null && !dob.isEmpty()) {
				String formatted_dob = DateTimeUtilities.formatDateForDisplay(dob);
				TextView dob_field = (TextView)rootView.findViewById(R.id.graph_child_dob);
				dob_field.setText(formatted_dob);
			} else {
				TextView dob_field = (TextView)rootView.findViewById(R.id.graph_child_dob);
				dob_field.setText("");
			}
			
			DetailedChildVisit dcv = null;
			if (!child_visits.isEmpty()) 
				dcv = child_visits.get(child_visits.size() - 1);
			
			TextView visit_header_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_header);
			if (dcv == null) { 
				if (child_age_in_weeks_weight.length > 0 || child_age_in_weeks_height.length > 0) {
					visit_header_field.setText("Visita de Nacimiento (" + DateTimeUtilities.formatDateForDisplay(child.getDate_created()) + ")");
					TextView age_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_age);
					age_field.setText("Edad: " + 0 + " semanas");
					if (child_age_in_weeks_weight.length > 0) {
						double weight = child_weight[0].doubleValue();
						TextView weight_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight);
						weight_field.setText("Peso: " + weight + " libras");
						double weight_z_score = GeneralUtilities.round(calculate_weight_z_score(weight, 0, gender), 2);
						TextView weight_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight_z_score);
						if (weight_z_score == -10)
							weight_z_score_field.setText("El Niño es mayor de 5 Años.");
						else
							weight_z_score_field.setText("Grado " + weight_z_score + " de peso");
						
					} 
					if (child_age_in_weeks_height.length > 0) {
						double height = child_height[0].doubleValue();
						TextView height_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height);
						height_field.setText("Talla: " + height + " cm");
						double height_z_score = GeneralUtilities.round(calculate_height_z_score(height, 0, gender), 2);
						TextView height_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height_z_score);
						if (height_z_score != -10)
							height_z_score_field.setText("Grado " + height_z_score + " de talla");
					}
					
				} else {
					visit_header_field.setText("No Hay Visitos");
				}
			} else {
				String visit_date = dcv.getVisit_date();
				String formatted_visit_date = DateTimeUtilities.formatDateForDisplay(DateTimeUtilities.getCurrentDateTimeString());
				int length =  child_age_in_weeks_weight.length;
				double age_in_weeks = child_age_in_weeks_weight[child_age_in_weeks_weight.length - 1].doubleValue(); //days
				double age_in_days = age_in_weeks * 7;
				double age_in_months = age_in_days / 30.4167;
				double weight = child_weight[child_weight.length - 1].doubleValue();
				double height = child_height[child_height.length - 1].doubleValue();
								
				visit_header_field.setText("Última Visita (" + formatted_visit_date + ")"); //Format visit date
				
				TextView age_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_age);
				age_field.setText("Edad: " + GeneralUtilities.round(age_in_months, 1) + " meses");
				
				TextView weight_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight);
				weight_field.setText("Peso: " + weight + " libras");
				
				TextView height_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height);
				height_field.setText("Talla: " + height + " cm");
				
				double prev_age_in_weeks = 0;
				double prev_age_in_days = 0;
				double prev_weight_z_score = 0;
				if (length > 2) {
					prev_age_in_weeks = child_age_in_weeks_weight[child_age_in_weeks_weight.length - 2].doubleValue();
					prev_age_in_days = prev_age_in_weeks * 7;
					prev_weight_z_score = GeneralUtilities.round(calculate_weight_z_score(child_weight[child_weight.length - 2].doubleValue(), prev_age_in_days, gender), 2);

				}

				double weight_z_score = GeneralUtilities.round(calculate_weight_z_score(weight, age_in_days, gender), 2);
				 
				double height_z_score = GeneralUtilities.round(calculate_height_z_score(height, age_in_days, gender), 2);
				if (weight_z_score != -10 && height_z_score != -10) {
					String weight_grade = getGradeFromZScore(weight_z_score);
					String height_grade = getGradeFromZScore(height_z_score);

					TextView weight_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight_z_score);
					weight_z_score_field.setText("Grado " + weight_grade + " de peso");

					TextView height_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height_z_score);
					height_z_score_field.setText("Grado " + height_grade+ " de talla");
				}
				
				
				Predictor predictor = new Predictor();
				predictor.setAge(age_in_days);
				predictor.setMonth(Integer.parseInt(formatted_visit_date.substring(3,5)));
				predictor.setWAZ(weight_z_score, prev_weight_z_score);
				predictor.setITT(false);
				double no_value = predictor.predictWAZGain();
				predictor.setITT(true);
				double yes_value = predictor.predictWAZGain();
				
				double z_yes = GeneralUtilities.round(weight_z_score + yes_value, 3);
				double z_no = GeneralUtilities.round(weight_z_score + no_value, 3);
				
				TextView recommendation_field = (TextView)rootView.findViewById(R.id.graph_child_reccomendation_info);
				recommendation_field.setText("Basado de los datos, recomendamos que el Niño..\n" + "Con Suplemento: " + z_yes + "\nSin Suplemento: " + z_no);
				//Assign variables
				
			}
			return rootView;
		}

		private String getGradeFromZScore(double weight_z_score) {
			if (weight_z_score > -1) return "Normal";
			if (weight_z_score > -2) return "Primer";
			if (weight_z_score > -3) return "Segundo";
			return "Tercer";
		}
	}
	

	public static class GraphFragmentRight extends Fragment {
		
		protected static final Integer TERCERO_COLOR = Color.rgb(200, 0, 0);
		protected static final Integer SEGUNDO_COLOR = Color.rgb(220, 150, 0);
		protected static final Integer PRIMERO_COLOR = Color.rgb(200, 200, 0);
		protected static final Integer NORMAL_COLOR = Color.rgb(0, 180, 0);
		int selected;
		int sex;
		double age_in_months;
		
		static XYPlot plot;
		
		public GraphFragmentRight() {
			selected = 1;
		}
		
		public static GraphFragmentRight newInstance(String child_id) {
			GraphFragmentRight f = new GraphFragmentRight();		
			Bundle args =  new Bundle();
			args.putString("child_id", child_id);
			f.setArguments(args);
			return f;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);	
			Bundle args = getArguments();
			String child_id = args.getString("child_id");
			DetailedChild child = DetailedRecordsStore.get(getActivity()).getChild(child_id);
			String dob = child.getDob();
			if (dob != null && !dob.isEmpty()) {
				int age_in_days = (int)DateTimeUtilities.getCurrentAgeInDays(dob);
				age_in_months = DateTimeUtilities.convertDaysToMonths(age_in_days);
			} else {
				age_in_months = -1;
			}
			sex = child.getGender();
		}
		
		public Number[] makeXArray(int length, double factor) {
			Number[] newArray = new Number[length];
			for (int i= 0; i < newArray.length; i++)
				newArray[i] = i * factor;
			return newArray;
		}
		
		public Number[] convertArray(Number[] arr, double factor) {
			Number[] newArr = new Number[arr.length];
			for (int i = 0; i < arr.length; i++)
				newArr[i] = arr[i].doubleValue() * factor;
			return newArr;
		}
		
		private void addGuideLinesToPlot(Number[][] data) {
			Number[] x_array = makeXArray(data[0].length, 0.526);
			for (int i = data.length-2; i > 0; i--)  {
				XYSeries series = null;
				LineAndPointFormatter seriesFormat = null;
				if (i == 1) { //z-score = -3
					series = new SimpleXYSeries(Arrays.asList(x_array), Arrays.asList(data[i]), " 3");
					seriesFormat = new LineAndPointFormatter(TERCERO_COLOR, TERCERO_COLOR, null, null);
				} else if (i == 2) {// z-score = -2		
					series = new SimpleXYSeries(Arrays.asList(x_array), Arrays.asList(data[i]), " 2");
					seriesFormat = new LineAndPointFormatter(SEGUNDO_COLOR, SEGUNDO_COLOR, null, null);
				} else if (i == 3) {//z-score = -1
					series = new SimpleXYSeries(Arrays.asList(x_array), Arrays.asList(data[i]), " 1");
					seriesFormat = new LineAndPointFormatter(PRIMERO_COLOR, PRIMERO_COLOR, null, null);
				} else if (i == 4) { //z-score = 0
					series = new SimpleXYSeries(Arrays.asList(x_array), Arrays.asList(data[i]), " 0");
					seriesFormat = new LineAndPointFormatter(NORMAL_COLOR, NORMAL_COLOR, null, null);
				}
				seriesFormat.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(0));
				plot.addSeries(series, seriesFormat);
			}
		}

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_graph_right, container,false);
			plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
	        addLegend();
			
			final TextView weight_view = (TextView)rootView.findViewById(R.id.tab_weight);
			final TextView height_view = (TextView)rootView.findViewById(R.id.tab_height);
			
			plot.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent e) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						if (selected == 1) {
							System.out.println("ONE ONE" + e.getX());
							drawWeightPlot(weight_view, height_view, e.getX(), e.getY());
						}
						if (selected == 2) {
							System.out.println("TWO TWO" + e.getX());
							drawHeightPlot(weight_view, height_view, e.getX(), e.getY());
						}
					}
					return true;
				}
			});
			
			weight_view.setOnClickListener(new View.OnClickListener() {		
				@Override
				public void onClick(View v) {
					selected = 1;
					drawWeightPlot(weight_view, height_view, null, null);  
				}
			});
			
			height_view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					selected = 2;
					drawHeightPlot(weight_view, height_view, null, null);
				}
			});
			
			if (selected == 1) weight_view.performClick();
			else if (selected == 2) height_view.performClick();
			return rootView;
		}

		
		private void drawWeightPlot(final TextView weight_view,
				final TextView height_view, final Float clickX, final Float clickY) {
			plot.clear();
			weight_view.setBackgroundResource(color.gray_medium);
			height_view.setBackgroundResource(color.gray_dark);
			plot.setRangeLabel("Peso (libras)");	
			Number[][] data;
			if (sex == 1) {
				data = ChildDataStoreGraph.male_weight_lb_graph_data;
			} else {
				data = ChildDataStoreGraph.female_weight_lb_graph_data;
			}
			addGuideLinesToPlot(data);
			if ((child_age_in_weeks_weight[child_age_in_weeks_weight.length - 1]).doubleValue() < 260.0) {
				Number[] child_x_array = convertArray(child_age_in_weeks_weight, 0.23);
				XYSeries series = new SimpleXYSeries(Arrays.asList(child_x_array), Arrays.asList(child_weight), null);
				PointLabelFormatter plf = new PointLabelFormatter(Color.WHITE, 0, -15);
				LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.BLACK, Color.BLUE, null, plf);
				seriesFormat.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
				final int clickedPoint = getClickedPoint(clickX, clickY, series);
				seriesFormat.setPointLabeler(new PointLabeler(){
					@Override
					public String getLabel(XYSeries series, int index) {
						Number age = series.getX(index);
						Number weight = series.getY(index);
						String label = String.format(Locale.US, "Peso %.0f", weight);
						return index == clickedPoint ? label : "";
					}
				});
				plot.addSeries(series, seriesFormat);
			}
			plot.setTitle("");
			plot.setRangeBottomMax(3);
			plot.setDomainLeftMin(0);
	        plot.setTicksPerRangeLabel(2);
	        plot.setTicksPerDomainLabel(6);
	        plot.getGraphWidget().setDomainLabelOrientation(-45);
			plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
			plot.setDomainValueFormat(new DecimalFormat(""));
			plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
			plot.setRangeValueFormat(new DecimalFormat(""));
	        plot.redraw();
		} 
		
		private void drawHeightPlot(final TextView weight_view,
				final TextView height_view, final Float clickX, final Float clickY) {
			plot.clear();
			height_view.setBackgroundResource(color.gray_medium);
			weight_view.setBackgroundResource(color.gray_dark);
			plot.setRangeLabel("Talla (cm)");
			Number[][] data;
			if (sex == 1) {
				data = ChildDataStoreGraph.male_height_cm_graph_data;
			} else {
				data = ChildDataStoreGraph.female_height_cm_graph_data;
			}
			addGuideLinesToPlot(data);
			if ((child_age_in_weeks_height[child_age_in_weeks_height.length - 1]).doubleValue() < 260.0) {
				Number[] child_x_array = convertArray(child_age_in_weeks_height, 0.23);
				XYSeries series = new SimpleXYSeries(Arrays.asList(child_x_array), Arrays.asList(child_height), "Child");
				PointLabelFormatter plf = new PointLabelFormatter(Color.WHITE, 0, -15);
				LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.BLACK, Color.BLUE, null, plf);
				seriesFormat.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
				final int clickedPoint = getClickedPoint(clickX, clickY, series);
				seriesFormat.setPointLabeler(new PointLabeler(){
					@Override
					public String getLabel(XYSeries series, int index) {
						//Number age = series.getX(index);
						Number height = series.getY(index);
						String label = String.format(Locale.US, "Talla %.0f", height);
						return index == clickedPoint ? label : "";
					}
				});
				plot.addSeries(series, seriesFormat);	
			}	
			plot.setTitle("");
			plot.setRangeBottomMax(40);
			plot.setDomainLeftMin(0);
	        plot.setTicksPerRangeLabel(2);
	        plot.setTicksPerDomainLabel(6);
	        plot.getGraphWidget().setDomainLabelOrientation(-45);
			plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
			plot.setDomainValueFormat(new DecimalFormat(""));
			plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 5);
			plot.setRangeValueFormat(new DecimalFormat(""));
	        plot.redraw();
		}
		
		private int getClickedPoint(Float clickX, Float clickY, XYSeries series) {
			if (clickX == null || clickY == null) return -1;
			double maxX = plot.getCalculatedMaxX().doubleValue();
			double minX = plot.getCalculatedMinX().doubleValue();
			clickX = Math.max(0, clickX - plot.getGraphWidget().getGridRect().left);
			System.out.println("CLICK: " + clickX + " maxX " + maxX + " minX " + minX + " width ");
			double clickedXValue = ValPixConverter.pixToVal(clickX, minX, maxX, plot.getGraphWidget().getGridRect().width(), false);
			System.out.println("XVALUE: " + clickedXValue);
			int pointIndex = -1;
			double minDistance = 0;
			for (int i = 0; i < series.size(); i++) {
				double distance = Math.abs(clickedXValue - series.getX(i).doubleValue());
				if (i == 0) {
					pointIndex = 0;
					minDistance = distance;
				} else {
					if (distance < minDistance) {
						pointIndex = i;
						minDistance = distance;
					}
				}
			}
			
			return pointIndex;
		}

		private void addLegend() {
			plot.getLegendWidget().setTableModel(new DynamicTableModel(1, 4));

	        // add a semi-transparent black background to the legend
	        // so it's easier to see overlaid on top of our plot:
	        Paint bgPaint = new Paint();
	        bgPaint.setColor(Color.BLACK);
	        bgPaint.setStyle(Paint.Style.FILL);
	        bgPaint.setAlpha(140);
	        plot.getLegendWidget().setBackgroundPaint(bgPaint);
	 
	        // adjust the padding of the legend widget to look a little nicer:
	        plot.getLegendWidget().setPadding(15, 15, 15, 15);       
	        plot.getLegendWidget().setSize(new SizeMetrics(220, SizeLayoutType.ABSOLUTE, 90, SizeLayoutType.ABSOLUTE));
	        plot.getLegendWidget().position(90, XLayoutStyle.ABSOLUTE_FROM_RIGHT,
	                120, YLayoutStyle.ABSOLUTE_FROM_BOTTOM,
	                AnchorPosition.RIGHT_BOTTOM);
		}
	}
}


