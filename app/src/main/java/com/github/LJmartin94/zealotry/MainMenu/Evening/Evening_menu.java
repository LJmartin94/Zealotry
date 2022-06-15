package com.github.LJmartin94.zealotry.MainMenu.Evening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;

import com.github.LJmartin94.zealotry.MainMenu.Morning.Morning_menu_wake_up;
import com.github.LJmartin94.zealotry.R;

public class Evening_menu extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evening_menu);
	}

	public void launchSetAlarms(View v)
	{
		Intent i = new Intent(this, Morning_menu_wake_up.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
		}
		disable_button(v);
	}

	public void launchExercise(View v)
	{
		Intent i = new Intent(this, ExerciseTest_Activity.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
		}
		disable_button(v);
	}

	public void disable_button(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		v.getBackground().setColorFilter(filter);
		v.setClickable(false);
	}
}