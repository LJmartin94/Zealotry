package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.LJmartin94.zealotry.MainMenu.Data.App_Repository;
import com.github.LJmartin94.zealotry.MainMenu.Data.String_KVP_Entity;

import java.util.List;

public class Exercise_ViewModel extends AndroidViewModel
{
	private App_Repository mRepository;

	private final LiveData<List<String_KVP_Entity>> mAllExercises;

	public Exercise_ViewModel(Application application)
	{
		super(application);
		mRepository = new App_Repository(application);
		mAllExercises = mRepository.getAllKeys();
	}

	LiveData<List<String_KVP_Entity>> getAllExercises()
	{
		return mAllExercises;
	}

	public void insert(String_KVP_Entity exercise)
	{
		mRepository.insert(exercise);
	}

}
