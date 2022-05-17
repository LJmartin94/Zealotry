package com.github.LJmartin94.zealotry.MainMenu.WeatherForecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.LJmartin94.zealotry.R;

public class WFDetailActivity extends AppCompatActivity
{

	private String mForecast;
	private TextView mWeatherDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wf_detail);

		mWeatherDisplay = (TextView) findViewById(R.id.tv_display_weather);

		Intent i = getIntent();

		if (i != null)
		{
			if (i.hasExtra(Intent.EXTRA_TEXT))
			{
				mForecast = i.getStringExtra(Intent.EXTRA_TEXT);
				mWeatherDisplay.setText(mForecast);
			}
		}
	}

	private Intent createShareForecastIntent()
	{
		Intent shareIntent = ShareCompat.IntentBuilder.from(this)
				.setType("text/plain")
				.setText(mForecast)
				.getIntent();
		return shareIntent;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.forecast_detail, menu);
		MenuItem menuItem = menu.findItem(R.id.action_share);
		menuItem.setIntent(createShareForecastIntent());
		return true;
	}
}