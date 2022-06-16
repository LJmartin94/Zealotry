package com.github.LJmartin94.zealotry.MainMenu.Evening;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.github.LJmartin94.zealotry.R;

public class ExerciseTest_Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_test);
		RecyclerView recyclerView = findViewById(R.id.exerciseRecyclerView);
		final Exercise_rvAdapter adapter = new Exercise_rvAdapter(new Exercise_rvAdapter.ExerciseDiff());
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}
}
