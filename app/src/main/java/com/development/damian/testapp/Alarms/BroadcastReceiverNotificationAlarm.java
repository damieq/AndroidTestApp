package com.development.damian.testapp.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.development.damian.testapp.services.NotificationService;
import com.development.damian.testapp.utils.Constants;


import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by damia on 20/10/2016.
 */

public class BroadcastReceiverNotificationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, NotificationService.class);
        i.setAction(Constants.STARTFOREGROUND_ACTION);
        startWakefulService(context, i);
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BroadcastReceiverNotificationAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000 , pi);
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, BroadcastReceiverNotificationAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
