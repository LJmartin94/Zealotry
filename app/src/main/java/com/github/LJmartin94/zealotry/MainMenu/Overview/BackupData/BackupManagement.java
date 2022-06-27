package com.github.LJmartin94.zealotry.MainMenu.Overview.BackupData;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Data.App_DataBase;
import com.github.LJmartin94.zealotry.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BackupManagement extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup_management);
	}

	public void initiateDatabaseBackup(View view)
	{
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String backupFileName = "Zealotry_Database_Backup" + timeStamp;

		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("Zealotry/zb");
		intent.putExtra(Intent.EXTRA_TITLE, backupFileName);

		// Should set the directory the picker initially suggests, but doesn't seem to do anything
		Uri pickerInitialUri = Uri.fromFile(Environment.getDownloadCacheDirectory());
		intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

		fileCreationARL.launch(intent);
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
								Uri fileLocation = Uri.parse(fileCreated);
								resumeDatabaseBackup(fileLocation);
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Could not create file.", Toast.LENGTH_LONG).show();
							}
						}
					});

	public void resumeDatabaseBackup(Uri fileUri)
	{
		Context context = this;
		App_DataBase appDatabase = App_DataBase.getDatabase(context);
		appDatabase.close();
		File dbInstance = context.getDatabasePath("Zealotry_Database");

		try
		{
			InputStream input = new FileInputStream(dbInstance);
			OutputStream output = getContentResolver().openOutputStream(fileUri);
			long buffersizeL = dbInstance.length();
			buffersizeL = Math.min(buffersizeL, Integer.MAX_VALUE);
			buffersizeL = (buffersizeL <= 0) ? Integer.MAX_VALUE : buffersizeL;
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
			Toast.makeText( getApplicationContext(), "Created back-up file" , Toast.LENGTH_LONG).show();
		}
		catch (Exception e)
		{
			Toast.makeText( getApplicationContext(), "Encountered error whilst trying to back up database", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		reopenDB(appDatabase);
	}

	public void initiateDatabaseRestore(View view)
	{
		Toast.makeText( getApplicationContext(), "Restoring database to prior state from back-up", Toast.LENGTH_LONG).show();
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.setType("Zealotry/zb");
		Intent toLaunch = Intent.createChooser(i, "Select back-up file to revert to.");
		fileRestoreARL.launch(toLaunch);
	}

	ActivityResultLauncher<Intent> fileRestoreARL =
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
								String fileSpecified = data.getDataString();
								Uri fileLocation = Uri.parse(fileSpecified);
								resumeDatabaseRestore(fileLocation);
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Could not restore database from file.", Toast.LENGTH_LONG).show();
							}
						}
					});

	public void resumeDatabaseRestore(Uri fileLocation)
	{
		//TODO Make pop-up dialogue confirming user wants to restore from this file, as it will overwrite existing data.

		//TODO Apply intent filter so that Android automatically opens *.zb files with Zealotry and processes them
		// https://stackoverflow.com/questions/34300452/android-associate-app-with-custom-file-type
		// https://stackoverflow.com/questions/1733195/android-intent-filter-for-a-particular-file-extension
		// https://stackoverflow.com/questions/3760276/android-intent-filter-associate-app-with-file-extension

		try
		{
			FileInputStream newDB = (FileInputStream) getContentResolver().openInputStream(fileLocation);
			if (this.getContentResolver().getType(fileLocation).equals("Zealotry/zb"))
			{
				//Valid file type
				App_DataBase appDatabase = App_DataBase.getDatabase(this);
				appDatabase.close();
				File oldDB = this.getDatabasePath("Zealotry_Database");
				if (newDB != null)
				{
					try
					{
						copyContents(newDB, new FileOutputStream(oldDB));
						Toast.makeText(getApplicationContext(), "Database successfully restored", Toast.LENGTH_LONG).show();
					}
					catch (Exception e)
					{
						Toast.makeText( getApplicationContext(), "Encountered error whilst trying restore database from back-up", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
				else
				{
					Toast.makeText( getApplicationContext(), "Input file is NULL", Toast.LENGTH_LONG).show();
				}
				reopenDB(appDatabase);
			}
			else
			{
				//Invalid file type? (shouldn't be possible really)
				Toast.makeText( getApplicationContext(), "Invalid back-up file supplied.", Toast.LENGTH_LONG).show();
			}
			newDB.close();
		}
		catch (Exception e)
		{
			Toast.makeText( getApplicationContext(), "Could not open input stream from specified file", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	public void copyContents(FileInputStream src, FileOutputStream dst) throws IOException
	{
		FileChannel fromChannel = src.getChannel();
		FileChannel toChannel = dst.getChannel();
		fromChannel.transferTo(0, fromChannel.size(), toChannel);
		fromChannel.close();
		toChannel.close();
	}

	public void reopenDB(App_DataBase db)
	{
//		if (db == null)
//		{
//			db = ExerciseInfo_db.getDatabase(this);
//			Toast.makeText( getApplicationContext(), "DB PASSED OVERWRITTEN", Toast.LENGTH_LONG).show();
//		}
//		if (db != null)
//		{
//			if (!db.isOpen())
//			{
//				db.getOpenHelper().getWritableDatabase();
//				Toast.makeText( getApplicationContext(), "Reopened DB", Toast.LENGTH_LONG).show();
//			}
//		}
		Intent intent = new Intent(this, BackupManagement.class);
		phoenix(this, intent);
	}

	private static final String KEY_RESTART_INTENT = "PHOENIX";

	public static void phoenix(Context context, Intent nextIntent)
	{
		Intent intent = new Intent(context, BackupManagement.class);
		intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(KEY_RESTART_INTENT, nextIntent);
		context.startActivity(intent);
		if (context instanceof Activity)
		{
			((Activity) context).finish();
		}
		Runtime.getRuntime().exit(0);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
