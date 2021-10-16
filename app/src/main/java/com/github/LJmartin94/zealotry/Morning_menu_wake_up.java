package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import java.lang.Math;
import android.provider.AlarmClock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Morning_menu_wake_up extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_morning_menu_wake_up);
	}

	public double old_calculateSunrise()
	{
		//Input
		double	lat = 52.370216;
		double	longitude = 4.895168;
		double	day_of_year = 290;
		double	hour = 0;
		double	leap_year = 0;

		//Calculation based on NOAA Solar Calculations
		double	y; //fractional year
		y = ((Math.PI * 2) / (365 + leap_year)) * (day_of_year - 1 + ((hour - 12) / 24));
		double	eqtime = 229.18 * (0.000075 + (0.001868 * Math.cos(y)) - (0.032077 * Math.sin(y)) - (0.014615 * Math.cos(2 * y)) - (0.040849 * Math.sin(2 * y)) );
		double	decl = 0.006918 - (0.399912 * Math.cos(y)) + (0.070257 * Math.sin(y)) - (0.006758 * Math.cos(2 * y)) + (0.000907 * Math.sin(2 * y)) - (0.002697 * Math.cos(3 * y)) + (0.00148 * Math.sin(3 * y));
		double	ha_calc = ( (Math.cos(Math.toRadians(90.833))/ (Math.cos(Math.toRadians(lat)) * Math.cos(decl))) - (Math.tan(Math.toRadians(lat)) * Math.tan(decl)) );
		double	ha = Math.acos(ha_calc);
		ha = Math.toDegrees(ha);

		//Output
		double	utc_sunrise_in_minutes = 720 - 4 * (longitude + ha) -eqtime;
		double	ret = utc_sunrise_in_minutes;
		double	utcHourSunrise = Math.floor(utc_sunrise_in_minutes/60);
		double	utcMinSunrise = (utc_sunrise_in_minutes - (utcHourSunrise * 60));

		//this gets results but can be around 3 minutes off I've noticed on the dates I cross referenced.
		return ret;
	}

	public void setAlarm(View v)
	{
		// Alarm should be set for: (time_you_need_to_leave - time_you_need_to_get_ready) at latest, and at (sunrise || 8:00 || half an hour before latest alarm -> whichever is earliest) at the earliest.
		// Eventually the app should be able to wake the user when they are in their lightest sleep somewhere between these two extremes.
		int	hourSunrise;
		int minSunrise;

		hourSunrise = 8;
		minSunrise = 0;
		//Function should delete previous alarm called 'Sunrise' if available
		Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
			i.putExtra(AlarmClock.EXTRA_MESSAGE, "Sunrise");
			i.putExtra(AlarmClock.EXTRA_HOUR, hourSunrise);
			i.putExtra(AlarmClock.EXTRA_MINUTES, minSunrise);
			//i.putExtra(AlarmClock.EXTRA_RINGTONE, )
			//https://developer.android.com/reference/android/provider/AlarmClock#EXTRA_RINGTONE

		double sunriseTime = old_calculateSunrise();
		String timeText = String.format("%,.4f", sunriseTime);

		if (i.resolveActivity(getPackageManager()) != null)
		{
			//startActivity(i);
			Toast showTime = Toast.makeText(this, timeText, Toast.LENGTH_SHORT);
			showTime.show();
		}
	}
}