package com.github.LJmartin94.zealotry.MainMenu.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise_Entity
{
	// Member variables
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "ID")
	private String mID;


	// Constructor
	public Exercise_Entity(@NonNull String mID)
	{
		this.mID = mID;
	};


	// Accessors
	public String getID(){return this.mID;}

}
