package com.tanzeer.batterymanager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import android.R.animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<BatteryData> {
	private Context thisContext;

	public ListViewAdapter(Context context, int textViewResourceId,
			ArrayList<BatteryData> items) {
		super(context, textViewResourceId, items);
		thisContext = context;

	}

	private class ViewHolder {

		TextView startDate;
		TextView startTime;
		TextView startPercent;
		TextView endDate;
		TextView endTime;
		TextView endPercent;
		TextView timeTaken;
		TextView chargeIncreased;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) thisContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);
			holder = new ViewHolder();
			holder.startDate = (TextView) convertView
					.findViewById(R.id.satrtdate);
			holder.startTime = (TextView) convertView
					.findViewById(R.id.starttime);
			holder.startPercent = (TextView) convertView
					.findViewById(R.id.satrtcharge);
			holder.endDate = (TextView) convertView.findViewById(R.id.enddate);
			holder.endTime = (TextView) convertView.findViewById(R.id.endtime);
			holder.endPercent = (TextView) convertView
					.findViewById(R.id.endcharge);
			holder.timeTaken = (TextView) convertView
					.findViewById(R.id.timetaken);
			holder.chargeIncreased = (TextView) convertView
					.findViewById(R.id.chargeincreased);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		BatteryData rowItem = getItem(position);
		holder.startDate.setText("Start date: "+rowItem.startdate);
		holder.startTime.setText("Time: "+rowItem.starttime);
		
		holder.startPercent.setText("Charge: "+rowItem.startpercent+"%");
		holder.endDate.setText("End date: "+rowItem.enddate);
		holder.endTime.setText("Time : "+rowItem.endtime);
		holder.endPercent.setText("Charge: "+rowItem.endpercent+"%");
		
		long startMili=Long.parseLong(rowItem.startmili);
		long endMili=Long.parseLong(rowItem.endmili);
		
		double gap=(double)endMili-startMili;
		
		double min=gap/(1000*60);
		DecimalFormat df=new DecimalFormat("#.##");
		min=Double.valueOf(df.format(min));
		holder.timeTaken.setText("Time Taken: "+min+ " min");
		holder.chargeIncreased.setText("Charge increased: "+(rowItem.endpercent- rowItem.startpercent)+"%");
		

		return convertView;
	}
}