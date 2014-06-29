package com.example.guatemal;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
 
public class LazyAdapter extends BaseAdapter implements Filterable{
 
    private android.support.v4.app.Fragment fragment;
    private ArrayList<HashMap<String, String>> data;
    private ArrayList<HashMap<String, String>> filtered;

    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;
 
    public LazyAdapter(android.support.v4.app.Fragment f, ArrayList<HashMap<String, String>> d) {
        fragment = f;
        data=d;
        filtered =d;
        inflater = (LayoutInflater)f.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.patient_list_des, null);
 
        TextView pname = (TextView)vi.findViewById(R.id.patient_name); // title
        TextView page = (TextView)vi.findViewById(R.id.patient_age); // patient age
        TextView pid = (TextView)vi.findViewById(R.id.patient_id); // patient id
        ImageView imageView = (ImageView)vi.findViewById(R.id.patient_image);
 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in list view
        pname.setText(Html.fromHtml(song.get(patient_list.KEY_NAME)));
        page.setText(Html.fromHtml(song.get(patient_list.KEY_DOB)));
        pid.setText(Html.fromHtml(song.get(patient_list.REC_ID)));

		imageView.setImageBitmap(decodeSampledBitmapFromResource(Html.fromHtml(song.get(patient_list.IMAGE_URL)).toString() ,100, 100));
		

        return vi;
    }

	
	
	public void notifyDataSetChanged() { 
        super.notifyDataSetChanged(); 
    } 
	 public static Bitmap decodeSampledBitmapFromResource(String picturePath,
		        int reqWidth, int reqHeight) {

		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(picturePath, options);
		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    return BitmapFactory.decodeFile(picturePath,options);
		}
	 public static int calculateInSampleSize(
	            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
                protected void publishResults(CharSequence constraint, FilterResults results) {
                //Log.d(TAG, "**** PUBLISHING RESULTS for: " + constraint);
            	
                data = (ArrayList<HashMap<String, String>>)results.values;
                notifyDataSetChanged();
            }

            protected FilterResults performFiltering(CharSequence constraint) {
               // Log.d(TAG, "**** PERFORM FILTERING for: " + constraint);
                constraint = constraint.toString().toLowerCase();
 
                FilterResults results = new FilterResults();
                if(constraint != null && constraint.toString().length() > 0) {
                       
                        ArrayList<HashMap<String, String>> filt = new ArrayList<HashMap<String, String>>();
                        ArrayList<HashMap<String, String>> lData = new ArrayList<HashMap<String, String>>();
                       
                        synchronized (this) {
                                lData.addAll(data);
                    }
                       
                    for(int i = 0, l = lData.size(); i < l; i++) {
                        HashMap<String, String> m = lData.get(i);
                        if(m.get(patient_list.KEY_NAME).toLowerCase().contains(constraint))
                            filt.add(m);
                    }
                   
                    results.count = filt.size();
                    results.values = filt;                 
                }               
                else {
                       
                    synchronized(this)
                    {
                        results.count = filtered.size();
                        results.values = filtered;
                    }
                } 
               
                return results;
            }
        };
    } 
}