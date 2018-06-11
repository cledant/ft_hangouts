package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AddUserActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		//Bind Toolbar
		Toolbar toolbar = findViewById(R.id.toolbar_add_user);
		setSupportActionBar(toolbar);

		//Bind Action Bar
		DrawerLayout drawer = findViewById(R.id.drawer_layout_add_user);

		//Disable Action bar title
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	// Inflate the option menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//Action Bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
