package fr.cledant.ft_hangouts;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SMSHandler
{
	private static SMSHandler instance = null;

	final public static String SENT = "SMS_SENT";
	final public static String DELIVERED = "SMS_DELIVERED";

	private SMSHandler()
	{
	}

	public static SMSHandler getSMSHandler()
	{
		if (instance == null)
			instance = new SMSHandler();
		return instance;
	}

	public void sendSMS(String phoneNumber, String message, Context app_context, long contact_id)
	{
		SmsManager sms = SmsManager.getDefault();
		ArrayList<String> parts = sms.divideMessage(message);
		int messageCount = parts.size();

		//Intent for single message
		PendingIntent sentPI = PendingIntent.getBroadcast(app_context, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(app_context, 0, new Intent(DELIVERED), 0);

		//Case of multiple message
		ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
		for (int j = 0; j < messageCount; j++)
		{
			sentIntents.add(sentPI);
			deliveryIntents.add(deliveredPI);
		}

		//Setup callback for SMS delivery status
		SentSMSBroadcastReceiver ssms = new SentSMSBroadcastReceiver();
		app_context.registerReceiver(ssms, new IntentFilter(SENT));
		DeliveredSMSBroadcastReceiver dsms = new DeliveredSMSBroadcastReceiver();
		app_context.registerReceiver(dsms, new IntentFilter(DELIVERED));

		//Add messages to db
		DAOMessage daoMessage = new DAOMessage(app_context);
		for (int i = 0; i < messageCount; i++)
		{
			Message msg = new Message(-1, parts.get(i), contact_id, DatabaseHandler.MSG_OUT,
					System.currentTimeMillis() + i, DatabaseHandler.MSG_DEFAULT);
			daoMessage.create(msg);
		}

		//Sending message
		if (messageCount == 1)
			sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
		else
			sms.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents);
	}

	public class SentSMSBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context arg0, Intent arg1)
		{
			switch (getResultCode())
			{
				case Activity.RESULT_OK:
					Log.d("SMS_SENT", "SMS sent");
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Log.d("SMS_SENT", "Generic failure");
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Log.d("SMS_SENT", "No service");
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Log.d("SMS_SENT", "Null PDU");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Log.d("SMS_SENT", "Radio off");
					break;
			}
		}
	}

	public class DeliveredSMSBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context arg0, Intent arg1)
		{
			switch (getResultCode())
			{
				case Activity.RESULT_OK:
					Log.d("SMS_DELIVERED", "SMS delivered");
					break;
				case Activity.RESULT_CANCELED:
					Log.d("SMS_DELIVERED", "SMS not delivered");
					break;
			}
		}
	}
}
