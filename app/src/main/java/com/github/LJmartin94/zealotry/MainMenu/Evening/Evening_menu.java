package com.github.LJmartin94.zealotry.MainMenu.Evening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.github.LJmartin94.zealotry.MainMenu.Morning.Wake_up;
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
		Intent i = new Intent(this, Wake_up.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		disable_button(v);
	}

	public void launchExercise(View v)
	{
		Intent i = new Intent(this, ExerciseTest_Activity.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		disable_button(v);
	}

	public void disable_button(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		v.getBackground().setColorFilter(filter);
		v.setClickable(false);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}