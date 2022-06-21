package com.github.LJmartin94.zealotry.MainMenu.Overview.BackupData;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.view.View;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Data.ExerciseInfo_db;
import com.github.LJmartin94.zealotry.MainMenu.Data.Exercise_Entity;
import com.github.LJmartin94.zealotry.MainMenu.Evening.ExerciseTest_Activity;
import com.github.LJmartin94.zealotry.MainMenu.Evening.ExerciseTest_NewExercise_Activity;
import com.github.LJmartin94.zealotry.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

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

	ActivityResultLauncher<Intent> fileCreationARL =
			registerForActivityResult(
					new ActivityResultContracts.StartActivityForResult(),
					new ActivityResultCallback<ActivityResult>()
					{
						@Override
						public void onActivityResult(ActivityResult result)
						{
							if (result.getResultCode() == RESULT_OK)
							{
								Intent data = result.getData();
								String fileCreated = data.getDataString();
								Toast.makeText( getApplicationContext(), "Created: " + fileCreated, Toast.LENGTH_LONG).show();
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Could not create file.", Toast.LENGTH_LONG).show();
							}
						}
					});

	private void createFile(Uri pickerInitialUri, String fileName)
	{
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("Zealotry/zb");
		intent.putExtra(Intent.EXTRA_TITLE, fileName);

		//TODO Apply intent filter so that Android automatically opens *.zb files with Zealotry and processes them
		// https://stackoverflow.com/questions/34300452/android-associate-app-with-custom-file-type
		// https://stackoverflow.com/questions/1733195/android-intent-filter-for-a-particular-file-extension
		// https://stackoverflow.com/questions/3760276/android-intent-filter-associate-app-with-file-extension

		// Optionally, specify a URI for the directory that should be opened in
		// the system file picker when your app creates the document.
		intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

//		ExtendedFloatingActionButton fabCompleted = findViewById(R.id.completedfab);
//		fabCompleted.setOnClickListener( view ->
//		{
//			Intent intent = new Intent(ExerciseTest_Activity.this, ExerciseTest_NewExercise_Activity.class);
//			completedActivityResultLauncher.launch(intent);
//		});

		fileCreationARL.launch(intent);
//		startActivityForResult(intent, CREATE_FILE);
//		onActivityResult(); // to write in created file
	}

	private static final int PICK_PDF_FILE = 2;

	private void openFile(Uri pickerInitialUri)
	{
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("Zealotry/zb");

		// Optionally, specify a URI for the file that should appear in the
		// system file picker when it loads.
		intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

		startActivityForResult(intent, PICK_PDF_FILE);
	}

	public void backupDatabase(Context context)
	{
		ExerciseInfo_db appDatabase = ExerciseInfo_db.getDatabase(context);
		appDatabase.close();
		File dbInstance = context.getDatabasePath("Zealotry_Database");
		String path = dbInstance.getAbsolutePath();

		Uri pickerInitialUri = Uri.fromFile(dbInstance);
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String backupFileName = "Zealotry_Database_Backup" + timeStamp;
		createFile(pickerInitialUri, backupFileName);
//		openFile(pickerInitialUri);




//		String dlpath = Environment.getDownloadCacheDirectory().getAbsolutePath();
//		dlpath = Environment.getStorageDirectory().getAbsolutePath();
//		dlpath = Environment.getDataDirectory().getAbsolutePath();
////		dlpath = Environment.DIRECTORY_DOWNLOADS;
//		dlpath = "Android/data/com.github.LJmartin94.zealotry/";
//
//		//https://developer.android.com/guide/topics/providers/document-provider
//
//		File sdir = new File(dlpath, "backup");
//
//		String backupFileName = "Zealotry_Database_Backup" + timeStamp;
//		String sfpath = sdir.getAbsolutePath() + File.separator + backupFileName;
//		if (!sdir.exists())
//		{
//			Toast.makeText(context, "This directory did not exist: " + sdir.getAbsolutePath(), Toast.LENGTH_LONG).show();
//			sdir.mkdirs();
//		}
//		if (sdir.exists())
//		{
//			Toast.makeText(context, "This directory DOES exist: " + sdir.getAbsolutePath(), Toast.LENGTH_LONG).show();
//		}
//
//		if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//		}
//		File backupFile = new File(sfpath);
//		try
//		{
//			Toast.makeText(context, "Trying to create: " + sfpath, Toast.LENGTH_LONG).show();
//			backupFile.createNewFile();
//		}
//		catch (IOException e)
//		{
//			Toast.makeText(context, "IO exception", Toast.LENGTH_LONG).show();
//			e.printStackTrace();
//		}
//		if (backupFile.exists())
//			Toast.makeText(context, "Made backup file: " + sfpath, Toast.LENGTH_LONG).show();
//
//
//
//		//DEBUG:
////		Toast.makeText(context, path, Toast.LENGTH_LONG).show();
	}
}