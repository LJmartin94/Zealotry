package com.github.LJmartin94.zealotry.MainMenu.Overview.WeatherForecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.LJmartin94.zealotry.MainMenu.MainActivity;
import com.github.LJmartin94.zealotry.MainMenu.ZealotryPreferences;
import com.github.LJmartin94.zealotry.R;

import java.net.URL;

public class WeatherForecast extends AppCompatActivity implements
		WF_ForecastAdapter.ForecastAdapterOnClickHandler,
		LoaderManager.LoaderCallbacks<String[]>
{
	private static final String TAG = MainActivity.class.getSimpleName();
	private RecyclerView mRecyclerView;
	private WF_ForecastAdapter mForecastAdapter;
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;
	private static final int FORECAST_LOADER_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weatherforecast);
		mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_forecast);
		LinearLayoutManager layoutManager
				= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setHasFixedSize(true);
		mForecastAdapter = new WF_ForecastAdapter(this);
		mRecyclerView.setAdapter(mForecastAdapter);
		mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_display);
		mLoadingIndicator = (ProgressBar)findViewById(R.id.pb_loading_indicator);
		LoaderManager.LoaderCallbacks<String[]> callback = WeatherForecast.this;
		LoaderManager.getInstance(this).initLoader(FORECAST_LOADER_ID, null, callback); // Ensures there's an active loader initialised
	}

	private void openLocationInMap()
	{
		String addressString = "Royal Observatory Edinburgh, Edinburgh EH9 3HJ, United Kingdom";
		Uri geoLocation = Uri.parse("geo:0,0?q=" + addressString);

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(geoLocation);

		if (intent.resolveActivity(getPackageManager()) != null)
		{	startActivity(intent); }
		else
		{
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
		switch (id)
		{
			case R.id.action_refresh:
				mForecastAdapter.setWeatherData(null);
				LoaderManager.getInstance(this).restartLoader(FORECAST_LOADER_ID, null, this);
				return true;

			case R.id.action_map:
				openLocationInMap();
				return true;

			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(String weatherForDay)
	{
		Intent i = new Intent (this, WFDetailActivity.class);
		i.putExtra(Intent.EXTRA_TEXT, weatherForDay);
		startActivity(i);
	}

	private void showWeatherDataView()
	{
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mRecyclerView.setVisibility(View.VISIBLE);
	}

	private void showErrorMessage()
	{
		mRecyclerView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}

	@NonNull
	@Override
	public Loader<String[]> onCreateLoader(int id, @Nullable Bundle args)
	{
		AsyncTaskLoader<String[]> Loader = new AsyncTaskLoader<String[]>(this)
		{
			String[] mWeatherData = null;

			@Override
			protected void onStartLoading()
			{
				if (mWeatherData != null)
					{deliverResult(mWeatherData);}
				else
				{
					mLoadingIndicator.setVisibility(View.VISIBLE);
					forceLoad();
				}
			}

			@Override
			public String[] loadInBackground()
			{
				String locationQuery = ZealotryPreferences
						.getPreferredWeatherLocation(WeatherForecast.this);
				URL weatherRequestUrl = NetworkUtils.WFbuildUrl(locationQuery);
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

			public void deliverResult(String[] data)
			{
				mWeatherData = data;
				super.deliverResult(data);
			}
		};
		return(Loader);
	}

	@Override
	public void onLoadFinished(@NonNull Loader<String[]> loader, String[] data)
	{
		mLoadingIndicator.setVisibility(View.INVISIBLE);
		mForecastAdapter.setWeatherData(data);

		if (data != null)
			{showWeatherDataView();}
		else
			{showErrorMessage();}
	}

	@Override
	public void onLoaderReset(@NonNull Loader<String[]> loader)
	{
		/**
		 * Called when a previously created loader is being reset, and thus
		 * making its data unavailable.  The application should at this point
		 * remove any references it has to the Loader's data.
		 */
	}
}