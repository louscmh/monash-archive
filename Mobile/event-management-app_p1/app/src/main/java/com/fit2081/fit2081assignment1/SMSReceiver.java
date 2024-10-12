package com.fit2081.fit2081assignment1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.lifecycle.DefaultLifecycleObserver;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_CATEGORY = "SMS_CATEGORY";
    public static final String SMS_EVENT = "SMS_EVENT";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i=0; i < messages.length ; i++) {
            SmsMessage currentMessage = messages[i];
            String message = currentMessage.getDisplayMessageBody();
            Intent msgIntent = new Intent();

            if (message.startsWith("category:")) {
                msgIntent.setAction(SMS_CATEGORY);
                msgIntent.putExtra(SMS_MSG_KEY, message);
            } else if (message.startsWith("event:")) {
                msgIntent.setAction(SMS_EVENT);
                msgIntent.putExtra(SMS_MSG_KEY, message);
            }
            context.sendBroadcast(msgIntent);
        }
    }
}