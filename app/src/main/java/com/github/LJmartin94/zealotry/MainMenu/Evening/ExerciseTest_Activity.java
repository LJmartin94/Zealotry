package com.github.LJmartin94.zealotry.MainMenu.Evening;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Data.Exercise_Entity;
import com.github.LJmartin94.zealotry.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ExerciseTest_Activity extends AppCompatActivity
{
	private Exercise_ViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_test);
		RecyclerView recyclerView = findViewById(R.id.exerciseRecyclerView);
		final Exercise_rvAdapter adapter = new Exercise_rvAdapter(new Exercise_rvAdapter.ExerciseDiff());
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		mViewModel = new ViewModelProvider(this).get(Exercise_ViewModel.class);

		mViewModel.getAllExercises().observe(this, exerciseInfo_entities ->
		{
			adapter.submitList(exerciseInfo_entities);
		});

		ActivityResultLauncher<Intent> completedActivityResultLauncher =
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
								Exercise_Entity exercise = new Exercise_Entity(data.getStringExtra(ExerciseTest_NewExercise_Activity.EXTRA_REPLY));
								mViewModel.insert(exercise);
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Empty string not saved.", Toast.LENGTH_LONG).show();
							}
					}
				});

		ExtendedFloatingActionButton fabCompleted = findViewById(R.id.completedfab);
		fabCompleted.setOnClickListener( view ->
		{
			Intent intent = new Intent(ExerciseTest_Activity.this, ExerciseTest_NewExercise_Activity.class);
			completedActivityResultLauncher.launch(intent);
		});
	}
}
