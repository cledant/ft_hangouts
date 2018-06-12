package fr.cledant.ft_hangouts;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity
		implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		//Bind Toolbar
		Toolbar toolbar = findViewById(R.id.toolbar_settings);
		setSupportActionBar(toolbar);

		//Disable Action bar title
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		//Bind Buttons
		TextView cancel = findViewById(R.id.toolbar_settings_cancel);
		cancel.setOnClickListener(this);
		TextView save = findViewById(R.id.toolbar_settings_save);
		save.setOnClickListener(this);

		//Bind Spinner
		Spinner theme_selection = findViewById(R.id.theme_spinner);
		ArrayAdapter<CharSequence> theme_adapter = ArrayAdapter.createFromResource(this,
				R.array.settings_color_choice, android.R.layout.simple_spinner_item);
		theme_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		theme_selection.setAdapter(theme_adapter);
		theme_selection.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			default:
				break;
			case R.id.toolbar_settings_cancel:
				super.onBackPressed();
				break;
			case R.id.toolbar_settings_save:
			{
				Snackbar.make(view, "WIP", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				break;
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	{
		switch (position)
		{
			case 0:
			{
				Snackbar.make(view, "BLUE", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				break;
			}
			case 1:
			{
				Snackbar.make(view, "RED", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				break;
			}
			case 2:
			{
				Snackbar.make(view, "YELLOW", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
				break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent)
	{
	}
}