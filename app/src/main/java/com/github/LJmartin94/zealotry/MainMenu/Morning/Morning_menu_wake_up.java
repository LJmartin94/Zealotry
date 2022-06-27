package com.github.LJmartin94.zealotry.MainMenu.Morning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;

import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.R;

public class Morning_menu_wake_up extends AppCompatActivity
{
	private final int LAT_LONG_REQUEST_CODE = 0;
	int	hourSunrise = 10;
	int minSunrise = 0;
	Button leaveTimeButton;
	int select_hour = 10;
	int select_minute = 0;
	int g_hours_to_leave = 10;
	int g_mins_to_leave = 0;
	boolean location_error = false;

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
		leaveTimeButton = (Button)findViewById(R.id.button_spinner_wake_up);
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
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); //First attempt at getting location

			if (location == null)
			{
				Criteria crit = new Criteria();
				crit.setAccuracy(Criteria.ACCURACY_FINE);
				String provider = lm.getBestProvider(crit, true);
				if (provider != null)
				{
					location = lm.getLastKnownLocation(provider); // Second attempt at getting location
				}
			}
			if (location == null)
			{
				location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); // Third attempt at getting location
			}
			if (location != null)
			{
				location_error = false; //Setting location if any of the above worked
				lat = location.getLatitude();
				longitude = location.getLongitude();
			}
			else
			{
				lat = 55.954757; //Defaulting to set location if none of the above methods worked.
				longitude = -3.184216;
				location_error = true;
				Toast latlongError = Toast.makeText(this, "ZEALOTRY failed to fetch latitude and longitude - Sunrise time may be inaccurate", Toast.LENGTH_LONG);
				latlongError.show();
			}
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
		if (location_error)
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
		if (location_error)
		{
			String zoneId = "Europe/London"; // Or whatever timezone is most apt for the City observatory on Calton Hill, Edinburgh.
			time_zone_offset = (TimeZone.getTimeZone(zoneId).getOffset(now)) / 3600000.0;
		}
		double local_sunrise_time = utc_Sunrise_Time + time_zone_offset;

		//Output
		double local_sunrise_time_hours = Math.floor(local_sunrise_time);
		double local_sunrise_time_minutes = Math.round((local_sunrise_time - local_sunrise_time_hours) * 60);
		if (local_sunrise_time_hours >= 10.0) //Hardcode sunrise to be 10am at the latest
		{
			local_sunrise_time_hours = 10.0;
			local_sunrise_time_minutes = 0;
		}
		hourSunrise = (int)local_sunrise_time_hours;
		minSunrise = (int)local_sunrise_time_minutes;
		double[] Hours_Minutes_Local_Sunrise = {local_sunrise_time_hours, local_sunrise_time_minutes};

		updateTextViews();
		//Return
		return Hours_Minutes_Local_Sunrise;
	}

	private void updateTextViews()
	{
		//Set TextView Sunrise Display
		String hourpad = "";
		String minpad = "";
		//if (hourSunrise < 10)
		//	hourpad = "0";
		if (minSunrise < 10)
			minpad = "0";
		String sunriseDisplay = hourpad + String.valueOf(hourSunrise) + ":" + minpad + String.valueOf(minSunrise);
		((TextView)findViewById(R.id.timeSunriseTime)).setText(sunriseDisplay);

		//Set TextView today or tomorrow Sunrise
		String which_sunrise;
		if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 10) //TODO or user has marked themselves as UP
			which_sunrise = getResources().getString(R.string.textSunriseTimeTomorrow);
		else
			which_sunrise = getResources().getString(R.string.textSunriseTime);
		((TextView)findViewById(R.id.textSunriseTime)).setText(which_sunrise);

		//Set TextView Latest Rise:
		hourpad = "";
		minpad = "";
		//if (g_hours_to_leave < 10)
		//	hourpad = "0";
		if (g_mins_to_leave < 10)
			minpad = "0";
		String latestRiseDisplay = hourpad + String.valueOf(g_hours_to_leave) + ":" + minpad + String.valueOf(g_mins_to_leave);
		((TextView)findViewById(R.id.timeLatestAlarm)).setText(latestRiseDisplay);
	}

	public double[] calculateLeaveTime()
	{
		double hours_to_leave = select_hour;
		double mins_to_leave = select_minute;
		double time_to_ready = 2.64; // TODO Change from hardcoded value to adaptive estimate

		if ((hours_to_leave + mins_to_leave / 60) < time_to_ready)
			hours_to_leave = hours_to_leave + 24;

		hours_to_leave = hours_to_leave - Math.floor(time_to_ready);
		mins_to_leave = mins_to_leave - ((time_to_ready % 1.0) * 60);
		hours_to_leave = hours_to_leave + (mins_to_leave / 60);
		mins_to_leave = (hours_to_leave % 1.0) * 60;
		hours_to_leave = Math.floor(hours_to_leave);
		g_mins_to_leave = (int)mins_to_leave;
		g_hours_to_leave = (int)hours_to_leave;

		updateTextViews();
		double []Hours_Minutes_need_to_leave = {hours_to_leave, mins_to_leave};
		return Hours_Minutes_need_to_leave;
	}

	public void popTimePicker(View view)
	{
		int style = R.style.DarkSpinner;
		//TODO if you want a custom time spinner, make a class and xlm file for it. Keywords: AlertDialog, timepicker spinner, dialogfragments.
		// Useful link : https://material.io/components/dialogs/android#theming-dialogs
		TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
		{
			@Override
			public void onTimeSet(TimePicker timePicker, int h, int m)
			{
				select_hour = h;
				select_minute = m;
				leaveTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", select_hour, select_minute));
				calculateLeaveTime();
			}
		};
		TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, select_hour, select_minute, true);
		timePickerDialog.setTitle("Select Time");
		timePickerDialog.show();
	}

	public int[] convertHoursMins(double Time)
	{
		double 	hours;
		double 	mins;

		hours = (Math.floor(Time));
		mins = ((Time % 1) * 60);
		mins = Math.round(mins);
		hours = Math.round(hours);

		int[] ret = {(int)hours, (int)mins};
		return (ret);
	}

	public Uri setAlarmAtTime(double alarmTime, String alarmName)
	{
		int[] time = convertHoursMins(alarmTime);
		int hours = time[0];
		int mins = time[1];


		Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
		i.putExtra(AlarmClock.EXTRA_MESSAGE, alarmName);
		i.putExtra(AlarmClock.EXTRA_HOUR, hours);
		i.putExtra(AlarmClock.EXTRA_MINUTES, mins);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			{i.putExtra(AlarmClock.EXTRA_VIBRATE, false);}
		i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
		//i.putExtra(AlarmClock.EXTRA_RINGTONE, gongAlarm); //TODO set a ringtone for the alarm, MAKE IT LOUD IF LASTALARM
		//https://developer.android.com/reference/android/provider/AlarmClock#EXTRA_RINGTONE
		if (i.resolveActivity(getPackageManager()) != null)
		{
			//TODO Replace deprecated startActivityForResult
			startActivityForResult(i, 0);
//			startActivity(i);
		}
		Uri alarmDeeplink = i.getData();
		return (alarmDeeplink);
	}

	public void deleteOldAlarms(Uri to_delete)
	{
		Intent i = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_LABEL);
		i.putExtra(AlarmClock.EXTRA_MESSAGE, to_delete);
//		i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			//TODO Replace deprecated startActivityForResult
			startActivityForResult(i, 1);
			Toast test = Toast.makeText(this, "Tried to delete alarm", Toast.LENGTH_LONG);
			test.show();
		}

		//TODO Function should delete previous alarm called 'Sunrise / First Rise' if available
		//TODO Function should delete previous alarm called 'Last Rise' if available
		//TODO Function should delete previous alarm called 'Zealotry' if available

		//None of these approaches seem to work (neither using the alarm name as label, nor trying to delete it with a direct Uri).
		//Two options remain: either write your entire own alarm manager,
		//or see if this approach is perhaps viable after all if you use an 'Activity Result API' instead of the startActivityForResult function call.
	}

	public void setAlarm(View v)
	{
		if (location_error)
			calculateSunrise();
		// TODO Eventually the app should be able to wake the user when they are in their lightest sleep somewhere between these two extremes.

		//Uri gongAlarm = Uri.parse("android.resource://com.github.LJmartin94.zealotry/raw/gong.mp3");

		double sunriseAlarm = hourSunrise + (minSunrise / 60.0);
		double lastRiseAlarm = g_hours_to_leave + (g_mins_to_leave / 60.0);

		double bedBeforeSunrise = sunriseAlarm - 6.0; //Ensure at least 6 hours of dark-sleep
		double bedBeforeLastRise = lastRiseAlarm - 8.0;
		double bedAlarm = Math.min(bedBeforeSunrise, bedBeforeLastRise);
		if (bedAlarm <= 0)
			bedAlarm = bedAlarm + 24.0;

		double earliestAlarm;
		double lastAlarm;
		double medianAlarm;

		lastAlarm = lastRiseAlarm;
		earliestAlarm = Math.min(sunriseAlarm, lastAlarm - 0.5);
		medianAlarm = (earliestAlarm + lastAlarm) / 2.0;

		String sunriseAlarmName;
		if (earliestAlarm == sunriseAlarm)
			sunriseAlarmName = "Sunrise";
		else
			sunriseAlarmName = "First Rise";
		Uri i = setAlarmAtTime(earliestAlarm, sunriseAlarmName);
		Uri j = setAlarmAtTime(lastAlarm, "Last Rise");
		Uri k = setAlarmAtTime(medianAlarm, "Zealotry");
		Uri l = setAlarmAtTime(bedAlarm, "Get ready for bed");
//		deleteOldAlarms(i);
//		deleteOldAlarms(j);
//		deleteOldAlarms(k);
	}

	@Override
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
}