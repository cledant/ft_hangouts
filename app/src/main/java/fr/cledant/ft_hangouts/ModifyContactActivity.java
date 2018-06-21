package fr.cledant.ft_hangouts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ModifyContactActivity extends CommonContact
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//Load default image for contact
		ImageView image_view = findViewById(R.id.add_user_image);
		image_view.setOnClickListener(this);

		//Change ActionBar Title
		TextView title = findViewById(R.id.toolbar_add_user_title);
		title.setText(R.string.modify_user_title);

		//Set TextView
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
			contact_id = bundle.getLong("ID");
		if (contact_id == -1)
			super.onBackPressed();
		setContactTextView();
		try
		{
			setContactImageView();
		}
		catch (Exception e)
		{
			return;
		}
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

	public void setContactImageView() throws IOException
	{
		if (contact_id == -1)
			return;

		DAOContact dao = new DAOContact(getApplicationContext());
		Contact contact = dao.select(contact_id);
		String path = contact.getImagePath();
		ImageView iw = findViewById(R.id.add_user_image);

		if (!path.equals(""))
		{
			if (path.equals(Utility.DEFAULT_IMG))
			{
				InputStream is = assetManager.open(path);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				iw.setImageBitmap(bitmap);
			}
			else
				iw.setImageURI(Uri.parse(path));
		}
	}
}