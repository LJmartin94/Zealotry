package com.github.LJmartin94.zealotry;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class WF_ForecastAdapter {

	public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {
		public final TextView mWeatherTextView;

		public ForecastAdapterViewHolder(View view) {
			super(view);
			mWeatherTextView = view.findViewById(R.id.tv_weather_data);
		}
	}
}
