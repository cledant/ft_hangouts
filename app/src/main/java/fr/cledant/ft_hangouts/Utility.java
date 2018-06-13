package fr.cledant.ft_hangouts;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utility
{
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
}
