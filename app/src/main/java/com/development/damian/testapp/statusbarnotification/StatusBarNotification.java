
package com.development.damian.testapp.statusbarnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.development.damian.testapp.ApplicationState;
import com.development.damian.testapp.Activities.MainActivity;


public class StatusBarNotification {

  private NotificationManager mNotificationManager;

    public void showTestNotification(final Context context, final CharSequence contentTitle, final CharSequence contentText, String[] detailText, final int messageID, final ApplicationState appState) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = StatusNotificationIntent.buildTripNotification(context, contentTitle, contentText, detailText, messageID);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        Intent notificationIntent = new Intent(context, MainActivity.class);
                        notificationIntent.putExtra("MessageID", messageID);
                        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        PendingIntent contentIntent = PendingIntent.getActivity(context, messageID, notificationIntent, 0);

                        mBuilder.setContentIntent(contentIntent);
                        mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
                        mBuilder.setPriority(Notification.PRIORITY_MAX);

                        Notification noti = mBuilder.build();

                        noti.defaults |= Notification.DEFAULT_VIBRATE;
                        noti.defaults |= Notification.DEFAULT_SOUND;
                        noti.flags|= Notification.FLAG_AUTO_CANCEL;

                        //noti.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
                        mNotificationManager.notify(messageID, noti);

                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 30; incr+=1) {

                            if (!appState.isNotificatioVisible()){
                                incr = 30;
                            }

                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(30, incr, false);

                            noti = mBuilder.build();
                            noti.flags|= Notification.FLAG_AUTO_CANCEL;
                            // Displays the progress bar for the first time.
                            mNotificationManager.notify(messageID, noti);
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 1 seconds
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        // When the loop is finished, updates the notification

                        // Removes the progress bar
                        mBuilder.setProgress(0,0,false);

                        mBuilder.setStyle(null);

                        if (!appState.isNotificatioVisible())
                        {
                            mNotificationManager.cancel(messageID);
                        }else{
                            mBuilder.setContentText("Fuera de Tiempo");

                            mBuilder.setContentIntent(null);
                            noti = mBuilder.build();

                            //noti.setLatestEventInfo(context, contentTitle, "Fuera de Tiempo", null);

                            mNotificationManager.notify(messageID, noti);
                        }

                    }
                }
        // Starts the thread by calling the run() method in its Runnable
        ).start();

    }

    public void clearNotification(Context context, int notificationID) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationID);
    }

    public void clearAllNotification(Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }
}
