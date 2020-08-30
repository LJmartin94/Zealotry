package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Sunshine extends AppCompatActivity {

	private EditText mSearchBoxEditText;
	private TextView mUrlDisplayTextView;
	private TextView mSearchResultsTextView;
	//could move this inside the function, no?

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sunshine);

		mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
		mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
	}
}
