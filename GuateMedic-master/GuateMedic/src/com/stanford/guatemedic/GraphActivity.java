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
	
	
	public static double calculate_weight_z_score(double weight, double age_weeks, int sex) {
		double[] x_value ={0, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 26.5, 27.5, 28.5, 29.5, 30.5, 31.5, 32.5, 33.5, 34.5, 35.5, 36};
		
		//male weight in pounds
    	double[] male_weight_mean_data = {7.782686, 8.825247, 10.757401, 12.506451, 14.090463, 15.524432, 16.822035, 17.996042, 19.058397, 20.020237, 20.891892, 21.68292, 22.402131, 23.057581, 23.656747, 24.206332, 24.712596, 25.181096, 25.616967, 26.024818, 26.408837, 26.772751, 27.119975, 27.453509, 27.776042, 28.089999, 28.397497, 28.700409, 29.000411, 29.298914, 29.597174, 29.896316, 30.197244, 30.500751, 30.807477, 31.117995, 31.432746, 31.591786};
    	double[] male_weight_standard_deviations = {1.20887, 1.30063, 1.46927, 1.62106, 1.75863, 1.88371, 1.99759, 2.10128, 2.19572, 2.28173, 2.36011, 2.43162, 2.49698, 2.55689, 2.61202, 2.663, 2.71044, 2.75489, 2.7969, 2.83698, 2.87559, 2.91317, 2.95012, 2.98683, 3.0236, 3.06076, 3.09857, 3.13729, 3.17714, 3.2183, 3.26093, 3.3052, 3.3512, 3.39905, 3.44883, 3.50059, 3.55438, 3.58205};		
		//female weight in pounds
    	double[] female_weight_mean_data = {7.493845, 8.37203, 10.019415, 11.531345, 12.91887, 14.192307, 15.361322, 16.434971, 17.42171, 18.329437, 19.165509, 19.936775, 20.649593, 21.30986, 21.923041, 22.494173, 23.027951, 23.528616, 24.000135, 24.446104, 24.869872, 25.274416, 25.662492, 26.036635, 26.399071, 26.751851, 27.096783, 27.435564, 27.769627, 28.100295, 28.428692, 28.755854, 29.082664, 29.409871, 29.738136, 30.068032, 30.400045, 30.566933};
		double[] female_weight_standard_deviations = {1.07327, 1.16113, 1.32125, 1.46295, 1.58868, 1.7006, 1.8006, 1.89039, 1.97151, 2.04535, 2.11315, 2.17603, 2.235, 2.29094, 2.34464, 2.39682, 2.44808, 2.49895, 2.5499, 2.60133, 2.65357, 2.70689, 2.76154, 2.81769, 2.87549, 2.93504, 2.99642, 3.05968, 3.12482, 3.19186, 3.26075, 3.33149, 3.40399, 3.47821, 3.55407, 3.63148, 3.71037, 3.75034};
		
		double[] standard_deviations;
		double[] mean_data;
		if (sex <= 1) { //Defaults to girl
			standard_deviations = male_weight_standard_deviations;
			mean_data = male_weight_mean_data;
		} else if (sex == 2) {
			standard_deviations = female_weight_standard_deviations;
			mean_data = female_weight_mean_data;
		} else {
			return 99; //invalid result
		}
		
		double num_days_in_month = 30.4167;
		double months = (age_weeks * 7) / num_days_in_month;
		int index = (int)Math.floor(months + 0.49999999);
		double factor;
		if (months < 0.5) {
			factor = months * 2;
		} else {
			double new_num = months - 0.5;
			long iPart = (long)new_num;
			factor = new_num - iPart;
		}
		
		double sd_lower_num = standard_deviations[index];
		double sd_larger_num = standard_deviations[index + 1];
		
		double sd_difference = sd_larger_num - sd_lower_num;
		double sd_extra = sd_difference * factor;
		double sd = sd_lower_num + sd_extra;
		
		double mean_lower_num = mean_data[index];
		double mean_larger_num = mean_data[index + 1];
		
		double mean_difference = mean_larger_num - mean_lower_num;
		double mean_extra = mean_difference * factor;
		double mean = mean_lower_num + mean_extra;
		
		double z_score = (weight - mean) / sd;
		
		return z_score;
		
		
	}
	
	public static double calculate_height_z_score(double height, double age_weeks, int sex) {
		double[] x_value ={0, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 26.5, 27.5, 28.5, 29.5, 30.5, 31.5, 32.5, 33.5, 34.5, 35.5};
		
		//male height in centimeters
		double[] male_height_mean_data = {49.98888, 52.69598, 56.62843, 59.60895, 62.077, 64.21686, 66.12531, 67.86018, 69.45908, 70.94804, 72.34586, 73.66665, 74.9213, 76.11838, 77.2648, 78.36622, 79.42734, 80.45209, 81.44384, 82.40544, 83.33938, 84.24783, 85.1327, 85.99565, 86.83818, 87.66161, 88.45247, 89.22326, 89.97549, 90.71041, 91.42908, 92.13242, 92.82127, 93.49638, 94.15847, 94.80823, 95.44637};
		double[] male_height_standard_deviations = {2.656725014236541, 2.5664985477304496, 2.503544709401027, 2.501004407791473, 2.52361248869609, 2.5589084187198243, 2.601272590516277, 2.647775076831393, 2.6967537669603505, 2.7471899310468726, 2.7984425486102484, 2.8500929306782576, 2.901846009055823, 2.953513176037026, 3.004954985288619, 3.0560751078604227, 3.106810871339537, 3.1571125504711706, 3.206954931088994, 3.2563145732429772, 3.305176623520067, 3.3535431743613295, 3.4014059269072625, 3.448766072879765, 3.4956331380727024, 3.5420076496678385, 3.5898145823218996, 3.634078058524114, 3.674790301277003, 3.711999495978349, 3.7458033058036087, 3.7763205623905693, 3.8037106388199846, 3.8281592976177556, 3.8498659093509144, 3.8690419126226456, 3.8859064672143813};
		
		//female height in centimeters
		double[] female_height_mean_data = {49.2864, 51.68358, 55.28613, 58.09382, 60.45981, 62.5367, 64.40633, 66.11842, 67.70574, 69.19124, 70.59164, 71.91962, 73.18501, 74.39564, 75.55785, 76.67686, 77.75701, 78.80198, 79.81492, 80.79852, 81.75512, 82.68679, 83.59532, 84.48233, 85.34924, 86.19732, 87.09026, 87.95714, 88.79602, 89.60551, 90.38477, 91.13342, 91.85154, 92.53964, 93.19854, 93.82945, 94.43382};
		double[] female_height_standard_deviations = {2.4870613029834696, 2.429097238627928, 2.404242870797675, 2.424064933265939, 2.4614640482650056, 2.5072197072244666, 2.557285729949861, 2.6096189425659215, 2.663086430191851, 2.7170249666227964, 2.771030655667209, 2.8248415800736235, 2.878302249861729, 2.9313052451852815, 2.9837878218756497, 3.0357088176324596, 3.0870485252870585, 3.13779304364398, 3.1879407979655974, 3.237497324985195, 3.2864706324024415, 3.334868155232764, 3.3827014426260815, 3.429985726520747, 3.476733294025174, 3.5229555708779836, 3.5840339927779796, 3.637872308158759, 3.685892787816266, 3.729272672511177, 3.76897977912965, 3.8057960117128733, 3.840349409706832, 3.8731319850965833, 3.9045386495657826, 3.934854019883145, 3.9643144118023366};
		
		double[] standard_deviations;
		double[] mean_data;
		if (sex <= 1) { //Defaults to girl
			standard_deviations = male_height_standard_deviations;
			mean_data = male_height_mean_data;
		} else if (sex == 2) {
			standard_deviations = female_height_standard_deviations;
			mean_data = female_height_mean_data;
		} else {
			return 99; //invalid result
		}
		
		double num_days_in_month = 30.4167;
		double months = (age_weeks * 7) / num_days_in_month;
		int index = (int)Math.floor(months + 0.49999999);
		double factor;
		if (months < 0.5) {
			factor = months * 2;
		} else {
			double new_num = months - 0.5;
			long iPart = (long)new_num;
			factor = new_num - iPart;
		}
		
		double sd_lower_num = standard_deviations[index];
		double sd_larger_num = standard_deviations[index + 1];
		
		double sd_difference = sd_larger_num - sd_lower_num;
		double sd_extra = sd_difference * factor;
		double sd = sd_lower_num + sd_extra;
		
		double mean_lower_num = mean_data[index];
		double mean_larger_num = mean_data[index + 1];
		
		double mean_difference = mean_larger_num - mean_lower_num;
		double mean_extra = mean_difference * factor;
		double mean = mean_lower_num + mean_extra;
		
		double z_score = (height - mean) / sd;
		
		return z_score;
		
		
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
				sex_field.setText("Ñino");
			} else if (gender == 2) {
				sex_field.setText("Ñino");
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
				double age = child_age_in_weeks_weight[child_age_in_weeks_weight.length - 1].doubleValue(); //Weeks?
				
				double weight = child_weight[child_weight.length -1].doubleValue();
				double height = child_height[child_height.length - 1].doubleValue();
				double weight_z_score = Utilities.round(calculate_weight_z_score(weight, age, gender), 2);
				double height_z_score = Utilities.round(calculate_height_z_score(height, age, gender), 2);
				
				
				visit_header_field.setText("Child Visit (" + formatted_visit_date + ")"); //Format visit date
				
				TextView age_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_age);
				age_field.setText("Edad: " + age + " semanas");
				
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
				//Assign variables
			}
			
			return rootView;
		}
	}

	public static class GraphFragmentRight extends Fragment {
		
		int selected;
		int sex;
		double age_in_weeks;
		
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
				age_in_weeks = Utilities.getAge(Utilities.formatDate(dob)) / 52.143;
			} else {
				age_in_weeks = -1;
			}
			sex = child.getGender();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_graph_right, container,false);
			final XYPlot plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
			
			final CustomPointRenderer<LineAndPointFormatter> cpr = new CustomPointRenderer<>(plot);
			cpr.setWidth(50);
			 // use a 2x2 grid:
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
	        plot.getLegendWidget().setPadding(10, 5, 5, 5);       
	 

	        plot.getLegendWidget().setSize(new SizeMetrics(90, SizeLayoutType.ABSOLUTE, 300, SizeLayoutType.ABSOLUTE));

	       //plot.disableAllMarkup();
	        plot.getLegendWidget().position(25,
	                XLayoutStyle.ABSOLUTE_FROM_RIGHT,
	                70,
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
					if (sex <= 1) {
						
					} else {
					
					}
					
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


