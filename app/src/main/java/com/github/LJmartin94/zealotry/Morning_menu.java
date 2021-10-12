package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

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

	public void launchWakeUp(View v)
	{
		Intent i = new Intent(this, Morning_menu_wake_up.class);
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