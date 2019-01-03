package com.example.android.mynews.Controllers.Helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import com.example.android.mynews.Controllers.Activities.ShowNotifications;
import com.example.android.mynews.R;

public class SendNotifications extends AppCompatActivity {

    final int NOTIFICATION_ID = 5; // as project number
    final String NOTIFICATION_TAG = "MyNews"; // name of the app

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sendVisualNotification(getApplicationContext());
    }
    public void sendVisualNotification(Context context) {

        // Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(context, ShowNotifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Channel (Android 8)
        String channelId = "5";

        // Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.baseline_notification_important_white_18dp)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.notification_text))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        // Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = context.getString(R.string.new_articles);
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