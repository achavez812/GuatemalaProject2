package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.stanford.guatemedic.R.color;

public class GraphActivity extends ActionBarActivity {
	
    private static String child_id;
    private static Number[] child_age_in_weeks_weight;
    private static Number[] child_age_in_weeks_height;
    private static Number[] child_weight;
    private static Number[] child_height;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		getActionBar().setHomeButtonEnabled(true);
		child_id = getIntent().getStringExtra("child_id");
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
				child_weight[0] = Utilities.round(birth_weight, 2);
				birth_weight_plus = 1;
			} else {
				child_age_in_weeks_weight = new Number[0];
				child_weight = new Number[0];
			}
			
			if (birth_height != 0) {
				child_age_in_weeks_height = new Number[1];
				child_height = new Number[1];
				child_age_in_weeks_height[0] = 0;
				child_height[0] = Utilities.round(birth_height, 2);
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
			double age_in_weeks = Utilities.convertMonthsToWeeks(Utilities.timeBetween(Utilities.formatDate(date_of_birth), Utilities.formatDate(visit_date)));
			child_age_in_weeks_weight[i + birth_weight_plus] = Utilities.round(age_in_weeks, 1);
			child_age_in_weeks_height[i + birth_height_plus] = Utilities.round(age_in_weeks, 1);
			child_weight[i + birth_weight_plus] = Utilities.round(weight, 2); //convert here
			child_height[i + birth_height_plus] = Utilities.round(height, 2); //convert here
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
			Intent i = new Intent(getApplication(), AddNewChildVisitActivity.class);
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
		double mean = mean_data[index].doubleValue();
		double sd = standard_deviations[index].doubleValue();
		
		return (height - mean) / sd;
	}
	
	public static double calculate_other_score() {
		return 0;
	}
	

	
	public static class GraphFragmentLeft extends Fragment {
		
		private static String child_id;

		public GraphFragmentLeft() {
			
		}
		
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
				sex_field.setText("Niño");
			} else if (gender == 0) {
				sex_field.setText("No Sexo");
			}
			
			if (dob != null && !dob.isEmpty()) {
				String formatted_dob = Utilities.formatDate(dob);
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
					visit_header_field.setText("Visita de Nacimiento (" + Utilities.formatDate(child.getDate_created()) + ")");
					TextView age_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_age);
					age_field.setText("Edad: " + 0 + " semanas");
					if (child_age_in_weeks_weight.length > 0) {
						double weight = child_weight[0].doubleValue();
						TextView weight_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight);
						weight_field.setText("Peso: " + weight + " libras");
						double weight_z_score = Utilities.round(calculate_weight_z_score(weight, 0, gender), 2);
						TextView weight_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight_z_score);
						weight_z_score_field.setText("Peso z-score: " + weight_z_score);
						
					} 
					if (child_age_in_weeks_height.length > 0) {
						double height = child_height[0].doubleValue();
						TextView height_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height);
						height_field.setText("Talla: " + height + " cm");
						double height_z_score = Utilities.round(calculate_height_z_score(height, 0, gender), 2);
						TextView height_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height_z_score);
						height_z_score_field.setText("Talla z-score: " + height_z_score);
					}
					
				} else {
					visit_header_field.setText("No Hay Visitos");
				}
			} else {
				String visit_date = dcv.getVisit_date();
				String formatted_visit_date = Utilities.formatDate(visit_date);
				double age_in_weeks = child_age_in_weeks_weight[child_age_in_weeks_weight.length - 1].doubleValue(); //days
				double age_in_days = age_in_weeks * 7;
				double age_in_months = age_in_days / 30.4167;
				double age_in_years = age_in_months / 12;
				double weight = child_weight[child_weight.length -1].doubleValue();
				double height = child_height[child_height.length - 1].doubleValue();
				double weight_z_score = Utilities.round(calculate_weight_z_score(weight, age_in_days, gender), 2);
				double height_z_score = Utilities.round(calculate_height_z_score(height, age_in_days, gender), 2);
				
				
				visit_header_field.setText("Child Visit (" + formatted_visit_date + ")"); //Format visit date
				
				TextView age_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_age);
				age_field.setText("Edad: " + Utilities.round(age_in_months, 1) + " meses");
				
				TextView weight_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight);
				weight_field.setText("Peso: " + weight + " libras");
				
				TextView height_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height);
				height_field.setText("Talla: " + height + " cm");
							
				TextView weight_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight_z_score);
				weight_z_score_field.setText("Peso z-score: " + weight_z_score);
				
				TextView height_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height_z_score);
				height_z_score_field.setText("Talla z-score: " + height_z_score);

				TextView other_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_other_score);
				
				TextView recommendation_field = (TextView)rootView.findViewById(R.id.graph_child_reccomendation_info);
				recommendation_field.setText("Basado de los datos, recomendamos que el niño..");
				//Assign variables
			}
			
			return rootView;
		}
	}

	public static class GraphFragmentRight extends Fragment {
		
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
				age_in_months = Utilities.getAgeInMonths(Utilities.formatDate(dob));
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

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_graph_right, container,false);
			plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
			
			//final CustomPointRenderer<LineAndPointFormatter> cpr = new CustomPointRenderer<LineAndPointFormatter>(plot);
			//cpr.setWidth(50);
	        plot.getLegendWidget().setTableModel(new DynamicTableModel(3, 3));
	 
	        // adjust the legend size so there is enough room
	        // to draw the new legend grid:
	 
	        // add a semi-transparent black background to the legend
	        // so it's easier to see overlaid on top of our plot:
	        Paint bgPaint = new Paint();
	        bgPaint.setColor(Color.BLACK);
	        bgPaint.setStyle(Paint.Style.FILL);
	        bgPaint.setAlpha(140);
	        plot.getLegendWidget().setBackgroundPaint(bgPaint);
	 
	        // adjust the padding of the legend widget to look a little nicer:
	        plot.getLegendWidget().setPadding(10, 10, 10, 10);       

	        plot.getLegendWidget().setSize(new SizeMetrics(130, SizeLayoutType.ABSOLUTE, 330, SizeLayoutType.ABSOLUTE));

	       //plot.disableAllMarkup();
	        plot.getLegendWidget().position(25,
	                XLayoutStyle.ABSOLUTE_FROM_RIGHT,
	                90,
	                YLayoutStyle.ABSOLUTE_FROM_BOTTOM,
	                AnchorPosition.RIGHT_BOTTOM);
			
			final TextView weight_view = (TextView)rootView.findViewById(R.id.tab_weight);
			final TextView height_view = (TextView)rootView.findViewById(R.id.tab_height);
			final TextView other_view = (TextView)rootView.findViewById(R.id.tab_other);
			
			weight_view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					plot.clear();
					weight_view.setBackgroundResource(color.gray_medium);
					height_view.setBackgroundResource(color.gray_dark);
					other_view.setBackgroundResource(color.gray_dark);

					plot.setRangeLabel("Peso (libras)");
					
					Number[][] data;
					if (sex == 1) {
						data = ChildDataStoreGraph.male_weight_lb_graph_data;
					} else {
						data = ChildDataStoreGraph.female_weight_lb_graph_data;
					}
					Number[] x_array = makeXArray(data[0].length, 0.526);
					for (int i = 1; i < data.length -1; i++)  {
						XYSeries series = new SimpleXYSeries(Arrays.asList(x_array), Arrays.asList(data[i])," " + (-4 + i) + "z");
						LineAndPointFormatter seriesFormat = null;
						if (i == 1) //z-score = -3
							seriesFormat = new LineAndPointFormatter(Color.rgb(128, 0, 0), Color.rgb(128, 0, 0), null, null);
						else if (i == 2)// z-score = -2
							seriesFormat = new LineAndPointFormatter(Color.rgb(220, 20, 60), Color.rgb(220, 20, 60), null, null);
						else if (i == 3) //z-score = -1
							seriesFormat = new LineAndPointFormatter(Color.rgb(255, 127, 80), Color.rgb(255, 127, 80), null, null);
						else if (i == 4) //z-score = 0
							seriesFormat = new LineAndPointFormatter(Color.rgb(255, 160, 122), Color.rgb(255, 160, 122), null, null);
						plot.addSeries(series, seriesFormat);
					}
					
					Number[] child_x_array = convertArray(child_age_in_weeks_weight, 0.23);
					XYSeries series = new SimpleXYSeries(Arrays.asList(child_x_array), Arrays.asList(child_weight), "Child");
					LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.BLACK, Color.BLUE, null, null);
					plot.addSeries(series, seriesFormat);
					//XYSeries series = new SimpleXYSeries(Arrays.asList(data[0]), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
			        //plot.addSeries(series, cpr.getFormatter(series));
			        // reduce the number of range labels
			        plot.setTicksPerRangeLabel(3);
			        plot.getGraphWidget().setDomainLabelOrientation(-45);
					plot.redraw();
					
				} 
				
			});
			height_view.setOnClickListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View v) {
					plot.clear();
					height_view.setBackgroundResource(color.gray_medium);
					weight_view.setBackgroundResource(color.gray_dark);
					other_view.setBackgroundResource(color.gray_dark);

					plot.setRangeLabel("Talla (cm)");
					
					Number[][] data;
					if (sex == 1) {
						data = ChildDataStoreGraph.male_height_cm_graph_data;
					} else {
						data = ChildDataStoreGraph.female_height_cm_graph_data;
					}
					Number[] x_array = makeXArray(data[0].length, 0.526);
					for (int i = 1; i < data.length -1; i++)  {
						XYSeries series = new SimpleXYSeries(Arrays.asList(x_array), Arrays.asList(data[i])," " + (-4 + i) + "z");
						LineAndPointFormatter seriesFormat = null;
						if (i == 1) //z-score = -3
							seriesFormat = new LineAndPointFormatter(Color.rgb(128, 0, 0), Color.rgb(128, 0, 0), null, null);
						else if (i == 2)// z-score = -2
							seriesFormat = new LineAndPointFormatter(Color.rgb(220, 20, 60), Color.rgb(220, 20, 60), null, null);
						else if (i == 3) //z-score = -1
							seriesFormat = new LineAndPointFormatter(Color.rgb(255, 127, 80), Color.rgb(255, 127, 80), null, null);
						else if (i == 4) //z-score = 0
							seriesFormat = new LineAndPointFormatter(Color.rgb(255, 160, 122), Color.rgb(255, 160, 122), null, null);
						plot.addSeries(series, seriesFormat);
					}
					
					Number[] child_x_array = convertArray(child_age_in_weeks_height, 0.23);
					XYSeries series = new SimpleXYSeries(Arrays.asList(child_x_array), Arrays.asList(child_height), "Child");
					LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.BLACK, Color.BLUE, null, null);
					plot.addSeries(series, seriesFormat);
				
					
			     // reduce the number of range labels
			        plot.setTicksPerRangeLabel(3);
			        plot.getGraphWidget().setDomainLabelOrientation(-45);
					plot.redraw();
				}
			});
		
			other_view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {

					other_view.setBackgroundResource(color.gray_medium);
					height_view.setBackgroundResource(color.gray_dark);
					weight_view.setBackgroundResource(color.gray_dark);
				}
			});
		
		
			if (selected == 1) weight_view.performClick();
			else if (selected == 2) height_view.performClick();
			else if (selected == 3) other_view.performClick();
			
			return rootView;
		}
		
	}
}


