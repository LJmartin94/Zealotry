package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.provider.AlarmClock;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Morning_menu extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_morning_menu);
	}

	public void calculateSunrise()
	{
		double	hourSunrise;
		double	minSunrise;

		double	longitude = 1;
		double	ha = 1;
		double	eqtime = 1;

		double	utc_sunrise;
		utc_sunrise = 720 - 4 * (longitude + ha) -eqtime;


	}

	public void launchAlarm(View v)
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
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
		}
	}

	public void launchLanguage(View v)
	{
		Intent i = getPackageManager().getLaunchIntentForPackage("com.duolingo");
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
		}
		else
		{
			Toast error = Toast.makeText(this, "Please download Duolingo first", Toast.LENGTH_SHORT);
			error.show();
		}
	}

	public void launchMeditation(View v)
	{
		Intent i = getPackageManager().getLaunchIntentForPackage("com.getsomeheadspace.android");
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
		}
		else
		{
			Toast error = Toast.makeText(this, "Please download Headspace first", Toast.LENGTH_SHORT);
			error.show();
		}
	}
}