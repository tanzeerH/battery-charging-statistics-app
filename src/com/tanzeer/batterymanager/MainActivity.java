package com.tanzeer.batterymanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;


import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity implements
		OnClickListener {

	Time thattime;
	long that;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseOPenHelper db=new DatabaseOPenHelper(this);
		ArrayList<BatteryData> list=db.getAlldata();
		Collections.reverse(list);
		
		listView=(ListView)findViewById(R.id.listView);
		ListViewAdapter adapter=new ListViewAdapter(getApplicationContext(), R.layout.row,list);
		
		listView.setAdapter(adapter);
		if(list.size()==0)
			Toast.makeText(getApplicationContext(), "No data saved yet", Toast.LENGTH_LONG).show();


		

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		getSupportMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getItemId()==R.id.about)
			{
				final Dialog dialog=new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.about);
				dialog.setTitle("About");
				TextView details=(TextView)dialog.findViewById(R.id.details);
				TableRow imageback=(TableRow)dialog.findViewById(R.id.backimage);
				imageback.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if(v.getId()==R.id.backimage)
							dialog.dismiss();
						
					}
				});
				String str="Battery Charging Statistcis app saves battery charging data of your last 50 charging." +
						"Using this app user can know much time the phone is taking to charge.";
				details.setText(str);
				
			
				dialog.show();
				Display display=((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
				int width=display.getWidth();
				int height=display.getHeight();
				dialog.getWindow().setLayout(width,height);
				
					
				
			}
		return true;
	}

	@Override
	public void onClick(View v) {

		

	}

}
