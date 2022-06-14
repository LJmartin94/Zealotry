package com.github.LJmartin94.zealotry.MainMenu.Data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Database migrations are beyond the scope of this codelab, so we set exportSchema to false
// here to avoid a build warning. In a real app, you should consider setting a
// directory for Room to use to export the schema so you can check the
// current schema into your version control system.
@Database(entities = {ExerciseInfo_entity.class}, version = 1, exportSchema = false)
public abstract class ExerciseInfo_db extends RoomDatabase
{

	public abstract ExerciseInfo_DAO eiDAO();

	private static volatile ExerciseInfo_db INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	static final ExecutorService databaseWriteExecutor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	static ExerciseInfo_db getDatabase(final Context context)
	{
		if (INSTANCE == null)
		{
			synchronized (ExerciseInfo_db.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							ExerciseInfo_db.class, "exercise_database")
							.build();
				}
			}
		}
		return INSTANCE;
	}

 	//Exercise type
	//Exercise week
	//Exercise day
	//SetType
	//Rest period

	//L_SetThreshold
	//L_Set1
	//L_Set2
	//L_Set3
	//L_Set4
	//L_Set5
	//L_Set6
	//L_Set7
	//L_Set8
	//L_Set9
	//L_Set10

	//M_SetThreshold
	//M_Set1
	//M_Set2
	//M_Set3
	//M_Set4
	//M_Set5
	//M_Set6
	//M_Set7
	//M_Set8
	//M_Set9
	//M_Set10

	//H_SetThreshold
	//H_Set1
	//H_Set2
	//H_Set3
	//H_Set4
	//H_Set5
	//H_Set6
	//H_Set7
	//H_Set8
	//H_Set9
	//H_Set10

}
