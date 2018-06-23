package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MessagePanelActivity extends BaseActivity
		implements View.OnClickListener
{
	private long contact_id = -1;
	private String phone_number = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Contact contact = null;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_panel);

		//Bind Toolbar
		Toolbar toolbar = findViewById(R.id.toolbar_message_panel);
		setSupportActionBar(toolbar);

		//Disable Action bar title
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		//Bind Buttons
		TextView ret = findViewById(R.id.toolbar_message_panel_return);
		ret.setOnClickListener(this);

		//Set contact info
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
			contact_id = bundle.getLong("ID");

		//Get contact phonenumber
		if (contact_id != -1)
		{
			DAOContact dao = new DAOContact(getApplicationContext());
			contact = dao.select(contact_id);
			phone_number = contact.getPhonenumber();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_message_panel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.menu_message_panel_view:
			{
				Intent intent = new Intent(this, DisplayContactActivity.class);
				intent.putExtra("ID", contact_id);
				startActivity(intent);
				return true;
			}
			case R.id.menu_message_panel_phone:
			{
				dialPhone(phone_number);
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
			case R.id.toolbar_message_panel_return:
				super.onBackPressed();
				break;
		}
	}

	public void dialPhone(String number)
	{
		Intent dialIntent = new Intent(Intent.ACTION_DIAL);
		String uri = String.format("tel: %s", phone_number);
		dialIntent.setData(Uri.parse(uri));
		if (dialIntent.resolveActivity(getPackageManager()) != null)
			startActivity(dialIntent);
		else
			super.onBackPressed();
	}
}
