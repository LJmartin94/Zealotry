package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.LJmartin94.zealotry.R;

public class Exercise_rvViewHolder extends RecyclerView.ViewHolder
{
	private final TextView exerciseItemView;

	private Exercise_rvViewHolder (View itemView)
	{
		super(itemView);
		exerciseItemView = itemView.findViewById(R.id.exerciseTextView);
	}

	public void bind(String text)
	{
		exerciseItemView.setText(text);
	}

	static Exercise_rvViewHolder create(ViewGroup parent)
	{
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.recycler_item_exercise, parent, false);
		return new Exercise_rvViewHolder(view);
	}
}
