package fr.cledant.ft_hangouts;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class BaseActivity extends AppCompatActivity
{
	private final static int THEME_BLUE = 0;
	private final static int THEME_RED = 1;
	private final static int THEME_YELLOW = 2;

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
		updateTheme();
	}

	public void updateTheme()
	{
		switch (Utility.getTheme(getApplicationContext()))
		{
			case THEME_BLUE:
			{
				setTheme(R.style.AppTheme_Blue);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
				}
				break;
			}
			case THEME_RED:
			{
				setTheme(R.style.AppTheme_Red);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					getWindow().setStatusBarColor(getResources().getColor(R.color.colorSecondaryDark));
				}
				break;
			}
			case THEME_YELLOW:
			{
				setTheme(R.style.AppTheme_Yellow);
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
