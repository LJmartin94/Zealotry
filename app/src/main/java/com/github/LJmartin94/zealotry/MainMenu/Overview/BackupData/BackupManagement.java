package com.github.LJmartin94.zealotry.MainMenu.Overview.BackupData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Data.ExerciseInfo_db;
import com.github.LJmartin94.zealotry.R;

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

	public static void backupDatabase(Context context)
	{
		ExerciseInfo_db appDatabase = ExerciseInfo_db.getDatabase(context);
		appDatabase.close();
//		String path = context.getDatabasePath("exercise_database").getAbsolutePath();
		String path = context.getDatabasePath("exercise_database").getPath();

		//DEBUG:
		Toast.makeText(context, path, Toast.LENGTH_LONG).show();
	}
}