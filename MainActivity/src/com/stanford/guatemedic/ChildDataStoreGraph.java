package com.stanford.guatemedic;

public class ChildDataStoreGraph {
	//Every 16 days
	public static Number[][] male_weight_kg_graph_data = {
		{1.701, 2.049, 2.520, 2.948, 3.320, 3.646, 3.931, 4.182, 4.403, 4.599, 4.774, 4.930, 5.071, 5.199, 5.317, 5.426, 5.528, 5.625, 5.716, 5.804, 5.888, 5.969, 6.049, 6.126, 6.201, 6.274, 6.346, 6.417, 6.487, 6.555, 6.623, 6.690, 6.756, 6.821, 6.885, 6.948, 7.011, 7.073, 7.135, 7.196, 7.257, 7.317, 7.377, 7.436, 7.495, 7.553, 7.611, 7.669, 7.726, 7.781, 7.837, 7.892, 7.946, 7.999, 8.052, 8.104, 8.156, 8.206, 8.256, 8.306, 8.355, 8.403, 8.451, 8.499, 8.546, 8.593, 8.639, 8.686, 8.732, 8.778, 8.824, 8.870, 8.916, 8.961, 9.007, 9.052, 9.097, 9.143, 9.188, 9.233, 9.277, 9.322, 9.367, 9.411, 9.454, 9.498, 9.541, 9.584, 9.627, 9.669, 9.712, 9.754, 9.795, 9.837, 9.878, 9.919, 9.960, 10.000, 10.041, 10.081, 10.121, 10.161, 10.201, 10.240, 10.280, 10.319, 10.358, 10.397, 10.436, 10.475, 10.514, 10.552, 10.591, 10.629, 10.667, 10.705, 10.742},
		{2.080, 2.459, 2.982, 3.454, 3.860, 4.213, 4.521, 4.791, 5.031, 5.244, 5.434, 5.605, 5.761, 5.903, 6.034, 6.156, 6.270, 6.378, 6.481, 6.579, 6.674, 6.765, 6.855, 6.942, 7.027, 7.111, 7.192, 7.273, 7.352, 7.431, 7.508, 7.584, 7.660, 7.735, 7.809, 7.882, 7.955, 8.027, 8.098, 8.169, 8.240, 8.310, 8.380, 8.450, 8.519, 8.587, 8.655, 8.723, 8.790, 8.856, 8.922, 8.987, 9.051, 9.115, 9.177, 9.239, 9.301, 9.361, 9.421, 9.480, 9.538, 9.596, 9.653, 9.711, 9.767, 9.823, 9.879, 9.935, 9.990, 10.045, 10.100, 10.155, 10.210, 10.264, 10.319, 10.373, 10.428, 10.482, 10.537, 10.590, 10.644, 10.698, 10.752, 10.805, 10.858, 10.911, 10.964, 11.016, 11.069, 11.120, 11.172, 11.224, 11.275, 11.326, 11.377, 11.427, 11.478, 11.528, 11.578, 11.629, 11.679, 11.728, 11.778, 11.827, 11.877, 11.926, 11.975, 12.024, 12.073, 12.122, 12.170, 12.219, 12.267, 12.315, 12.363, 12.411, 12.459},
		{2.459, 2.869, 3.445, 3.960, 4.400, 4.780, 5.111, 5.401, 5.658, 5.888, 6.095, 6.281, 6.451, 6.607, 6.751, 6.885, 7.011, 7.131, 7.245, 7.354, 7.460, 7.562, 7.661, 7.759, 7.854, 7.947, 8.039, 8.129, 8.218, 8.306, 8.393, 8.479, 8.564, 8.649, 8.733, 8.816, 8.898, 8.980, 9.061, 9.142, 9.223, 9.303, 9.383, 9.463, 9.542, 9.621, 9.699, 9.777, 9.855, 9.931, 10.007, 10.082, 10.156, 10.230, 10.303, 10.375, 10.445, 10.516, 10.585, 10.654, 10.722, 10.789, 10.856, 10.922, 10.988, 11.053, 11.118, 11.183, 11.248, 11.312, 11.376, 11.440, 11.504, 11.568, 11.631, 11.695, 11.758, 11.822, 11.885, 11.948, 12.011, 12.074, 12.137, 12.200, 12.262, 12.324, 12.386, 12.448, 12.510, 12.571, 12.633, 12.694, 12.754, 12.815, 12.876, 12.936, 12.996, 13.056, 13.116, 13.176, 13.236, 13.296, 13.355, 13.415, 13.474, 13.533, 13.592, 13.651, 13.710, 13.769, 13.827, 13.885, 13.944, 14.002, 14.060, 14.118, 14.175},
		{2.881, 3.327, 3.962, 4.522, 4.999, 5.408, 5.763, 6.074, 6.351, 6.600, 6.823, 7.026, 7.212, 7.383, 7.542, 7.691, 7.831, 7.964, 8.091, 8.212, 8.330, 8.444, 8.556, 8.665, 8.771, 8.876, 8.979, 9.080, 9.181, 9.280, 9.378, 9.476, 9.572, 9.668, 9.763, 9.858, 9.952, 10.045, 10.138, 10.231, 10.323, 10.415, 10.507, 10.599, 10.690, 10.781, 10.871, 10.962, 11.051, 11.140, 11.228, 11.315, 11.401, 11.487, 11.572, 11.655, 11.738, 11.820, 11.901, 11.981, 12.060, 12.139, 12.217, 12.295, 12.372, 12.449, 12.525, 12.601, 12.676, 12.751, 12.827, 12.902, 12.976, 13.051, 13.126, 13.200, 13.275, 13.349, 13.424, 13.498, 13.572, 13.647, 13.721, 13.795, 13.868, 13.942, 14.015, 14.089, 14.162, 14.235, 14.308, 14.381, 14.454, 14.526, 14.599, 14.671, 14.743, 14.816, 14.888, 14.960, 15.032, 15.104, 15.176, 15.247, 15.319, 15.391, 15.462, 15.533, 15.605, 15.676, 15.747, 15.818, 15.889, 15.960, 16.030, 16.100, 16.171},
		{3.346, 3.839, 4.536, 5.147, 5.662, 6.102, 6.482, 6.817, 7.115, 7.383, 7.626, 7.848, 8.052, 8.240, 8.416, 8.580, 8.736, 8.884, 9.026, 9.162, 9.294, 9.422, 9.546, 9.669, 9.789, 9.906, 10.023, 10.137, 10.251, 10.363, 10.474, 10.585, 10.695, 10.804, 10.912, 11.020, 11.128, 11.234, 11.341, 11.448, 11.554, 11.660, 11.766, 11.872, 11.977, 12.083, 12.188, 12.292, 12.396, 12.499, 12.602, 12.703, 12.804, 12.904, 13.003, 13.101, 13.197, 13.293, 13.388, 13.482, 13.576, 13.668, 13.760, 13.851, 13.942, 14.032, 14.122, 14.211, 14.300, 14.389, 14.477, 14.565, 14.654, 14.742, 14.830, 14.918, 15.006, 15.094, 15.182, 15.270, 15.358, 15.445, 15.533, 15.621, 15.709, 15.797, 15.884, 15.972, 16.059, 16.147, 16.234, 16.322, 16.409, 16.496, 16.584, 16.671, 16.758, 16.845, 16.933, 17.020, 17.107, 17.195, 17.282, 17.369, 17.456, 17.543, 17.630, 17.718, 17.804, 17.891, 17.978, 18.065, 18.152, 18.238, 18.324, 18.411, 18.497},
		{3.859, 4.407, 5.173, 5.838, 6.394, 6.867, 7.275, 7.634, 7.955, 8.245, 8.509, 8.751, 8.975, 9.184, 9.378, 9.561, 9.734, 9.900, 10.058, 10.211, 10.359, 10.503, 10.643, 10.781, 10.916, 11.050, 11.181, 11.311, 11.439, 11.567, 11.694, 11.820, 11.945, 12.069, 12.193, 12.317, 12.440, 12.563, 12.686, 12.809, 12.931, 13.054, 13.176, 13.299, 13.421, 13.543, 13.666, 13.787, 13.908, 14.029, 14.149, 14.268, 14.385, 14.502, 14.618, 14.733, 14.847, 14.960, 15.071, 15.182, 15.292, 15.401, 15.510, 15.617, 15.724, 15.831, 15.937, 16.042, 16.148, 16.253, 16.357, 16.462, 16.566, 16.671, 16.775, 16.880, 16.984, 17.089, 17.193, 17.298, 17.402, 17.507, 17.612, 17.717, 17.822, 17.927, 18.032, 18.137, 18.242, 18.347, 18.453, 18.558, 18.664, 18.769, 18.875, 18.981, 19.087, 19.193, 19.299, 19.405, 19.512, 19.618, 19.725, 19.832, 19.938, 20.045, 20.152, 20.258, 20.365, 20.472, 20.579, 20.685, 20.792, 20.898, 21.005, 21.111, 21.218}
	};
	
	//-4, -3, -2, -1, 0, 1
	public static Number[][] male_weight_lb_graph_data = {
		{3.75 ,4.517 ,5.556 ,6.499 ,7.319 ,8.038 ,8.666 ,9.22 ,9.707 ,10.139 ,10.525 ,10.869 ,11.18 ,11.462 ,11.722 ,11.962 ,12.187 ,12.401 ,12.601 ,12.795 ,12.981 ,13.159 ,13.336 ,13.505 ,13.671 ,13.832 ,13.99 ,14.147 ,14.301 ,14.451 ,14.601 ,14.749 ,14.894 ,15.038 ,15.179 ,15.318 ,15.456 ,15.593 ,15.73 ,15.864 ,15.999 ,16.131 ,16.263 ,16.393 ,16.523 ,16.651 ,16.779 ,16.907 ,17.033 ,17.154 ,17.277 ,17.399 ,17.518 ,17.635 ,17.751 ,17.866 ,17.981 ,18.091 ,18.201 ,18.311 ,18.419 ,18.525 ,18.631 ,18.737 ,18.841 ,18.944 ,19.046 ,19.149 ,19.251 ,19.352 ,19.453 ,19.555 ,19.656 ,19.755 ,19.857 ,19.956 ,20.055 ,20.157 ,20.256 ,20.355 ,20.452 ,20.551 ,20.65 ,20.747 ,20.842 ,20.939 ,21.034 ,21.129 ,21.224 ,21.316 ,21.411 ,21.504 ,21.594 ,21.687 ,21.777 ,21.867 ,21.958 ,22.046 ,22.136 ,22.225 ,22.313 ,22.401 ,22.489 ,22.575 ,22.663 ,22.749 ,22.835 ,22.921 ,23.007 ,23.093 ,23.179 ,23.263 ,23.349 ,23.433 ,23.516 ,23.6 ,23.682},
		{4.586 ,5.421 ,6.574 ,7.615 ,8.51 ,9.288 ,9.967 ,10.562 ,11.091 ,11.561 ,11.98 ,12.357 ,12.701 ,13.014 ,13.303 ,13.572 ,13.823 ,14.061 ,14.288 ,14.504 ,14.714 ,14.914 ,15.113 ,15.304 ,15.492 ,15.677 ,15.855 ,16.034 ,16.208 ,16.382 ,16.552 ,16.72 ,16.887 ,17.053 ,17.216 ,17.377 ,17.538 ,17.696 ,17.853 ,18.009 ,18.166 ,18.32 ,18.475 ,18.629 ,18.781 ,18.931 ,19.081 ,19.231 ,19.378 ,19.524 ,19.669 ,19.813 ,19.954 ,20.095 ,20.232 ,20.368 ,20.505 ,20.637 ,20.77 ,20.9 ,21.027 ,21.155 ,21.281 ,21.409 ,21.532 ,21.656 ,21.779 ,21.903 ,22.024 ,22.145 ,22.266 ,22.388 ,22.509 ,22.628 ,22.749 ,22.868 ,22.99 ,23.109 ,23.23 ,23.347 ,23.466 ,23.585 ,23.704 ,23.821 ,23.938 ,24.054 ,24.171 ,24.286 ,24.403 ,24.515 ,24.63 ,24.744 ,24.857 ,24.969 ,25.082 ,25.192 ,25.304 ,25.415 ,25.525 ,25.637 ,25.748 ,25.856 ,25.966 ,26.074 ,26.184 ,26.292 ,26.4 ,26.508 ,26.616 ,26.724 ,26.83 ,26.938 ,27.044 ,27.15 ,27.255 ,27.361 ,27.467},
		{5.421 ,6.325 ,7.595 ,8.73 ,9.7 ,10.538 ,11.268 ,11.907 ,12.474 ,12.981 ,13.437 ,13.847 ,14.222 ,14.566 ,14.883 ,15.179 ,15.456 ,15.721 ,15.972 ,16.213 ,16.446 ,16.671 ,16.889 ,17.105 ,17.315 ,17.52 ,17.723 ,17.921 ,18.117 ,18.311 ,18.503 ,18.693 ,18.88 ,19.068 ,19.253 ,19.436 ,19.617 ,19.797 ,19.976 ,20.154 ,20.333 ,20.509 ,20.686 ,20.862 ,21.036 ,21.21 ,21.382 ,21.554 ,21.726 ,21.894 ,22.061 ,22.227 ,22.39 ,22.553 ,22.714 ,22.873 ,23.027 ,23.184 ,23.336 ,23.488 ,23.638 ,23.785 ,23.933 ,24.079 ,24.224 ,24.367 ,24.511 ,24.654 ,24.797 ,24.938 ,25.08 ,25.221 ,25.362 ,25.503 ,25.642 ,25.783 ,25.922 ,26.063 ,26.202 ,26.341 ,26.479 ,26.618 ,26.757 ,26.896 ,27.033 ,27.169 ,27.306 ,27.443 ,27.58 ,27.714 ,27.851 ,27.985 ,28.117 ,28.252 ,28.386 ,28.519 ,28.651 ,28.783 ,28.916 ,29.048 ,29.18 ,29.312 ,29.442 ,29.575 ,29.705 ,29.835 ,29.965 ,30.095 ,30.225 ,30.355 ,30.483 ,30.611 ,30.741 ,30.869 ,30.997 ,31.125 ,31.25},
		{6.351 ,7.335 ,8.735 ,9.969 ,11.021 ,11.922 ,12.705 ,13.391 ,14.001 ,14.55 ,15.042 ,15.49 ,15.9 ,16.277 ,16.627 ,16.956 ,17.264 ,17.557 ,17.837 ,18.104 ,18.364 ,18.616 ,18.863 ,19.103 ,19.337 ,19.568 ,19.795 ,20.018 ,20.24 ,20.459 ,20.675 ,20.891 ,21.102 ,21.314 ,21.524 ,21.733 ,21.94 ,22.145 ,22.35 ,22.555 ,22.758 ,22.961 ,23.164 ,23.367 ,23.567 ,23.768 ,23.966 ,24.167 ,24.363 ,24.559 ,24.753 ,24.945 ,25.135 ,25.324 ,25.512 ,25.695 ,25.878 ,26.058 ,26.237 ,26.413 ,26.587 ,26.762 ,26.934 ,27.106 ,27.275 ,27.445 ,27.613 ,27.78 ,27.946 ,28.111 ,28.278 ,28.444 ,28.607 ,28.772 ,28.938 ,29.101 ,29.266 ,29.429 ,29.595 ,29.758 ,29.921 ,30.086 ,30.249 ,30.412 ,30.573 ,30.737 ,30.897 ,31.061 ,31.222 ,31.382 ,31.543 ,31.704 ,31.865 ,32.024 ,32.185 ,32.344 ,32.502 ,32.663 ,32.822 ,32.981 ,33.14 ,33.298 ,33.457 ,33.614 ,33.772 ,33.931 ,34.088 ,34.244 ,34.403 ,34.559 ,34.716 ,34.872 ,35.029 ,35.185 ,35.34 ,35.494 ,35.651},
		{7.377 ,8.463 ,10.0 ,11.347 ,12.482 ,13.452 ,14.29 ,15.029 ,15.686 ,16.277 ,16.812 ,17.302 ,17.751 ,18.166 ,18.554 ,18.915 ,19.259 ,19.586 ,19.899 ,20.199 ,20.49 ,20.772 ,21.045 ,21.316 ,21.581 ,21.839 ,22.097 ,22.348 ,22.599 ,22.846 ,23.091 ,23.336 ,23.578 ,23.818 ,24.057 ,24.295 ,24.533 ,24.766 ,25.002 ,25.238 ,25.472 ,25.706 ,25.939 ,26.173 ,26.404 ,26.638 ,26.87 ,27.099 ,27.328 ,27.555 ,27.782 ,28.005 ,28.228 ,28.448 ,28.666 ,28.882 ,29.094 ,29.306 ,29.515 ,29.722 ,29.93 ,30.132 ,30.335 ,30.536 ,30.737 ,30.935 ,31.133 ,31.33 ,31.526 ,31.722 ,31.916 ,32.11 ,32.306 ,32.5 ,32.694 ,32.888 ,33.082 ,33.276 ,33.47 ,33.664 ,33.858 ,34.05 ,34.244 ,34.438 ,34.632 ,34.826 ,35.018 ,35.212 ,35.404 ,35.598 ,35.789 ,35.983 ,36.175 ,36.367 ,36.561 ,36.753 ,36.945 ,37.136 ,37.33 ,37.522 ,37.714 ,37.908 ,38.1 ,38.292 ,38.483 ,38.675 ,38.867 ,39.061 ,39.251 ,39.442 ,39.634 ,39.826 ,40.018 ,40.207 ,40.397 ,40.589 ,40.778},
		{8.508 ,9.716 ,11.404 ,12.87 ,14.096 ,15.139 ,16.038 ,16.83 ,17.538 ,18.177 ,18.759 ,19.292 ,19.786 ,20.247 ,20.675 ,21.078 ,21.46 ,21.826 ,22.174 ,22.511 ,22.837 ,23.155 ,23.464 ,23.768 ,24.065 ,24.361 ,24.65 ,24.936 ,25.218 ,25.501 ,25.781 ,26.058 ,26.334 ,26.607 ,26.881 ,27.154 ,27.425 ,27.696 ,27.968 ,28.239 ,28.508 ,28.779 ,29.048 ,29.319 ,29.588 ,29.857 ,30.128 ,30.395 ,30.662 ,30.928 ,31.193 ,31.455 ,31.713 ,31.971 ,32.227 ,32.48 ,32.732 ,32.981 ,33.226 ,33.47 ,33.713 ,33.953 ,34.193 ,34.429 ,34.665 ,34.901 ,35.135 ,35.366 ,35.6 ,35.831 ,36.061 ,36.292 ,36.521 ,36.753 ,36.982 ,37.214 ,37.443 ,37.674 ,37.904 ,38.135 ,38.364 ,38.596 ,38.827 ,39.059 ,39.29 ,39.522 ,39.753 ,39.985 ,40.216 ,40.448 ,40.681 ,40.913 ,41.147 ,41.378 ,41.612 ,41.846 ,42.079 ,42.313 ,42.547 ,42.78 ,43.016 ,43.25 ,43.486 ,43.722 ,43.955 ,44.191 ,44.427 ,44.661 ,44.897 ,45.133 ,45.368 ,45.602 ,45.838 ,46.072 ,46.308 ,46.541 ,46.777}
	};
	
	//-4, -3, -2, -1, 0, 1
	public static Number[][] female_weight_kg_graph_data = {
		{1.671, 1.935, 2.341, 2.700, 3.008, 3.277, 3.515, 3.727, 3.918, 4.090, 4.245, 4.386, 4.515, 4.635, 4.745, 4.849, 4.945, 5.037, 5.124, 5.208, 5.289, 5.367, 5.444, 5.519, 5.593, 5.667, 5.740, 5.812, 5.884, 5.956, 6.027, 6.098, 6.168, 6.239, 6.308, 6.378, 6.447, 6.515, 6.584, 6.651, 6.719, 6.786, 6.853, 6.920, 6.986, 7.052, 7.118, 7.184, 7.250, 7.315, 7.379, 7.442, 7.505, 7.567, 7.628, 7.688, 7.747, 7.806, 7.863, 7.919, 7.974, 8.029, 8.083, 8.136, 8.188, 8.240, 8.292, 8.343, 8.393, 8.443, 8.493, 8.541, 8.590, 8.638, 8.685, 8.732, 8.778, 8.824, 8.870, 8.915, 8.959, 9.003, 9.047, 9.090, 9.133, 9.176, 9.218, 9.261, 9.303, 9.345, 9.386, 9.428, 9.469, 9.511, 9.552, 9.593, 9.634, 9.675, 9.717, 9.758, 9.799, 9.840, 9.881, 9.921, 9.962, 10.003, 10.043, 10.083, 10.123, 10.163, 10.202, 10.242, 10.281, 10.320, 10.359, 10.398, 10.436},
		{2.033, 2.325, 2.774, 3.170, 3.508, 3.804, 4.065, 4.299, 4.509, 4.698, 4.869, 5.024, 5.167, 5.300, 5.423, 5.538, 5.646, 5.748, 5.846, 5.940, 6.030, 6.118, 6.204, 6.289, 6.372, 6.455, 6.536, 6.617, 6.698, 6.778, 6.858, 6.937, 7.017, 7.095, 7.174, 7.252, 7.329, 7.407, 7.484, 7.560, 7.637, 7.713, 7.789, 7.865, 7.941, 8.016, 8.092, 8.167, 8.242, 8.317, 8.391, 8.464, 8.536, 8.608, 8.679, 8.749, 8.817, 8.885, 8.952, 9.018, 9.083, 9.148, 9.212, 9.275, 9.337, 9.399, 9.460, 9.522, 9.582, 9.642, 9.702, 9.762, 9.821, 9.879, 9.937, 9.995, 10.052, 10.108, 10.165, 10.221, 10.276, 10.331, 10.386, 10.440, 10.494, 10.548, 10.601, 10.654, 10.707, 10.760, 10.813, 10.865, 10.918, 10.970, 11.023, 11.075, 11.127, 11.179, 11.231, 11.283, 11.335, 11.387, 11.439, 11.490, 11.542, 11.593, 11.644, 11.695, 11.746, 11.797, 11.847, 11.897, 11.948, 11.997, 12.047, 12.096, 12.145},
		{2.395, 2.714, 3.207, 3.639, 4.009, 4.331, 4.616, 4.871, 5.100, 5.306, 5.493, 5.663, 5.820, 5.965, 6.100, 6.227, 6.346, 6.460, 6.568, 6.671, 6.772, 6.869, 6.965, 7.059, 7.151, 7.242, 7.333, 7.423, 7.512, 7.601, 7.689, 7.777, 7.865, 7.952, 8.039, 8.126, 8.212, 8.298, 8.384, 8.469, 8.555, 8.640, 8.725, 8.810, 8.895, 8.980, 9.065, 9.150, 9.234, 9.318, 9.402, 9.485, 9.567, 9.649, 9.729, 9.809, 9.887, 9.965, 10.042, 10.118, 10.192, 10.267, 10.340, 10.413, 10.486, 10.558, 10.629, 10.701, 10.771, 10.842, 10.912, 10.982, 11.051, 11.120, 11.189, 11.257, 11.325, 11.393, 11.460, 11.527, 11.593, 11.659, 11.724, 11.790, 11.855, 11.919, 11.984, 12.048, 12.112, 12.176, 12.240, 12.303, 12.366, 12.430, 12.493, 12.556, 12.619, 12.682, 12.746, 12.809, 12.872, 12.934, 12.997, 13.060, 13.122, 13.184, 13.246, 13.308, 13.370, 13.431, 13.492, 13.553, 13.614, 13.674, 13.734, 13.794, 13.854},
		{2.794, 3.152, 3.695, 4.169, 4.574, 4.927, 5.239, 5.518, 5.770, 5.996, 6.201, 6.389, 6.562, 6.723, 6.873, 7.014, 7.147, 7.274, 7.395, 7.511, 7.624, 7.733, 7.840, 7.945, 8.048, 8.150, 8.252, 8.352, 8.452, 8.551, 8.649, 8.748, 8.846, 8.943, 9.041, 9.138, 9.234, 9.331, 9.427, 9.523, 9.619, 9.715, 9.811, 9.907, 10.003, 10.099, 10.195, 10.292, 10.388, 10.483, 10.578, 10.673, 10.767, 10.860, 10.952, 11.044, 11.134, 11.223, 11.312, 11.400, 11.486, 11.572, 11.658, 11.743, 11.828, 11.912, 11.996, 12.080, 12.163, 12.246, 12.329, 12.412, 12.494, 12.577, 12.659, 12.740, 12.822, 12.903, 12.983, 13.064, 13.144, 13.223, 13.303, 13.382, 13.461, 13.539, 13.617, 13.695, 13.773, 13.851, 13.929, 14.006, 14.084, 14.161, 14.238, 14.316, 14.393, 14.470, 14.547, 14.624, 14.701, 14.778, 14.854, 14.931, 15.007, 15.083, 15.159, 15.235, 15.311, 15.386, 15.461, 15.535, 15.610, 15.684, 15.758, 15.832, 15.905},
		{3.232, 3.644, 4.243, 4.765, 5.210, 5.599, 5.943, 6.251, 6.529, 6.780, 7.007, 7.215, 7.408, 7.588, 7.756, 7.915, 8.066, 8.208, 8.345, 8.477, 8.604, 8.728, 8.850, 8.968, 9.085, 9.201, 9.315, 9.428, 9.541, 9.653, 9.764, 9.875, 9.985, 10.095, 10.205, 10.314, 10.424, 10.532, 10.641, 10.750, 10.859, 10.968, 11.077, 11.186, 11.296, 11.406, 11.515, 11.625, 11.735, 11.844, 11.954, 12.062, 12.170, 12.278, 12.384, 12.490, 12.594, 12.698, 12.801, 12.903, 13.005, 13.106, 13.206, 13.306, 13.406, 13.505, 13.604, 13.703, 13.802, 13.901, 14.000, 14.099, 14.198, 14.297, 14.395, 14.494, 14.592, 14.690, 14.788, 14.885, 14.983, 15.080, 15.177, 15.273, 15.370, 15.466, 15.562, 15.658, 15.753, 15.849, 15.944, 16.040, 16.135, 16.231, 16.326, 16.421, 16.516, 16.611, 16.706, 16.802, 16.897, 16.992, 17.086, 17.181, 17.275, 17.370, 17.464, 17.558, 17.651, 17.744, 17.837, 17.930, 18.022, 18.115, 18.206, 18.298, 18.389},
		{3.711, 4.193, 4.857, 5.434, 5.926, 6.356, 6.737, 7.080, 7.389, 7.669, 7.923, 8.157, 8.374, 8.577, 8.768, 8.949, 9.120, 9.284, 9.440, 9.591, 9.737, 9.880, 10.019, 10.155, 10.289, 10.421, 10.552, 10.681, 10.809, 10.936, 11.063, 11.189, 11.315, 11.440, 11.565, 11.690, 11.814, 11.939, 12.063, 12.187, 12.312, 12.436, 12.561, 12.687, 12.813, 12.939, 13.065, 13.191, 13.318, 13.444, 13.571, 13.697, 13.822, 13.946, 14.071, 14.194, 14.316, 14.438, 14.558, 14.678, 14.798, 14.917, 15.036, 15.154, 15.272, 15.391, 15.509, 15.628, 15.746, 15.865, 15.984, 16.103, 16.223, 16.342, 16.462, 16.581, 16.701, 16.821, 16.941, 17.060, 17.180, 17.299, 17.418, 17.538, 17.657, 17.776, 17.895, 18.013, 18.132, 18.251, 18.369, 18.488, 18.607, 18.725, 18.844, 18.963, 19.081, 19.200, 19.319, 19.437, 19.556, 19.675, 19.793, 19.912, 20.030, 20.148, 20.266, 20.383, 20.501, 20.618, 20.735, 20.851, 20.967, 21.083, 21.199, 21.314, 21.429}
	};
	
	//-4, -3, -2, -1, 0, 1
	public static Number[][] female_weight_lb_graph_data = {
		{3.684 ,4.266 ,5.161 ,5.952 ,6.631 ,7.224 ,7.749 ,8.217 ,8.638 ,9.017 ,9.359 ,9.669 ,9.954 ,10.218 ,10.461 ,10.69 ,10.902 ,11.105 ,11.296 ,11.482 ,11.66 ,11.832 ,12.002 ,12.167 ,12.33 ,12.493 ,12.654 ,12.813 ,12.972 ,13.131 ,13.287 ,13.444 ,13.598 ,13.754 ,13.907 ,14.061 ,14.213 ,14.363 ,14.515 ,14.663 ,14.813 ,14.96 ,15.108 ,15.256 ,15.401 ,15.547 ,15.692 ,15.838 ,15.983 ,16.127 ,16.268 ,16.407 ,16.546 ,16.682 ,16.817 ,16.949 ,17.079 ,17.209 ,17.335 ,17.458 ,17.579 ,17.701 ,17.82 ,17.937 ,18.051 ,18.166 ,18.281 ,18.393 ,18.503 ,18.613 ,18.724 ,18.829 ,18.938 ,19.043 ,19.147 ,19.251 ,19.352 ,19.453 ,19.555 ,19.654 ,19.751 ,19.848 ,19.945 ,20.04 ,20.135 ,20.229 ,20.322 ,20.417 ,20.509 ,20.602 ,20.692 ,20.785 ,20.875 ,20.968 ,21.058 ,21.149 ,21.239 ,21.33 ,21.422 ,21.512 ,21.603 ,21.693 ,21.784 ,21.872 ,21.962 ,22.053 ,22.141 ,22.229 ,22.317 ,22.405 ,22.491 ,22.58 ,22.665 ,22.751 ,22.837 ,22.923 ,23.007},
		{4.482 ,5.126 ,6.116 ,6.989 ,7.734 ,8.386 ,8.962 ,9.478 ,9.941 ,10.357 ,10.734 ,11.076 ,11.391 ,11.684 ,11.956 ,12.209 ,12.447 ,12.672 ,12.888 ,13.095 ,13.294 ,13.488 ,13.677 ,13.865 ,14.048 ,14.231 ,14.409 ,14.588 ,14.766 ,14.943 ,15.119 ,15.293 ,15.47 ,15.642 ,15.816 ,15.988 ,16.158 ,16.329 ,16.499 ,16.667 ,16.837 ,17.004 ,17.172 ,17.339 ,17.507 ,17.672 ,17.84 ,18.005 ,18.17 ,18.336 ,18.499 ,18.66 ,18.818 ,18.977 ,19.134 ,19.288 ,19.438 ,19.588 ,19.736 ,19.881 ,20.024 ,20.168 ,20.309 ,20.448 ,20.584 ,20.721 ,20.856 ,20.992 ,21.124 ,21.257 ,21.389 ,21.521 ,21.651 ,21.779 ,21.907 ,22.035 ,22.161 ,22.284 ,22.41 ,22.533 ,22.654 ,22.776 ,22.897 ,23.016 ,23.135 ,23.254 ,23.371 ,23.488 ,23.605 ,23.721 ,23.838 ,23.953 ,24.07 ,24.184 ,24.301 ,24.416 ,24.531 ,24.645 ,24.76 ,24.875 ,24.989 ,25.104 ,25.218 ,25.331 ,25.445 ,25.558 ,25.67 ,25.783 ,25.895 ,26.008 ,26.118 ,26.228 ,26.341 ,26.449 ,26.559 ,26.667 ,26.775},
		{5.28 ,5.983 ,7.07 ,8.023 ,8.838 ,9.548 ,10.176 ,10.739 ,11.243 ,11.698 ,12.11 ,12.485 ,12.831 ,13.15 ,13.448 ,13.728 ,13.99 ,14.242 ,14.48 ,14.707 ,14.93 ,15.143 ,15.355 ,15.562 ,15.765 ,15.966 ,16.166 ,16.365 ,16.561 ,16.757 ,16.951 ,17.145 ,17.339 ,17.531 ,17.723 ,17.915 ,18.104 ,18.294 ,18.483 ,18.671 ,18.86 ,19.048 ,19.235 ,19.423 ,19.61 ,19.797 ,19.985 ,20.172 ,20.357 ,20.542 ,20.728 ,20.911 ,21.091 ,21.272 ,21.449 ,21.625 ,21.797 ,21.969 ,22.139 ,22.306 ,22.469 ,22.635 ,22.796 ,22.956 ,23.117 ,23.276 ,23.433 ,23.591 ,23.746 ,23.902 ,24.057 ,24.211 ,24.363 ,24.515 ,24.667 ,24.817 ,24.967 ,25.117 ,25.265 ,25.412 ,25.558 ,25.703 ,25.847 ,25.992 ,26.136 ,26.277 ,26.42 ,26.561 ,26.702 ,26.843 ,26.984 ,27.123 ,27.262 ,27.403 ,27.542 ,27.681 ,27.82 ,27.959 ,28.1 ,28.239 ,28.378 ,28.514 ,28.653 ,28.792 ,28.929 ,29.065 ,29.202 ,29.339 ,29.476 ,29.61 ,29.744 ,29.879 ,30.013 ,30.146 ,30.278 ,30.41 ,30.543},
		{6.16 ,6.949 ,8.146 ,9.191 ,10.084 ,10.862 ,11.55 ,12.165 ,12.721 ,13.219 ,13.671 ,14.085 ,14.467 ,14.822 ,15.152 ,15.463 ,15.756 ,16.036 ,16.303 ,16.559 ,16.808 ,17.048 ,17.284 ,17.516 ,17.743 ,17.967 ,18.192 ,18.413 ,18.633 ,18.852 ,19.068 ,19.286 ,19.502 ,19.716 ,19.932 ,20.146 ,20.357 ,20.571 ,20.783 ,20.994 ,21.206 ,21.418 ,21.629 ,21.841 ,22.053 ,22.264 ,22.476 ,22.69 ,22.901 ,23.111 ,23.32 ,23.53 ,23.737 ,23.942 ,24.145 ,24.348 ,24.546 ,24.742 ,24.938 ,25.132 ,25.322 ,25.512 ,25.701 ,25.889 ,26.076 ,26.261 ,26.446 ,26.632 ,26.815 ,26.998 ,27.181 ,27.363 ,27.544 ,27.727 ,27.908 ,28.087 ,28.267 ,28.446 ,28.622 ,28.801 ,28.977 ,29.151 ,29.328 ,29.502 ,29.676 ,29.848 ,30.02 ,30.192 ,30.364 ,30.536 ,30.708 ,30.878 ,31.05 ,31.219 ,31.389 ,31.561 ,31.731 ,31.901 ,32.07 ,32.24 ,32.41 ,32.58 ,32.747 ,32.917 ,33.084 ,33.252 ,33.42 ,33.587 ,33.755 ,33.92 ,34.085 ,34.248 ,34.414 ,34.577 ,34.74 ,34.903 ,35.064},
		{7.125 ,8.034 ,9.354 ,10.505 ,11.486 ,12.344 ,13.102 ,13.781 ,14.394 ,14.947 ,15.448 ,15.906 ,16.332 ,16.729 ,17.099 ,17.449 ,17.782 ,18.095 ,18.397 ,18.688 ,18.968 ,19.242 ,19.511 ,19.771 ,20.029 ,20.285 ,20.536 ,20.785 ,21.034 ,21.281 ,21.526 ,21.77 ,22.013 ,22.255 ,22.498 ,22.738 ,22.981 ,23.219 ,23.459 ,23.699 ,23.94 ,24.18 ,24.42 ,24.661 ,24.903 ,25.146 ,25.386 ,25.628 ,25.871 ,26.111 ,26.354 ,26.592 ,26.83 ,27.068 ,27.302 ,27.535 ,27.765 ,27.994 ,28.221 ,28.446 ,28.671 ,28.893 ,29.114 ,29.334 ,29.555 ,29.773 ,29.991 ,30.21 ,30.428 ,30.646 ,30.864 ,31.083 ,31.301 ,31.519 ,31.735 ,31.953 ,32.17 ,32.386 ,32.602 ,32.815 ,33.032 ,33.245 ,33.459 ,33.671 ,33.885 ,34.096 ,34.308 ,34.52 ,34.729 ,34.941 ,35.15 ,35.362 ,35.571 ,35.783 ,35.992 ,36.202 ,36.411 ,36.621 ,36.83 ,37.042 ,37.251 ,37.461 ,37.668 ,37.877 ,38.084 ,38.294 ,38.501 ,38.708 ,38.913 ,39.118 ,39.323 ,39.528 ,39.731 ,39.936 ,40.137 ,40.34 ,40.54},
		{8.181 ,9.244 ,10.708 ,11.98 ,13.064 ,14.012 ,14.852 ,15.609 ,16.29 ,16.907 ,17.467 ,17.983 ,18.461 ,18.909 ,19.33 ,19.729 ,20.106 ,20.468 ,20.811 ,21.144 ,21.466 ,21.781 ,22.088 ,22.388 ,22.683 ,22.974 ,23.263 ,23.547 ,23.83 ,24.11 ,24.389 ,24.667 ,24.945 ,25.221 ,25.496 ,25.772 ,26.045 ,26.321 ,26.594 ,26.867 ,27.143 ,27.416 ,27.692 ,27.97 ,28.248 ,28.525 ,28.803 ,29.081 ,29.361 ,29.639 ,29.919 ,30.196 ,30.472 ,30.745 ,31.021 ,31.292 ,31.561 ,31.83 ,32.095 ,32.359 ,32.624 ,32.886 ,33.148 ,33.409 ,33.669 ,33.931 ,34.191 ,34.453 ,34.714 ,34.976 ,35.238 ,35.501 ,35.765 ,36.028 ,36.292 ,36.554 ,36.819 ,37.084 ,37.348 ,37.61 ,37.875 ,38.137 ,38.4 ,38.664 ,38.927 ,39.189 ,39.451 ,39.711 ,39.974 ,40.236 ,40.496 ,40.759 ,41.021 ,41.281 ,41.543 ,41.806 ,42.066 ,42.328 ,42.591 ,42.851 ,43.113 ,43.376 ,43.636 ,43.898 ,44.158 ,44.418 ,44.678 ,44.936 ,45.197 ,45.454 ,45.712 ,45.968 ,46.224 ,46.48 ,46.735 ,46.989 ,47.242}
	};
	
	//HEIGHT
	//z-scores = 1, 0, -1, -2, -3, -4
	public static Number[][] male_height_cm_graph_data = {
		{42.312, 44.985, 47.138, 49.048, 50.741, 52.266, 53.638, 54.873, 55.981, 56.980, 57.891, 58.730, 59.515, 60.253, 60.956, 61.633, 62.287, 62.918, 63.533, 64.128, 64.703, 65.260, 65.803, 66.332, 66.848, 67.347, 67.837, 68.315, 68.781, 69.235, 69.682, 70.118, 70.543, 70.961, 71.372, 71.773, 72.163, 72.550, 72.927, 73.295, 73.656, 74.015, 74.361, 74.706, 75.045, 75.379, 75.005, 75.329, 75.648, 75.960, 76.267, 76.572, 76.868, 77.162, 77.454, 77.736, 78.017, 78.292, 78.566, 78.835, 79.100, 79.363, 79.623, 79.878, 80.134, 80.386, 80.636, 80.886, 81.134, 81.383, 81.629, 81.873, 82.114, 82.357, 82.598, 82.837, 83.077, 83.315, 83.550, 83.783, 84.018, 84.247, 84.477, 84.706, 84.932, 85.156, 85.379, 85.604, 85.823, 86.045, 86.262, 86.482, 86.701, 86.918, 87.136, 87.348, 87.563, 87.779, 87.993, 88.208, 88.426, 88.639, 88.852, 89.065, 89.277, 89.493, 89.704, 89.915, 90.130, 90.341, 90.551, 90.764, 90.978, 91.187, 91.399, 91.607, 91.819},
		{44.205, 46.901, 49.088, 51.028, 52.746, 54.294, 55.689, 56.943, 58.068, 59.083, 60.010, 60.864, 61.664, 62.419, 63.139, 63.835, 64.507, 65.159, 65.795, 66.412, 67.011, 67.592, 68.159, 68.713, 69.254, 69.781, 70.297, 70.802, 71.296, 71.778, 72.253, 72.719, 73.174, 73.622, 74.062, 74.493, 74.914, 75.331, 75.739, 76.139, 76.532, 76.922, 77.300, 77.677, 78.048, 78.414, 78.072, 78.428, 78.778, 79.122, 79.460, 79.796, 80.123, 80.447, 80.768, 81.081, 81.391, 81.696, 81.998, 82.295, 82.587, 82.878, 83.164, 83.447, 83.728, 84.006, 84.282, 84.557, 84.830, 85.102, 85.372, 85.640, 85.905, 86.171, 86.435, 86.696, 86.958, 87.217, 87.474, 87.729, 87.984, 88.235, 88.486, 88.734, 88.981, 89.226, 89.469, 89.713, 89.953, 90.194, 90.431, 90.670, 90.908, 91.145, 91.382, 91.614, 91.849, 92.084, 92.318, 92.551, 92.788, 93.021, 93.253, 93.485, 93.717, 93.951, 94.182, 94.413, 94.646, 94.876, 95.106, 95.338, 95.571, 95.799, 96.030, 96.258, 96.488},
		{46.098, 48.817, 51.037, 53.007, 54.750, 56.323, 57.740, 59.013, 60.156, 61.187, 62.129, 62.999, 63.814, 64.585, 65.323, 66.036, 66.728, 67.401, 68.057, 68.696, 69.318, 69.924, 70.515, 71.094, 71.661, 72.214, 72.758, 73.289, 73.811, 74.322, 74.825, 75.319, 75.804, 76.282, 76.752, 77.213, 77.666, 78.113, 78.552, 78.983, 79.408, 79.828, 80.240, 80.647, 81.050, 81.448, 81.139, 81.526, 81.908, 82.283, 82.653, 83.019, 83.378, 83.732, 84.083, 84.426, 84.765, 85.099, 85.429, 85.754, 86.075, 86.392, 86.706, 87.015, 87.322, 87.627, 87.928, 88.228, 88.525, 88.822, 89.116, 89.407, 89.696, 89.985, 90.271, 90.555, 90.839, 91.120, 91.399, 91.675, 91.951, 92.223, 92.494, 92.763, 93.030, 93.295, 93.559, 93.822, 94.082, 94.343, 94.600, 94.858, 95.116, 95.372, 95.628, 95.880, 96.135, 96.389, 96.642, 96.895, 97.150, 97.402, 97.654, 97.905, 98.157, 98.410, 98.660, 98.910, 99.162, 99.412, 99.661, 99.912, 100.163, 100.411, 100.661, 100.909, 101.158},
		{47.991, 50.733, 52.987, 54.987, 56.755, 58.352, 59.790, 61.083, 62.243, 63.291, 64.248, 65.133, 65.964, 66.751, 67.506, 68.237, 68.948, 69.642, 70.320, 70.981, 71.626, 72.255, 72.871, 73.475, 74.067, 74.648, 75.218, 75.777, 76.326, 76.865, 77.397, 77.920, 78.435, 78.942, 79.442, 79.933, 80.417, 80.894, 81.364, 81.827, 82.284, 82.735, 83.179, 83.618, 84.053, 84.482, 84.205, 84.624, 85.038, 85.445, 85.846, 86.243, 86.632, 87.017, 87.397, 87.771, 88.139, 88.502, 88.861, 89.214, 89.562, 89.907, 90.247, 90.583, 90.917, 91.247, 91.574, 91.899, 92.221, 92.541, 92.859, 93.174, 93.487, 93.799, 94.108, 94.414, 94.720, 95.023, 95.323, 95.621, 95.917, 96.210, 96.502, 96.792, 97.079, 97.365, 97.648, 97.931, 98.212, 98.491, 98.769, 99.047, 99.323, 99.599, 99.874, 100.147, 100.420, 100.693, 100.966, 101.239, 101.512, 101.783, 102.055, 102.326, 102.597, 102.868, 103.138, 103.408, 103.678, 103.948, 104.217, 104.486, 104.756, 105.024, 105.292, 105.560, 105.827},
		{49.884, 52.649, 54.937, 56.967, 58.760, 60.381, 61.841, 63.153, 64.331, 65.395, 66.367, 67.268, 68.113, 68.917, 69.690, 70.438, 71.169, 71.883, 72.582, 73.265, 73.933, 74.587, 75.227, 75.856, 76.474, 77.081, 77.678, 78.264, 78.841, 79.409, 79.969, 80.521, 81.066, 81.602, 82.132, 82.654, 83.168, 83.675, 84.176, 84.671, 85.159, 85.642, 86.118, 86.589, 87.055, 87.516, 87.272, 87.723, 88.168, 88.606, 89.039, 89.466, 89.887, 90.303, 90.712, 91.116, 91.513, 91.905, 92.292, 92.674, 93.050, 93.422, 93.789, 94.152, 94.511, 94.867, 95.220, 95.570, 95.916, 96.261, 96.602, 96.941, 97.278, 97.612, 97.944, 98.274, 98.600, 98.925, 99.247, 99.567, 99.884, 100.198, 100.511, 100.821, 101.129, 101.434, 101.738, 102.040, 102.341, 102.640, 102.938, 103.235, 103.530, 103.825, 104.120, 104.413, 104.706, 104.998, 105.291, 105.582, 105.874, 106.165, 106.456, 106.746, 107.036, 107.326, 107.616, 107.905, 108.194, 108.483, 108.772, 109.060, 109.348, 109.636, 109.923, 110.210, 110.497},
		{51.777, 54.565, 56.887, 58.946, 60.765, 62.410, 63.892, 65.223, 66.419, 67.498, 68.487, 69.402, 70.263, 71.083, 71.873, 72.639, 73.389, 74.125, 74.845, 75.550, 76.241, 76.918, 77.583, 78.237, 78.881, 79.515, 80.138, 80.751, 81.356, 81.952, 82.541, 83.122, 83.696, 84.263, 84.821, 85.374, 85.919, 86.457, 86.989, 87.515, 88.035, 88.548, 89.057, 89.560, 90.058, 90.550, 90.339, 90.821, 91.298, 91.768, 92.232, 92.689, 93.142, 93.588, 94.027, 94.460, 94.887, 95.309, 95.723, 96.133, 96.537, 96.936, 97.330, 97.720, 98.105, 98.487, 98.866, 99.240, 99.612, 99.980, 100.346, 100.709, 101.069, 101.426, 101.781, 102.133, 102.481, 102.828, 103.171, 103.512, 103.850, 104.186, 104.519, 104.849, 105.178, 105.504, 105.828, 106.150, 106.471, 106.789, 107.107, 107.423, 107.738, 108.052, 108.365, 108.679, 108.992, 109.303, 109.615, 109.926, 110.236, 110.546, 110.857, 111.167, 111.476, 111.785, 112.094, 112.403, 112.711, 113.019, 113.327, 113.634, 113.941, 114.248, 114.554, 114.861, 115.166}
	};
	
	//HEIGHT 
	//Every 16 days
	//z-scores = 1, 0, -1, -2, -3, -4
	public static Number[][] female_height_cm_graph_data = {
		{41.697, 44.152, 46.044, 47.718, 49.199, 50.523, 51.722, 52.816, 53.812, 54.723, 55.565, 56.354, 57.097, 57.806, 58.490, 59.148, 59.787, 60.406, 61.008, 61.598, 62.170, 62.727, 63.274, 63.807, 64.327, 64.837, 65.334, 65.823, 66.299, 66.770, 67.230, 67.682, 68.126, 68.560, 68.990, 69.410, 69.824, 70.234, 70.635, 71.030, 71.418, 71.801, 72.178, 72.549, 72.915, 73.275, 72.932, 73.282, 73.630, 73.970, 74.308, 74.638, 74.967, 75.290, 75.609, 75.924, 76.233, 76.539, 76.839, 77.140, 77.432, 77.724, 78.013, 78.299, 78.581, 78.860, 79.140, 79.413, 79.687, 79.959, 80.228, 80.495, 80.759, 81.021, 81.281, 81.542, 81.797, 82.054, 82.308, 82.560, 82.806, 83.054, 83.299, 83.547, 83.788, 84.027, 84.264, 84.503, 84.736, 84.972, 85.201, 85.432, 85.658, 85.886, 86.113, 86.338, 86.561, 86.783, 87.004, 87.223, 87.441, 87.657, 87.876, 88.090, 88.302, 88.517, 88.726, 88.934, 89.145, 89.350, 89.558, 89.761, 89.966, 90.170, 90.369, 90.571, 90.771},
		{43.560, 46.061, 48.003, 49.721, 51.242, 52.604, 53.837, 54.961, 55.987, 56.926, 57.795, 58.609, 59.379, 60.114, 60.823, 61.507, 62.173, 62.818, 63.448, 64.065, 64.665, 65.251, 65.825, 66.387, 66.935, 67.474, 68.001, 68.518, 69.023, 69.523, 70.011, 70.492, 70.965, 71.429, 71.887, 72.336, 72.778, 73.217, 73.646, 74.069, 74.486, 74.898, 75.303, 75.702, 76.096, 76.484, 76.168, 76.545, 76.920, 77.287, 77.652, 78.010, 78.365, 78.715, 79.060, 79.400, 79.736, 80.067, 80.393, 80.718, 81.036, 81.353, 81.666, 81.976, 82.283, 82.586, 82.889, 83.186, 83.484, 83.779, 84.071, 84.361, 84.648, 84.933, 85.216, 85.499, 85.777, 86.055, 86.332, 86.606, 86.874, 87.144, 87.411, 87.679, 87.942, 88.202, 88.461, 88.720, 88.975, 89.230, 89.481, 89.733, 89.980, 90.228, 90.475, 90.720, 90.964, 91.206, 91.446, 91.685, 91.923, 92.159, 92.397, 92.631, 92.863, 93.097, 93.326, 93.554, 93.783, 94.008, 94.235, 94.457, 94.682, 94.905, 95.123, 95.343, 95.562},
		{45.422, 47.971, 49.962, 51.725, 53.286, 54.685, 55.951, 57.107, 58.163, 59.130, 60.025, 60.865, 61.660, 62.421, 63.156, 63.867, 64.558, 65.231, 65.888, 66.532, 67.160, 67.774, 68.376, 68.966, 69.544, 70.111, 70.667, 71.212, 71.747, 72.275, 72.793, 73.303, 73.805, 74.297, 74.784, 75.262, 75.733, 76.199, 76.657, 77.109, 77.554, 77.994, 78.427, 78.854, 79.276, 79.692, 79.403, 79.809, 80.210, 80.605, 80.997, 81.381, 81.763, 82.139, 82.510, 82.877, 83.238, 83.595, 83.947, 84.297, 84.640, 84.982, 85.320, 85.654, 85.985, 86.312, 86.639, 86.960, 87.281, 87.599, 87.914, 88.227, 88.538, 88.846, 89.151, 89.456, 89.757, 90.057, 90.355, 90.651, 90.943, 91.234, 91.522, 91.811, 92.095, 92.378, 92.658, 92.937, 93.213, 93.489, 93.761, 94.033, 94.301, 94.570, 94.837, 95.102, 95.366, 95.628, 95.889, 96.148, 96.406, 96.662, 96.919, 97.172, 97.424, 97.677, 97.926, 98.174, 98.422, 98.667, 98.912, 99.154, 99.397, 99.639, 99.877, 100.116, 100.353},
		{47.285, 49.880, 51.922, 53.729, 55.330, 56.766, 58.066, 59.253, 60.338, 61.333, 62.255, 63.120, 63.941, 64.729, 65.489, 66.226, 66.944, 67.644, 68.328, 68.999, 69.655, 70.297, 70.928, 71.546, 72.152, 72.748, 73.333, 73.907, 74.472, 75.028, 75.575, 76.113, 76.644, 77.166, 77.681, 78.188, 78.688, 79.182, 79.668, 80.149, 80.623, 81.090, 81.552, 82.007, 82.457, 82.900, 82.639, 83.072, 83.500, 83.923, 84.341, 84.753, 85.161, 85.563, 85.961, 86.353, 86.740, 87.123, 87.501, 87.875, 88.244, 88.611, 88.973, 89.331, 89.686, 90.038, 90.388, 90.734, 91.078, 91.419, 91.758, 92.093, 92.427, 92.758, 93.086, 93.413, 93.737, 94.059, 94.379, 94.697, 95.011, 95.324, 95.634, 95.943, 96.249, 96.553, 96.854, 97.155, 97.452, 97.748, 98.041, 98.333, 98.623, 98.912, 99.199, 99.484, 99.768, 100.050, 100.331, 100.610, 100.888, 101.164, 101.440, 101.714, 101.985, 102.257, 102.526, 102.793, 103.061, 103.325, 103.589, 103.851, 104.113, 104.373, 104.631, 104.888, 105.144},
		{49.148, 51.790, 53.881, 55.732, 57.373, 58.846, 60.181, 61.399, 62.514, 63.536, 64.485, 65.376, 66.223, 67.036, 67.822, 68.586, 69.330, 70.057, 70.768, 71.466, 72.150, 72.820, 73.479, 74.125, 74.760, 75.385, 75.999, 76.602, 77.196, 77.780, 78.356, 78.924, 79.483, 80.034, 80.577, 81.113, 81.642, 82.164, 82.680, 83.188, 83.691, 84.187, 84.676, 85.160, 85.637, 86.109, 85.875, 86.336, 86.791, 87.241, 87.685, 88.125, 88.559, 88.987, 89.411, 89.829, 90.243, 90.651, 91.055, 91.454, 91.849, 92.239, 92.626, 93.009, 93.388, 93.765, 94.138, 94.508, 94.875, 95.239, 95.601, 95.960, 96.316, 96.670, 97.022, 97.370, 97.717, 98.061, 98.403, 98.742, 99.079, 99.413, 99.746, 100.075, 100.403, 100.728, 101.051, 101.372, 101.690, 102.007, 102.321, 102.634, 102.945, 103.254, 103.561, 103.866, 104.170, 104.473, 104.774, 105.073, 105.370, 105.667, 105.962, 106.255, 106.547, 106.837, 107.126, 107.413, 107.699, 107.984, 108.267, 108.548, 108.828, 109.107, 109.384, 109.660, 109.935},
		{51.010, 53.699, 55.840, 57.736, 59.417, 60.927, 62.296, 63.545, 64.689, 65.740, 66.715, 67.631, 68.504, 69.343, 70.155, 70.945, 71.715, 72.469, 73.208, 73.933, 74.644, 75.344, 76.030, 76.705, 77.369, 78.022, 78.665, 79.297, 79.920, 80.533, 81.138, 81.734, 82.322, 82.902, 83.474, 84.039, 84.597, 85.147, 85.691, 86.228, 86.759, 87.283, 87.801, 88.312, 88.818, 89.317, 89.111, 89.599, 90.081, 90.558, 91.030, 91.496, 91.957, 92.412, 92.861, 93.306, 93.745, 94.179, 94.609, 95.032, 95.453, 95.868, 96.279, 96.686, 97.090, 97.491, 97.887, 98.281, 98.672, 99.059, 99.444, 99.826, 100.205, 100.582, 100.957, 101.328, 101.697, 102.063, 102.426, 102.788, 103.147, 103.503, 103.857, 104.207, 104.556, 104.903, 105.248, 105.589, 105.929, 106.265, 106.601, 106.934, 107.266, 107.595, 107.923, 108.249, 108.573, 108.895, 109.216, 109.535, 109.853, 110.169, 110.483, 110.796, 111.108, 111.417, 111.726, 112.033, 112.338, 112.642, 112.944, 113.245, 113.544, 113.841, 114.138, 114.433, 114.726}
	};

}