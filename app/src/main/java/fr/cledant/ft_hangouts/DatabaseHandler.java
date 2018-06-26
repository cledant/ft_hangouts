package fr.cledant.ft_hangouts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	//Columns for contact
	public static final String CONTACT_KEY = "id";
	public static final String CONTACT_FIRSTNAME = "firstname";
	public static final String CONTACT_LASTNAME = "lastname";
	public static final String CONTACT_SURNAME = "surname";
	public static final String CONTACT_PHONENUMBER = "phonenumber";
	public static final String CONTACT_EMAIL = "email";
	public static final String CONTACT_IMG = "img";

	//Creation + delete for contact
	public static final String CONTACT_TABLE_NAME = "Contact";
	public static final String CONTACT_TABLE_CREATE =
			"CREATE TABLE " + CONTACT_TABLE_NAME + " (" +
					CONTACT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					CONTACT_FIRSTNAME + " TEXT, " +
					CONTACT_LASTNAME + " TEXT, " +
					CONTACT_SURNAME + " TEXT, " +
					CONTACT_PHONENUMBER + " TEXT, " +
					CONTACT_EMAIL + " TEXT, " +
					CONTACT_IMG + " TEXT);";
	public static final String CONTACT_TABLE_DROP = "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME + ";";

	//Columns for message
	public static final String MSG_KEY = "id";
	public static final String MSG_CONTENT = "content";
	public static final String MSG_OWNER_ID = "owner_id";
	public static final String MSG_DESTINATION = "destination";
	public static final String MSG_TIME = "time";
	public static final String MSG_STATUS = "status";

	//Value for message
	public static final long MSG_IN = 0;
	public static final long MSG_OUT = 1;

	//Creation + delete for message
	public static final String MSG_TABLE_NAME = "Message";
	public static final String MSG_TABLE_CREATE =
			"CREATE TABLE " + MSG_TABLE_NAME + " (" +
					MSG_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					MSG_CONTENT + " TEXT, " +
					MSG_OWNER_ID + " INTEGER, " +
					MSG_DESTINATION + " INTEGER, " +
					MSG_TIME + " INTERGER, " +
					MSG_STATUS + " TEXT);";
	public static final String MSG_TABLE_DROP = "DROP TABLE IF EXISTS " + MSG_TABLE_NAME + ";";

	public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CONTACT_TABLE_CREATE);
		db.execSQL(MSG_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(CONTACT_TABLE_DROP);
		db.execSQL(MSG_TABLE_DROP);
		onCreate(db);
	}
}
