package com.tanzeer.batterymanager;

import android.R.string;

public class BatteryData {
	
	public  String startdate;
	public String starttime;
	public String startmili;
	public int startpercent;
	public String enddate;
	public String endtime;
	public String endmili;
	public int endpercent;
	public BatteryData(String startDate, String startTime, int startPercent,
			String startMili, String endDate, String endTime, int endPercent,
			String endMili)
	{
		this.startdate=startDate;
		this.starttime=startTime;
		this.startmili=startMili;
		this.startpercent=startPercent;
		this.enddate=endDate;
		this.endtime=endTime;
		this.endmili=endMili;
		this.endpercent=endPercent;
		
	}

}
