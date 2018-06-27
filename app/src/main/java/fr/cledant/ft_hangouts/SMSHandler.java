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

		if (parts.size() == 1)
			singleSMS(phoneNumber, message, app_context, contact_id);
		else
			multipleSMS(phoneNumber, parts, app_context, contact_id);
	}

	private void singleSMS(String phoneNumber, String message, Context app_context, long contact_id)
	{
		SmsManager sms = SmsManager.getDefault();
		DAOMessage daoMessage = new DAOMessage(app_context);

		//Setup callback for SMS delivery status
		SentSMSBroadcastReceiver ssms = new SentSMSBroadcastReceiver();
		app_context.registerReceiver(ssms, new IntentFilter(SENT));
		DeliveredSMSBroadcastReceiver dsms = new DeliveredSMSBroadcastReceiver();
		app_context.registerReceiver(dsms, new IntentFilter(DELIVERED));

		//Add msg to db
		Message msg = new Message(-1, message, contact_id, DatabaseHandler.MSG_OUT,
				System.currentTimeMillis(), DatabaseHandler.MSG_DEFAULT);
		long newid = daoMessage.create(msg);

		//Create Intent and link id to it
		Intent sendIntent = new Intent(SENT);
		Intent deliveredIntent = new Intent(DELIVERED);
		sendIntent.putExtra("msg_id", newid);
		deliveredIntent.putExtra("msg_id", newid);

		//Sending message
		PendingIntent sentPI = PendingIntent.getBroadcast(app_context, 0, sendIntent, 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(app_context, 0, deliveredIntent, 0);
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}

	private void multipleSMS(String phoneNumber, ArrayList<String> parts, Context app_context, long contact_id)
	{
		SmsManager sms = SmsManager.getDefault();
		DAOMessage daoMessage = new DAOMessage(app_context);

		//Setup callback for SMS delivery status
		SentSMSBroadcastReceiver ssms = new SentSMSBroadcastReceiver();
		app_context.registerReceiver(ssms, new IntentFilter(SENT));
		DeliveredSMSBroadcastReceiver dsms = new DeliveredSMSBroadcastReceiver();
		app_context.registerReceiver(dsms, new IntentFilter(DELIVERED));

		//Creating arrays
		ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

		//Adding message to db
		for (int j = 0; j < parts.size(); j++)
		{
			Message msg = new Message(-1, parts.get(j), contact_id, DatabaseHandler.MSG_OUT,
					System.currentTimeMillis() + j, DatabaseHandler.MSG_DEFAULT);
			long newid = daoMessage.create(msg);
			Intent sendIntent = new Intent(SENT);
			Intent deliveredIntent = new Intent(DELIVERED);
			sendIntent.putExtra("msg_id", newid);
			deliveredIntent.putExtra("msg_id", newid);
			PendingIntent sentPI = PendingIntent.getBroadcast(app_context, 0, sendIntent, 0);
			PendingIntent deliveredPI = PendingIntent.getBroadcast(app_context, 0, deliveredIntent, 0);

			//Adding to array
			sentIntents.add(sentPI);
			deliveryIntents.add(deliveredPI);
		}
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
