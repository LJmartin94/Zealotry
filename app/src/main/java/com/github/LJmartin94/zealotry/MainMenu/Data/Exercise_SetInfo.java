package com.github.LJmartin94.zealotry.MainMenu.Data;

class exSets
{
	String		mXID;
	int 		mRest_sec;

	int 		mNumber_of_sets;
	String[] 	mDailySets;


	public exSets(String exercise_id, int rest_sec, int number_of_sets, String[] dailySets)
	{
		mXID = exercise_id;
		mRest_sec = rest_sec;
		mNumber_of_sets = number_of_sets;
		mDailySets = dailySets;
	}
}

class exDay
{
	int			mNumModes;
	exSets[]	mAllModes;

	public exDay(int number_of_modes, exSets[] all_sets)
	{
		mNumModes = number_of_modes;
		mAllModes = all_sets;
	}
}

class exWeek
{
	String		mExercise_type;
	String		mExercise_info;
	String		mExercise_link;

	int			mNum_of_days;
	int[]		mMin_threshold_value;
	exDay[]		mWeekdays;

	public exWeek(String exercise_type, String exercise_info, String exercise_link,
					   int num_of_days, int[] min_threshold_value, exDay[] weekdays)
	{
		mExercise_type = exercise_type;
		mExercise_info = exercise_info;
		mExercise_link = exercise_link;
		mNum_of_days = num_of_days;
		mMin_threshold_value = min_threshold_value;
		mWeekdays = weekdays;
	}
}

class exType
{
	String		mTarget; //Arms, Core, Legs
	int			mNum_of_weeks;
	exWeek[]	mProgramme;

	public exType(String target, int num_of_weeks, exWeek[] programme)
	{
		mTarget = target;
		mNum_of_weeks = num_of_weeks;
		mProgramme = programme;
	}
}


//TODO Rename Exercise planning.
public class Exercise_SetInfo
{
	public exType createAProgramme()
	{
		String		type = "Push ups";
		String		info =	"Place your hands firmly on the ground, directly under shoulders.\n" +
							"Flatten your back so your entire body is straight and slowly lower your body\n" +
							"Draw shoulder blades back and down, keeping elbows tucked close to your body\n" +
							"Exhale as you push back to the starting position.\n";
		String		link = "";

		//WEEK 0
			//Day1
			String[] sets = {"Try and do as many good form push-ups as you can!"};
			exSets A00aLMH = new exSets("A00aHML", 0, 1, sets);
			exSets[] all_modes = {A00aLMH};
			exDay A00a = new exDay(1, all_modes);

			//Week
			exDay[] all_weekdays = {A00a};
			int[] thresholdVals = {0};
			exWeek week0 = new exWeek(type, info, link, 1, thresholdVals, all_weekdays);

		//WEEK 1
			//Day1
			sets = new String[]{"2", "3", "2", "2", "3+"};
			exSets light = new exSets("A01aL", 60, 5, sets);
			sets = new String[]{"6", "6", "4", "4", "5+"};
			exSets medium = new exSets("A01aM", 60, 5, sets);
			sets = new String[]{"10", "12", "7", "7", "9+"};
			exSets heavy = new exSets("A01aH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A01a = new exDay(3, all_modes);

			//Day2
			sets = new String[]{"3", "4", "2", "3", "4+"};
			light = new exSets("A01bL", 60, 5, sets);
			sets = new String[]{"6", "8", "6", "6", "7+"};
			medium = new exSets("A01bM", 60, 5, sets);
			sets = new String[]{"10", "12", "8", "8", "12+"};
			heavy = new exSets("A01bH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A01b = new exDay(3, all_modes);

			//Day3
			sets = new String[]{"4", "5", "4", "4", "5+"};
			light = new exSets("A01cL", 60, 5, sets);
			sets = new String[]{"8", "10", "7", "7", "10+"};
			medium = new exSets("A01cM", 60, 5, sets);
			sets = new String[]{"11", "15", "9", "9", "13+"};
			heavy = new exSets("A01cH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A01c = new exDay(3, all_modes);

			//Day4
			sets = new String[]{"Try and do as many good form push-ups as you can!"};
			light = new exSets("A01dLMH", 0, 1, sets);
			all_modes = new exSets[]{light};
			exDay A01d = new exDay(1, all_modes);

			//Week
			all_weekdays = new exDay[]{A01a, A01b, A01c, A01d};
			thresholdVals = new int[]{0, 6, 11};
			exWeek week1 = new exWeek(type, info, link, 4, thresholdVals, all_weekdays);

		//WEEK 2
			//Day1(a)
			sets = new String[]{"4", "6", "4", "4", "6+"};
			light = new exSets("A02aL", 60, 5, sets);
			sets = new String[]{"9", "11", "8", "8", "11+"};
			medium = new exSets("A02aM", 60, 5, sets);
			sets = new String[]{"14", "14", "10", "10", "15+"};
			heavy = new exSets("A02aH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A02a = new exDay(3, all_modes);

			//Day2(b)
			sets = new String[]{"5", "6", "4", "4", "7+"};
			light = new exSets("A02bL", 90, 5, sets);
			sets = new String[]{"10", "12", "9", "9", "13+"};
			medium = new exSets("A02bM", 90, 5, sets);
			sets = new String[]{"14", "16", "12", "12", "17+"};
			heavy = new exSets("A02bH", 90, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A02b = new exDay(3, all_modes);

			//Day3(c)
			sets = new String[]{"5", "7", "5", "5", "8+"};
			light = new exSets("A02cL", 120, 5, sets);
			sets = new String[]{"12", "13", "10", "10", "15+"};
			medium = new exSets("A02cM", 120, 5, sets);
			sets = new String[]{"16", "17", "14", "14", "20+"};
			heavy = new exSets("A02cH", 120, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A02c = new exDay(3, all_modes);

			//Day4(d)
			sets = new String[]{"Try and do as many good form push-ups as you can!\nTo challenge yourself, see if you can do at least 16+"};
			light = new exSets("A02dLMH", 0, 1, sets);
			all_modes = new exSets[]{light};
			exDay A02d = new exDay(1, all_modes);

			//Week
			all_weekdays = new exDay[]{A02a, A02b, A02c, A02d};
			thresholdVals = new int[]{0, 6, 11};
			exWeek week2 = new exWeek(type, info, link, 4, thresholdVals, all_weekdays);

		//WEEK 3
			//Day1(a)
			sets = new String[]{"10", "12", "7", "7", "9+"};
			light = new exSets("A03aL", 60, 5, sets);
			sets = new String[]{"12", "17", "13", "13", "17+"};
			medium = new exSets("A03aM", 60, 5, sets);
			sets = new String[]{"14", "18", "14", "14", "20+"};
			heavy = new exSets("A03aH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A03a = new exDay(3, all_modes);

			//Day2(b)
			sets = new String[]{"10", "12", "8", "8", "12+"};
			light = new exSets("A03bL", 90, 5, sets);
			sets = new String[]{"14", "19", "14", "14", "19+"};
			medium = new exSets("A03bM", 90, 5, sets);
			sets = new String[]{"20", "25", "15", "15", "25+"};
			heavy = new exSets("A03bH", 90, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A03b = new exDay(3, all_modes);

			//Day3(c)
			sets = new String[]{"11", "13", "9", "9", "13+"};
			light = new exSets("A03cL", 120, 5, sets);
			sets = new String[]{"16", "21", "15", "15", "21+"};
			medium = new exSets("A03cM", 120, 5, sets);
			sets = new String[]{"22", "30", "20", "20", "28+"};
			heavy = new exSets("A03cH", 120, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A03c = new exDay(3, all_modes);

			//Day4(d)
			sets = new String[]{"Try and do as many good form push-ups as you can!\nTo challenge yourself, see if you can do at least 16+"};
			light = new exSets("A03dLMH", 0, 1, sets);
			all_modes = new exSets[]{light};
			exDay A03d = new exDay(1, all_modes);

			//Week
			all_weekdays = new exDay[]{A03a, A03b, A03c, A03d};
			thresholdVals = new int[]{16, 21, 26};
			exWeek week3 = new exWeek(type, info, link, 4, thresholdVals, all_weekdays);

		//WEEK 4
			//Day1(a)
			sets = new String[]{"12", "14", "11", "10", "16+"};
			light = new exSets("A04aL", 60, 5, sets);
			sets = new String[]{"18", "22", "16", "16", "25+"};
			medium = new exSets("A04aM", 60, 5, sets);
			sets = new String[]{"21", "25", "21", "21", "32+"};
			heavy = new exSets("A04aH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A04a = new exDay(3, all_modes);

			//Day2(b)
			sets = new String[]{"14", "16", "12", "12", "18+"};
			light = new exSets("A04bL", 90, 5, sets);
			sets = new String[]{"20", "25", "20", "20", "28+"};
			medium = new exSets("A04bM", 90, 5, sets);
			sets = new String[]{"25", "29", "25", "25", "36+"};
			heavy = new exSets("A04bH", 90, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A04b = new exDay(3, all_modes);

			//Day3(c)
			sets = new String[]{"16", "18", "13", "13", "20+"};
			light = new exSets("A04cL", 120, 5, sets);
			sets = new String[]{"23", "28", "23", "23", "33+"};
			medium = new exSets("A04cM", 120, 5, sets);
			sets = new String[]{"29", "33", "29", "29", "40+"};
			heavy = new exSets("A04cH", 120, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A04c = new exDay(3, all_modes);

			//Day4(d)
			sets = new String[]{"Try and do as many good form push-ups as you can!\nTo challenge yourself, see if you can do at least 31+"};
			light = new exSets("A04dLMH", 0, 1, sets);
			all_modes = new exSets[]{light};
			exDay A04d = new exDay(1, all_modes);

			//Week
			all_weekdays = new exDay[]{A04a, A04b, A04c, A04d};
			thresholdVals = new int[]{16, 21, 26};
			exWeek week4 = new exWeek(type, info, link, 4, thresholdVals, all_weekdays);

		//WEEK 5
			//Day1(a)
			sets = new String[]{"17", "19", "15", "15", "20+"};
			light = new exSets("A05aL", 60, 5, sets);
			sets = new String[]{"28", "35", "25", "22", "35+"};
			medium = new exSets("A05aM", 60, 5, sets);
			sets = new String[]{"36", "40", "30", "24", "40+"};
			heavy = new exSets("A05aH", 60, 5, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A05a = new exDay(3, all_modes);

			//Day2(b)
			sets = new String[]{"10", "10", "13", "13", "10", "10", "9", "25+"};
			light = new exSets("A05bL", 45, 8, sets);
			sets = new String[]{"18", "18", "20", "20", "14", "14", "16", "40+"};
			medium = new exSets("A05bM", 45, 8, sets);
			sets = new String[]{"19", "19", "22", "22", "18", "18", "22", "45+"};
			heavy = new exSets("A05bH", 45, 8, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A05b = new exDay(3, all_modes);

			//Day3(c)
			sets = new String[]{"13", "13", "15", "15", "12", "12", "10", "30+"};
			light = new exSets("A05cL", 45, 8, sets);
			sets = new String[]{"18", "18", "20", "20", "17", "17", "20", "45+"};
			medium = new exSets("A05cM", 45, 8, sets);
			sets = new String[]{"20", "20", "24", "24", "20", "20", "22", "50+"};
			heavy = new exSets("A05cH", 45, 8, sets);
			all_modes = new exSets[]{light, medium, heavy};
			exDay A05c = new exDay(3, all_modes);

			//Day4(d)
			sets = new String[]{"Try and do as many good form push-ups as you can!\nTo challenge yourself, see if you can do at least 46+"};
			light = new exSets("A05dLMH", 0, 1, sets);
			all_modes = new exSets[]{light};
			exDay A05d = new exDay(1, all_modes);

			//Week
			all_weekdays = new exDay[]{A05a, A05b, A05c, A05d};
			thresholdVals = new int[]{31, 36, 41};
			exWeek week5 = new exWeek(type, info, link, 4, thresholdVals, all_weekdays);


		// RETURN VAL
		exWeek[]	all_training_weeks = {week0, week1, week2, week3, week4, week5};
		exType		arms = new exType("Arms", 8, all_training_weeks);
		return (arms);
	}
	public exType createCProgramme()
	{

	}
	public exType createLProgramme()
	{

	}
}
