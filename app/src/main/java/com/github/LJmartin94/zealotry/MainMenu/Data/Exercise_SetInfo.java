package com.github.LJmartin94.zealotry.MainMenu.Data;

class exSets
{
	int 		mRest_sec;

	int 		mNumber_of_sets;
	String[] 	mDailySets;

	public void exSets(int rest_sec, int number_of_sets, String[] dailySets)
	{
		mRest_sec = rest_sec;
		mNumber_of_sets = number_of_sets;
		mDailySets = dailySets;
	}
}

class exDay
{
	int			promotionThreshold;
	boolean 	promotionDay;

	int			heavyThreshold;
	exSets		heavy;
	int			mediumThreshold;
	exSets		medium;
	int			lightThreshold;
	exSets		light;
}

class exWeek
{
	boolean 	promotable;
	int			promotionThreshold;

	int			num_of_days;
	exDay[]		weekdays = new exDay[num_of_days];
}

class exType
{
	int			num_of_weeks;
	exWeek[]	programme = new exWeek[num_of_weeks];
}

public class Exercise_SetInfo
{
	exSets

}
