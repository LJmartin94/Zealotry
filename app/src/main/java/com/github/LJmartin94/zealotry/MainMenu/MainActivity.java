package com.github.LJmartin94.zealotry.MainMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.LJmartin94.zealotry.MainMenu.Day.Day_menu;
import com.github.LJmartin94.zealotry.MainMenu.Evening.Evening_menu;
import com.github.LJmartin94.zealotry.MainMenu.Morning.Morning_menu;
import com.github.LJmartin94.zealotry.MainMenu.Overview.Overview_menu;
import com.github.LJmartin94.zealotry.MainMenu.Overview.WeatherForecast.WeatherForecast;
import com.github.LJmartin94.zealotry.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
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
		today = today + addDate(today.length());
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


	// Saturn's day - 91st day of Spri...
	public String	shortenDateAddition(int dayStringLen, String dateAddition, String season)
	{
		int title_max_chars = 31;

		// " - 87th day of Spring"
		if ((dayStringLen + dateAddition.length()) > title_max_chars)
			dateAddition = dateAddition.replace(" day ", " ");

		// " - 87th of Spring"
		if ((dayStringLen + dateAddition.length()) > title_max_chars)
		{
			dateAddition = dateAddition.replace((" of " + season), "");
			dateAddition = dateAddition.replace( " -", (" - " + season));
		}

		// " - Spring 87th"
		if ((dayStringLen + dateAddition.length()) > title_max_chars)
		{
			dateAddition = dateAddition.replace(("th" + season), "");
			dateAddition = dateAddition.replace(("rd" + season), "");
			dateAddition = dateAddition.replace(("nd" + season), "");
			dateAddition = dateAddition.replace(("st" + season), "");
		}

		// " - Spring 87"
		if ((dayStringLen + dateAddition.length()) > title_max_chars)
			dateAddition = " - " + season;

		// " - Spring"
		if ((dayStringLen + dateAddition.length()) > title_max_chars)
			dateAddition = "";

		// ""
		return (dateAddition);
	}

	public String	addDate(int dayStringLen)
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

		String date_addition = "";
		String season = "this season";

		int current_day = calendar.get(Calendar.DAY_OF_YEAR);

		//This makes days only roll over at 4am, as per the rest of the app, and accounts for leap
		//years on the first of January every four years.
			if (calendar.get(Calendar.HOUR_OF_DAY) < 4)
				current_day = current_day - 1;
			leap_year = (((calendar.get(Calendar.YEAR) - 1) % 4) == 0) ? 1 : 0;
			if (current_day <= 0)
				current_day = 365 + leap_year;

		int day_of_season = 0;
		if (current_day >= spring && current_day < summer)
		{
			date_addition = " - ";
			day_of_season = current_day - spring + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " day of Spring";
			season = "Spring";
		}
		else if (current_day >= summer && current_day < autumn)
		{
			date_addition = " - ";
			day_of_season = current_day - summer + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " day of Summer";
			season = "Summer";
		}
		else if (current_day >= autumn && current_day < winter)
		{
			date_addition = " - ";
			day_of_season = current_day - autumn + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " day of Autumn";
			season = "Autumn";
		}
		else if (current_day >= winter)
		{
			date_addition = " - ";
			day_of_season = current_day - winter + 1;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " day of Winter";
			season = "Winter";
		}
		else if (current_day < spring)
		{
			date_addition = " - ";
			day_of_season = current_day + 11;
			date_addition = date_addition + day_of_season;
			date_addition = date_addition + suffixDate(day_of_season);
			date_addition = date_addition + " day of Winter";
			season = "Winter";
		}
		date_addition = shortenDateAddition(dayStringLen, date_addition, season);
		return (date_addition);
	}

	public void launchOverviewMenu(View v)
	{
		//launch new activity
		Intent i = new Intent(this, Overview_menu.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
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