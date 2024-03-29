package com.github.LJmartin94.zealotry.MainMenu.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kvp_table")
public class String_KVP_Entity
{
	// Member variables
	@PrimaryKey
	@NonNull
	@ColumnInfo(name = "dbKEY")
	private String key;

	@NonNull
	@ColumnInfo(name = "dbVALUE")
	private String value;


	// Constructor
	public String_KVP_Entity(@NonNull String key, @NonNull String value)
	{
		this.key = key;
		this.value = value;
	};

	// Accessors
	public String getKey(){return this.key;}
	public String getValue(){return this.value;}

}
