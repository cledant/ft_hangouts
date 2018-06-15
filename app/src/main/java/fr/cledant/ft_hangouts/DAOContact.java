package fr.cledant.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
		this.db.insert(DatabaseHandler.CONTACT_TABLE_NAME, null, value);
	}

	public void delete(long id)
	{
		this.db.delete(DatabaseHandler.CONTACT_TABLE_NAME, DatabaseHandler.CONTACT_KEY + " = ?",
				new String[]{String.valueOf(id)});
	}

	public void modify(Contact contact)
	{
		ContentValues value = new ContentValues();
		value.put(DatabaseHandler.CONTACT_FIRSTNAME, contact.getFirstname());
		value.put(DatabaseHandler.CONTACT_LASTNAME, contact.getLastname());
		value.put(DatabaseHandler.CONTACT_SURNAME, contact.getSurname());
		value.put(DatabaseHandler.CONTACT_EMAIL, contact.getEmail());
		value.put(DatabaseHandler.CONTACT_PHONENUMBER, contact.getPhonenumber());
		this.db.update(DatabaseHandler.CONTACT_TABLE_NAME, value, DatabaseHandler.CONTACT_KEY + " = ?",
				new String[]{String.valueOf(contact.getId())});
	}

	public Contact select(long id)
	{
		Contact contact = new Contact(-1, "", "", "", "", "");
		String query = "select * from " + DatabaseHandler.CONTACT_TABLE_NAME + " where " + DatabaseHandler.CONTACT_KEY + " = ?";
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
			cursor.close();
		}
		return contact;
	}
}
