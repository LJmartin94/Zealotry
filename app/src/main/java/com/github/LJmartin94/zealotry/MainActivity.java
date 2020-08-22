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
			day = (7 + day - 1) % 7;
		return (day);
	}

	public int	setDay(int	day)
	{
		String today;

		today = getString(R.string.NaN);
		switch (day) {
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
		((TextView)findViewById(R.id.Day)).setText(today);
		setTitle(today);
		return (1);
	}

	public void launchNewActivity(View v)
	{
		//launch new activity
		Intent i = new Intent(this, NewActivity.class);
		String input = ((EditText)findViewById(R.id.inputTextName)).getText().toString();
		i.putExtra("Extra_info", input);
		startActivity(i);
	}

    public void unusedExample()
    {
//        ((Button)findViewById(R.id.someViewName)).setText("This is how to cast a view to a" +
//        "button and immediately set its text");
        Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show();
    }
}