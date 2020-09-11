package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

	private TextView mWeatherTextView;
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weatherforecast);

		mWeatherTextView = (TextView)findViewById(R.id.tv_weather_data);
		mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
		mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
		loadWeatherData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		/* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
		MenuInflater inflater = getMenuInflater();
		/* Use the inflater's inflate method to inflate our menu layout to this menu */
		inflater.inflate(R.menu.forecast, menu);
		/* Return true so that the menu is displayed in the Toolbar */
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_refresh)
		{
			mWeatherTextView.setText("");
			loadWeatherData();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadWeatherData()
	{
		showWeatherDataView();
		String location = ZealotryPreferences.getPreferredWeatherLocation(this);
		new FetchWeatherTask().execute(location);
	}

	private void showWeatherDataView()
	{
		mWeatherTextView.setVisibility(View.VISIBLE);
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
	}

	private void showErrorMessage()
	{
		mWeatherTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}

	public class FetchWeatherTask extends AsyncTask<String, Void, String[]>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			mLoadingIndicator.setVisibility(View.VISIBLE);
		}

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
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			if (weatherData != null)
			{
				showWeatherDataView();
				for (String weatherString : weatherData)
					{ mWeatherTextView.append((weatherString) + "\n\n\n");}
			}
			else
				{ showErrorMessage();}
		}
	}
}