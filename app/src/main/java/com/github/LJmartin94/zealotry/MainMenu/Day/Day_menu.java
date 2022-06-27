package com.github.LJmartin94.zealotry.MainMenu.Day;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.github.LJmartin94.zealotry.R;

public class Day_menu extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_menu);
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