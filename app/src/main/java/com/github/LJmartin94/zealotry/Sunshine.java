package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;

public class Sunshine extends AppCompatActivity {

	private EditText mSearchBoxEditText;
	private TextView mUrlDisplayTextView;
	private TextView mSearchResultsTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sunshine);

		mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
		mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
	}

	/**
	 * This method retrieves the search text from the EditText, constructs
	 * the URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
	 * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
	 * our (not yet created) {@link GithubQueryTask}
	 */
	private void makeGithubSearchQuery() {
		String githubQuery = mSearchBoxEditText.getText().toString();
		URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
		mUrlDisplayTextView.setText(githubSearchUrl.toString());
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
		int itemThatWasClickedId = item.getItemId();
		if (itemThatWasClickedId == R.id.action_search)
		{
			Context context = Sunshine.this;
			// String textToShow = "Search clicked";
			// Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
			makeGithubSearchQuery();
			return true;
		}
		// If you do NOT handle the menu click, return super.onOptionsItemSelected to let Android handle the menu click
		return super.onOptionsItemSelected(item);
	}
}
