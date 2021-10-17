package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;

import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.AlarmClock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Morning_menu_wake_up extends AppCompatActivity
{
	private final int LAT_LONG_REQUEST_CODE = 0;
	int	hourSunrise = 10;
	int minSunrise = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_morning_menu_wake_up);

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
												Manifest.permission.ACCESS_COARSE_LOCATION};

			ActivityCompat.requestPermissions(this, permissions, LAT_LONG_REQUEST_CODE);
			return;
		}
		double[] sunriseTime = calculateSunrise();
		hourSunrise = (int)sunriseTime[0];
		minSunrise = (int)sunriseTime[1];
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		switch (requestCode)
		{
			case LAT_LONG_REQUEST_CODE:
				for (int i = 0; i < permissions.length; i++)
				{
					String permission = permissions[i];
					int grantResult = grantResults[i];

					if((permission == Manifest.permission.ACCESS_FINE_LOCATION ||
						permission == Manifest.permission.ACCESS_COARSE_LOCATION) &&
							grantResult == PackageManager.PERMISSION_GRANTED)
					{
						String permission_ok = "OK";
						Toast showTime = Toast.makeText(this, permission_ok, Toast.LENGTH_LONG);
						showTime.show();
					}
					else
					{
						String permission_error = "Zealotry wants to know your location to calculate what time the sun will rise";
						Toast showTime = Toast.makeText(this, permission_error, Toast.LENGTH_LONG);
						showTime.show();
					}
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	public double[] get_location()
	{
		double	lat = 55.954757; //Default latitude in case permission denied or null
		double	longitude = -3.184216; //Default longitude in case permission denied or null
		double[] lat_long = {lat, longitude};

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
					Manifest.permission.ACCESS_COARSE_LOCATION};

			ActivityCompat.requestPermissions(this, permissions, LAT_LONG_REQUEST_CODE);
			return(lat_long);
		}
		else
		{
			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			lat = location.getLatitude();
			longitude = location.getLongitude();
		}
		lat_long[0] = lat;
		lat_long[1] = longitude;
		return(lat_long);
	}

	public double[] calculateSunrise()
	{
		//Input - place
		double[] lat_long;
		lat_long = get_location();
		double lat = lat_long[0];
		double longitude = lat_long[1];
		//Input - time
		Date	current_date = Calendar.getInstance().getTime();
		double	hour = 0.1; // Leave hardcoded to 6 minutes past midnight
		double	time_zone = 0; //leave at zero

		//Calculation based on extended NOAA Solar Calculations
		// General variables
		String	start_string = "30/12/1899";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date	start_date = null;
		try {
			start_date = sdf.parse(start_string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diff = current_date.getTime() - start_date.getTime();
		diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 10)
			diff = diff + 1; //Unless its past midnight but before 10am, you should be looking at tomorrow's sunrise, not today's
		double date_as_number;
		date_as_number = (double)diff;
		double time_past_midnight;
		time_past_midnight = hour / 24;
		double julian_day;
		julian_day = date_as_number + 2415018.5 + time_past_midnight - time_zone / 24;
		double jul_century;
		jul_century = (julian_day - 2451545) / 36525;

		double geom_mean_long_sun;
		geom_mean_long_sun = 280.46646 + jul_century * (36000.76983 + jul_century * 0.0003032);
		geom_mean_long_sun = geom_mean_long_sun % 360;
		double geom_mean_anom_sun;
		geom_mean_anom_sun = 357.52911 + jul_century * (35999.05029 - 0.0001537 * jul_century);

		double mean_obliq_ecliptic;
		mean_obliq_ecliptic = 46.815 + jul_century * (0.00059 - jul_century * 0.001813);
		mean_obliq_ecliptic = 21.448 - jul_century * mean_obliq_ecliptic;
		mean_obliq_ecliptic = 23 + (26 + mean_obliq_ecliptic / 60 ) / 60;
		double obliq_corr;
		obliq_corr = Math.toRadians(125.04 - 1934.136 * jul_century);
		obliq_corr = mean_obliq_ecliptic + 0.00256 * Math.cos(obliq_corr);

		// Calculating ha_sunrise
		double sun_eq_of_ctr;
		sun_eq_of_ctr = Math.sin(Math.toRadians(geom_mean_anom_sun));
		sun_eq_of_ctr = sun_eq_of_ctr * (1.914602 - jul_century * (0.004817 + 0.000014 * jul_century));
		sun_eq_of_ctr = sun_eq_of_ctr + Math.sin(Math.toRadians(2 * geom_mean_anom_sun)) * (0.019993 - 0.000101 * jul_century);
		sun_eq_of_ctr = sun_eq_of_ctr + Math.sin(Math.toRadians(3 * geom_mean_anom_sun)) * 0.000289;
		double sun_true_long;
		sun_true_long = geom_mean_long_sun + sun_eq_of_ctr;
		double sun_app_long;
		sun_app_long = sun_true_long - 0.00569 - 0.00478 * Math.sin(Math.toRadians(125.04 - 1934.136 * jul_century));
		double sun_declin;
		sun_declin = Math.sin(Math.toRadians(obliq_corr)) * Math.sin(Math.toRadians(sun_app_long));
		sun_declin = Math.asin(sun_declin);
		sun_declin = Math.toDegrees(sun_declin);
		double ha_sunrise;
		ha_sunrise = Math.cos(Math.toRadians(90.833));
		ha_sunrise = ha_sunrise / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(sun_declin)));
		ha_sunrise = ha_sunrise - Math.tan(Math.toRadians(lat)) * Math.tan(Math.toRadians(sun_declin));
		ha_sunrise = Math.acos(ha_sunrise);
		ha_sunrise = Math.toDegrees(ha_sunrise);

		// Calculating solarNoon
		double eccent_earth_orbit;
		eccent_earth_orbit = 0.016708634 - jul_century * (0.000042037 + 0.0000001267 * jul_century);
		double var_y;
		var_y = Math.tan(Math.toRadians(obliq_corr / 2)) * Math.tan(Math.toRadians(obliq_corr / 2));
		double eqTime;
		eqTime = var_y * Math.sin(2 * Math.toRadians(geom_mean_long_sun));
		eqTime = eqTime - 2 * eccent_earth_orbit * Math.sin(Math.toRadians(geom_mean_anom_sun));
		eqTime = eqTime + 4 * eccent_earth_orbit * var_y * Math.sin(Math.toRadians(geom_mean_anom_sun)) * Math.cos(2 * Math.toRadians(geom_mean_long_sun));
		eqTime = eqTime - 0.5 * var_y * var_y * Math.sin( 4 * Math.toRadians(geom_mean_long_sun));
		eqTime = eqTime - 1.25 * eccent_earth_orbit * eccent_earth_orbit * Math.sin(2 * Math.toRadians(geom_mean_anom_sun));
		eqTime = 4 * Math.toDegrees(eqTime);
		double solarNoon = (720 - 4 * longitude - eqTime + time_zone *60)/1440;

		//Final calculation:
		double utc_day_fraction = solarNoon - ha_sunrise * 4 / 1440;
		double utc_Sunrise_Time = utc_day_fraction * 24;
		double utc_Sunrise_Hours = Math.floor(utc_Sunrise_Time);
		double utc_Sunrise_Minutes_Practical = Math.round((utc_Sunrise_Time - utc_Sunrise_Hours) * 60);
		double utc_Sunrise_Minutes = Math.floor((utc_Sunrise_Time - utc_Sunrise_Hours) * 60);
		double utc_Sunrise_Seconds = Math.floor(((utc_Sunrise_Time - utc_Sunrise_Hours) * 60 - utc_Sunrise_Minutes) * 60);

		long now = System.currentTimeMillis();
		double time_zone_offset = (TimeZone.getDefault().getOffset(now)) / 3600000.0; //TODO Might not work the day before the clocks change, as it looks at current offset - add a day to 'now' if not past midnight to fix.
		double local_sunrise_time = utc_Sunrise_Time + time_zone_offset;

		//Output
		double local_sunrise_time_hours = Math.floor(local_sunrise_time);
		double local_sunrise_time_minutes = Math.round((local_sunrise_time - local_sunrise_time_hours) * 60);
		double[] Hours_Minutes_Local_Sunrise = {local_sunrise_time_hours, local_sunrise_time_minutes};

		//Set TextView
		String hourpad = "";
		String minpad = "";
		//if ((int)local_sunrise_time_hours < 10)
		//	hourpad = "0";
		if ((int)local_sunrise_time_minutes < 10)
			minpad = "0";
		String sunriseDisplay = hourpad + String.valueOf((int)local_sunrise_time_hours) + ":" + minpad + String.valueOf((int)local_sunrise_time_minutes);
		((TextView)findViewById(R.id.timeSunriseTime)).setText(sunriseDisplay);

		//Return
		return Hours_Minutes_Local_Sunrise;
	}

	public double[] calculateLeaveTime()
	{
		double []Hours_Minutes_need_to_leave = {(double)hourSunrise, (double)minSunrise};
		return Hours_Minutes_need_to_leave;
	}

	public void setAlarm(View v)
	{
		// Alarm should be set for: (time_you_need_to_leave - time_you_need_to_get_ready) at latest, and at (sunrise || 8:00 || half an hour before latest alarm -> whichever is earliest) at the earliest.
		// Eventually the app should be able to wake the user when they are in their lightest sleep somewhere between these two extremes.

		//Function should delete previous alarm called 'Sunrise' if available
		Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
			i.putExtra(AlarmClock.EXTRA_MESSAGE, "Sunrise");
			i.putExtra(AlarmClock.EXTRA_HOUR, hourSunrise);
			i.putExtra(AlarmClock.EXTRA_MINUTES, minSunrise);
			//i.putExtra(AlarmClock.EXTRA_RINGTONE, )
			//https://developer.android.com/reference/android/provider/AlarmClock#EXTRA_RINGTONE


		//String timeText = String.format("%,.4f", sunriseTime);

		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
//			Toast showTime = Toast.makeText(this, timeText, Toast.LENGTH_SHORT);
//			showTime.show();
		}
	}
}