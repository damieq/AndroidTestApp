// This class is used on all Androids below Honeycomb
package com.development.damian.testapp.statusbarnotification;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.development.damian.testapp.R;


public class StatusNotificationIntent {

    public static NotificationCompat.Builder buildTripNotification(Context context, CharSequence contentTitle, CharSequence contentText, String[] detailText, int messageID) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(contentTitle);

        if (!TextUtils.isEmpty(contentText)){
            mBuilder.setContentText(contentText);
        }

        if (detailText.length > 0) {
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();

            inboxStyle.setBigContentTitle(contentTitle);

            String tempText = "";
            for (int i = 0; i < detailText.length; i++) {

                if (TextUtils.isEmpty(tempText)){
                    tempText = detailText[i];
                }else{
                    tempText = tempText + "\n" + detailText[i];
                }
            }

            inboxStyle.addLine(tempText);

            //mBuilder.setStyle(inboxStyle);
            mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(tempText));
        }

        return mBuilder;
    }

   /* public static RemoteViews buildCustomTripNotification( Context context, CharSequence contentTitle, CharSequence contentText, String[] detailText) {

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_push_trip);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
        contentView.setTextViewText(R.id.title, contentTitle);

        if (!TextUtils.isEmpty(contentText)){
            contentView.setTextViewText(R.id.text, contentText);
        }

        if (detailText.length > 0) {

            String tempText = "";
            for (int i = 0; i < detailText.length; i++) {

                tempText = tempText + "\n" + detailText[i];
            }

            contentView.setTextViewText(R.id.text, tempText);
        }

        return contentView;
    }*/

}