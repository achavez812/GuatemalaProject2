package com.stanford.guatemedic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.stanford.guatemedic.R.color;

public class GraphActivity extends ActionBarActivity {
	
	//static String date_of_birth;
	static String sex = null;
	//static String visit_date;
	static int age_weeks = -1;
	static double weight = -1;
	static double height = -1;
	
	static ArrayList<Integer> weeks_array;
	static ArrayList<Double> weight_array;
	static ArrayList<Double> height_array;
	
	//This represents age in months and matches up with weight/height data
    static double[] x_weight_values = {0, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 26.5, 27.5, 28.5, 29.5, 30.5, 31.5, 32.5, 33.5, 34.5, 35.5, 36};
    static double[] x_height_values = {0, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 26.5, 27.5, 28.5, 29.5, 30.5, 31.5, 32.5, 33.5, 34.5, 35.5};
    
    static double[] z_score_values = {-1.881, -1.645, -1.281, -0.674, 0, 0.674, 1.281, 1.645, 1.881};
    //9 growth curves for each
    //1: 3rd percentile: z = -1.881
    //2: 5th percentile: z = -1.645 
    //3: 10th percentile: z = -1.281
    //4: 25th percentile: z = -0.674
    //5: 50th percentile: z = 0
    //6: 75th percentile: z = 0.674
    //7: 90th percentile: z = 1.281
    //8: 95th percentile: z = 1.645
    //9: 97th percentile: z = 1.881
    
    static double[][] male_height_data = {
        {44.9251, 47.97812, 52.19859, 55.26322, 57.73049, 59.82569, 61.66384, 63.31224, 64.81395, 66.19833, 67.48635, 68.6936, 69.832, 70.91088, 71.9377, 72.91853, 73.85839, 74.76147, 75.63132, 76.47096, 77.283, 78.06971, 78.83308, 79.57485, 80.29656, 80.99959, 81.74464, 82.47365, 83.18812, 83.88931, 84.57826, 85.25589, 85.92294, 86.58009, 87.22791, 87.86696, 88.49774},
        {45.56841, 48.55809, 52.72611, 55.77345, 58.23744, 60.33647, 62.18261, 63.84166, 65.35584, 66.75398, 68.05675, 69.27949, 70.43397, 71.52941, 72.57318, 73.5713, 74.52871, 75.44958, 76.33742, 77.19523, 78.0256, 78.83077, 79.61271, 80.37315, 81.11363, 81.83552, 82.58135, 83.31105, 84.02609, 84.72769, 85.41688, 86.09452, 86.76134, 87.41799, 88.06503, 88.70301, 89.33242},
        {46.55429, 49.4578, 53.55365, 56.57772, 59.0383, 61.1441, 63.00296, 64.67854, 66.21181, 67.63088, 68.95591, 70.20192, 71.38046, 72.50055, 73.56946, 74.59309, 75.57634, 76.5233, 77.43742, 78.32168, 79.17863, 80.01048, 80.81919, 81.60646, 82.37381, 83.12259, 83.87245, 84.60576, 85.32399, 86.02833, 86.71978, 87.39917, 88.06723, 88.72457, 89.37177, 90.00937, 90.63786},
        {48.18937, 50.97919, 54.9791, 57.9744, 60.43433, 62.55409, 64.43546, 66.13896, 67.70375, 69.15682, 70.51761, 71.80065, 73.01712, 74.17581, 75.2838, 76.34685, 77.36973, 78.35646, 79.31042, 80.23453, 81.13131, 82.00292, 82.85129, 83.67811, 84.48487, 85.2729, 86.03703, 86.78329, 87.51317, 88.22788, 88.9284, 89.6156, 90.2902, 90.95287, 91.60421, 92.24482, 92.87525},
        {49.98888, 52.69598, 56.62843, 59.60895, 62.077, 64.21686, 66.12531, 67.86018, 69.45908, 70.94804, 72.34586, 73.66665, 74.9213, 76.11838, 77.2648, 78.36622, 79.42734, 80.45209, 81.44384, 82.40544, 83.33938, 84.24783, 85.1327, 85.99565, 86.83818, 87.66161, 88.45247, 89.22326, 89.97549, 90.71041, 91.42908, 92.13242, 92.82127, 93.49638, 94.15847, 94.80823, 95.44637},
        {51.77126, 54.44054, 58.35059, 61.33788, 63.82543, 65.99131, 67.92935, 69.69579, 71.32735, 72.84947, 74.2806, 75.63462, 76.92224, 78.15196, 79.33061, 80.4638, 81.5562, 82.61174, 83.63377, 84.62515, 85.58837, 86.52562, 87.43879, 88.32957, 89.19948, 90.04985, 90.8787, 91.68468, 92.46929, 93.23385, 93.97951, 94.70732, 95.41824, 96.11319, 96.79307, 97.45873, 98.11108},
        {53.36153, 56.03444, 59.9664, 62.98158, 65.49858, 67.69405, 69.66122, 71.45609, 73.11525, 74.6641, 76.1211, 77.50016, 78.81202, 80.0652, 81.2666, 82.42185, 83.53568, 84.61204, 85.65431, 86.66541, 87.64786, 88.60385, 89.53533, 90.44402, 91.33143, 92.19893, 93.07143, 93.91817, 94.74064, 95.54016, 96.318, 97.07531, 97.81324, 98.53287, 99.23531, 99.92162, 100.5929},
        {54.30721, 56.99908, 60.96465, 64.00789, 66.54889, 68.76538, 70.75128, 72.56307, 74.23767, 75.80074, 77.27095, 78.66234, 79.98578, 81.2499, 82.46167, 83.6268, 84.75006, 85.83547, 86.88645, 87.90595, 88.89652, 89.86038, 90.79951, 91.71563, 92.61031, 93.48491, 94.38775, 95.263, 96.1121, 96.93639, 97.73717, 98.51569, 99.27318, 100.0109, 100.73, 101.4318, 102.1174},
        {54.919, 57.62984, 61.62591, 64.69241, 67.2519, 69.48354, 71.48218, 73.30488, 74.98899, 76.56047, 78.03819, 79.43637, 80.76602, 82.03585, 83.25292, 84.42302, 85.55095, 86.64078, 87.69597, 88.7195, 89.71393, 90.68153, 91.62428, 92.54392, 93.44203, 94.31998, 95.24419, 96.13962, 97.00763, 97.84957, 98.66677, 99.46052, 100.2321, 100.9829, 101.7142, 102.4274, 103.1237}
    };
	
    static double[][] male_weight_data = {
        {2.355451, 2.799549, 3.614688, 4.342341, 4.992898, 5.575169, 6.096775, 6.56443, 6.984123, 7.361236, 7.700624, 8.006677, 8.283365, 8.534275, 8.762649, 8.971407, 9.16318, 9.340328, 9.504964, 9.658975, 9.804039, 9.941645, 10.07311, 10.19957, 10.32206, 10.44144, 10.55847, 10.6738, 10.78798, 10.90147, 11.01466, 11.12787, 11.24135, 11.3553, 11.46988, 11.58521, 11.70137, 11.75978},
        {2.526904, 2.964656, 3.774849, 4.503255, 5.157412, 5.744752, 6.272175, 6.745993, 7.171952, 7.555287, 7.900755, 8.212684, 8.495, 8.751264, 8.984701, 9.198222, 9.394454, 9.575757, 9.744251, 9.90183, 10.05019, 10.19082, 10.32507, 10.4541, 10.57895, 10.70051, 10.81958, 10.93681, 11.0528, 11.16803, 11.28293, 11.39782, 11.513, 11.62869, 11.74508, 11.8623, 11.98046, 12.03991},
        {2.773802, 3.20951, 4.020561, 4.754479, 5.416803, 6.013716, 6.551379, 7.035656, 7.472021, 7.865533, 8.220839, 8.542195, 8.833486, 9.098246, 9.339688, 9.560722, 9.763982, 9.95184, 10.12643, 10.28968, 10.4433, 10.58881, 10.72759, 10.86084, 10.98963, 11.1149, 11.23747, 11.35806, 11.47728, 11.59567, 11.71368, 11.8317, 11.95005, 12.069, 12.18875, 12.30948, 12.43132, 12.49268},
        {3.150611, 3.597396, 4.428873, 5.183378, 5.866806, 6.484969, 7.043627, 7.548346, 8.004399, 8.416719, 8.789882, 9.12811, 9.435279, 9.714942, 9.970338, 10.20442, 10.41986, 10.6191, 10.80433, 10.97753, 11.14047, 11.29477, 11.44185, 11.58298, 11.7193, 11.85182, 11.98142, 12.10889, 12.23491, 12.36007, 12.4849, 12.60983, 12.73523, 12.86144, 12.9887, 13.11723, 13.24721, 13.31278},
        {3.530203, 4.003106, 4.879525, 5.672889, 6.391392, 7.041836, 7.630425, 8.162951, 8.644832, 9.08112, 9.4765, 9.835308, 10.16154, 10.45885, 10.73063, 10.97992, 11.20956, 11.42207, 11.61978, 11.80478, 11.97897, 12.14404, 12.30154, 12.45283, 12.59913, 12.74154, 12.88102, 13.01842, 13.1545, 13.2899, 13.42519, 13.56088, 13.69738, 13.83505, 13.97418, 14.11503, 14.2578, 14.32994},
        {3.879077, 4.387423, 5.327328, 6.175598, 6.942217, 7.635323, 8.262033, 8.828786, 9.34149, 9.805593, 10.22612, 10.60772, 10.95466, 11.27087, 11.55996, 11.82524, 12.06973, 12.29617, 12.50708, 12.70473, 12.89117, 13.06825, 13.23765, 13.40086, 13.5592, 13.71386, 13.8659, 14.01623, 14.16567, 14.31493, 14.46462, 14.61527, 14.76732, 14.92117, 15.07711, 15.23541, 15.39628, 15.47772},
        {4.172493, 4.718161, 5.728153, 6.638979, 7.460702, 8.202193, 8.871384, 9.475466, 10.02101, 10.51406, 10.96017, 11.36445, 11.7316, 12.06595, 12.37145, 12.65175, 12.91015, 13.14969, 13.37311, 13.5829, 13.78133, 13.97042, 14.15201, 14.32772, 14.499, 14.66716, 14.83332, 14.99848, 15.16351, 15.32917, 15.4961, 15.66485, 15.83588, 16.00958, 16.18624, 16.36612, 16.5494, 16.64237},
        {4.340293, 4.91013, 5.967102, 6.921119, 7.781401, 8.556813, 9.255615, 9.885436, 10.45331, 10.96574, 11.42868, 11.84763, 12.22766, 12.5734, 12.88911, 13.17867, 13.44564, 13.69325, 13.92444, 14.14187, 14.34795, 14.54484, 14.73448, 14.91861, 15.09876, 15.2763, 15.45242, 15.62819, 15.8045, 15.98214, 16.16177, 16.34395, 16.52915, 16.71773, 16.91, 17.10619, 17.30646, 17.40816},
        {4.446488, 5.032625, 6.121929, 7.10625, 7.993878, 8.793444, 9.513307, 10.16135, 10.74492, 11.27084, 11.74538, 12.17436, 12.56308, 12.91645, 13.23893, 13.53462, 13.80724, 14.06019, 14.29655, 14.51909, 14.73034, 14.93256, 15.12777, 15.31777, 15.50418, 15.68841, 15.8717, 16.05514, 16.23967, 16.42609, 16.61508, 16.8072, 17.00291, 17.2026, 17.40654, 17.61495, 17.82797, 17.93625}
    };
	
    static double[][] female_height_data = {
        {45.09488, 47.46916, 50.95701, 53.62925, 55.8594, 57.8047, 59.54799, 61.13893, 62.60993, 63.98348, 65.2759, 66.49948, 67.66371, 68.77613, 69.8428, 70.86874, 71.85807, 72.81433, 73.74047, 74.63908, 75.51237, 76.36229, 77.19056, 77.99868, 78.78801, 79.55974, 80.33998, 81.11332, 81.87334, 82.61506, 83.33473, 84.02972, 84.69837, 85.33987, 85.95413, 86.54167, 87.10349},
        {45.57561, 47.96324, 51.47996, 54.17907, 56.43335, 58.40032, 60.16323, 61.77208, 63.25958, 64.64845, 65.9552, 67.19226, 68.36925, 69.4938, 70.57207, 71.60911, 72.60914, 73.57571, 74.51184, 75.42012, 76.30282, 77.16191, 77.9991, 78.81595, 79.61381, 80.39391, 81.18804, 81.97223, 82.74084, 83.48951, 84.21496, 84.91494, 85.58809, 86.23379, 86.85208, 87.44359, 88.00937},
        {46.33934, 48.74248, 52.29627, 55.03144, 57.31892, 59.31633, 61.10726, 62.7421, 64.25389, 65.66559, 66.99394, 68.25154, 69.44814, 70.59149, 71.68784, 72.74233, 73.75924, 74.74217, 75.6942, 76.61797, 77.51576, 78.38958, 79.2412, 80.07216, 80.88385, 81.67752, 82.49318, 83.29459, 84.07717, 84.83741, 85.57273, 86.28139, 86.96242, 87.6155, 88.24089, 88.83932, 89.41196},
        {47.68345, 50.09686, 53.69078, 56.47125, 58.80346, 60.84386, 62.6759, 64.35005, 65.89952, 67.34745, 68.7107, 70.00202, 71.23128, 72.40633, 73.53349, 74.61799, 75.66416, 76.67568, 77.65565, 78.60678, 79.53138, 80.4315, 81.30893, 82.16525, 83.00187, 83.82007, 84.67209, 85.5036, 86.31151, 87.09346, 87.84783, 88.57362, 89.27042, 89.93835, 90.57795, 91.1902, 91.77639},
        {49.2864, 51.68358, 55.28613, 58.09382, 60.45981, 62.5367, 64.40633, 66.11842, 67.70574, 69.19124, 70.59164, 71.91962, 73.18501, 74.39564, 75.55785, 76.67686, 77.75701, 78.80198, 79.81492, 80.79852, 81.75512, 82.68679, 83.59532, 84.48233, 85.34924, 86.19732, 87.09026, 87.95714, 88.79602, 89.60551, 90.38477, 91.13342, 91.85154, 92.53964, 93.19854, 93.82945, 94.43382},
        {51.0187, 53.36362, 56.93136, 59.74045, 62.1233, 64.22507, 66.12418, 67.8685, 69.48975, 71.01019, 72.44614, 73.80997, 75.11133, 76.35791, 77.55594, 78.71058, 79.82613, 80.90623, 81.95399, 82.97211, 83.96292, 84.92846, 85.87054, 86.79077, 87.69056, 88.57121, 89.50562, 90.40982, 91.28258, 92.12313, 92.93113, 93.70662, 94.45005, 95.16218, 95.84411, 96.49721, 97.12307},
        {52.7025, 54.96222, 58.45612, 61.24306, 63.62648, 65.74096, 67.65995, 69.42868, 71.07731, 72.62711, 74.09378, 75.48923, 76.82282, 78.10202, 79.3329, 80.5205, 81.66903, 82.78208, 83.86269, 84.91353, 85.93689, 86.93481, 87.90908, 88.86127, 89.79282, 90.70499, 91.67718, 92.61658, 93.52227, 94.39371, 95.23082, 96.03385, 96.80343, 97.54052, 98.24636, 98.92246, 99.57056},
        {53.77291, 55.96094, 59.38911, 62.15166, 64.52875, 66.64653, 68.57452, 70.35587, 72.01952, 73.58601, 75.0705, 76.4846, 77.83742, 79.13625, 80.38705, 81.59475, 82.7635, 83.89683, 84.99774, 86.06887, 87.11249, 88.13061, 89.125, 90.09723, 91.04873, 91.98074, 92.97574, 93.93693, 94.86339, 95.75464, 96.61061, 97.43164, 98.2184, 98.97193, 99.69353, 100.3848, 101.0475},
        {54.49527, 56.62728, 60.00338, 62.74547, 65.11577, 67.23398, 69.16668, 70.95545, 72.62835, 74.20532, 75.70118, 77.12729, 78.49257, 79.80419, 81.06801, 82.28891, 83.47098, 84.6177, 85.73205, 86.81663, 87.8737, 88.90526, 89.91305, 90.89866, 91.86347, 92.80876, 93.81864, 94.79426, 95.73464, 96.63928, 97.50808, 98.34139, 99.13993, 99.90473, 100.6372, 101.3388, 102.0116}    
    };
    
    static double[][] female_weight_data = {
        {2.414112, 2.756917, 3.402293, 3.997806, 4.547383, 5.054539, 5.5225, 5.954272, 6.352668, 6.720328, 7.059732, 7.373212, 7.662959, 7.93103, 8.179356, 8.409744, 8.623887, 8.82337, 9.009668, 9.18416, 9.348127, 9.50276, 9.649162, 9.788355, 9.921281, 10.04881, 10.17173, 10.29079, 10.40664, 10.5199, 10.63112, 10.74078, 10.84935, 10.95722, 11.06475, 11.17225, 11.28, 11.33404},
        {2.547905, 2.894442, 3.54761, 4.150639, 4.707123, 5.220488, 5.693974, 6.130641, 6.533373, 6.904886, 7.247736, 7.564327, 7.856916, 8.127621, 8.378425, 8.611186, 8.827638, 9.029399, 9.21798, 9.394782, 9.56111, 9.71817, 9.867081, 10.00887, 10.1445, 10.27483, 10.40066, 10.52274, 10.64171, 10.75819, 10.87273, 10.98581, 11.09789, 11.20934, 11.32054, 11.43177, 11.54332, 11.59929},
        {2.747222, 3.101767, 3.770157, 4.387042, 4.955926, 5.480295, 5.96351, 6.408775, 6.819122, 7.197414, 7.546342, 7.868436, 8.166069, 8.44146, 8.696684, 8.93368, 9.154251, 9.360079, 9.552723, 9.73363, 9.90414, 10.06549, 10.21882, 10.36518, 10.50553, 10.64076, 10.77167, 10.89899, 11.02338, 11.14545, 11.26575, 11.38474, 11.50288, 11.62054, 11.73806, 11.85574, 11.97384, 12.03312},
        {3.064865, 3.437628, 4.138994, 4.78482, 5.379141, 5.925888, 6.428828, 6.891533, 7.317373, 7.709516, 8.070932, 8.4044, 8.712513, 8.997692, 9.262185, 9.508085, 9.737329, 9.951715, 10.1529, 10.34241, 10.52167, 10.69196, 10.85446, 11.01027, 11.16037, 11.30567, 11.44697, 11.58501, 11.72047, 11.85392, 11.98592, 12.11692, 12.24735, 12.37757, 12.50791, 12.63865, 12.77001, 12.836},
        {3.399186, 3.797528, 4.544777, 5.230584, 5.859961, 6.437588, 6.96785, 7.454854, 7.902436, 8.314178, 8.693418, 9.043262, 9.366594, 9.666089, 9.944226, 10.20329, 10.44541, 10.67251, 10.88639, 11.08868, 11.2809, 11.4644, 11.64043, 11.81014, 11.97454, 12.13456, 12.29102, 12.44469, 12.59622, 12.74621, 12.89517, 13.04357, 13.19181, 13.34023, 13.48913, 13.63877, 13.78937, 13.86507},
        {3.717519, 4.145594, 4.946766, 5.680083, 6.351512, 6.966524, 7.53018, 8.047178, 8.521877, 8.958324, 9.360271, 9.731193, 10.07431, 10.39258, 10.68874, 10.96532, 11.22463, 11.46878, 11.69972, 11.91921, 12.12887, 12.33016, 12.52439, 12.71277, 12.89636, 13.07613, 13.25293, 13.42753, 13.60059, 13.77271, 13.9444, 14.11611, 14.28822, 14.46106, 14.63491, 14.80998, 14.98647, 15.07529},
        {3.992572, 4.450126, 5.305632, 6.087641, 6.80277, 7.457119, 8.056331, 8.605636, 9.109878, 9.573546, 10.00079, 10.39545, 10.76106, 11.10089, 11.41792, 11.71491, 11.99438, 12.25862, 12.50974, 12.74964, 12.98004, 13.2025, 13.41844, 13.62911, 13.83564, 14.03902, 14.24017, 14.43984, 14.63873, 14.83743, 15.03646, 15.23626, 15.43719, 15.63957, 15.84365, 16.04963, 16.25767, 16.3625},
        {4.152637, 4.628836, 5.519169, 6.332837, 7.076723, 7.757234, 8.38033, 8.951544, 9.476009, 9.95848, 10.40335, 10.8147, 11.19625, 11.55145, 11.88348, 12.19522, 12.48934, 12.76825, 13.03415, 13.28904, 13.53473, 13.77284, 14.00484, 14.23205, 14.45561, 14.67659, 14.89587, 15.11428, 15.33249, 15.55113, 15.7707, 15.99164, 16.21432, 16.43904, 16.66605, 16.89553, 17.12762, 17.24469},
        {4.254922, 4.743582, 5.657379, 6.492574, 7.256166, 7.95473, 8.594413, 9.180938, 9.719621, 10.21539, 10.6728, 11.09607, 11.48908, 11.85539, 12.19829, 12.52078, 12.82561, 13.11527, 13.39204, 13.65799, 13.91497, 14.16467, 14.40858, 14.64807, 14.88432, 15.11839, 15.35122, 15.58363, 15.81632, 16.0499, 16.28491, 16.52176, 16.76085, 17.00245, 17.24681, 17.49412, 17.7445, 17.87089}    
    };
	

	protected void onCreate(Bundle savedInstanceState) {
		weeks_array = new ArrayList<Integer>();
		weight_array = new ArrayList<Double>();
		height_array = new ArrayList<Double>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		getActionBar().setHomeButtonEnabled(true);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.left_content, new GraphFragmentLeft()).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.right_content, new GraphFragmentRight()).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.graph_menu_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
		}
		if (id == R.id.action_newchild) {
			NewChildGraphDialog ncgd = new NewChildGraphDialog();
			ncgd.show(getSupportFragmentManager(), null);
			return false;
		}
		if (id == R.id.action_addvisit) {
			AddVisitGraphDialog avgd = new AddVisitGraphDialog();
			avgd.show(getSupportFragmentManager(), null);
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

			final RadioGroup sex_radio = (RadioGroup)v.findViewById(R.id.sex_radio);
			
		    // Add action buttons
           view.setPositiveButton("Add New Child", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
            	   /*
                   String month = month_spinner.getSelectedItem().toString();
                   String day = day_spinner.getSelectedItem().toString();
                   String year = year_spinner.getSelectedItem().toString();
                   if (month.length() == 1) month = 0 + month;
                   if (day.length() == 1) day = 0 + day;
                   date_of_birth = month + "/" + day + "/" + year;
                   */
                   sex = ((RadioButton)v.findViewById(sex_radio.getCheckedRadioButtonId())).getText().toString();
                   age_weeks = -1;
                   weight = -1;
                   height = -1;
                   
                   weeks_array.clear();
                   weight_array.clear();
                   height_array.clear();
                   
                   getSupportFragmentManager().beginTransaction().replace(R.id.left_content, new GraphFragmentLeft()).commit();
                   getSupportFragmentManager().beginTransaction().replace(R.id.right_content, new GraphFragmentRight()).commit();
               }
           });
           view.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   NewChildGraphDialog.this.getDialog().cancel();
               }
           });      
		    return builder.create();
		}
	}
	
	public class AddVisitGraphDialog extends DialogFragment {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    // Get the layout inflater
		    LayoutInflater inflater = getActivity().getLayoutInflater();
		    
		    final View v = inflater.inflate(R.layout.fragment_graph_add_visit_dialog, null);
	
		    // Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		    AlertDialog.Builder view = builder.setView(v);
		    
		    final EditText age_field = (EditText)v.findViewById(R.id.graph_add_visit_weeks_age);
		    final EditText weight_field = (EditText)v.findViewById(R.id.graph_add_visit_weight);
		    final EditText height_field = (EditText)v.findViewById(R.id.graph_add_visit_height);
		    
		    // Add action buttons
           view.setPositiveButton("Add New Visit", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
            	   age_weeks = Integer.parseInt(age_field.getText().toString());
            	   weight = Double.parseDouble(weight_field.getText().toString());
            	   height = Double.parseDouble(height_field.getText().toString());
            	   weeks_array.add(age_weeks);
            	   weight_array.add(weight);
            	   height_array.add(height);
            	   getSupportFragmentManager().beginTransaction().replace(R.id.left_content, new GraphFragmentLeft()).commit();
                   getSupportFragmentManager().beginTransaction().replace(R.id.right_content, new GraphFragmentRight()).commit();

               }
           });
           view.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	   AddVisitGraphDialog.this.getDialog().cancel();
               }
           });      
		    return builder.create();
		}
	}
	
	
	
	public static double calculate_weight_z_score() {
		double[] x_value ={0, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 26.5, 27.5, 28.5, 29.5, 30.5, 31.5, 32.5, 33.5, 34.5, 35.5, 36};
		
		//male weight in kilograms
		double[] male_weight_mean_data = {3.530203, 4.003106, 4.879525, 5.672889, 6.391392, 7.041836, 7.630425, 8.162951, 8.644832, 9.08112, 9.4765, 9.835308, 10.16154, 10.45885, 10.73063, 10.97992, 11.20956, 11.42207, 11.61978, 11.80478, 11.97897, 12.14404, 12.30154, 12.45283, 12.59913, 12.74154, 12.88102, 13.01842, 13.1545, 13.2899, 13.42519, 13.56088, 13.69738, 13.83505, 13.97418, 14.11503, 14.2578, 14.32994};
		double[] male_weight_standard_deviations = {0.5483388691636308, 0.5899617828596111, 0.6664550675377855, 0.7353077161040221, 0.7977077843674071, 0.8544459425408992, 0.906099060498862, 0.9531362941802027, 0.9959705728155609, 1.0349856374072548, 1.0705382544191666, 1.102975035569814, 1.132623337338286, 1.1597999014779625, 1.1848050385035822, 1.207929214780667, 1.2294452237365494, 1.2496087374808416, 1.2686666623451814, 1.2868445072622676, 1.3043597199631523, 1.3214059701285943, 1.3381679141488099, 1.3548155352308067, 1.371495406210474, 1.3883500383546428, 1.4055036216423076, 1.423066888330417, 1.4411391271542766, 1.459810211505902, 1.4791502672511687, 1.4992276392320234, 1.5200958441225314, 1.5418009836815039, 1.5643775415899328, 1.5878568414316763, 1.612257112638661, 1.624809414181855};
		
		//female weight in kilograms
		double[] female_weight_mean_data = {3.399186, 3.797528, 4.544777, 5.230584, 5.859961, 6.437588, 6.96785, 7.454854, 7.902436, 8.314178, 8.693418, 9.043262, 9.366594, 9.666089, 9.944226, 10.20329, 10.44541, 10.67251, 10.88639, 11.08868, 11.2809, 11.4644, 11.64043, 11.81014, 11.97454, 12.13456, 12.29102, 12.44469, 12.59622, 12.74621, 12.89517, 13.04357, 13.19181, 13.34023, 13.48913, 13.63877, 13.78937, 13.86507};
		double[] female_weight_standard_deviations = {0.4868317793152519, 0.526686945129281, 0.5993150515466676, 0.6635877636928744, 0.7206212275044657, 0.7713859944899943, 0.8167455265127599, 0.8574748220662556, 0.8942713470732092, 0.927763771920194, 0.9585169772679141, 0.9870407033905422, 1.0137879351111063, 1.0391619172023916, 1.0635229179410999, 1.087189035727074, 1.1104407785020416, 1.1335156616703101, 1.1566276426873197, 1.1799557593671335, 1.2036494429981581, 1.2278382142342168, 1.2526258947078388, 1.2780977914501177, 1.3043145957622737, 1.3313258529395857, 1.359168725618415, 1.3878615264596312, 1.4174100518009947, 1.447818009178694, 1.4790687342629933, 1.5111525841589346, 1.5440390371811814, 1.577704356900138, 1.6121132997563938, 1.647227552286689, 1.6830116164721458, 1.7011412416713223};
		
		double[] standard_deviations;
		double[] mean_data;
		if (sex.equals("Male")) {
			standard_deviations = male_weight_standard_deviations;
			mean_data = male_weight_mean_data;
		} else if (sex.equals("Female")) {
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
	
	public static double calculate_height_z_score() {
		double[] x_value ={0, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5, 24.5, 25.5, 26.5, 27.5, 28.5, 29.5, 30.5, 31.5, 32.5, 33.5, 34.5, 35.5};
		
		//male height in centimeters
		double[] male_height_mean_data = {49.98888, 52.69598, 56.62843, 59.60895, 62.077, 64.21686, 66.12531, 67.86018, 69.45908, 70.94804, 72.34586, 73.66665, 74.9213, 76.11838, 77.2648, 78.36622, 79.42734, 80.45209, 81.44384, 82.40544, 83.33938, 84.24783, 85.1327, 85.99565, 86.83818, 87.66161, 88.45247, 89.22326, 89.97549, 90.71041, 91.42908, 92.13242, 92.82127, 93.49638, 94.15847, 94.80823, 95.44637};
		double[] male_height_standard_deviations = {2.656725014236541, 2.5664985477304496, 2.503544709401027, 2.501004407791473, 2.52361248869609, 2.5589084187198243, 2.601272590516277, 2.647775076831393, 2.6967537669603505, 2.7471899310468726, 2.7984425486102484, 2.8500929306782576, 2.901846009055823, 2.953513176037026, 3.004954985288619, 3.0560751078604227, 3.106810871339537, 3.1571125504711706, 3.206954931088994, 3.2563145732429772, 3.305176623520067, 3.3535431743613295, 3.4014059269072625, 3.448766072879765, 3.4956331380727024, 3.5420076496678385, 3.5898145823218996, 3.634078058524114, 3.674790301277003, 3.711999495978349, 3.7458033058036087, 3.7763205623905693, 3.8037106388199846, 3.8281592976177556, 3.8498659093509144, 3.8690419126226456, 3.8859064672143813};
		
		//female height in centimeters
		double[] female_height_mean_data = {49.2864, 51.68358, 55.28613, 58.09382, 60.45981, 62.5367, 64.40633, 66.11842, 67.70574, 69.19124, 70.59164, 71.91962, 73.18501, 74.39564, 75.55785, 76.67686, 77.75701, 78.80198, 79.81492, 80.79852, 81.75512, 82.68679, 83.59532, 84.48233, 85.34924, 86.19732, 87.09026, 87.95714, 88.79602, 89.60551, 90.38477, 91.13342, 91.85154, 92.53964, 93.19854, 93.82945, 94.43382};
		double[] female_height_standard_deviations = {2.4870613029834696, 2.429097238627928, 2.404242870797675, 2.424064933265939, 2.4614640482650056, 2.5072197072244666, 2.557285729949861, 2.6096189425659215, 2.663086430191851, 2.7170249666227964, 2.771030655667209, 2.8248415800736235, 2.878302249861729, 2.9313052451852815, 2.9837878218756497, 3.0357088176324596, 3.0870485252870585, 3.13779304364398, 3.1879407979655974, 3.237497324985195, 3.2864706324024415, 3.334868155232764, 3.3827014426260815, 3.429985726520747, 3.476733294025174, 3.5229555708779836, 3.5840339927779796, 3.637872308158759, 3.685892787816266, 3.729272672511177, 3.76897977912965, 3.8057960117128733, 3.840349409706832, 3.8731319850965833, 3.9045386495657826, 3.934854019883145, 3.9643144118023366};
		
		double[] standard_deviations;
		double[] mean_data;
		if (sex.equals("Male")) {
			standard_deviations = male_height_standard_deviations;
			mean_data = male_height_mean_data;
		} else if (sex.equals("Female")) {
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
	
	public static double calculate_bmi_z_score() {
		return 0;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static class GraphFragmentLeft extends Fragment {

		public GraphFragmentLeft() {
			
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_graph_left, container,false);
			
			TextView sex_field = (TextView)rootView.findViewById(R.id.graph_child_sex);
			
			TextView age_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_age);
			
			TextView weight_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight);
			
			TextView height_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height);
			
			TextView bmi_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_bmi);
			
			TextView weight_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_weight_z_score);
			
			TextView height_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_height_z_score);
			
			TextView bmi_z_score_field = (TextView)rootView.findViewById(R.id.graph_last_child_visit_bmi_z_score);
			
			TextView recommendation_field = (TextView)rootView.findViewById(R.id.graph_child_reccomendation_info);
			
			if (sex == null) { //No child (therefore no visit either)
				
			} else if (age_weeks == -1) { //No visit
				sex_field.setText("Sex: " + sex);
			} else { //It's good
				sex_field.setText("Sex: " + sex);
				age_field.setText("Age: " + age_weeks + " weeks");
				weight_field.setText("Weight: " + round(weight, 2) + " kg");
				height_field.setText("Height: " + round(height, 2) + " cm");
				double bmi = weight / Math.pow((height/100), 2);
				bmi_field.setText("BMI: " + round(bmi, 2));
				double weight_z_score = round(calculate_weight_z_score(), 2);
				double height_z_score = round(calculate_height_z_score(), 2);
				double bmi_z_score = round(calculate_bmi_z_score(), 2);
				weight_z_score_field.setText("Weight z-score: " + weight_z_score);
				height_z_score_field.setText("Height z-score: " + height_z_score);
				
			}
			
			return rootView;
		}
	}
	
	public static double convertMonthsToWeeks(double months) {
		double days_in_month = 30.4167;
		
		return (months * days_in_month) / 7;
	}
	
	public static class GraphFragmentRight extends Fragment {
		
		//1 = weight
		//2 = height
		//3 = bmi
		int selected;
		
		public GraphFragmentRight() {
			selected = 1;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_graph_right, container,false);

			
			final TextView weight_view = (TextView)rootView.findViewById(R.id.tab_weight);
			final TextView height_view = (TextView)rootView.findViewById(R.id.tab_height);
			final TextView bmi_view = (TextView)rootView.findViewById(R.id.tab_bmi);
			
			weight_view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {

					weight_view.setBackgroundResource(color.gray_medium);
					height_view.setBackgroundResource(color.gray_dark);
					bmi_view.setBackgroundResource(color.gray_dark);
					if (sex != null) {
						if (sex.equals("Female")) {
							XYPlot plot;
							plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
							 
					        // Create a couple arrays of y-values to plot:
					        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
					        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
					 
					        // Turn the above arrays into XYSeries':
					        XYSeries series1 = new SimpleXYSeries(
					                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
					                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
					                "Series1");                             // Set the display title of the series
					 
					        // same as above
					        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
					 
					        // Create a formatter to use for drawing a series using LineAndPointRenderer
					        // and configure it from xml:
					        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
					 
					        // add a new series' to the xyplot:
					        plot.addSeries(series1, series1Format);
					 
					        // same as above:
					        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
					        plot.addSeries(series2, series2Format);
					 
					        // reduce the number of range labels
					        plot.setTicksPerRangeLabel(3);
					        plot.getGraphWidget().setDomainLabelOrientation(-45);
							
						} else {
			
						}

					} 
				}
			});
			
			
			height_view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					height_view.setBackgroundResource(color.gray_medium);
					weight_view.setBackgroundResource(color.gray_dark);
					bmi_view.setBackgroundResource(color.gray_dark);
					if (sex != null) {

						if (sex.equals("Female")) {
		
						} else {

						}	

						
					} 
				}
			});
			
			
			bmi_view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {

					bmi_view.setBackgroundResource(color.gray_medium);
					height_view.setBackgroundResource(color.gray_dark);
					weight_view.setBackgroundResource(color.gray_dark);
				}
			});
			
			
			if (selected == 1) weight_view.performClick();
			else if (selected == 2) height_view.performClick();
			else if (selected == 3) bmi_view.performClick();
			
			return rootView;
		}
	}

}