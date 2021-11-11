package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	 	int day;

	 	super.onCreate(savedInstanceState);
	 	setContentView(R.layout.activity_main);
	 	setTitle(getString(R.string.act_main));
	 	day = getDay();
	 	setDay(day);
	 	Log.d("lbo_Main", "Main completed successfully");
	}

	public int	getDay()
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		//We want one day to turn into the next at 4am, not 12am.
		//It is my devout belief no one should go to bed after 4am nor get up before 4am.
		if (calendar.get(Calendar.HOUR_OF_DAY) < 4)
			day = ((day + 5) % 7) + 1;
			//day = (day == 1) ? 7 : day - 1;
		Log.d("lbo_Day", String.valueOf(day));
		return (day);
	}

	public int	setDay(int	day)
	{
		String today;

		today = getString(R.string.NaN);
		switch (day)
		{
			case Calendar.SUNDAY:
				today = getString(R.string.Sunday);
				break;
			case Calendar.MONDAY:
				today = getString(R.string.Monday);
				break;
			case Calendar.TUESDAY:
				today = getString(R.string.Tuesday);
				break;
			case Calendar.WEDNESDAY:
				today = getString(R.string.Wednesday);
				break;
			case Calendar.THURSDAY:
				today = getString(R.string.Thursday);
				break;
			case Calendar.FRIDAY:
				today = getString(R.string.Friday);
				break;
			case Calendar.SATURDAY:
				today = getString(R.string.Saturday);
				break;
		}
		//((TextView)findViewById(R.id.Day)).setText(today);
		today = today + addDate();
		setTitle(today);
		return (1);
	}

	public String	suffixDate(int date)
	{
		//1st, 21st, 31st, 41st, etc (if ends in 1 but not 11th)
		//2nd, 22nd, etc (if ends in 2 but not 12th)
		//3rd, 23rd, etc (if ends in 3 but not 13th)
		//4th - 20th (DEFAULT)
		date = date % 100;
		if (date >= 4 && date <= 20)
			return ("th");
		else if ((date % 10) == 1)
			return ("st");
		else if ((date % 10) == 2)
			return ("nd");
		else if ((date % 10) == 3)
			return ("rd");
		else
			return ("th");
	}

	public String	addDate()
	{
		Calendar calendar = Calendar.getInstance();
		int leap_year = ((calendar.get(Calendar.YEAR) % 4) == 0) ? 1 : 0;
		//1st of Spring: 20th of March
		int spring = 79 + leap_year;
		//1st of Summer: 21st of June
		int summer = 172 + leap_year;
		//1st of Autumn: 22nd of September
		int autumn = 265 + leap_year;
		//1st of Winter: 21st of December
		int winter = 355 + leap_year;

		String date_addition;

		int current_day = calendar.get(Calendar.DAY_OF_YEAR);
		date_addition = "";
		int day_of_season = 0;
		if (current_day >= spring && current_day < summer)
		{
			date_addition = " - ";
			day_of_season = current_day - spring + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " of Spring";
		}
		else if (current_day >= summer && current_day < autumn)
		{
			date_addition = " - ";
			day_of_season = current_day - summer + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " of Summer";
		}
		else if (current_day >= autumn && current_day < winter)
		{
			date_addition = " - ";
			day_of_season = current_day - autumn + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " of Autumn";
		}
		else if (current_day >= winter)
		{
			date_addition = " - ";
			day_of_season = current_day - winter + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " of Winter";
		}
		else if (current_day < spring)
		{
			date_addition = " - ";
			day_of_season = current_day + 10;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " of Winter";
		}
		return (date_addition);
	}

	public void launchWeatherForecast(View v)
	{
		//launch new activity
		Intent i = new Intent(this, WeatherForecast.class);
		startActivity(i);
	}

	public void launchMorningMenu(View v)
	{
		//launch new activity
		Intent i = new Intent(this, Morning_menu.class);
		//String input = ((EditText)findViewById(R.id.inputTextName)).getText().toString();
		//i.putExtra("Extra_info", input);
		startActivity(i);
	}

	public void launchDayMenu(View v)
	{
		//launch new activity
		Intent i = new Intent(this, Day_menu.class);
		//String input = ((EditText)findViewById(R.id.inputTextName)).getText().toString();
		//i.putExtra("Extra_info", input);
		startActivity(i);
	}

	public void launchEveningMenu(View v)
	{
		//launch new activity
		Intent i = new Intent(this, Evening_menu.class);
		//String input = ((EditText)findViewById(R.id.inputTextName)).getText().toString();
		//i.putExtra("Extra_info", input);
		startActivity(i);
	}
}