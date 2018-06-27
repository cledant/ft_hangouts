package fr.cledant.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DAOMessage extends DAOBase
{
	public DAOMessage(Context context)
	{
		super(context);
	}

	public long create(Message msg)
	{
		long created_id = -1;
		ContentValues value = new ContentValues();

		value.put(DatabaseHandler.MSG_CONTENT, msg.getContent());
		value.put(DatabaseHandler.MSG_OWNER_ID, msg.getOwner_id());
		value.put(DatabaseHandler.MSG_DESTINATION, msg.getDest());
		value.put(DatabaseHandler.MSG_TIME, msg.getTime());
		value.put(DatabaseHandler.MSG_STATUS, msg.getStatus());
		this.open();
		this.db.insert(DatabaseHandler.MSG_TABLE_NAME, null, value);
		this.close();
		return created_id;
	}

	public void delete(long id)
	{
		this.open();
		this.db.delete(DatabaseHandler.MSG_TABLE_NAME, DatabaseHandler.MSG_KEY + " = ?",
				new String[]{String.valueOf(id)});
		this.close();
	}

	public void modify(Message msg)
	{
		ContentValues value = new ContentValues();
		value.put(DatabaseHandler.MSG_CONTENT, msg.getContent());
		value.put(DatabaseHandler.MSG_OWNER_ID, msg.getOwner_id());
		value.put(DatabaseHandler.MSG_DESTINATION, msg.getDest());
		value.put(DatabaseHandler.MSG_TIME, msg.getTime());
		value.put(DatabaseHandler.MSG_STATUS, msg.getStatus());
		this.open();
		this.db.update(DatabaseHandler.MSG_TABLE_NAME, value, DatabaseHandler.CONTACT_KEY + " = ?",
				new String[]{String.valueOf(msg.getId())});
		this.close();
	}

	public Message select(long id)
	{
		Message msg = new Message(-1, "", -1, DatabaseHandler.MSG_HIDDEN, 0,
				DatabaseHandler.MSG_DEFAULT);
		String query = "select * from " + DatabaseHandler.MSG_TABLE_NAME + " where " + DatabaseHandler.MSG_KEY + " = ?";
		this.open();
		Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
		if ((cursor != null) && (cursor.getCount() > 0))
		{
			cursor.moveToFirst();
			msg.setId(cursor.getLong(0));
			msg.setContent(cursor.getString(1));
			msg.setOwner_id(cursor.getLong(2));
			msg.setDest(cursor.getLong(3));
			msg.setTime(cursor.getLong(4));
			msg.setStatus(cursor.getLong(5));
			cursor.close();
		}
		this.close();
		return msg;
	}
}
