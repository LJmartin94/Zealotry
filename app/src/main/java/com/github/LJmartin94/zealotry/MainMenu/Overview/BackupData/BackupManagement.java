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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BackupManagement extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup_management);
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

								Uri fileLocation = Uri.parse(fileCreated);
								resumeDatabaseBackup(fileLocation);
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Could not create file.", Toast.LENGTH_LONG).show();
							}
						}
					});

	public void initiateDatabaseBackup(View view)
	{
		// Should set the directory the picker initially suggests, but doesn't seem to do anything
		Uri pickerInitialUri = Uri.fromFile(Environment.getDownloadCacheDirectory());

		String timeStamp = String.valueOf(System.currentTimeMillis());
		String backupFileName = "Zealotry_Database_Backup" + timeStamp;
		createFileForBackup(pickerInitialUri, backupFileName);
	}

	private void createFileForBackup(Uri pickerInitialUri, String fileName)
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

		fileCreationARL.launch(intent);
	}

	public void resumeDatabaseBackup(Uri fileUri)
	{
		Context context = this;
		ExerciseInfo_db appDatabase = ExerciseInfo_db.getDatabase(context);
		appDatabase.close();
		File dbInstance = context.getDatabasePath("Zealotry_Database");

		try
		{
			InputStream input = new FileInputStream(dbInstance);
			OutputStream output = getContentResolver().openOutputStream(fileUri);
			long buffersizeL = Math.abs(dbInstance.length());
			Math.min(buffersizeL, Integer.MAX_VALUE);
			int buffersize = (int)buffersizeL;
			byte[] b = new byte[buffersize];
			int bytes_read;
			while ((bytes_read = input.read(b, 0, buffersize)) > 0)
			{
				output.write(b, 0, bytes_read);
			}
			output.flush();
			input.close();
			output.close();
		}
		catch (Exception e)
		{
			Toast.makeText( getApplicationContext(), "Encountered error whilst trying to back up database", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void initiateDatabaseRestore(View view)
	{
		Toast.makeText( getApplicationContext(), "Restoring database to prior state from back-up", Toast.LENGTH_LONG).show();
	}
}