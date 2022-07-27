package com.github.LJmartin94.zealotry.MainMenu.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class App_Repository
{
	private KVP_DAO mKvpDao;
	private LiveData<List<String_KVP_Entity>> mAllKeys;

	public App_Repository(Application application)
	{
		App_DataBase db = App_DataBase.getDatabase(application);
		mKvpDao = db.eiDAO();
		mAllKeys = mKvpDao.getOrderedKeys();
	}

	// Room executes all queries on a separate thread.
	// Observed LiveData will notify the observer when the data has changed.
	public LiveData<List<String_KVP_Entity>> getAllKeys()
	{
		return mAllKeys;
	}

	// You must call this on a non-UI thread or your app will throw an exception. Room ensures
	// that you're not doing any long running operations on the main thread, blocking the UI.
	public void insert(String_KVP_Entity entity)
	{
		App_DataBase.databaseWriteExecutor.execute(() ->
		{
			mKvpDao.insert(entity);
		});
	}

}
