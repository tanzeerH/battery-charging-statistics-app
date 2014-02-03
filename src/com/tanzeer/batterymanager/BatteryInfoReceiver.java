package com.tanzeer.batterymanager;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class BatteryInfoReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {

			Toast.makeText(context, "powerConnected", Toast.LENGTH_LONG).show();
			onBatteryStateChangedConncted(context);
		} else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
			Toast.makeText(context, "Power DisConnected", Toast.LENGTH_LONG)
					.show();
			onBatteryStateChangedDisConncted(context);

		}

		/*
		 * int temp=intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
		 * Log.v("msg",""+temp); int health=
		 * intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0); int icon_small=
		 * intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0); int level=
		 * intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0); int plugged=
		 * intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0); boolean present=
		 * intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT); int
		 * scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0); int status=
		 * intent.getIntExtra(BatteryManager.EXTRA_STATUS,0); String technology=
		 * intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY); int
		 * temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
		 * int voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);
		 * Toast.makeText(context,
		 * "plugged: "+plugged,Toast.LENGTH_SHORT).show(); String str="temp: "
		 * +temp + "health:"+
		 * health+"plugged: "+plugged+" Technology: "+technology;
		 * Toast.makeText(context, str,Toast.LENGTH_SHORT).show(); if (level >=
		 * 0 && scale > 0) { int percentage = (level * 100) / scale;
		 * Log.d("tag", level+" "+scale+" "+percentage); }
		 */

	}

	public void onBatteryStateChangedConncted(Context context) {
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent baterystatus = context.getApplicationContext().registerReceiver(
				null, ifilter);
		int level = baterystatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		boolean present = baterystatus.getExtras().getBoolean(
				BatteryManager.EXTRA_PRESENT);
		int scale = baterystatus.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
		int status = baterystatus.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
		int percentage = 0;
		if (level >= 0 && scale > 0) {
			percentage = (level * 100) / scale;
		}
		Time time = new Time();
		time.setToNow();
		String date = "" + time.monthDay + "." + (time.month + 1) + "."
				+ time.year;
		String tim = "" + time.hour + ":" + time.minute;

		long miliSec = System.currentTimeMillis();

		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putInt("isConnected", 1);
		editor.putInt("startparcent", percentage);
		editor.putString("starttime", tim);
		editor.putString("startdate", date);
		editor.putString("starttimemili", "" + miliSec);
		editor.putInt("endparcent", -1);
		editor.putString("endtime", "");
		editor.putString("enddate", "");
		editor.putString("endtimemili", "");
		editor.commit();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
	/*	Toast.makeText(
				context,
				prefs.getString("starttime", "")
						+ prefs.getInt("startparcent", -1)
						+ prefs.getString("endtime", "")
						+ prefs.getInt("endparcent", -1), Toast.LENGTH_SHORT)
				.show();*/

	}

	public void onBatteryStateChangedDisConncted(Context context) {
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent baterystatus = context.getApplicationContext().registerReceiver(
				null, ifilter);
		int level = baterystatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		boolean present = baterystatus.getExtras().getBoolean(
				BatteryManager.EXTRA_PRESENT);
		int scale = baterystatus.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
		int status = baterystatus.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
		int percentage = 0;
		if (level >= 0 && scale > 0) {
			percentage = (level * 100) / scale;
		}
		Time time = new Time();
		time.setToNow();
		String date = "" + time.monthDay + "." + (time.month + 1) + "."
				+ time.year;
		String tim = "" + time.hour + ":" + time.minute;

		long miliSec = System.currentTimeMillis();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		int ans = prefs.getInt("isConnected", -1);

		if (ans ==1) {

			SharedPreferences.Editor editor = PreferenceManager
					.getDefaultSharedPreferences(context).edit();
			editor.putInt("endparcent", percentage);
			editor.putString("endtime", tim);
			editor.putString("enddate", date);
			editor.putString("endtimemili", "" + miliSec);
			editor.commit();

			/*Toast.makeText(
					context,
					prefs.getString("starttime", "")
							+ prefs.getInt("startparcent", -1)
							+ prefs.getString("endtime", "")
							+ prefs.getInt("endparcent", -1),
					Toast.LENGTH_SHORT).show();*/

			DatabaseOPenHelper db = new DatabaseOPenHelper(context);
			db.setdata(prefs.getString("startdate", ""),
					prefs.getString("starttime", ""),
					prefs.getInt("startparcent", -1),
					prefs.getString("starttimemili", ""),
					prefs.getString("enddate", ""),
					prefs.getString("endtime", ""),
					prefs.getInt("endparcent", -1),
					prefs.getString("endtimemili", ""));
		}

	}
}
