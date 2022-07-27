package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.github.LJmartin94.zealotry.MainMenu.Data.String_KVP_Entity;

public class Exercise_rvAdapter extends ListAdapter<String_KVP_Entity, Exercise_rvViewHolder>
{
	public Exercise_rvAdapter(@NonNull DiffUtil.ItemCallback<String_KVP_Entity> diffCallback)
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
		String_KVP_Entity current = getItem(position);
		holder.bind(current.getKey());
	}

	static class ExerciseDiff extends DiffUtil.ItemCallback<String_KVP_Entity>
	{
		@Override
		public boolean areItemsTheSame(@NonNull String_KVP_Entity oldItem,
									   @NonNull String_KVP_Entity newItem)
		{
			return oldItem == newItem;
		}

		@Override
		public boolean areContentsTheSame(@NonNull String_KVP_Entity oldItem,
										  @NonNull String_KVP_Entity newItem)
		{
			//For all fields in Entity, add a comparison to the return expression.
			return ( oldItem.getKey().equals(newItem.getKey()) &&
					oldItem.getValue().equals(newItem.getValue()) );
		}
	}
}
