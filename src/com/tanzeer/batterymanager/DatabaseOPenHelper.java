package com.tanzeer.batterymanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.util.Log;

public class DatabaseOPenHelper extends SQLiteOpenHelper {

	private static String Databasename = "BATTERYDATAN";
	private static int Databasevertion = 1;

	public DatabaseOPenHelper(Context context) {
		super(context, Databasename, null, Databasevertion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlstring = "Create Table batteryinfo (" + "KEY_ID"
				+ " INTERGER PRIMARY KEY, " + " startdate " + " TEXT, "
				+ " starttime" + " TEXT, " + " startpercent " + " INTEGER,"
				+ " starttimemili " + " TEXT ," + " enddate " + " TEXT, "
				+ " endtime " + " TEXT, " + " endpercent " + " INTEGER,"
				+ " endtimemili " + " TEXT " + ")";
		db.execSQL(sqlstring);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + "batteryinfo");

	}

	public void onConnct(int percent) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Time time = new Time();
		time.setToNow();
		String date = "" + time.monthDay + "." + (time.month + 1) + "."
				+ time.year;
		String tim = "" + time.hour + ":" + time.minute;

		long miliSec = System.currentTimeMillis();

		int battery = percent;
		values.put("startdate", date);
		values.put("starttime", tim);
		values.put("starttimemili", miliSec);
		values.put("startpercent", battery);
		db.insert("batteryinfoconnect", null, values);

		db.close();
	}

	public void setdata(String startDate, String startTime, int startPercent,
			String startMili, String endDate, String endTime, int endPercent,
			String endMili) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("startdate", startDate);
		values.put("starttime", startTime);
		values.put("starttimemili", startMili);
		values.put("startpercent", startPercent);
		values.put("enddate", endDate);
		values.put("endtime", endTime);
		values.put("endtimemili", endMili);
		values.put("endpercent", endPercent);
		db.insert("batteryinfo", null, values);
		//check for 50 insertion
		
		String[] colums = new String[] { "starttimemili" };
		
		int rowNum=0;
		Cursor cr=db.query("batteryinfo", colums,null,null,null,null,null);

		while(cr.moveToNext())
		{
			rowNum=rowNum+1;
			
		}
		if(rowNum>50)
		{
			cr.moveToFirst();
			String mili=cr.getString(0);
			db.delete("batteryinfo","starttimemili = "+mili,null);
		}
		cr.close();
		db.close();
	}

	public ArrayList<BatteryData> getAlldata() {
		
		ArrayList<BatteryData> list=new ArrayList<BatteryData>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] colums = new String[] { "startdate,starttime,starttimemili,startpercent,enddate,endtime,endtimemili,endpercent" };
		Cursor cr=db.query("batteryinfo", colums,null,null,null,null,null);
		while(cr.moveToNext())
		{
			list.add(new BatteryData(cr.getString(0),cr.getString(1),cr.getInt(3),cr.getString(2),cr.getString(4),cr.getString(5),cr.getInt(7),cr.getString(6)));
			
			//Log.v("msg",cr.getString(0)+cr.getString(1)+cr.getString(2)+cr.getString(3)+cr.getString(4)+cr.getString(5)+cr.getString(6)+cr.getString(7));
		}
		cr.close();
		db.close();
		return list;


	}

}
