package com.example.android.mynews.Controllers.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import com.example.android.mynews.R;
import java.util.Hashtable;


public class NotificationsActivity extends AppCompatActivity {

    private EditText queryInput;
    private CheckBox arts,business,entrepreneurs,politics,sports,travel;
    private Switch switch1;
    private final int NOTIFICATION_ID = 5; // as project number
    private final String NOTIFICATION_TAG = "MyNews"; // name of the app
    private int position = 0;
    private int term = 0;
    private Hashtable checkboxes = new Hashtable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // 1 - Configuring
        this.configureToolbar();
        this.configureNotifications();
        this.configureAlarmManager();
        // this.sendVisualNotification();

        // 2- Widgets init
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        CheckBox arts = (CheckBox) findViewById(R.id.arts_CheckBox);
        CheckBox business = (CheckBox) findViewById(R.id.business_CheckBox);
        CheckBox entrepreneurs = (CheckBox) findViewById(R.id.entrepreneurs_CheckBox);
        CheckBox politics = (CheckBox) findViewById(R.id.politics_CheckBox);
        CheckBox sports = (CheckBox) findViewById(R.id.sports_CheckBox);
        CheckBox travel = (CheckBox) findViewById(R.id.travel_CheckBox);
        EditText queryInput = (EditText) findViewById(R.id.activity_query_input);

        // 3- Listeners

        // Switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    position = 1;
                }
                else {position = 0;}
            }
        });

        // Checkbox arts
        arts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxes.put(0,1);

                    // Save this item for search
                }
                else {checkboxes.put(0,0);}
            }
        });

        // Checkbox business
        business.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxes.put(1,1);

                    // Save this item for search
                }
                else {checkboxes.put(1,0);}
            }
        });

        // Checkbox entrepreneurs
        entrepreneurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxes.put(2,1);

                    // Save this item for search
                }
                else {checkboxes.put(2,0);}
            }
        });


    }

    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNotifications(){


    }

    private void configureAlarmManager(){

    }

    private void sendVisualNotification(String messageBody) {

        // 1 - Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // 2 - Create a Style for the Notification
        // NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        // inboxStyle.setBigContentTitle(getString(R.string.notification_title));
        // inboxStyle.addLine(messageBody);

        // 3 - Create a Channel (Android 8)
        String channelId = "5";

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_import_contacts_white_24dp)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.notification_title))
                        .setAutoCancel(true)
                        // .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);
        // .setStyle(inboxStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "New Article";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // 7 - Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }
}