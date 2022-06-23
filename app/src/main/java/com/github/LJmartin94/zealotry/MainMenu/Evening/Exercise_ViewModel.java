package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.LJmartin94.zealotry.MainMenu.Data.Exercise_Entity;
import com.github.LJmartin94.zealotry.MainMenu.Data.App_Repository;

import java.util.List;

public class Exercise_ViewModel extends AndroidViewModel
{
	private App_Repository mRepository;

	private final LiveData<List<Exercise_Entity>> mAllExercises;

	public Exercise_ViewModel(Application application)
	{
		super(application);
		mRepository = new App_Repository(application);
		mAllExercises = mRepository.getAllExercises();
	}

	LiveData<List<Exercise_Entity>> getAllExercises()
	{
		return mAllExercises;
	}

	public void insert(Exercise_Entity exercise)
	{
		mRepository.insert(exercise);
	}

}
