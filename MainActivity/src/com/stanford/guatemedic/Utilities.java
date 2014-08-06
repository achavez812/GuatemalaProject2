
package com.stanford.guatemedic;

import static com.nativecss.enums.RemoteContentRefreshPeriod.Never;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.nativecss.NativeCSS;


/**
 * The Class Utilities.
 */
/**
 * @author achavez
 *
 */
public class Utilities {

	/**
	 * Returns current date and time Formatted as Year-Month-Day
	 * Hour:Minute:Second.
	 * 
	 * @return current date and time as String
	 */
	public static String getTodayString() {
		Calendar calendar = Calendar.getInstance(); //Uses default timezone (figure out what to use)
		
		String year = "" + calendar.get(Calendar.YEAR); 
		String month = "" + (calendar.get(Calendar.MONTH) + 1) ;
		if (month.length() == 1) month = 0 + month;
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		if (day.length() == 1) day = 0 + day;
		
		String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
		if (hour.length() == 1) hour = 0 + hour;
		String minute = "" + calendar.get(Calendar.MINUTE);
		if (minute.length() == 1) minute = 0 + minute;
		String second = "" + calendar.get(Calendar.SECOND);
		if (second.length() == 1) second = 0 + second;
		
		String date = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second;
		return date;
	}
	
	public static boolean hasInternetConnection(Context context) {
		ConnectivityManager cm =
		        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
	}

	/**
	 * Performs an HTTP GET Request.
	 * 
	 * @param urlString
	 *            The desired URL
	 * @param headerMap
	 * 			  This can be null
	 *            Mapping from Key -> Value for Headers
	 * @return The response from the HTTP Request 
	 * 		   Will return null if an exception is thrown
	 * 	   	   Will return "-1" if status code does not equal 200  
	 */
	
	public static String getRequest(String urlString, Map<String, String> headerMap) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
			if (headerMap != null) {
				for (String key : headerMap.keySet()) {
					connection.addRequestProperty(key, headerMap.get(key));
				}
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return "-1";
			}
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			connection.disconnect();
			return new String(out.toByteArray());
		} catch(IOException e) {
			return null;
		}
	}
	
	
	/*Loads Native CSS*/
	public void loadNativeCSS() {
    URL css;
	try {
		css = new URL("http://10.0.2.2:8000/styles.css");
		NativeCSS.styleWithCSS("styles.css",css,Never);
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Performs an HTTP POST Request
	 * 
	 * @param urlString
	 *            The desired URL
	 * @param headerMap
	 * 			  This can be null
	 *            Mapping from Key -> Value for headers
	 * @param jsonBody
	 * 			  This can be null
	 *            The body of the POST Request as a JSON String
	 * @return The response from the HTTP Request 
	 * 		   Will return null if an exception is thrown
	 * 	   	   Will return "-1" if status code does not equal 200  
	 */
	public static String postRequest(String urlString, Map<String, String> headerMap, String jsonBody) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urlString);
			if (headerMap != null) {
			    for (String key : headerMap.keySet()) {
			    	httppost.addHeader(key, headerMap.get(key));
			    }
			}
		    if (jsonBody != null) {
			    StringEntity sEntity = new StringEntity(jsonBody, "UTF-8");
			    httppost.setEntity(sEntity);
		    }
		    HttpResponse response = httpclient.execute(httppost);
		    if (response.getStatusLine().getStatusCode() != 200) {
		    	return "-1";
		    }
		    return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		} catch (IOException e) {
			return null;
		} 
	}
	
	public static double kilogramsToPounds(double kilograms) {
		return kilograms * 2.2046;
	}
	
	public static double poundsToKilograms(double pounds) {
		return pounds / 2.2046;
	}
	
	public static String formatDate(String old_date) {
		String new_date = old_date.replace('-', '/');
		return new_date.substring(0, 10);
	}
	
	public static double convertMonthsToWeeks(double months) {
		double days_in_month = 30.4167;
		
		return (months * days_in_month) / 7;
	}
	
	//in months
	public static double timeBetween(String date1, String date2) {
		Log.i("WTF", "date1: " + date1);
		Log.i("WTF", "date2: " + date2);
		int year1 = Integer.parseInt(date1.substring(0,4));
		int year2 = Integer.parseInt(date2.substring(0, 4));
		int month1 = Integer.parseInt(date1.substring(5, 7));
		int month2 = Integer.parseInt(date2.substring(5, 7));
		int day1 = Integer.parseInt(date1.substring(8, 10));
		int day2 = Integer.parseInt(date2.substring(8, 10));
		
		int years = year2 - year1;
		int months = month2 - month1;
		int days = day2 - day1;
		
		return years*12 + months + days/30.4167;
	}
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	//This only handles String dates formatted correctly
	public static int getAgeInYears(String birth_date) {
		String current_date = formatDate(getTodayString());
		Log.i("WTF", current_date);
		int year1 = Integer.parseInt(current_date.substring(0,4));
		int year2 = Integer.parseInt(birth_date.substring(0, 4));
		int month1 = Integer.parseInt(current_date.substring(5, 7));
		int month2 = Integer.parseInt(birth_date.substring(5, 7));
		int day1 = Integer.parseInt(current_date.substring(8, 10));
		int day2 = Integer.parseInt(birth_date.substring(8, 10));
		
		int years = year1 - year2;
		int months = month1 - month2;
		int days = day1 - day2;
		
		return (int)(years + months/12.0 + days/365.0);
	}
	
	public static double getAgeInMonths(String birth_date) {
		String current_date = formatDate(getTodayString());
		Log.i("WTF", current_date);
		int year1 = Integer.parseInt(current_date.substring(0,4));
		int year2 = Integer.parseInt(birth_date.substring(0, 4));
		int month1 = Integer.parseInt(current_date.substring(5, 7));
		int month2 = Integer.parseInt(birth_date.substring(5, 7));
		int day1 = Integer.parseInt(current_date.substring(8, 10));
		int day2 = Integer.parseInt(birth_date.substring(8, 10));
		
		int years = year1 - year2;
		int months = month1 - month2;
		int days = day1 - day2;
		
		return years*12.0 + months + days/30.4167;
	}
	
	public static int getAgeInDays(String birth_date) {
		String current_date = formatDate(getTodayString());
		Log.i("WTF", current_date);
		int year1 = Integer.parseInt(current_date.substring(0,4));
		int year2 = Integer.parseInt(birth_date.substring(0, 4));
		int month1 = Integer.parseInt(current_date.substring(5, 7));
		int month2 = Integer.parseInt(birth_date.substring(5, 7));
		int day1 = Integer.parseInt(current_date.substring(8, 10));
		int day2 = Integer.parseInt(birth_date.substring(8, 10));
		
		int years = year1 - year2;
		int months = month1 - month2;
		int days = day1 - day2;
		
		return (int)(years * 365.2 + months * 30.4167 + days);
	}
	
	public static Number[] truncateArray(Number[] array, int values) {
		Number[] new_array = new Number[values];
		for (int i = 0; i < values; i++)
			new_array[i] = array[i];
		return new_array;
	}
}