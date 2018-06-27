package fr.cledant.ft_hangouts;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class CommonContact extends BaseActivity
		implements View.OnClickListener
{
	final protected static int RESULT_LOAD_IMG = 1;
	protected String path_img = Utility.DEFAULT_IMG;
	protected long contact_id = -1;
	protected AssetManager assetManager = null;
	protected Toolbar toolbar = null;
	protected TextView cancel = null;
	protected TextView save = null;
	protected TextView title = null;
	protected ImageView image_view = null;
	protected EditText firstname = null;
	protected EditText lastname = null;
	protected EditText surname = null;
	protected EditText email = null;
	protected EditText phone = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		assetManager = getAssets();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		//Bind Toolbar
		toolbar = findViewById(R.id.toolbar_add_user);
		setSupportActionBar(toolbar);

		//Disable Action bar title
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		//Get title text
		title = findViewById(R.id.toolbar_add_user_title);

		//Bind Buttons
		cancel = findViewById(R.id.toolbar_add_user_cancel);
		cancel.setOnClickListener(this);
		save = findViewById(R.id.toolbar_add_user_save);
		save.setOnClickListener(this);

		//Load default image for contact
		image_view = findViewById(R.id.add_user_image);
		image_view.setOnClickListener(this);

		//Saving EditText field
		firstname = findViewById(R.id.add_user_firstname);
		lastname = findViewById(R.id.add_user_lastname);
		surname = findViewById(R.id.add_user_surname);
		email = findViewById(R.id.add_user_email);
		phone = findViewById(R.id.add_user_phone);
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

	@Override
	protected void onActivityResult(int reqCode, int resultCode, Intent data)
	{
		super.onActivityResult(reqCode, resultCode, data);

		//Image Retriver
		String backup = path_img;
		switch (resultCode)
		{
			case RESULT_OK:
			{
				try
				{
					Uri imageUri = data.getData();
					this.path_img = imageUri.toString();
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
					image_view.setImageBitmap(bitmap);
				}
				catch (Exception e)
				{
					this.path_img = backup;
					Log.e("IMG_LOADING", "CommonContact Image loading error");
				}
				break;
			}
			default:
			{
				this.path_img = backup;
				break;
			}
		}
	}

	public void saveUser(View view)
	{
		String formated_phone = Utility.formatPhoneNumber(phone.getText().toString());
		Contact contact = new Contact(this.contact_id, firstname.getText().toString(),
				lastname.getText().toString(),
				surname.getText().toString(),
				formated_phone,
				email.getText().toString(),
				this.path_img);
		long id = -1;

		if (contact.getFirstname().length() == 0 || contact.getLastname().length() == 0)
		{
			Snackbar.make(view, R.string.add_user_error_names, Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			return;
		}
		DAOContact dao = new DAOContact(getApplicationContext());
		if (this.contact_id == -1)
			id = dao.create(contact);
		else
		{
			dao.modify(contact);
			id = this.contact_id;
		}
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
}
