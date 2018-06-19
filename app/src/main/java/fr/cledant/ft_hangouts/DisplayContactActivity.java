package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.net.Uri;
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
	private long contact_id = -1;
	private String phone_number = "";

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

		//Set contact info
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
			contact_id = bundle.getLong("ID");
		displayContactInfo();
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
				modifyContact();
				return true;
			}
			case R.id.menu_display_dial_phone:
			{
				dialPhone(phone_number);
				return true;
			}
			case R.id.menu_display_delete:
			{
				deleteContact();
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

	@Override
	public void onResume()
	{
		super.onResume();
		displayContactInfo();
	}

	public void displayContactInfo()
	{
		DAOContact dao = new DAOContact(getApplicationContext());
		TextView firstname = findViewById(R.id.display_user_firstname);
		TextView lastname = findViewById(R.id.display_user_lastname);
		TextView surname = findViewById(R.id.display_user_surname);
		TextView phone = findViewById(R.id.display_user_phone);
		TextView email = findViewById(R.id.display_user_email);
		String dao_firstname = getString(R.string.display_firstname);
		String dao_lastname = getString(R.string.display_lastname);
		String dao_surname = getString(R.string.display_surname);
		String dao_email = getString(R.string.display_mail);
		String dao_phone = getString(R.string.display_phonenumber);

		if (contact_id != -1)
		{
			Contact contact = dao.select(contact_id);
			//concatenate
			dao_firstname += contact.getFirstname();
			dao_lastname += contact.getLastname();
			dao_surname += contact.getSurname();
			dao_email += contact.getEmail();
			phone_number = contact.getPhonenumber();
			dao_phone += phone_number;

			//display
			firstname.setText(dao_firstname);
			lastname.setText(dao_lastname);
			surname.setText(dao_surname);
			phone.setText(dao_phone);
			email.setText(dao_email);
		}
	}

	public void deleteContact()
	{
		Bundle bundle = getIntent().getExtras();
		long val = -1;
		if (bundle != null)
			val = bundle.getLong("ID");
		if (val != -1)
		{
			DAOContact dao = new DAOContact(getApplicationContext());
			dao.delete(val);
		}
		super.onBackPressed();
	}

	public void modifyContact()
	{
		Intent intent = new Intent(this, ModifyContactActivity.class);
		intent.putExtra("ID", contact_id);
		super.onBackPressed();
		if (contact_id != -1)
			startActivity(intent);
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
