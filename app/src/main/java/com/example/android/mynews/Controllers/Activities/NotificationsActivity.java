package com.example.android.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.android.mynews.R;
import java.util.Calendar;

public class NotificationsActivity extends AppCompatActivity {

    private static CheckBox checkBox_Arts, checkBox_Business, checkBox_Entrepreneurs, checkBox_Politics, checkBox_Sports, checkBox_Travel;

    public enum NotificationsCheckboxes_and_names {
        ARTS (checkBox_Arts, "CHECKBOX_ARTS"),
        BUSINESS (checkBox_Business, "CHECKBOX_BUSINESS"),
        ENTREPRENEURS (checkBox_Entrepreneurs, "CHECKBOX_ENTREPRENEURS"),
        POLITICS (checkBox_Politics, "CHECKBOX_POLITICS"),
        SPORTS (checkBox_Sports,"CHECKBOX_SPORTS"),
        TRAVEL (checkBox_Travel,"CHECKBOX_TRAVEL");

        private CheckBox checkBoxes_names;
        private String chechBoxex_values;

        // Constructor
        NotificationsCheckboxes_and_names(CheckBox checkBox, String string) {
        }
    }

    private final int NOTIFICATION_ID = 5; // as project number
    private final String NOTIFICATION_TAG = "MyNews"; // name of the app
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_notifications);
        this.configureToolbar();

        // Widgets initialization
        Switch notifications_Switch = (Switch) findViewById(R.id.notifications_Switch);
        EditText query_Input = (EditText) findViewById(R.id.query_Input);
        CheckBox checkBox_Arts = (CheckBox) findViewById(R.id.checkBox_Arts);
        CheckBox checkBox_Business = (CheckBox) findViewById(R.id.checkBox_Business);
        CheckBox checkbox_Entrepreneurs = (CheckBox) findViewById(R.id.checkbox_Entrepreneurs);
        CheckBox checkbox_Politics = (CheckBox) findViewById(R.id.checkbox_Politics);
        CheckBox checkBox_Sports = (CheckBox) findViewById(R.id.checkBox_Sports);
        CheckBox checkBox_Travel = (CheckBox) findViewById(R.id.checkBox_Travel);

        // Put the switch to off
        notifications_Switch.setChecked(false);

        // Listeners

        // - EditText
        query_Input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0) {
                    sharedPreferences.edit().putBoolean("QUERY_INPUT", true).apply();
                }
                else {
                    sharedPreferences.edit().putBoolean("QUERY_INPUT", false).apply();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        // - Switch
        notifications_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sharedPreferences.edit().putBoolean("SWITCH", true).apply();
                }
                else {
                    sharedPreferences.edit().putBoolean("SWITCH", false).apply();
                }
            }
        });

        // - Checkboxes

        for (final NotificationsCheckboxes_and_names notificationsCheckboxes_and_names : NotificationsCheckboxes_and_names.values()) {

            notificationsCheckboxes_and_names.checkBoxes_names.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sharedPreferences.edit().putBoolean(notificationsCheckboxes_and_names.chechBoxex_values, true).apply();
                    } else {
                        sharedPreferences.edit().putBoolean(notificationsCheckboxes_and_names.chechBoxex_values, false).apply();
                    }
                }
            });
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        this.conditionsForNotifications();
    }

    private void conditionsForNotifications(){

        // work with enum

        configureAlarmManager();
    }

    private void configureAlarmManager(){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        // Set the alarm to start at 8:00 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        // Set interval
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            configureNotifications();
        }
    }

    private void configureNotifications(){
        // Methods to load articles => si articles nouveaux send sinon rien - Attention si nouvelle query
        sendVisualNotification();
    }

    private void sendVisualNotification() {

        // Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Create a Channel (Android 8)
        String channelId = "5";

        // Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_import_contacts_white_24dp)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.notification_title))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        // Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "New Article";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }
}