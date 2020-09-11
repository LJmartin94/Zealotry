package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

	private TextView mWeatherTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weatherforecast);

		mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
		loadWeatherData();
	}

	private void loadWeatherData()
	{
		String location = ZealotryPreferences.getPreferredWeatherLocation(this);
		new FetchWeatherTask().execute(location);
	}

	public class FetchWeatherTask extends AsyncTask<String, Void, String[]>
	{
		@Override
		protected String[] doInBackground(String... params)
		{
			if (params.length == 0)
			{ return null;}
			String location = params[0];
			URL weatherRequestUrl = NetworkUtils.WFbuildUrl(location);
			try
			{
				String jsonWeatherResponse = NetworkUtils
						.getResponseFromHttpUrl(weatherRequestUrl);
				String[] simpleJsonWeatherData = OpenWeatherJsonUtils
						.getSimpleWeatherStringsFromJson(WeatherForecast.this, jsonWeatherResponse);
				return simpleJsonWeatherData;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String[] weatherData)
		{
			if (weatherData != null)
			{
				/*
				 * Iterate through the array and append the Strings to the TextView. The reason why we add
				 * the "\n\n\n" after the String is to give visual separation between each String in the
				 * TextView. Later, we'll learn about a better way to display lists of data.
				 */
				for (String weatherString : weatherData)
				{ mWeatherTextView.append((weatherString) + "\n\n\n");}
			}
		}
	}
}