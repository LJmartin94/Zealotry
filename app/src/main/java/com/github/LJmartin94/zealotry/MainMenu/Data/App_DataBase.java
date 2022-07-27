package com.github.LJmartin94.zealotry.MainMenu.Data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Database migrations are beyond the scope of this codelab, so we set exportSchema to false
// here to avoid a build warning. In a real app, you should consider setting a
// directory for Room to use to export the schema so you can check the
// current schema into your version control system.

//TODO Can maybe just have one db for this app, instead of separate dbs for separate information groups
//TODO Rename to App_DataBase with refactor

@Database(entities = {String_KVP_Entity.class}, version = 2, exportSchema = false)
public abstract class App_DataBase extends RoomDatabase
{

	public abstract KVP_DAO eiDAO();

	private static volatile App_DataBase INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	static final ExecutorService databaseWriteExecutor =
			Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static App_DataBase getDatabase(final Context context)
	{
		if (INSTANCE == null)
		{
			synchronized (App_DataBase.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							App_DataBase.class, "Zealotry_Database")
							.addCallback(sRoomDatabaseCallback)
							.fallbackToDestructiveMigrationFrom(1)
							.build();
				}
			}
		}
		return INSTANCE;
	}

	private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
	{
		@Override
		public void onCreate(@NonNull SupportSQLiteDatabase db)
		{
			super.onCreate(db);

			//This code block deletes and resets database on app reinstall
			databaseWriteExecutor.execute(() ->
			{
				KVP_DAO dao = INSTANCE.eiDAO();
				dao.deleteAll();

				long val;

				val = 0;
				val = val * 60 * 1000; //convert minutes to milliseconds.
				String_KVP_Entity MorningUI_WakeUp_duration = new String_KVP_Entity("MorningUI_WakeUp_duration", String.valueOf(val));
				dao.insert(MorningUI_WakeUp_duration);
				String_KVP_Entity MorningUI_WakeUp_attempts = new String_KVP_Entity("MorningUI_WakeUp_attempt", String.valueOf(1));
				dao.insert(MorningUI_WakeUp_attempts);

				val = 0;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_GetUp_duration = new String_KVP_Entity("MorningUI_GetUp_duration", String.valueOf(val));
				dao.insert(MorningUI_GetUp_duration);
				String_KVP_Entity MorningUI_GetUp_attempts = new String_KVP_Entity("MorningUI_GetUp_attempt", String.valueOf(1));
				dao.insert(MorningUI_GetUp_attempts);

				val = 28;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Shower_duration = new String_KVP_Entity("MorningUI_Shower_duration", String.valueOf(val));
				dao.insert(MorningUI_Shower_duration);
				String_KVP_Entity MorningUI_Shower_attempts = new String_KVP_Entity("MorningUI_Shower_attempt", String.valueOf(1));
				dao.insert(MorningUI_Shower_attempts);

				val = 2;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_News_duration = new String_KVP_Entity("MorningUI_News_duration", String.valueOf(val));
				dao.insert(MorningUI_News_duration);
				String_KVP_Entity MorningUI_News_attempts = new String_KVP_Entity("MorningUI_News_attempt", String.valueOf(1));
				dao.insert(MorningUI_News_attempts);

				val = 2;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Curtains_duration = new String_KVP_Entity("MorningUI_Curtains_duration", String.valueOf(val));
				dao.insert(MorningUI_Curtains_duration);
				String_KVP_Entity MorningUI_Curtains_attempts = new String_KVP_Entity("MorningUI_Curtains_attempt", String.valueOf(1));
				dao.insert(MorningUI_Curtains_attempts);

				val = 18;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Coffee_duration = new String_KVP_Entity("MorningUI_Coffee_duration", String.valueOf(val));
				dao.insert(MorningUI_Coffee_duration);
				String_KVP_Entity MorningUI_Coffee_attempts = new String_KVP_Entity("MorningUI_Coffee_attempt", String.valueOf(1));
				dao.insert(MorningUI_Coffee_attempts);

				val = 0;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Smoothie_duration = new String_KVP_Entity("MorningUI_Smoothie_duration", String.valueOf(val));
				dao.insert(MorningUI_Smoothie_duration);
				String_KVP_Entity MorningUI_Smoothie_attempts = new String_KVP_Entity("MorningUI_Smoothie_attempt", String.valueOf(1));
				dao.insert(MorningUI_Smoothie_attempts);

				val = 3;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Hydrate_duration = new String_KVP_Entity("MorningUI_Hydrate_duration", String.valueOf(val));
				dao.insert(MorningUI_Hydrate_duration);
				String_KVP_Entity MorningUI_Hydrate_attempts = new String_KVP_Entity("MorningUI_Hydrate_attempt", String.valueOf(1));
				dao.insert(MorningUI_Hydrate_attempts);

				val = 2;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Vitamins_duration = new String_KVP_Entity("MorningUI_Vitamins_duration", String.valueOf(val));
				dao.insert(MorningUI_Vitamins_duration);
				String_KVP_Entity MorningUI_Vitamins_attempts = new String_KVP_Entity("MorningUI_Vitamins_attempt", String.valueOf(1));
				dao.insert(MorningUI_Vitamins_attempts);

				val = 8;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Dishes_duration = new String_KVP_Entity("MorningUI_Dishes_duration", String.valueOf(val));
				dao.insert(MorningUI_Dishes_duration);
				String_KVP_Entity MorningUI_Dishes_attempts = new String_KVP_Entity("MorningUI_Dishes_attempt", String.valueOf(1));
				dao.insert(MorningUI_Dishes_attempts);

				val = 17;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Breakfast_duration = new String_KVP_Entity("MorningUI_Breakfast_duration", String.valueOf(val));
				dao.insert(MorningUI_Breakfast_duration);
				String_KVP_Entity MorningUI_Breakfast_attempts = new String_KVP_Entity("MorningUI_Breakfast_attempt", String.valueOf(1));
				dao.insert(MorningUI_Breakfast_attempts);

				val = 4;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Plants_duration = new String_KVP_Entity("MorningUI_Plants_duration", String.valueOf(val));
				dao.insert(MorningUI_Plants_duration);
				String_KVP_Entity MorningUI_Plants_attempts = new String_KVP_Entity("MorningUI_Plants_attempt", String.valueOf(1));
				dao.insert(MorningUI_Plants_attempts);

				val = 19;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Meditation_duration = new String_KVP_Entity("MorningUI_Meditation_duration", String.valueOf(val));
				dao.insert(MorningUI_Meditation_duration);
				String_KVP_Entity MorningUI_Meditation_attempts = new String_KVP_Entity("MorningUI_Meditation_attempt", String.valueOf(1));
				dao.insert(MorningUI_Meditation_attempts);

				val = 17;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Language_duration = new String_KVP_Entity("MorningUI_Language_duration", String.valueOf(val));
				dao.insert(MorningUI_Language_duration);
				String_KVP_Entity MorningUI_Language_attempts = new String_KVP_Entity("MorningUI_Language_attempt", String.valueOf(1));
				dao.insert(MorningUI_Language_attempts);

				val = 11;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Teeth_duration = new String_KVP_Entity("MorningUI_Teeth_duration", String.valueOf(val));
				dao.insert(MorningUI_Teeth_duration);
				String_KVP_Entity MorningUI_Teeth_attempts = new String_KVP_Entity("MorningUI_Teeth_attempt", String.valueOf(1));
				dao.insert(MorningUI_Teeth_attempts);

				val = 4;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Cosmeticism_duration = new String_KVP_Entity("MorningUI_Cosmeticism_duration", String.valueOf(val));
				dao.insert(MorningUI_Cosmeticism_duration);
				String_KVP_Entity MorningUI_Cosmeticism_attempts = new String_KVP_Entity("MorningUI_Cosmeticism_attempt", String.valueOf(1));
				dao.insert(MorningUI_Cosmeticism_attempts);

				val = 11;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Pack_duration = new String_KVP_Entity("MorningUI_Pack_duration", String.valueOf(val));
				dao.insert(MorningUI_Pack_duration);
				String_KVP_Entity MorningUI_Pack_attempts = new String_KVP_Entity("MorningUI_Pack_attempt", String.valueOf(1));
				dao.insert(MorningUI_Pack_attempts);

				val = 1;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_MakeBed_duration = new String_KVP_Entity("MorningUI_MakeBed_duration", String.valueOf(val));
				dao.insert(MorningUI_MakeBed_duration);
				String_KVP_Entity MorningUI_MakeBed_attempts = new String_KVP_Entity("MorningUI_MakeBed_attempt", String.valueOf(1));
				dao.insert(MorningUI_MakeBed_attempts);

				val = 11;
				val = val * 60 * 1000;
				String_KVP_Entity MorningUI_Bin_duration = new String_KVP_Entity("MorningUI_Bin_duration", String.valueOf(val));
				dao.insert(MorningUI_Bin_duration);
				String_KVP_Entity MorningUI_Bin_attempts = new String_KVP_Entity("MorningUI_Bin_attempt", String.valueOf(1));
				dao.insert(MorningUI_Bin_attempts);
			});

		}
	};
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

