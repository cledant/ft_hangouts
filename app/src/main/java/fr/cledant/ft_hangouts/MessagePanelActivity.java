package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MessagePanelActivity extends BaseActivity
		implements View.OnClickListener
{
	private long contact_id = -1;

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
		TextView send = findViewById(R.id.message_panel_send_sms);
		send.setOnClickListener(this);

		//Update name
		TextView contact_name = findViewById(R.id.toolbar_message_panel_title);
		contact_name.setText(getName());

		//Set contact info
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
			contact_id = bundle.getLong("ID");

		//Get contact phonenumber
		if (contact_id == -1)
			super.onBackPressed();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		TextView contact_name = findViewById(R.id.toolbar_message_panel_title);
		contact_name.setText(getName());
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
				dialPhone(getPhonenumber());
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
			{
				super.onBackPressed();
				break;
			}
			case R.id.message_panel_send_sms:
			{
				EditText input = findViewById(R.id.message_panel_input);
				String msg = input.getText().toString();
				if (!msg.equals(""))
				{
					input.setText("");
					SMSHandler.getSMSHandler().sendSMS(getPhonenumber(), msg,
							getApplicationContext(), contact_id);
				}
				break;
			}
		}
	}

	public void dialPhone(String number)
	{
		Intent dialIntent = new Intent(Intent.ACTION_DIAL);
		String uri = String.format("tel: %s", getPhonenumber());
		dialIntent.setData(Uri.parse(uri));
		if (dialIntent.resolveActivity(getPackageManager()) != null)
			startActivity(dialIntent);
		else
			super.onBackPressed();
	}

	public String getPhonenumber()
	{
		DAOContact dao = new DAOContact(getApplicationContext());
		Contact contact = dao.select(contact_id);
		String phone_number = contact.getPhonenumber();

		return phone_number;
	}

	public String getName()
	{
		DAOContact dao = new DAOContact(getApplicationContext());
		Contact contact = dao.select(contact_id);

		if (contact.getSurname().equals(""))
			return (contact.getFirstname() + " " + contact.getLastname());
		return contact.getSurname();
	}
}
