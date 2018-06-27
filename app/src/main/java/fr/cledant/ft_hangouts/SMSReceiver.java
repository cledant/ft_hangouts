package fr.cledant.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	SmsManager sms = SmsManager.getDefault();

	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle = intent.getExtras();
		DAOMessage daoMessage = new DAOMessage(context);
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
					Toast.makeText(context, sender, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
