package com.github.LJmartin94.zealotry.MainMenu.WeatherForecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.MainActivity;
import com.github.LJmartin94.zealotry.MainMenu.ZealotryPreferences;
import com.github.LJmartin94.zealotry.R;

import java.net.URL;

public class WeatherForecast extends AppCompatActivity implements WF_ForecastAdapter.ForecastAdapterOnClickHandler
{

//	private TextView mWeatherTextView;
	private static final String TAG = MainActivity.class.getSimpleName();
	private RecyclerView mRecyclerView;
	private WF_ForecastAdapter mForecastAdapter;
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weatherforecast);

//		mWeatherTextView = (TextView)findViewById(R.id.tv_weather_data);
		mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_forecast);
		LinearLayoutManager layoutManager
				= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);

		mForecastAdapter = new WF_ForecastAdapter(this);
		mRecyclerView.setAdapter(mForecastAdapter);

		mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);

		mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
		loadWeatherData();
	}

	private void openLocationInMap()
	{
		String addressString = "Royal Observatory Edinburgh, Edinburgh EH9 3HJ, United Kingdom";
		Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(geoLocation);

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		} else {
			Log.d(TAG, "Couldn't call " + geoLocation.toString()
					+ ", no receiving apps installed!");
		}
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
			mForecastAdapter.setWeatherData(null);
			loadWeatherData();
			return true;
		}

		if (id == R.id.action_map)
		{
			openLocationInMap();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	Toast toast = null;

	@Override
	public void onClick(String weatherForDay)
	{
		//		if (toast != null)
		//		{
		//			toast.cancel();
		//		}
		//		toast = Toast.makeText(this, weatherForDay, Toast.LENGTH_SHORT);
		//		toast.show();
		Intent i = new Intent (this, WFDetailActivity.class);
		i.putExtra(Intent.EXTRA_TEXT, weatherForDay);
		startActivity(i);
	}

	private void loadWeatherData()
	{
		showWeatherDataView();
		String location = ZealotryPreferences.getPreferredWeatherLocation(this);
		new FetchWeatherTask().execute(location);
	}

	private void showWeatherDataView()
	{
		mRecyclerView.setVisibility(View.VISIBLE);
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
	}

	private void showErrorMessage()
	{
		mRecyclerView.setVisibility(View.INVISIBLE);
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
				mForecastAdapter.setWeatherData(weatherData);
			}
			else
			{
				showErrorMessage();
			}
		}
	}
}