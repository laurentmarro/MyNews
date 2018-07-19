package com.example.android.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import com.example.android.mynews.R;
import java.util.Calendar;

public class NotificationsActivity extends AppCompatActivity {

    private static final String SWITCH = "SWITCH";
    private static final String QUERY_INPUT = "QUERY_INPUT";
    private static final String CHECKBOX_ARTS = "CHECKBOX_ARTS";
    private static final String CHECKBOX_BUSINESS = "CHECKBOX_BUSINESS";
    private static final String CHECKBOX_ENTREPRENEURS = "CHECKBOX_ENTREPRENEURS";
    private static final String CHECKBOX_POLITICS = "CHECKBOX_POLITICS";
    private static final String CHECKBOX_SPORTS = "CHECKBOX_SPORTS";
    private static final String CHECKBOX_TRAVEL = "CHECKBOX_TRAVEL";
    private Switch switch1;
    private EditText queryInput;
    private CheckBox arts,business,entrepreneurs,politics,sports,travel;
    private final int NOTIFICATION_ID = 5; // as project number
    private final String NOTIFICATION_TAG = "MyNews"; // name of the app
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // 1 - Configuring
        this.configureToolbar();
        this.conditionsForNotifications();
        // this.sendVisualNotification();

        // 2- Widgets init
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        EditText queryInput = (EditText) findViewById(R.id.activity_query_input);
        CheckBox arts = (CheckBox) findViewById(R.id.arts_CheckBox);
        CheckBox business = (CheckBox) findViewById(R.id.business_CheckBox);
        CheckBox entrepreneurs = (CheckBox) findViewById(R.id.entrepreneurs_CheckBox);
        CheckBox politics = (CheckBox) findViewById(R.id.politics_CheckBox);
        CheckBox sports = (CheckBox) findViewById(R.id.sports_CheckBox);
        CheckBox travel = (CheckBox) findViewById(R.id.travel_CheckBox);

        // 3- Put the switch to off
        switch1.setChecked(false);

        // 4- Listeners

        // A- EditText
        queryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("Information : ","You have to ask something for your query");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0) {sharedPreferences.edit().putBoolean("QUERY_INPUT", true).apply();}
                else {sharedPreferences.edit().putBoolean("QUERY_INPUT", false).apply();}
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // B- Switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("SWITCH", true).apply();}
                else {sharedPreferences.edit().putBoolean("SWITCH", false).apply();}
            }
        });

        // C- Checkboxes
        arts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("CHECKBOX_ARTS", true).apply();}
                else {sharedPreferences.edit().putBoolean("CHECKBOX_ARTS", false).apply();}
            }
        });

        business.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("CHECKBOX_BUSINESS", true).apply();}
                else {sharedPreferences.edit().putBoolean("CHECKBOX_BUSINESS", false).apply();}
            }
        });

        entrepreneurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("CHECKBOX_ENTREPRENEURS", true).apply();}
                else {sharedPreferences.edit().putBoolean("CHECKBOX_ENTREPRENEURS", false).apply();}
            }
        });

        politics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("CHECKBOX_POLITICS", true).apply();}
                else {sharedPreferences.edit().putBoolean("CHECKBOX_POLITICS", false).apply();}
            }
        });

        sports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("CHECKBOX_SPORTS", true).apply();}
                else {sharedPreferences.edit().putBoolean("CHECKBOX_SPORTS", false).apply();}
            }
        });

        travel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {sharedPreferences.edit().putBoolean("CHECKBOX_TRAVEL", true).apply();}
                else {sharedPreferences.edit().putBoolean("CHECKBOX_TRAVEL", false).apply();}
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

    private void conditionsForNotifications(){
        String query_input = sharedPreferences.getString(QUERY_INPUT, null);
        String switch1 = sharedPreferences.getString(SWITCH,null);
        String arts = sharedPreferences.getString(CHECKBOX_ARTS,null);
        String business = sharedPreferences.getString(CHECKBOX_BUSINESS,null);
        String entrepreneurs = sharedPreferences.getString(CHECKBOX_ENTREPRENEURS,null);
        String politics = sharedPreferences.getString(CHECKBOX_POLITICS,null);
        String sports = sharedPreferences.getString(CHECKBOX_SPORTS,null);
        String travel = sharedPreferences.getString(CHECKBOX_TRAVEL,null);

        if ((query_input.equals(true)) && (switch1.equals(true)) && ((arts.equals(true)) || (business.equals(true)) || (entrepreneurs.equals(true)) ||
                    (politics.equals(true)) || (sports.equals(true)) || (travel.equals(true)))) {
            configureAlarmManager();
        }
        else { Log.i("Information : ","You need to ask something, to click at least" +
                " a box and after you will be able to switch on the notifications");}
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
        setBootReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            configureNotifications();
            Intent intent1 = new Intent(context,NotificationsActivity.class);
            context.startActivity(intent1);
        }
    }

    private void configureNotifications(){
        // Methods
        // sendVisualNotification();
    }

    private void sendVisualNotification(String messageBody) {

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