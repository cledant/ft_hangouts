package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyContactActivity extends BaseActivity
		implements View.OnClickListener
{
	private long contact_id = -1;

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

		//Set TextView
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
			contact_id = bundle.getLong("ID");
		if (contact_id == -1)
			super.onBackPressed();
		setContactTextView();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			default:
				break;
			case R.id.toolbar_add_user_cancel:
			{
				super.onBackPressed();
				break;
			}
			case R.id.toolbar_add_user_save:
			{
				saveUser(view);
				break;
			}
		}
	}

	public void saveUser(View view)
	{
		EditText firstname = findViewById(R.id.add_user_firstname);
		EditText lastname = findViewById(R.id.add_user_lastname);
		EditText surname = findViewById(R.id.add_user_surname);
		EditText email = findViewById(R.id.add_user_email);
		EditText phone = findViewById(R.id.add_user_phone);
		Contact contact = new Contact(contact_id, firstname.getText().toString(),
				lastname.getText().toString(),
				surname.getText().toString(),
				email.getText().toString(),
				phone.getText().toString());

		if (contact.getFirstname().length() == 0 || contact.getLastname().length() == 0)
		{
			Snackbar.make(view, R.string.add_user_error_names, Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			return;
		}
		DAOContact dao = new DAOContact(getApplicationContext());
		dao.modify(contact);
		Intent intent = new Intent(this, DisplayContactActivity.class);
		intent.putExtra("ID", contact_id);
		super.onBackPressed();
		startActivity(intent);
	}

	public void setContactTextView()
	{
		EditText firstname = findViewById(R.id.add_user_firstname);
		EditText lastname = findViewById(R.id.add_user_lastname);
		EditText surname = findViewById(R.id.add_user_surname);
		EditText email = findViewById(R.id.add_user_email);
		EditText phone = findViewById(R.id.add_user_phone);

		//Retrive data
		DAOContact dao = new DAOContact(getApplicationContext());
		Contact contact = dao.select(contact_id);

		//display
		firstname.setText(contact.getFirstname());
		lastname.setText(contact.getLastname());
		surname.setText(contact.getSurname());
		phone.setText(contact.getPhonenumber());
		email.setText(contact.getEmail());
	}
}