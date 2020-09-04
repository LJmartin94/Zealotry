package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sunshine, menu);
		return true;
	}

	// Override onOptionsItemSelected
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Get the ID of the item that was selected
		int itemThatWasClickedId = item.getItemId();
		// If the item's ID is R.id.action_search,
		if (itemThatWasClickedId == R.id.action_search)
		{
			Context context = Sunshine.this;
			// show a Toast
			String textToShow = "Search clicked";
			Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
			// and return true to tell Android that you've handled this menu click.
			return true;
		}
		// If you do NOT handle the menu click, return super.onOptionsItemSelected to let Android handle the menu click
		return super.onOptionsItemSelected(item);
	}
}
