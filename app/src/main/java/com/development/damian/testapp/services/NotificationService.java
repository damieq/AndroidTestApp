package com.development.damian.testapp.services;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;

import com.development.damian.testapp.MainActivity;
import com.development.damian.testapp.R;
import com.development.damian.testapp.utils.Constants;

/**
 * Created by damia on 16/04/2017.
 */

public class NotificationService extends Service {

    private String TAG = this.getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            if (intent.getAction().equals(Constants.STARTFOREGROUND_ACTION)) {

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("NotificationService");

                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1, notificationIntent, 0);

                mBuilder.setContentIntent(contentIntent);

                Notification notification = mBuilder.build();

                notification.flags |= Notification.FLAG_ONGOING_EVENT;

                startForeground(1, notification);

                createDialog();

            } else if (intent.getAction().equals((Constants.STOPFOREGROUND_ACTION))) {
                stopForeground(false);
                stopSelf();
            }
        }

        return START_STICKY;
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Dialog in Lock Screen")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object
        AlertDialog alert = builder.create();

        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.getWindow().setType(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        alert.getWindow().setType(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        alert.getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        alert.getWindow().setType(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
