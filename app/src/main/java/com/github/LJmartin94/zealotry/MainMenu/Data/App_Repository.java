package com.github.LJmartin94.zealotry.MainMenu.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class App_Repository
{
	private Exercise_DAO mExerciseDao;
	private LiveData<List<Exercise_Entity>> mAllExercises;

	public App_Repository(Application application)
	{
		App_DataBase db = App_DataBase.getDatabase(application);
		mExerciseDao = db.eiDAO();
		mAllExercises = mExerciseDao.getOrderedExercises();
	}

	// Room executes all queries on a separate thread.
	// Observed LiveData will notify the observer when the data has changed.
	public LiveData<List<Exercise_Entity>> getAllExercises()
	{
		return mAllExercises;
	}

	// You must call this on a non-UI thread or your app will throw an exception. Room ensures
	// that you're not doing any long running operations on the main thread, blocking the UI.
	public void insert(Exercise_Entity exercise)
	{
		App_DataBase.databaseWriteExecutor.execute(() ->
		{
			mExerciseDao.insert(exercise);
		});
	}

}
