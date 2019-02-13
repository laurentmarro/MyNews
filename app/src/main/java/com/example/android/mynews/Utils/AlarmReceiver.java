package com.example.android.mynews.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.android.mynews.Controllers.Helpers.NotificationResearch;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, NotificationResearch.class);
        context.startService(myIntent);
    }
}