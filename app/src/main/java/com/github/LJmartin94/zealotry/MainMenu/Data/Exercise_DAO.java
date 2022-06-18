package com.github.LJmartin94.zealotry.MainMenu.Data;

//DAO == Data Access Object

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Exercise_DAO
{
	// allowing the insert of the same word multiple times by passing a conflict
	// resolution strategy

	//	The @Insert annotation is a special DAO method annotation where you don't
	//	have to provide any SQL. There are also @Delete and @Update annotations
	//	for deleting and updating rows.

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Exercise_Entity exercise);

	@Query("DELETE FROM exercise_table")
	void deleteAll();

	@Query("SELECT * FROM exercise_table ORDER BY ID ASC")
	LiveData<List<Exercise_Entity>> getOrderedExercises();

}
