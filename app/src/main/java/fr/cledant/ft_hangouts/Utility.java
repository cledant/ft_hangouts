package fr.cledant.ft_hangouts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.view.WindowManager;

import java.util.Locale;

public class Utility
{
	final public static String DEFAULT_IMG = "renko.png";

	public static void setThemePref(Context context, int theme)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putInt(context.getString(R.string.prefs_theme_key), theme).apply();
	}

	public static int getThemePref(Context context)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getInt(context.getString(R.string.prefs_theme_key), -1);
	}

	public static String formatPhoneNumber(String raw_number)
	{
		String formated_number = null;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			formated_number = PhoneNumberUtils.formatNumber(raw_number, Locale.getDefault().getCountry());
		else
			formated_number = PhoneNumberUtils.formatNumber(raw_number);
		if (formated_number == null)
			return raw_number;
		return formated_number;
	}

	public static void setDummyTrigger(Context context, boolean val)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit().putBoolean("DummyDB", val).apply();
	}

	public static boolean getDummyTrigger(Context context)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean("DummyDB", false);
	}

	public static void updateMessageStatus(long id, long type, Context context)
	{
		Message msg = null;
		DAOMessage dao = new DAOMessage(context);

		msg = dao.select(id);
		msg.setStatus(type);
		dao.modify(msg);
	}

	public static int getThemeColor(Context context)
	{
		switch (Utility.getThemePref(context.getApplicationContext()))
		{
			case BaseActivity.THEME_BLUE:
				return context.getResources().getColor(R.color.colorPrimaryDark);
			case BaseActivity.THEME_RED:
				return context.getResources().getColor(R.color.colorSecondaryDark);
			case BaseActivity.THEME_YELLOW:
				return context.getResources().getColor(R.color.colorTertiaryDark);
		}
		return 0;
	}
}
