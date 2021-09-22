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
		Context context = viewGroup.getContext();
		int layoutIdForListItem = R.layout.wf_list_item;
		LayoutInflater inflater = LayoutInflater.from(context);
		boolean attachImmediately = false;

		View view = inflater.inflate(layoutIdForListItem, viewGroup, attachImmediately);
		return new ForecastAdapterViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position)
	{
		String weatherForThisDay = mWeatherData[position];
		forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
	}

	@Override
	public int getItemCount()
	{
		if (mWeatherData == null)
			return(0);
		return mWeatherData.length;
	}

}
