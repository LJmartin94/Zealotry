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
	protected void onCreate(Bundle savedInstanceState)
	{
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

	public double new_calculateSunrise()
	{
		//TODO get input
		//Input
		double	lat = 52.370216; //B3
		double	longitude = 4.895168; //B4
		//double	day_of_year = 290;
		double	day_of_year = 44486;
		double	hour = 0.1; // Leave hardcoded to 6 minutes past midnight
		double	leap_year = 0;
		double	time_zone = 0; //B5 //leave at zero

		//Calculation based on extended NOAA Solar Calculations
		// General variables
		double date_as_number; //D3
		date_as_number = day_of_year; //TODO Needs changing based on input
		double time_past_midnight; //E3
		time_past_midnight = hour / 24;
		double julian_day; //F3
		julian_day = date_as_number + 2415018.5 + time_past_midnight - time_zone / 24;
		double jul_century; //G3
		jul_century = (julian_day - 2451545) / 36525;

		double geom_mean_long_sun; //I3
		geom_mean_long_sun = 280.46646 + jul_century * (36000.76983 + jul_century * 0.0003032);
		geom_mean_long_sun = geom_mean_long_sun % 360;
		double geom_mean_anom_sun; //J3
		geom_mean_anom_sun = 357.52911 + jul_century * (35999.05029 - 0.0001537 * jul_century);

		double mean_obliq_ecliptic; //Q3
		mean_obliq_ecliptic = 46.815 + jul_century * (0.00059 - jul_century * 0.001813);
		mean_obliq_ecliptic = 21.448 - jul_century * mean_obliq_ecliptic;
		mean_obliq_ecliptic = 23 + (26 + mean_obliq_ecliptic / 60 ) / 60;
		double obliq_corr; //R3
		obliq_corr = Math.toRadians(125.04 - 1934.136 * jul_century);
		obliq_corr = mean_obliq_ecliptic + 0.00256 * Math.cos(obliq_corr);

		// Calculating ha_sunrise
		double sun_eq_of_ctr; //L3
		sun_eq_of_ctr = Math.sin(Math.toRadians(geom_mean_anom_sun));
		sun_eq_of_ctr = sun_eq_of_ctr * (1.914602 - jul_century * (0.004817 + 0.000014 * jul_century));
		sun_eq_of_ctr = sun_eq_of_ctr + Math.sin(Math.toRadians(2 * geom_mean_anom_sun)) * (0.019993 - 0.000101 * jul_century);
		sun_eq_of_ctr = sun_eq_of_ctr + Math.sin(Math.toRadians(3 * geom_mean_anom_sun)) * 0.000289;
		double sun_true_long; //M3
		sun_true_long = geom_mean_long_sun + sun_eq_of_ctr;
		double sun_app_long; //P3
		sun_app_long = sun_true_long - 0.00569 - 0.00478 * Math.sin(Math.toRadians(125.04 - 1934.136 * jul_century));
		double sun_declin; //T3
		sun_declin = Math.sin(Math.toRadians(obliq_corr)) * Math.sin(Math.toRadians(sun_app_long));
		sun_declin = Math.asin(sun_declin);
		sun_declin = Math.toDegrees(sun_declin);
		double ha_sunrise; //W3
		ha_sunrise = Math.cos(Math.toRadians(90.833));
		ha_sunrise = ha_sunrise / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(sun_declin)));
		ha_sunrise = ha_sunrise - Math.tan(Math.toRadians(lat)) * Math.tan(Math.toRadians(sun_declin));
		ha_sunrise = Math.acos(ha_sunrise);
		ha_sunrise = Math.toDegrees(ha_sunrise);

		// Calculating solarNoon
		double eccent_earth_orbit; //K3
		eccent_earth_orbit = 0.016708634 - jul_century * (0.000042037 + 0.0000001267 * jul_century);
		double var_y; //U3
		var_y = Math.tan(Math.toRadians(obliq_corr / 2)) * Math.tan(Math.toRadians(obliq_corr / 2));
		double eqTime; //V3
		eqTime = var_y * Math.sin(2 * Math.toRadians(geom_mean_long_sun));
		eqTime = eqTime - 2 * eccent_earth_orbit * Math.sin(Math.toRadians(geom_mean_anom_sun));
		eqTime = eqTime + 4 * eccent_earth_orbit * var_y * Math.sin(Math.toRadians(geom_mean_anom_sun)) * Math.cos(2 * Math.toRadians(geom_mean_long_sun));
		eqTime = eqTime - 0.5 * var_y * var_y * Math.sin( 4 * Math.toRadians(geom_mean_long_sun));
		eqTime = eqTime - 1.25 * eccent_earth_orbit * eccent_earth_orbit * Math.sin(2 * Math.toRadians(geom_mean_anom_sun));
		eqTime = 4 * Math.toDegrees(eqTime);
		double solarNoon = (720 - 4 * longitude - eqTime + time_zone *60)/1440; //X3

		//Final calculation:
		double utc_day_fraction = solarNoon - ha_sunrise * 4 / 1440;
		double utc_Sunrise_Time = utc_day_fraction * 24;
		double utc_Sunrise_Hours = Math.floor(utc_Sunrise_Time);
		double utc_Sunrise_Minutes_Practical = Math.round((utc_Sunrise_Time - utc_Sunrise_Hours) * 60);
		double utc_Sunrise_Minutes = Math.floor((utc_Sunrise_Time - utc_Sunrise_Hours) * 60);
		double utc_Sunrise_Seconds = Math.floor(((utc_Sunrise_Time - utc_Sunrise_Hours) * 60 - utc_Sunrise_Minutes) * 60);

		//Output
		double ret = utc_Sunrise_Time;



		return ret;

		//	// DEBUGGING

		//	System.out.println("Time in hours (decimal) = " + date_as_number);
		//	System.out.println("Hours = " + utc_Sunrise_Hours);
		//	System.out.println("Minutes = " + utc_Sunrise_Minutes_Practical);
		//	System.out.println("Seconds = " + utc_Sunrise_Seconds);

		//	System.out.println("date as number = " + date_as_number);
		//	System.out.println("time_past_midnight = " + time_past_midnight);
		//	System.out.println("julian_day = " + julian_day);
		//	System.out.println("julian_century = " + jul_century);
		//	System.out.println("geom_mean_long_sun = " + geom_mean_long_sun);
		//	System.out.println("geom_mean_anom_sun = " + geom_mean_anom_sun);
		//	System.out.println("mean_obliq_ecliptic = " + mean_obliq_ecliptic);
		//	System.out.println("obliq_corr = " + obliq_corr);
		//	System.out.println("sun_eq_of_ctr = " + sun_eq_of_ctr);
		//	System.out.println("sun_true_long = " + sun_true_long);
		//	System.out.println("sun_app_long = " + sun_app_long);
		//	System.out.println("sun_declin = " + sun_declin);
		//	System.out.println("ha_sunrise = " + ha_sunrise);
		//	System.out.println("eccent_earth_orbit = " + eccent_earth_orbit);
		//	System.out.println("var_y = " + var_y);
		//	System.out.println("eqTime = " + eqTime);
		//	System.out.println("solarNoon = " + solarNoon);
		//	System.out.println("utc_Sunrise_Time = " + utc_Sunrise_Time);
		//	System.out.println("ret = " + ret);
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