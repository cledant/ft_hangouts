package fr.cledant.ft_hangouts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.Locale;

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
}
