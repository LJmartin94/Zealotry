package com.github.LJmartin94.zealotry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class GitHubSearch extends AppCompatActivity
{
	private EditText mSearchBoxEditText;
	private TextView mUrlDisplayTextView;
	private TextView mSearchResultsTextView;
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_githubsearch);

		mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
		mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
		mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
	}

	/**
	 * This method retrieves the search text from the EditText, constructs
	 * the URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
	 * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
	 * our (not yet created) {@link GithubQueryTask}
	 */
	private void makeGithubSearchQuery()
	{
		String githubQuery = mSearchBoxEditText.getText().toString();
		URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
		mUrlDisplayTextView.setText(githubSearchUrl.toString());
		new GithubQueryTask().execute(githubSearchUrl);
	}

	private void showJsonDataView()
	{
		mSearchResultsTextView.setVisibility(View.VISIBLE);
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
	}

	private void showErrorMessage()
	{
		mSearchResultsTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}

	public class GithubQueryTask extends AsyncTask<URL, Void, String>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadingIndicator.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(URL... params)
		{
			URL searchUrl = params[0];
			String githubSearchResults = null;
			try
				{ githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);}
			catch (IOException e)
				{ e.printStackTrace();}
			return githubSearchResults;
		}

		@Override
		protected void onPostExecute(String githubSearchResults)
		{
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			if (githubSearchResults != null && !githubSearchResults.equals(""))
			{
				showJsonDataView();
				mSearchResultsTextView.setText(githubSearchResults);
			}
			else
				{ showErrorMessage();}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.githubsearch, menu);
		return true;
	}

	// Override onOptionsItemSelected
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int itemThatWasClickedId = item.getItemId();
		if (itemThatWasClickedId == R.id.action_search)
		{
			Context context = GitHubSearch.this;
			// String textToShow = "Search clicked";
			// Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
			makeGithubSearchQuery();
			return true;
		}
		// If you do NOT handle the menu click, return super.onOptionsItemSelected to let Android handle the menu click
		return super.onOptionsItemSelected(item);
	}
}
