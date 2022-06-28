package com.github.LJmartin94.zealotry.MainMenu.Morning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Utils.WebView_Util_Activity;
import com.github.LJmartin94.zealotry.R;

import java.util.Calendar;

public class Morning_menu extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_morning_menu);
	}

	public void launchWakeUp(View v)
	{
		Intent i = new Intent(this, Wake_up.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		disable_button(v);
	}

	public void launchGetUp(View v)
	{
		View getUpMenu = findViewById(R.id.GetUp_Menu);
		if (getUpMenu.getVisibility() == View.GONE)
			getUpMenu.setVisibility(View.VISIBLE);
		else
			getUpMenu.setVisibility(View.GONE);
	}

	public void GetUpWordle(View v)
	{
		Intent i = new Intent(this, WebView_Util_Activity.class);
		i.putExtra("URL_NAME", "https://www.nytimes.com/games/wordle/index.html");
		i.putExtra("ACTIVITY_NAME", "Wordle");
		if (i.resolveActivity(getPackageManager()) != null)
		{
			v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
			v.setBackgroundColor(Color.GRAY);
			v.setClickable(false);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
	}

	public void GetUpXordle(View v)
	{
		Intent i = new Intent(this, WebView_Util_Activity.class);
		i.putExtra("URL_NAME", "https://xordle.xyz/");
		i.putExtra("ACTIVITY_NAME", "Xordle");
		if (i.resolveActivity(getPackageManager()) != null)
		{
			v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
			v.setBackgroundColor(Color.GRAY);
			v.setClickable(false);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
	}

	public void GetUpWorLdle(View v)
	{
		Intent i = new Intent(this, WebView_Util_Activity.class);
		i.putExtra("URL_NAME", "https://worldle.teuteuf.fr/");
		i.putExtra("ACTIVITY_NAME", "WorLdle");
		if (i.resolveActivity(getPackageManager()) != null)
		{
			v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
			v.setBackgroundColor(Color.GRAY);
			v.setClickable(false);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
	}

	public void disableGetUp(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		View m = findViewById(R.id.GetUp_Menu);
		Button b = findViewById(R.id.get_up_button);

		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
		m.setVisibility(View.GONE);
		b.getBackground().setColorFilter(filter);
		b.setClickable(false);
	}

	public void launchShower(View v)
	{
		View getUpMenu = findViewById(R.id.Shower_Menu);
		if (getUpMenu.getVisibility() == View.GONE)
			getUpMenu.setVisibility(View.VISIBLE);
		else
			getUpMenu.setVisibility(View.GONE);

		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 4)
			day = (day == 1) ? 7 : day - 1;
		if (day == Calendar.SATURDAY) //Wash hair on a SATURDAY
		{
			findViewById(R.id.WashHair).setVisibility(View.VISIBLE);
		}
	}

	public void ShowerBody(View v)
	{
		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
	}

	public void ShowerHair(View v)
	{
		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
	}

	public void ShowerShave(View v)
	{
		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
	}

	public void ShowerDone(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		View m = findViewById(R.id.Shower_Menu);
		Button b = findViewById(R.id.shower_button);

		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
		m.setVisibility(View.GONE);
		b.getBackground().setColorFilter(filter);
		b.setClickable(false);
	}

	public void ShowerSkip(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		View m = findViewById(R.id.Shower_Menu);
		Button b = findViewById(R.id.shower_button);

		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
		m.setVisibility(View.GONE);
		b.getBackground().setColorFilter(filter);
		b.setClickable(false);
	}

	public void launchNews(View v)
	{
		String url = "https://tunein.com/embed/player/s96218/";
		View news_player = findViewById(R.id.news_player);
		news_player.setVisibility(View.VISIBLE);
		WebView webView = (WebView) findViewById(R.id.news_player_wv);

		ProgressDialog progDailog;
		progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
		progDailog.setCancelable(false);


		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient()
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

		webView.loadUrl(url);


//		//		<iframe src="https://tunein.com/embed/player/s96218/" style="width:100%; height:100px;" scrolling="no" frameborder="no"></iframe>
//		Uri uri = Uri.parse(url);
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		if (intent.resolveActivity(getPackageManager()) != null)
//		{
//			Toast.makeText(this, "Host: " + uri.getHost(), Toast.LENGTH_LONG).show();
//			v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
//			v.setBackgroundColor(Color.GRAY);
//			v.setClickable(false);
//			startActivity(intent);
//			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//		}


//		Intent i = new Intent();
//		i.setAction(android.content.Intent.ACTION_VIEW);
//		i.setDataAndType()

	}

	public void launchLanguage(View v)
	{
		Intent i = getPackageManager().getLaunchIntentForPackage("com.duolingo");
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		else
		{
			Toast error = Toast.makeText(this, "Please download Duolingo first", Toast.LENGTH_SHORT);
			error.show();
		}
		disable_button(v);
	}

	public void launchMeditation(View v)
	{
		Intent i = new Intent(AlarmClock.ACTION_SET_TIMER);
		i.putExtra(AlarmClock.EXTRA_MINUTES, 10);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		disable_button(v);
	}

	public void disable_button(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		v.getBackground().setColorFilter(filter);
		v.setClickable(false);
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
}