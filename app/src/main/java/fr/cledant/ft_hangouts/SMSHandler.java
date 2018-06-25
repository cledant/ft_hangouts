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

	public void sendSMS(String phoneNumber, String message, Context app_context)
	{
		SmsManager sms = SmsManager.getDefault();
		ArrayList<String> parts = sms.divideMessage(message);
		int messageCount = parts.size();

		ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

		PendingIntent sentPI = PendingIntent.getBroadcast(app_context, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(app_context, 0, new Intent(DELIVERED), 0);

		for (int j = 0; j < messageCount; j++)
		{
			sentIntents.add(sentPI);
			deliveryIntents.add(deliveredPI);
		}

		SentSMSBroadcastReceiver ssms = new SentSMSBroadcastReceiver();
		app_context.registerReceiver(ssms, new IntentFilter(SENT));

		DeliveredSMSBroadcastReceiver dsms = new DeliveredSMSBroadcastReceiver();
		app_context.registerReceiver(dsms, new IntentFilter(DELIVERED));

		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
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
