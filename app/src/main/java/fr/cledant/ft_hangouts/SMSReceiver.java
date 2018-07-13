package fr.cledant.ft_hangouts;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SMSReceiver extends BroadcastReceiver
{
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	public static final String CHANNEL_ID = "channel_msg";
	private static int notif_id = 0;

	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		if (intent.getAction().equals(SMS_RECEIVED))
		{
			if (bundle != null)
			{
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < pdus.length; i++)
				{
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					sb.append(messages[i].getMessageBody());
				}
				String sender = messages[0].getOriginatingAddress();
				String message = sb.toString();
				addMessageToDB(sender, message, context);
			}
		}
	}

	private void addMessageToDB(String sender, String message, Context context)
	{
		DAOContact dao_contact = new DAOContact(context);
		DAOMessage dao_message = new DAOMessage(context);
		long id = -1;

		long time = System.currentTimeMillis();
		List<Contact> contacts = dao_contact.findByNumber(sender);
		if (contacts.size() == 0)
		{
			if ((id = createNewContact(sender, context)) == -1)
				return;
			Message msg = new Message(-1, message, id, DatabaseHandler.MSG_IN,
					time, DatabaseHandler.MSG_DELIVER_OK);
			dao_message.create(msg);
			return;
		}
		for (int i = 0; i < contacts.size(); i++)
		{
			Message msg = new Message(-1, message, contacts.get(i).getId(),
					DatabaseHandler.MSG_IN, time, DatabaseHandler.MSG_DELIVER_OK);
			dao_message.create(msg);
			addNotification(context, contacts.get(i), message);
		}
	}

	private long createNewContact(String sender, Context context)
	{
		long newID = -1;
		DAOContact dao_contact = new DAOContact(context);

		Contact contact = new Contact(-1, sender, "", "", sender, "", Utility.DEFAULT_IMG);
		newID = dao_contact.create(contact);
		return newID;
	}

	private void addNotification(Context context, Contact contact, String message)
	{
		//Set notification String title
		String title;
		if (contact.getSurname().length() > 0)
			title = contact.getSurname();
		else if (contact.getFirstname().length() > 0)
			title = contact.getFirstname() + " " + contact.getLastname();
		else
			title = contact.getLastname();

		//Loading Contact image
		Bitmap large_icon;
		try
		{
			large_icon = loadImage(contact.getImagePath(), context);
		}
		catch (Exception e)
		{
			large_icon = null;
		}

		// Create an explicit intent
		Intent intent = new Intent(context, MessagePanelActivity.class);
		intent.putExtra("ID", contact.getId());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		//Create notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.mipmap.ic_launcher_foreground)
				.setLargeIcon(large_icon)
				.setColor(Utility.getThemeColor(context))
				.setContentTitle(title)
				.setContentText(message)
				.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		//Display Notification
		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
		notificationManager.notify(notif_id, builder.build());
		notif_id++;
	}

	private Bitmap loadImage(String path, Context context) throws IOException
	{
		AssetManager assetManager = context.getAssets();

		switch (path)
		{
			case "":
			{
				InputStream is = assetManager.open(path);
				return BitmapFactory.decodeStream(is);
			}
			case Utility.DEFAULT_IMG:
			{
				InputStream is = assetManager.open(path);
				return BitmapFactory.decodeStream(is);
			}
			default:
			{
				return MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(path));
			}
		}
	}
}
