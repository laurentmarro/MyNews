package com.example.android.mynews.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.android.mynews.Controllers.Activities.DisplayNotificationsActivity;
import com.example.android.mynews.Controllers.Activities.SearchAndDisplayActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start SearchAndDisplay Activity for result
        Intent searchIntent = new Intent(context, SearchAndDisplayActivity.class);
        // url for the called activity is saves in DisplayNotificationsActivity.
        context.startActivity(searchIntent);

        DisplayNotificationsActivity displayNotificationsActivity = DisplayNotificationsActivity.getInstance();
        displayNotificationsActivity.sendVisualNotification();
    }
}