package com.github.LJmartin94.zealotry.MainMenu.Evening;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.LJmartin94.zealotry.MainMenu.Data.ExerciseInfo_entity;
import com.github.LJmartin94.zealotry.MainMenu.Data.ExerciseRepository;

import java.util.List;

public class Exercise_ViewModel extends AndroidViewModel
{
	private ExerciseRepository mRepository;

	private final LiveData<List<ExerciseInfo_entity>> mAllExercises;

	public Exercise_ViewModel(Application application)
	{
		super(application);
		mRepository = new ExerciseRepository(application);
		mAllExercises = mRepository.getAllExercises();
	}

	LiveData<List<ExerciseInfo_entity>> getAllExercises()
	{
		return mAllExercises;
	}

	public void insert(ExerciseInfo_entity exercise)
	{
		mRepository.insert(exercise);
	}

}
