package com.github.LJmartin94.zealotry.MainMenu.Morning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

	public void launchWakeUp(View b)
	{
		Intent i = new Intent(this, Wake_up.class);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		TextView t = (TextView)findViewById(R.id.wakeup_time);
		disable_menu(b, true, t, null, true);
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
		disable_menu(b, true, t, m, true);
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
		disable_menu(b, true, t, m, true);
	}

	public void ShowerSkip(View v)
	{
		Button b = findViewById(R.id.shower_button);
		TextView t = findViewById(R.id.shower_time);
		View m = findViewById(R.id.Shower_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
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

		disable_fab(v);

		Button b = findViewById(R.id.news_button);
		TextView t = findViewById(R.id.news_time);
		disable_menu(b, false, t, null, true);
	}

	public void closeRadioPlayer(View v)
	{
		disable_fab(v);

		View news_player = findViewById(R.id.news_player);
		WebView webView = (WebView) findViewById(R.id.news_player_wv);
		View player_buttons = findViewById(R.id.RadioPlayerButtons);

		news_player.setVisibility(View.GONE);
		player_buttons.setVisibility(View.GONE);
		webView.destroy();

		Button b = findViewById(R.id.news_button);
		View m = findViewById(R.id.News_Menu);

		disable_menu(b, true, null, m, false);
	}

	public void changeRadioChannel(View v)
	{
		// TODO: Save user's preferred radio channel in db, prompt them to select one if none is saved yet.
		// TODO: Allow them to change their preferred radio channel here.
		Toast.makeText(this, "Feature not implemented yet lmao", Toast.LENGTH_LONG).show();
	}

	public void NewsSkip(View v)
	{
		disable_fab(v);

		Button b = findViewById(R.id.news_button);
		TextView t = findViewById(R.id.news_time);
		if (t.getText().toString().length() != 0)
			t = null;
		View m = findViewById(R.id.News_Menu);

		disable_menu(b, true, t, m, false);
	}

	public void launchCurtains(View v)
	{
		View newsRadioMenu = findViewById(R.id.Curtains_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void CurtainsDone(View v)
	{
		Button b = findViewById(R.id.curtains_button);
		TextView t = findViewById(R.id.curtains_time);
		View m = findViewById(R.id.Curtains_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void CurtainsSkip(View v)
	{
		Button b = findViewById(R.id.curtains_button);
		TextView t = findViewById(R.id.curtains_time);
		View m = findViewById(R.id.Curtains_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	//TODO: Coffee option should only be visible if user gets up within X time of wake-up.
	public void launchCoffee(View v)
	{
		View newsRadioMenu = findViewById(R.id.Coffee_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void CoffeeDone(View v)
	{
		Button b = findViewById(R.id.coffee_button);
		TextView t = findViewById(R.id.coffee_time);
		View m = findViewById(R.id.Coffee_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	//TODO: skipping coffee should still give reward.
	public void CoffeeSkip(View v)
	{
		Button b = findViewById(R.id.coffee_button);
		TextView t = findViewById(R.id.coffee_time);
		View m = findViewById(R.id.Coffee_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchSmoothie(View v)
	{
		View newsRadioMenu = findViewById(R.id.Smoothie_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void SmoothieDone(View v)
	{
		Button b = findViewById(R.id.smoothie_button);
		TextView t = findViewById(R.id.smoothie_time);
		View m = findViewById(R.id.Smoothie_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void SmoothieSkip(View v)
	{
		Button b = findViewById(R.id.smoothie_button);
		TextView t = findViewById(R.id.smoothie_time);
		View m = findViewById(R.id.Smoothie_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	//TODO: Refactor the way 'Water' works, make it its own activity
	// that remind you to stay hydrated throughout the day and helps you track your progress towards 2L intake.
	public void launchWater(View v)
	{
		View newsRadioMenu = findViewById(R.id.Hydrate_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void HydrateDone(View v)
	{
		Button b = findViewById(R.id.hydrate_button);
		TextView t = findViewById(R.id.hydrate_time);
		View m = findViewById(R.id.Hydrate_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void HydrateSkip(View v)
	{
		Button b = findViewById(R.id.hydrate_button);
		TextView t = findViewById(R.id.hydrate_time);
		View m = findViewById(R.id.Hydrate_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchVitamins(View v)
	{
		View newsRadioMenu = findViewById(R.id.Vitamins_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void VitaminsDone(View v)
	{
		Button b = findViewById(R.id.vitamins_button);
		TextView t = findViewById(R.id.vitamins_time);
		View m = findViewById(R.id.Vitamins_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void VitaminsSkip(View v)
	{
		Button b = findViewById(R.id.vitamins_button);
		TextView t = findViewById(R.id.vitamins_time);
		View m = findViewById(R.id.Vitamins_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchDishes(View v)
	{
		View newsRadioMenu = findViewById(R.id.Dishes_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void LoadDishwasher(View v)
	{
		disable_fab(v);
	}

	public void CleanPans(View v)
	{
		disable_fab(v);
	}

	public void DishesDone(View v)
	{
		Button b = findViewById(R.id.dishes_button);
		TextView t = findViewById(R.id.dishes_time);
		View m = findViewById(R.id.Dishes_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void DishesSkip(View v)
	{
		Button b = findViewById(R.id.dishes_button);
		TextView t = findViewById(R.id.dishes_time);
		View m = findViewById(R.id.Dishes_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchBreakfast(View v)
	{
		View newsRadioMenu = findViewById(R.id.Breakfast_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void BreakfastDone(View v)
	{
		Button b = findViewById(R.id.breakfast_button);
		TextView t = findViewById(R.id.breakfast_time);
		View m = findViewById(R.id.Breakfast_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void BreakfastSkip(View v)
	{
		Button b = findViewById(R.id.breakfast_button);
		TextView t = findViewById(R.id.breakfast_time);
		View m = findViewById(R.id.Breakfast_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchPlants(View v)
	{
		View newsRadioMenu = findViewById(R.id.Plants_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void PlantsDone(View v)
	{
		Button b = findViewById(R.id.plants_button);
		TextView t = findViewById(R.id.plants_time);
		View m = findViewById(R.id.Plants_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void PlantsSkip(View v)
	{
		Button b = findViewById(R.id.plants_button);
		TextView t = findViewById(R.id.plants_time);
		View m = findViewById(R.id.Plants_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchMeditation(View v)
	{
		View newsRadioMenu = findViewById(R.id.Meditation_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void MeditationStart(View v)
	{
		Intent i = new Intent(AlarmClock.ACTION_SET_TIMER);
		i.putExtra(AlarmClock.EXTRA_MINUTES, 10);
		if (i.resolveActivity(getPackageManager()) != null)
		{
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		}
		disable_fab(v);
	}

	public void MeditationDone(View v)
	{
		Button b = findViewById(R.id.meditation_button);
		TextView t = findViewById(R.id.meditation_time);
		View m = findViewById(R.id.Meditation_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void MeditationSkip(View v)
	{
		Button b = findViewById(R.id.meditation_button);
		TextView t = findViewById(R.id.meditation_time);
		View m = findViewById(R.id.Meditation_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchLanguage(View v)
	{
		View newsRadioMenu = findViewById(R.id.Language_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void LanguageStart(View v)
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
		disable_fab(v);
	}

	public void LanguageDone(View v)
	{
		Button b = findViewById(R.id.language_button);
		TextView t = findViewById(R.id.language_time);
		View m = findViewById(R.id.Language_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void LanguageSkip(View v)
	{
		Button b = findViewById(R.id.language_button);
		TextView t = findViewById(R.id.language_time);
		View m = findViewById(R.id.Language_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchTeeth(View v)
	{
		View newsRadioMenu = findViewById(R.id.Teeth_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void TeethBrushed(View v)
	{
		disable_fab(v);
	}

	public void Flossed(View v)
	{
		disable_fab(v);
	}

	//TODO: Add timer in FAB for mouthwash?
	public void MouthWashed(View v)
	{
		disable_fab(v);
	}

	public void TeethDone(View v)
	{
		Button b = findViewById(R.id.teeth_button);
		TextView t = findViewById(R.id.teeth_time);
		View m = findViewById(R.id.Teeth_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void TeethSkip(View v)
	{
		Button b = findViewById(R.id.teeth_button);
		TextView t = findViewById(R.id.teeth_time);
		View m = findViewById(R.id.Teeth_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchCosmeticism(View v)
	{
		View newsRadioMenu = findViewById(R.id.Cosmeticism_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void CologneApplied(View v)
	{
		disable_fab(v);
	}

	public void BodySprayed(View v)
	{
		disable_fab(v);
	}

	public void SkinCared(View v)
	{
		disable_fab(v);
	}

	public void HairGelled(View v)
	{
		disable_fab(v);
	}

	public void CosmeticismDone(View v)
	{
		Button b = findViewById(R.id.cosmeticism_button);
		TextView t = findViewById(R.id.cosmeticism_time);
		View m = findViewById(R.id.Cosmeticism_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void CosmeticismSkip(View v)
	{
		Button b = findViewById(R.id.cosmeticism_button);
		TextView t = findViewById(R.id.cosmeticism_time);
		View m = findViewById(R.id.Cosmeticism_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchPack(View v)
	{
		View newsRadioMenu = findViewById(R.id.Pack_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void HouseKeys(View v)
	{
		disable_fab(v);
	}

	public void BikeKeys(View v)
	{
		disable_fab(v);
	}

	public void WorkKeys(View v)
	{
		disable_fab(v);
	}

	public void Wallet(View v)
	{
		disable_fab(v);
	}

	public void Phone(View v)
	{
		disable_fab(v);
	}

	public void PhoneCharger(View v)
	{
		disable_fab(v);
	}

	public void Headphones(View v)
	{
		disable_fab(v);
	}

	public void Laptop(View v)
	{
		disable_fab(v);
	}

	public void LaptopCharger(View v)
	{
		disable_fab(v);
	}

	public void WaterBottle(View v)
	{
		disable_fab(v);
	}

	public void Lunch(View v)
	{
		disable_fab(v);
	}

	public void PackDone(View v)
	{
		Button b = findViewById(R.id.pack_button);
		TextView t = findViewById(R.id.pack_time);
		View m = findViewById(R.id.Pack_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void PackSkip(View v)
	{
		Button b = findViewById(R.id.pack_button);
		TextView t = findViewById(R.id.pack_time);
		View m = findViewById(R.id.Pack_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchMake_bed(View v)
	{
		View newsRadioMenu = findViewById(R.id.make_bed_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void make_bed_Done(View v)
	{
		Button b = findViewById(R.id.make_bed_button);
		TextView t = findViewById(R.id.make_bed_time);
		View m = findViewById(R.id.make_bed_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void make_bed_Skip(View v)
	{
		Button b = findViewById(R.id.make_bed_button);
		TextView t = findViewById(R.id.make_bed_time);
		View m = findViewById(R.id.make_bed_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void launchBins(View v)
	{
		View newsRadioMenu = findViewById(R.id.bin_Menu);
		if (newsRadioMenu.getVisibility() == View.GONE)
			newsRadioMenu.setVisibility(View.VISIBLE);
		else
			newsRadioMenu.setVisibility(View.GONE);
	}

	public void generalRubbish(View v)
	{
		disable_fab(v);
	}

	public void paperRecycling(View v)
	{
		disable_fab(v);
	}

	public void glassRecycling(View v)
	{
		disable_fab(v);
	}

	public void plasticRecycling(View v)
	{
		disable_fab(v);
	}

	public void organicRecycling(View v)
	{
		disable_fab(v);
	}

	// TODO: Give EXP iff generalRubbish hasn't been taken out. Disable all other fabs.
	public void noBins(View v)
	{
		disable_fab(v);
	}

	public void binDone(View v)
	{
		Button b = findViewById(R.id.bin_button);
		TextView t = findViewById(R.id.bin_time);
		View m = findViewById(R.id.bin_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, true);
	}

	public void binSkip(View v)
	{
		Button b = findViewById(R.id.bin_button);
		TextView t = findViewById(R.id.bin_time);
		View m = findViewById(R.id.bin_Menu);

		disable_fab(v);
		disable_menu(b, true, t, m, false);
	}

	public void disable_fab(View v)
	{
		v.setBackgroundTintBlendMode(BlendMode.MULTIPLY);
		v.setBackgroundColor(Color.GRAY);
		v.setClickable(false);
	}

	public void disable_menu(View b, Boolean disable_button, TextView t, View m, Boolean timed)
	{
		if (t != null)
		{
			Calendar calendar = Calendar.getInstance();
			long timeLong = calendar.getTimeInMillis();
			SimpleDateFormat hmm = new SimpleDateFormat("H:mm");
			String timeString = hmm.format(timeLong);
			t.setText(timeString);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setTitle("Good Morning - ETD: " + calculate_ETD(timeLong));
		}

		ColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		b.getBackground().setColorFilter(filter);
		if (disable_button)
			b.setClickable(false);

		if (m != null)
		{
			m.setVisibility(View.GONE);
		}
	}

	public String calculate_ETD(long timeLong)
	{
		long ret = 0;

		if ( ( (TextView)findViewById(R.id.wakeup_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.get_up_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.shower_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.news_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.curtains_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.coffee_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.smoothie_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.hydrate_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.vitamins_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.dishes_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.breakfast_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.plants_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.meditation_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.language_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.teeth_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.cosmeticism_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.pack_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.make_bed_time) ).getText().equals("") )
			ret++;
		if ( ( (TextView)findViewById(R.id.bin_time) ).getText().equals("") )
			ret++;
		return (String.valueOf(ret));
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