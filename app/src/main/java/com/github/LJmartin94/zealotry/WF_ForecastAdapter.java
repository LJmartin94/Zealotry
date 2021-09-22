package com.github.LJmartin94.zealotry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WF_ForecastAdapter extends RecyclerView.Adapter<WF_ForecastAdapter.ForecastAdapterViewHolder>
{
	private String[] mWeatherData;

	public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder
	{
		public final TextView mWeatherTextView;

		public ForecastAdapterViewHolder(View view)
		{
			super(view);
			mWeatherTextView = view.findViewById(R.id.tv_weather_data);
		}
	}

	@Override
	public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
	{

	}

	@Override
	public ForecastAdapterViewHolder onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position)
	{

	}

	@Override
	public ForecastAdapterViewHolder getItemCount()
	{

	}

}
