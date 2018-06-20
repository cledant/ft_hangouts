package fr.cledant.ft_hangouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactGridAdapter extends BaseAdapter
{
	private List<Contact> listData;
	private LayoutInflater layoutInflater;
	private Context context;

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

		holder.setView(this.listData.get(position));
		return convertView;
	}

	static class ViewHolder
	{
		ImageView contactPic;
		TextView name;
		TextView phoneNumber;

		public void setView(Contact contact)
		{
			if (contact.getSurname().equals(""))
			{
				String concat_name = contact.getFirstname() + " " + contact.getLastname();
				this.name.setText(concat_name);
			}
			else
				this.name.setText(contact.getSurname());
			this.phoneNumber.setText(contact.getPhonenumber());
		}
	}
}
