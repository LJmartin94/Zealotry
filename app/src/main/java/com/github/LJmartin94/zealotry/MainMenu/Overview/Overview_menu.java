package com.github.LJmartin94.zealotry.MainMenu.Overview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.LJmartin94.zealotry.MainMenu.Overview.BackupData.BackupManagement;
import com.github.LJmartin94.zealotry.MainMenu.Overview.Stats.StatsOverviewPage;
import com.github.LJmartin94.zealotry.MainMenu.Overview.WeatherForecast.WeatherForecast;
import com.github.LJmartin94.zealotry.R;

public class Overview_menu extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview_menu);
	}

	public void launchStatsPage(View v)
	{
		//launch new activity
		Intent i = new Intent(this, StatsOverviewPage.class);
		startActivity(i);
	}


	public void launchWeatherForecast(View v)
	{
		//launch new activity
		Intent i = new Intent(this, WeatherForecast.class);
		startActivity(i);
	}

	public void launchDataBackup(View v)
	{
		//launch new activity
		Intent i = new Intent(this, BackupManagement.class);
		startActivity(i);
	}
}