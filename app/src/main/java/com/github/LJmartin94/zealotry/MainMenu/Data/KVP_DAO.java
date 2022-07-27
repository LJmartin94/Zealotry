package com.github.LJmartin94.zealotry.MainMenu.Data;

//DAO == Data Access Object

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface KVP_DAO
{
	// allowing the insert of the same word multiple times by passing a conflict
	// resolution strategy

	//	The @Insert annotation is a special DAO method annotation where you don't
	//	have to provide any SQL. There are also @Delete and @Update annotations
	//	for deleting and updating rows.

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(String_KVP_Entity entity);

	@Query("DELETE FROM kvp_table")
	void deleteAll();

	@Query("SELECT * FROM kvp_table ORDER BY dbKEY ASC")
	LiveData<List<String_KVP_Entity>> getOrderedKeys();

	@Query("SELECT * FROM kvp_table WHERE dbKEY = :val LIMIT 1")
	String_KVP_Entity retrieveEntry(String val);

}
