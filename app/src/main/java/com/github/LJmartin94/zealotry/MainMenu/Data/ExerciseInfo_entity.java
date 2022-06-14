package com.github.LJmartin94.zealotry.MainMenu.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class ExerciseInfo_entity
{
	// Member variables
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "ID")
	private String mID;


	// Constructor
	public ExerciseInfo_entity(@NonNull String ID)
	{
		this.mID = ID;
	};


	// Accessors
	public String getID(){return this.mID;}

}
