package com.example.android.mynews.Utils;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.android.mynews.Controllers.Helpers.NotificationResearch;
import com.example.android.mynews.R;

public class AlarmReceiver extends BroadcastReceiver {
    SharedPreferences preferences;

    @SuppressLint("CommitPrefEdits")

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        // Define origine
        preferences.edit().putString("ORIGINE", String.valueOf(R.string.notifications)).apply();

        NotificationManager notificationManager = (NotificationManager) context.
                getSystemService(Context.NOTIFICATION_SERVICE);

        Toast.makeText(context, "Alarm Receiver", Toast.LENGTH_LONG).show();


        // Intent
        Intent searchIntent = new Intent(context, NotificationResearch.class);
        context.startActivity(searchIntent);
    }
}