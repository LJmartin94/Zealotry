package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.github.LJmartin94.zealotry.MainMenu.Data.Exercise_Entity;

public class Exercise_rvAdapter extends ListAdapter<Exercise_Entity, Exercise_rvViewHolder>
{
	public Exercise_rvAdapter(@NonNull DiffUtil.ItemCallback<Exercise_Entity> diffCallback)
	{
		super(diffCallback);
	}

	@Override
	public Exercise_rvViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return Exercise_rvViewHolder.create(parent);
	}

	@Override
	public void onBindViewHolder(Exercise_rvViewHolder holder, int position)
	{
		Exercise_Entity current = getItem(position);
		holder.bind(current.getID());
	}

	static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise_Entity>
	{
		@Override
		public boolean areItemsTheSame(@NonNull Exercise_Entity oldItem,
									   @NonNull Exercise_Entity newItem)
		{
			return oldItem == newItem;
		}

		@Override
		public boolean areContentsTheSame(@NonNull Exercise_Entity oldItem,
										  @NonNull Exercise_Entity newItem)
		{
			//For all fields in Entity, add a comparison to the return expression.
			return ( oldItem.getID().equals(newItem.getID()) &&
					true);
		}
	}
}
