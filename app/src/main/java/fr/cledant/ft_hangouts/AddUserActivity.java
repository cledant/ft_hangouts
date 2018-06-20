package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class AddUserActivity extends BaseActivity
		implements View.OnClickListener
{
	private static int RESULT_LOAD_IMG = 1;
	final private static String DEFAULT_PATH_IMG = "lol";
	private String path_img;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		InputStream imageStream;

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

		//Load default image for contact
		path_img = DEFAULT_PATH_IMG;
		ImageView image_view = findViewById(R.id.add_user_image);
		image_view.setOnClickListener(this);
/*		Uri imageUri = Uri.fromFile(new File(DEFAULT_PATH_IMG));
		try
		{
			imageStream = getContentResolver().openInputStream(imageUri);
			Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
		}
		catch (Exception e)
		{
			super.onBackPressed();
		}*/
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
			case R.id.add_user_image:
			{
				select_image();
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
		String formated_phone = Utility.formatPhoneNumber(phone.getText().toString());
		Contact contact = new Contact(0, firstname.getText().toString(),
				lastname.getText().toString(),
				surname.getText().toString(),
				formated_phone,
				email.getText().toString());

		if (contact.getFirstname().length() == 0 || contact.getLastname().length() == 0)
		{
			Snackbar.make(view, R.string.add_user_error_names, Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			return;
		}
		DAOContact dao = new DAOContact(getApplicationContext());
		dao.create(contact);
		long id = dao.getLastId();
		Intent intent = new Intent(this, DisplayContactActivity.class);
		intent.putExtra("ID", id);
		super.onBackPressed();
		startActivity(intent);
	}

	public void select_image()
	{
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
	}

	@Override
	protected void onActivityResult(int reqCode, int resultCode, Intent data)
	{
		super.onActivityResult(reqCode, resultCode, data);
		ImageView image_view = findViewById(R.id.add_user_image);
		switch (resultCode)
		{
			case RESULT_OK:
			{
				try
				{
					Uri imageUri = data.getData();
					path_img = imageUri.toString();
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
					image_view.setImageBitmap(bitmap);
				}
				catch (Exception e)
				{
					path_img = DEFAULT_PATH_IMG;
				}
				break;
			}
			default:
			{
				path_img = DEFAULT_PATH_IMG;
				break;
			}
		}
	}
}
