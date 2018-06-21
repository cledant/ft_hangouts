package fr.cledant.ft_hangouts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;

public class AddUserActivity extends CommonContact
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//Load default image for contact
		ImageView image_view = findViewById(R.id.add_user_image);
		image_view.setOnClickListener(this);
		try
		{
			InputStream is = assetManager.open(Utility.DEFAULT_IMG);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			image_view.setImageBitmap(bitmap);
		}
		catch (Exception e)
		{
			return;
		}
	}
}
