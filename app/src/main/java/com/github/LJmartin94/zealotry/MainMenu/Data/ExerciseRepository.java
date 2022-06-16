package com.github.LJmartin94.zealotry.MainMenu.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

//TODO Rename to Exercise_Repo with refactor

public class ExerciseRepository
{
	private ExerciseInfo_DAO mExerciseDao;
	private LiveData<List<ExerciseInfo_entity>> mAllExercises;

	public ExerciseRepository(Application application)
	{
		ExerciseInfo_db db = ExerciseInfo_db.getDatabase(application);
		mExerciseDao = db.eiDAO();
		mAllExercises = mExerciseDao.getOrderedExercises();
	}

	// Room executes all queries on a separate thread.
	// Observed LiveData will notify the observer when the data has changed.
	public LiveData<List<ExerciseInfo_entity>> getAllExercises()
	{
		return mAllExercises;
	}

	// You must call this on a non-UI thread or your app will throw an exception. Room ensures
	// that you're not doing any long running operations on the main thread, blocking the UI.
	public void insert(ExerciseInfo_entity exercise)
	{
		ExerciseInfo_db.databaseWriteExecutor.execute(() ->
		{
			mExerciseDao.insert(exercise);
		});
	}

}
