package fr.cledant.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.List;

public class SMSReceiver extends BroadcastReceiver
{
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		if (intent.getAction().equals(SMS_RECEIVED))
		{
			if (bundle != null)
			{
				Object[] pdus = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdus.length; i++)
				{
					SmsMessage[] messages = new SmsMessage[pdus.length];
					StringBuilder sb = new StringBuilder();
					for (int j = 0; i < pdus.length; i++)
					{
						messages[j] = SmsMessage.createFromPdu((byte[]) pdus[i]);
						sb.append(messages[j].getMessageBody());
					}
					String sender = messages[0].getOriginatingAddress();
					String message = sb.toString();
					addMessageToDB(sender, message, context);
				}
			}
		}
	}

	private void addMessageToDB(String sender, String message, Context context)
	{
		DAOContact dao_contact = new DAOContact(context);
		DAOMessage dao_message = new DAOMessage(context);

		long time = System.currentTimeMillis();
		List<Contact> contacts = dao_contact.findByNumber(sender);
		if (contacts.size() == 0)
		{
			createNewContact(sender, message, context);
			return;
		}
		for (int i = 0; i < contacts.size(); i++)
		{
			Message msg = new Message(-1, message, contacts.get(i).getId(),
					DatabaseHandler.MSG_IN, time, DatabaseHandler.MSG_DELIVER_OK);
			dao_message.create(msg);
		}
	}

	private void createNewContact(String sender, String message, Context context)
	{
		return;
	}
}
