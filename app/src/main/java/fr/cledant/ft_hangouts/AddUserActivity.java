package fr.cledant.ft_hangouts;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AddUserActivity extends AppCompatActivity
		implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		//Bind Toolbar
		Toolbar toolbar = findViewById(R.id.toolbar_add_user);
		setSupportActionBar(toolbar);

		//Disable Action bar title
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		//Bind Buttons
		TextView cancel = findViewById(R.id.toolbar_add_user_cancel);
		cancel.setOnClickListener(this);
		TextView save = findViewById(R.id.toolbar_add_user_save);
		save.setOnClickListener(this);

		//NotificationBar color
//		?attr/colorPrimary


	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			default:
				break;
			case R.id.toolbar_add_user_cancel:
				super.onBackPressed();
				break;
			case R.id.toolbar_add_user_save:
			{
				Snackbar.make(view, "WIP", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				break;
			}
		}
	}
}
