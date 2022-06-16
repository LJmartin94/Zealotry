package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.github.LJmartin94.zealotry.MainMenu.Data.ExerciseInfo_entity;

public class Exercise_rvAdapter extends ListAdapter<ExerciseInfo_entity, Exercise_rvViewHolder>
{
	public Exercise_rvAdapter(@NonNull DiffUtil.ItemCallback<ExerciseInfo_entity> diffCallback)
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
		ExerciseInfo_entity current = getItem(position);
		holder.bind(current.getID());
	}

	static class ExerciseDiff extends DiffUtil.ItemCallback<ExerciseInfo_entity>
	{
		@Override
		public boolean areItemsTheSame(@NonNull ExerciseInfo_entity oldItem,
									   @NonNull ExerciseInfo_entity newItem)
		{
			return oldItem == newItem;
		}

		@Override
		public boolean areContentsTheSame(@NonNull ExerciseInfo_entity oldItem,
										  @NonNull ExerciseInfo_entity newItem)
		{
			//For all fields in Entity, add a comparison to the return expression.
			return ( oldItem.getID().equals(newItem.getID()) &&
					true);
		}
	}
}
