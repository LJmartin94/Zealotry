package com.github.LJmartin94.zealotry.MainMenu.Morning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.LJmartin94.zealotry.MainMenu.Utils.WebView_Util_Activity;
import com.github.LJmartin94.zealotry.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Morning_menu extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_morning_menu);
		View dummyButton = findViewById(R.id.ReloadPlayer);
		loadRadioPlayer(dummyButton);
	}

	public void launchWakeUp(View v)
	{
		Intent i = new Intent(this, Wake_up.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		TextView timeText = (TextView)findViewById(R.id.wakeup_time);
		disable_menu(v, timeText, null);
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
			disable_fab(v);
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
			disable_fab(v);
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
			disable_fab(v);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
	}

	public void disableGetUp(View v)
	{
		disable_fab(v);

		View m = findViewById(R.id.GetUp_Menu);
		Button b = findViewById(R.id.get_up_button);
		TextView t = (TextView)findViewById(R.id.get_up_time);
		disable_menu(b, t, m);
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
		disable_fab(v);
	}

	public void ShowerHair(View v)
	{
		disable_fab(v);
	}

	public void ShowerShave(View v)
	{
		disable_fab(v);
	}

	public void ShowerDone(View v)
	{
		Button b = findViewById(R.id.shower_button);
		TextView t = findViewById(R.id.shower_time);
		View m = findViewById(R.id.Shower_Menu);

		disable_fab(v);
		disable_menu(b, t, m);
	}

	public void ShowerSkip(View v)
	{
		Button b = findViewById(R.id.shower_button);
		TextView t = findViewById(R.id.shower_time);
		View m = findViewById(R.id.Shower_Menu);

		disable_fab(v);
		disable_menu(b, t, m);
	}

	public void launchNews(View v)
	{
		View newsRadioMenu = findViewById(R.id.News_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void loadRadioPlayer(View v)
	{
		//TODO Let users save their own preferred radio station from radio.net.
		String unchanged_player =
				"<script>" +
				"(function(d, s)" +
				"{" +
					"if(!window.rel)" +
					"{" +
						"s = d.createElement(\"script\");" +
						"s.type = \"text/javascript\";" +
						"s.async = true;" +
						"s.id = \"radio-de-embedded\";" +
						"s.src = \"https://www.radio.net/inc/microsite/js/full.js\";" +
						"d.getElementsByTagName(\"head\")[0].appendChild(s);" +
						"window.rel = true;" +
					"}" +
				"}" +
				"(document));" +
				"</script>" +
				"<div class=\"ng-app-embedded\"><div ui-view class=\"microsite embedded-radio-player\" data-playerwidth=\"340px\" data-playertype=\"web_embedded\" data-playstation=\"bbcradioscotland\" data-autoplay=\"true\" data-apikey=\"df04ff67dd3339a6fc19c9b8be164d5b5245ae93\"></div></div><noscript><a href=\"https://www.radio.net/s/bbcradioscotland\" target=\"_blank\">BBC Radio Scotland on radio.net</a></noscript>";

		//TODO make sure use input string has 'data-playerwidth="100%"' not any other value set to that key.
		String width_adjusted_player =
				"<script>" +
				"(function(d, s)" +
				"{" +
				"if(!window.rel)" +
				"{" +
				"s = d.createElement(\"script\");" +
				"s.type = \"text/javascript\";" +
				"s.async = true;" +
				"s.id = \"radio-de-embedded\";" +
				"s.src = \"https://www.radio.net/inc/microsite/js/full.js\";" +
				"d.getElementsByTagName(\"head\")[0].appendChild(s);" +
				"window.rel = true;" +
				"}" +
				"}" +
				"(document));" +
				"</script> " +
				"<div class=\"ng-app-embedded\">" +
				"<div ui-view class=\"microsite embedded-radio-player\" " +
				"data-playerwidth=\"100%\" " +
				"data-playertype=\"web_embedded\" " +
				"data-playstation=\"bbcradioscotland\" " +
				"data-autoplay=\"true\" " +
				"data-apikey=\"df04ff67dd3339a6fc19c9b8be164d5b5245ae93\">" +
				"</div>" +
				"</div>" +
				"<noscript>" +
				"<a href=\"https://www.radio.net/s/bbcradioscotland\" target=\"_blank\">" +
				"BBC Radio Scotland on radio.net</a>" +
				"</noscript>";

		WebView webView = (WebView) findViewById(R.id.news_player_wv);

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setLoadWithOverviewMode(false);
		settings.setUseWideViewPort(false);
		settings.setDomStorageEnabled(true);
		settings.setSupportZoom(true);

		String data = width_adjusted_player;
		webView.loadDataWithBaseURL("https://www.radio.net/inc/microsite/js/full.js", data, "text/html", "utf-8", null);
		webView.invalidate();
		webView.requestLayout();
	}

	public void openRadioPlayer(View v)
	{
		View news_player = findViewById(R.id.news_player);
		WebView webView = (WebView) findViewById(R.id.news_player_wv);
		View player_buttons = findViewById(R.id.RadioPlayerButtons);
		player_buttons.setVisibility(View.VISIBLE);

		news_player.invalidate();
		news_player.requestLayout();
		webView.invalidate();
		webView.requestLayout();

		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);

		Button b = findViewById(R.id.news_button);
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		b.getBackground().setColorFilter(filter);
	}

	public void closeRadioPlayer(View v)
	{
		View news_player = findViewById(R.id.news_player);
		WebView webView = (WebView) findViewById(R.id.news_player_wv);
		View player_buttons = findViewById(R.id.RadioPlayerButtons);

		news_player.setVisibility(View.GONE);
		player_buttons.setVisibility(View.GONE);
		webView.destroy();

		Button b = findViewById(R.id.news_button);
		b.setClickable(false);

		View m = findViewById(R.id.News_Menu);
		m.setVisibility(View.GONE);
	}

	public void changeRadioChannel(View v)
	{
		// TODO: Save user's preferred radio channel in db, prompt them to select one if none is saved yet.
		// TODO: Allow them to change their preferred radio channel here.
		Toast.makeText(this, "Feature not implemented yet lmao", Toast.LENGTH_LONG).show();
	}

	public void NewsSkip(View v)
	{
		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		View m = findViewById(R.id.News_Menu);
		Button b = findViewById(R.id.news_button);

		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
		m.setVisibility(View.GONE);
		b.getBackground().setColorFilter(filter);
		b.setClickable(false);
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

	public void disable_fab(View v)
	{
		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
	}

	public void disable_menu(View b, TextView t, View m)
	{
		Calendar calendar = Calendar.getInstance();
		long timeLong = calendar.getTimeInMillis();
		SimpleDateFormat hmm = new SimpleDateFormat("H:mm");
		String timeString = hmm.format(timeLong);
		t.setText(timeString);

		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		b.getBackground().setColorFilter(filter);
		b.setClickable(false);

		if (m != null)
		{
			m.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				closeRadioPlayer(findViewById(R.id.ClosePlayer));
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}