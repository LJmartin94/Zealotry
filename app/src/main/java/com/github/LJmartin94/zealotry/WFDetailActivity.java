package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WFDetailActivity extends AppCompatActivity {

	private String mForecast;
	private TextView mWeatherDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
}