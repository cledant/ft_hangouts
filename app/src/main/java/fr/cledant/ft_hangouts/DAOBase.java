package fr.cledant.ft_hangouts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase
{
	protected final static int VERSION = 1;
	protected final static String DB_NAME = "database.db";

	protected SQLiteDatabase db = null;
	protected DatabaseHandler db_handler = null;

	public DAOBase(Context context)
	{
		this.db_handler = new DatabaseHandler(context, DB_NAME, null, VERSION);
	}

	public SQLiteDatabase open()
	{
		db = db_handler.getWritableDatabase();
		return db;
	}

	public void close()
	{
		db.close();
	}

	public SQLiteDatabase getDb()
	{
		return db;
	}
}