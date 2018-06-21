package fr.cledant.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DAOContact extends DAOBase
{
	public DAOContact(Context context)
	{
		super(context);
	}

	public void create(Contact contact)
	{
		ContentValues value = new ContentValues();
		value.put(DatabaseHandler.CONTACT_FIRSTNAME, contact.getFirstname());
		value.put(DatabaseHandler.CONTACT_LASTNAME, contact.getLastname());
		value.put(DatabaseHandler.CONTACT_SURNAME, contact.getSurname());
		value.put(DatabaseHandler.CONTACT_EMAIL, contact.getEmail());
		value.put(DatabaseHandler.CONTACT_PHONENUMBER, contact.getPhonenumber());
		value.put(DatabaseHandler.CONTACT_IMG, contact.getImagePath());
		this.open();
		this.db.insert(DatabaseHandler.CONTACT_TABLE_NAME, null, value);
		this.close();
	}

	public void delete(long id)
	{
		this.open();
		this.db.delete(DatabaseHandler.CONTACT_TABLE_NAME, DatabaseHandler.CONTACT_KEY + " = ?",
				new String[]{String.valueOf(id)});
		this.close();
	}

	public void modify(Contact contact)
	{
		ContentValues value = new ContentValues();
		value.put(DatabaseHandler.CONTACT_FIRSTNAME, contact.getFirstname());
		value.put(DatabaseHandler.CONTACT_LASTNAME, contact.getLastname());
		value.put(DatabaseHandler.CONTACT_SURNAME, contact.getSurname());
		value.put(DatabaseHandler.CONTACT_EMAIL, contact.getEmail());
		value.put(DatabaseHandler.CONTACT_PHONENUMBER, contact.getPhonenumber());
		value.put(DatabaseHandler.CONTACT_IMG, contact.getImagePath());
		this.open();
		this.db.update(DatabaseHandler.CONTACT_TABLE_NAME, value, DatabaseHandler.CONTACT_KEY + " = ?",
				new String[]{String.valueOf(contact.getId())});
		this.close();
	}

	public Contact select(long id)
	{
		Contact contact = new Contact(-1, "", "", "", "", "", "");
		String query = "select * from " + DatabaseHandler.CONTACT_TABLE_NAME + " where " + DatabaseHandler.CONTACT_KEY + " = ?";
		this.open();
		Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
		if ((cursor != null) && (cursor.getCount() > 0))
		{
			cursor.moveToFirst();
			contact.setId(cursor.getLong(0));
			contact.setFirstname(cursor.getString(1));
			contact.setLastname(cursor.getString(2));
			contact.setSurname(cursor.getString(3));
			contact.setPhonenumber(cursor.getString(4));
			contact.setEmail(cursor.getString(5));
			contact.setImagePath(cursor.getString(6));
			cursor.close();
		}
		this.close();
		return contact;
	}

	public long getLastId()
	{
		long id = -1;

		String query = "select * from " + DatabaseHandler.CONTACT_TABLE_NAME;
		this.open();
		Cursor cursor = db.rawQuery(query, null);
		if ((cursor != null) && (cursor.getCount() > 0))
		{
			cursor.moveToLast();
			id = cursor.getLong(0);
			cursor.close();
		}
		this.close();
		return id;
	}

	public List<Contact> getContactList()
	{
		List<Contact> list = new ArrayList<Contact>();
		String query = "select * from " + DatabaseHandler.CONTACT_TABLE_NAME;

		this.open();
		Cursor cursor = db.rawQuery(query, null);
		if ((cursor != null) && (cursor.getCount() > 0))
		{
			while (cursor.moveToNext())
			{
				Contact contact = new Contact(cursor.getLong(0),
						cursor.getString(1),
						cursor.getString(2),
						cursor.getString(3),
						cursor.getString(4),
						cursor.getString(5),
						cursor.getString(6));
				list.add(contact);
			}
			cursor.close();
		}
		this.close();
		return list;
	}
}
