package fr.cledant.ft_hangouts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

		//Change ActionBar Title
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
			Log.e("IMG_LOADING", "ModifyContact image loading error");
		}
	}

	public void setContactTextView()
	{
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

		switch (path)
		{
			case "":
				break;
			case Utility.DEFAULT_IMG:
			{
				InputStream is = assetManager.open(path);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				iw.setImageBitmap(bitmap);
				break;
			}
			default:
			{
				iw.setImageURI(Uri.parse(path));
				break;
			}
		}
	}
}