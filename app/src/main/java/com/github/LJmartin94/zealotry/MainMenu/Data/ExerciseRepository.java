package com.github.LJmartin94.zealotry.MainMenu.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseRepository
{
	private ExerciseInfo_DAO mExerciseDao;
	private LiveData<List<ExerciseInfo_entity>> mAllExercises;

	ExerciseRepository(Application application)
	{
		ExerciseInfo_db db = ExerciseInfo_db.getDatabase(application);
		mExerciseDao = db.eiDAO();
		mAllExercises = mExerciseDao.getOrderedExercises();
	}

	// Room executes all queries on a separate thread.
	// Observed LiveData will notify the observer when the data has changed.
	LiveData<List<ExerciseInfo_entity>> getAllExercises()
	{
		return mAllExercises;
	}

	// You must call this on a non-UI thread or your app will throw an exception. Room ensures
	// that you're not doing any long running operations on the main thread, blocking the UI.
	void insert(ExerciseInfo_entity exercise)
	{
		ExerciseInfo_db.databaseWriteExecutor.execute(() ->
		{
			mExerciseDao.insert(exercise);
		});
	}

}
