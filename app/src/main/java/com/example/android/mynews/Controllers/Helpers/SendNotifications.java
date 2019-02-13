package com.example.android.mynews.Controllers.Helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.example.android.mynews.R;

public class SendNotifications extends Service {

    final int NOTIFICATION_ID = 5; // as project number
    final String NOTIFICATION_TAG = "MyNews"; // name of the app

    @Override
    public void onCreate() {
        super.onCreate();
        this.sendVisualNotification(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendVisualNotification(Context context) {
        // Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(context, NotificationsArticles.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Channel (Android 8)
        String channelId = "5";

        // Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.notificationsbell)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.notificationtext))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        // Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = context.getString(R.string.notificationtext);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }

        // Show notification
        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }
}