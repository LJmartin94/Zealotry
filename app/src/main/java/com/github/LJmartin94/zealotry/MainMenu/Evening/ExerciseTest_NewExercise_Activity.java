package com.github.LJmartin94.zealotry.MainMenu.Evening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.github.LJmartin94.zealotry.R;

public class ExerciseTest_NewExercise_Activity extends AppCompatActivity
{
	public static final String EXTRA_REPLY = "com.github.LJmartin94.zealotry.MainMenu.Evening.REPLY";
	private EditText mEditExerciseView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_test__new_exercise_);
        mEditExerciseView = findViewById(R.id.edit_exercise);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener( view ->
		{
			Intent replyIntent = new Intent();
			if (TextUtils.isEmpty(mEditExerciseView.getText()))
			{
				setResult(RESULT_CANCELED, replyIntent);
			}
			else
			{
				String exercise = mEditExerciseView.getText().toString();
				replyIntent.putExtra(EXTRA_REPLY, exercise);
				setResult(RESULT_OK, replyIntent);
			}
			finish();
		});
    }
}