package com.stanford.guatemedic;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class LazyAdapterExam extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
 
    public LazyAdapterExam(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
 
    public LazyAdapterExam(OnClickListener onClickListener,
			ArrayList<HashMap<String, String>> patientList2) {
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
            vi = inflater.inflate(R.layout.examdetails_des, null);
 
        TextView examdate = (TextView)vi.findViewById(R.id.view_exam_date); // title
        TextView weight = (TextView)vi.findViewById(R.id.view_weight); // artist name
        TextView height = (TextView)vi.findViewById(R.id.view_height); // artist name
        TextView id = (TextView)vi.findViewById(R.id.viewexam_id); // artist name
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        examdate.setText(Html.fromHtml(song.get(view_patient.KEY_EXAMDATE)));
        weight.setText(Html.fromHtml(song.get(view_patient.KEY_WEIGHT)));
        height.setText(Html.fromHtml(song.get(view_patient.KEY_HEIGHT)));
        id.setText(Html.fromHtml(song.get(view_patient.REC_ID)));
        return vi;
    }
}