package com.example.ruhin.helploopapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.File;

/**
 * Created by jatin
 * this class responds to the timed notification and displays the notification at that time
 * version 3
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context)
                .setContentTitle("DO HOMEWORK")
                .setContentText("YOU HAVE INCOMPLETE ASSIGNMENTS")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true).setWhen(when)
                .setSound(Uri.parse("android.resource://"
                        + context.getPackageName() + "/" + R.raw.clock_alarm3))
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(100, mNotifyBuilder.build());

    }
}
