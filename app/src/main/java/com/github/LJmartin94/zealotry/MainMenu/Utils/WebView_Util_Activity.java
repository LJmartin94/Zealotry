package com.github.LJmartin94.zealotry.MainMenu.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.LJmartin94.zealotry.R;

public class WebView_Util_Activity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view__util_);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		String defaultURL = "https://www.google.com/";

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			String titleToSet = extras.getString("ACTIVITY_NAME");
			if (titleToSet != null)
			{
				setTitle(titleToSet);
			}
			String addressURL = extras.getString("URL_NAME");
			if (addressURL != null)
			{
				startWebView(addressURL);
			}
			else
			{
				startWebView(defaultURL);
			}
		}
		else
		{
			startWebView(defaultURL);
		}
	}

	private ProgressDialog progDailog;

	public void startWebView(String addressURL)
	{
		progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
		progDailog.setCancelable(true);

		WebView myWebView = (WebView) findViewById(R.id.webview);


		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setLoadWithOverviewMode(true);
		myWebView.getSettings().setUseWideViewPort(true);
		myWebView.getSettings().setDomStorageEnabled(true);
		myWebView.setWebChromeClient(new WebChromeClient());
		myWebView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				progDailog.show();
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageFinished(WebView view, final String url)
			{
				progDailog.dismiss();
			}
		});

		myWebView.loadUrl(addressURL);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finish()
	{
		super.finish();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
}