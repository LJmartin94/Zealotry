package com.github.LJmartin94.zealotry.MainMenu.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//TODO Rename to Exercise_roomEntity with refactor

@Entity(tableName = "exercise_table")
public class ExerciseInfo_entity
{
	// Member variables
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "ID")
	private String mID;


	// Constructor
	public ExerciseInfo_entity(@NonNull String mID)
	{
		this.mID = mID;
	};


	// Accessors
	public String getID(){return this.mID;}

}
