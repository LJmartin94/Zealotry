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
	 	fillToyBox();
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

	public void launchSunshine(View v)
	{
		//launch new activity
		Intent i = new Intent(this, Sunshine.class);
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

    public void fillToyBox()
	{
		TextView mToysListTextView;

		/*
		 * Using findViewById, we get a reference to our TextView from xml. This allows us to
		 * do things like set the text of the TextView.
		 */
		mToysListTextView = (TextView) findViewById(R.id.tv_toy_names);

		/*
		 * This String array contains names of classic toys. After all, these are toy apps. We
		 * wanted to create a way to break concepts down into smaller pieces that we thought might
		 * be a little easier to understand. In each lesson, we'll demonstrate new concepts using a
		 * toy app (no, sadly every one won't have ACTUAL toys in it) and then we'll guide you
		 * through adding the functionality that you've just learned by having you use those
		 * concepts in Sunshine! Let us know what you think! We're really excited to have you
		 * taking this course.
		 */
		String[] toyNames = ToyBox.getToyNames();

		/*
		 * Iterate through the array and append the Strings to the TextView. The reason why we add
		 * the "\n\n\n" after the String is to give visual separation between each String in the
		 * TextView. Later, we'll learn about a better way to display lists of data.
		 */
		for (String toyName : toyNames)
		{
			mToysListTextView.append(toyName + "\n\n\n");
		}
	}

}