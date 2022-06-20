package com.github.LJmartin94.zealotry.MainMenu.Overview.BackupData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsProvider;
import android.view.View;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Data.ExerciseInfo_db;
import com.github.LJmartin94.zealotry.R;

import java.io.File;
import java.io.IOException;

public class BackupManagement extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup_management);
	}

	public void launchBackupDatabase(View view)
	{
		backupDatabase(this);
	}

	public void backupDatabase(Context context)
	{
		ExerciseInfo_db appDatabase = ExerciseInfo_db.getDatabase(context);
		appDatabase.close();
		File dbInstance = context.getDatabasePath("Zealotry_Database");
		String path = dbInstance.getAbsolutePath();

		String dlpath = Environment.getDownloadCacheDirectory().getAbsolutePath();
		dlpath = Environment.getStorageDirectory().getAbsolutePath();
		dlpath = Environment.getDataDirectory().getAbsolutePath();
//		dlpath = Environment.DIRECTORY_DOWNLOADS;
		dlpath = "Android/data/com.github.LJmartin94.zealotry/";

		//https://developer.android.com/guide/topics/providers/document-provider

		File sdir = new File(dlpath, "backup");
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String backupFileName = "Zealotry_Database_Backup" + timeStamp;
		String sfpath = sdir.getAbsolutePath() + File.separator + backupFileName;
		if (!sdir.exists())
		{
			Toast.makeText(context, "This directory did not exist: " + sdir.getAbsolutePath(), Toast.LENGTH_LONG).show();
			sdir.mkdirs();
		}
		if (sdir.exists())
		{
			Toast.makeText(context, "This directory DOES exist: " + sdir.getAbsolutePath(), Toast.LENGTH_LONG).show();
		}

		if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
		}
		File backupFile = new File(sfpath);
		try
		{
			Toast.makeText(context, "Trying to create: " + sfpath, Toast.LENGTH_LONG).show();
			backupFile.createNewFile();
		}
		catch (IOException e)
		{
			Toast.makeText(context, "IO exception", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		if (backupFile.exists())
			Toast.makeText(context, "Made backup file: " + sfpath, Toast.LENGTH_LONG).show();



		//DEBUG:
//		Toast.makeText(context, path, Toast.LENGTH_LONG).show();
	}
}