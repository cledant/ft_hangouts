package fr.cledant.ft_hangouts;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class BaseActivity extends AppCompatActivity
{
	public final static int THEME_BLUE = 0;
	public final static int THEME_RED = 1;
	public final static int THEME_YELLOW = 2;
	public int userTheme = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		updateTheme();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (userTheme != Utility.getThemePref(getApplicationContext()))
		{
			updateTheme();
			recreate();
		}
	}

	public void updateTheme()
	{
		userTheme = Utility.getThemePref(getApplicationContext());
		switch (userTheme)
		{
			case THEME_BLUE:
			{
				setTheme(R.style.AppTheme_Blue_NoActionBar);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
				}
				break;
			}
			case THEME_RED:
			{
				setTheme(R.style.AppTheme_Red_NoActionBar);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					getWindow().setStatusBarColor(getResources().getColor(R.color.colorSecondaryDark));
				}
				break;
			}
			case THEME_YELLOW:
			{
				setTheme(R.style.AppTheme_Yellow_NoActionBar);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					getWindow().setStatusBarColor(getResources().getColor(R.color.colorTertiaryDark));
				}
				break;
			}
		}
	}
}
