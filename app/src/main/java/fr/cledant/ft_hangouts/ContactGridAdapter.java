package fr.cledant.ft_hangouts;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ContactGridAdapter extends BaseAdapter
{
	private List<Contact> listData;
	private LayoutInflater layoutInflater;
	private Context context;

	static class ViewHolder
	{
		ImageView contactPic;
		TextView name;
		TextView phoneNumber;
	}

	public ContactGridAdapter(Context aContext, List<Contact> listData)
	{
		this.context = aContext;
		this.listData = listData;
		layoutInflater = LayoutInflater.from(aContext);
	}

	@Override
	public int getCount()
	{
		return listData.size();
	}

	@Override
	public Object getItem(int position)
	{
		return listData.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.grid_contact, null);
			holder = new ViewHolder();
			holder.contactPic = convertView.findViewById(R.id.grid_contact_image);
			holder.name = convertView.findViewById(R.id.grid_contact_title);
			holder.phoneNumber = convertView.findViewById(R.id.grid_contact_number);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		this.setViewField(this.listData.get(position), holder);
		return convertView;
	}

	private void setViewField(Contact contact, ViewHolder holder)
	{
		if (contact.getSurname().equals(""))
		{
			String concat_name = contact.getFirstname() + " " + contact.getLastname();
			holder.name.setText(concat_name);
		}
		else
			holder.name.setText(contact.getSurname());
		holder.phoneNumber.setText(contact.getPhonenumber());
		try
		{
			loadImage(contact.getImagePath(), holder);
		}
		catch (Exception e)
		{
			return;
		}
	}

	private void loadImage(String path, ViewHolder holder) throws IOException
	{
		AssetManager assetManager = context.getAssets();

		if (!path.equals(""))
		{
			if (path.equals(Utility.DEFAULT_IMG))
			{
				InputStream is = assetManager.open(path);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				holder.contactPic.setImageBitmap(bitmap);
			}
			else
				holder.contactPic.setImageURI(Uri.parse(path));
		}
	}
}
