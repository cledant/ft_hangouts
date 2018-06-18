package fr.cledant.ft_hangouts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayContactActivity extends AppCompatActivity
		implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact);

		//Bind Toolbar
		Toolbar toolbar = findViewById(R.id.toolbar_contact_info);
		setSupportActionBar(toolbar);

		//Disable Action bar title
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		//Bind Buttons
		TextView ret = findViewById(R.id.toolbar_contact_user_return);
		ret.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_display_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.menu_display_modify:
			{
				//todo
				return true;
			}
			case R.id.menu_display_delete:
			{
				//todo
				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			default:
				break;
			case R.id.toolbar_contact_user_return:
				super.onBackPressed();
				break;
		}
	}
}
