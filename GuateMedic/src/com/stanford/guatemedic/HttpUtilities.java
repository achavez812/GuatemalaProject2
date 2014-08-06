package com.stanford.guatemedic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class HttpUtilities {
	
	/*
	 * THIS SHOULD BE UPDATED TO MAKE A SIMPLE GET REQUEST TO SERVER TO SEE IF IT IS CONNECTED
	 * @param context
	 * 			state of the application (getActivity() or getApplication() are common methods called)
	 * 
	 * @return boolean value representing if there is an Internet connection
	 * 			Having an active network interface doesn't guarantee that a particular networked service
	 * 			is available. Cannot be sure there is a valid connection until you get a response from server.
	 * 			Therefore this method may return true, but it may not actually be able to connect to a server.
	 */
	public static boolean hasInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	/*
	 *  @param urlString
	 *  		The desired URL as a String
	 *  
	 *  @param headerMap
	 *  		Mapping from String Key -> String Value for headers to be included in GET request
	 *  		This can be null to represent no header
	 *  
	 *  @return String response or a response representing an error
	 *  		null
	 *  			There was an exception thrown. Couldn't connect to server. Invalid Internet connection.
	 *  		"-1"
	 *  			Some response code other than 200 was returned. Received a response but it was invalid.	
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
	
	/*
	 *  @param urlString
	 *  		The desired URL as a String
	 *  
	 *  @param headerMap
	 *  		Mapping from String Key -> String Value for headers to be included in GET request
	 *  		This can be null to represent no header
	 *  
	 *  @param jsonBody
	 *  		The body of the POST request as a JSON String
	 *  		This can be null to represent no body
	 *  
	 *  @return String response or a response representing an error
	 *  		null
	 *  			There was an exception thrown. Couldn't connect to server. Invalid Internet connection.
	 *  		"-1"
	 *  			Some response code other than 200 was returned. Received a response but it was invalid.	
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
	
}
