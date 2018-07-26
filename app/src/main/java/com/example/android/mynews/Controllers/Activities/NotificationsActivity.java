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
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import com.example.android.mynews.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private CheckBox checkBox_Arts, checkBox_Business, checkBox_Entrepreneurs, checkBox_Politics, checkBox_Sports, checkBox_Travel;

    private enum Categories {
        checkBox_Arts("CHECKBOX_ARTS") ;

        private String categories ;

        Categories(String categories) {
            this.categories = categories ;
        }

        public String getCategories() {
            return this.categories ;
        }
    }

    private final int NOTIFICATION_ID = 5; // as project number
    private final String NOTIFICATION_TAG = "MyNews"; // name of the app
    private final List<CheckBox> checkBoxList = new ArrayList<>();
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        this.configureToolbar();

        // Widgets initialization
        Switch notifications_switch = (Switch) findViewById(R.id.notifications_switch);
        EditText queryInput = (EditText) findViewById(R.id.activity_query_input);
        CheckBox checkBox_Arts = (CheckBox) findViewById(R.id.arts_CheckBox);
        CheckBox checkBox_Business = (CheckBox) findViewById(R.id.business_CheckBox);
        CheckBox checkBox_Entrepreneurs = (CheckBox) findViewById(R.id.entrepreneurs_CheckBox);
        CheckBox checkBox_Politics = (CheckBox) findViewById(R.id.politics_CheckBox);
        CheckBox checkBox_Sports = (CheckBox) findViewById(R.id.sports_CheckBox);
        CheckBox checkBox_Travel = (CheckBox) findViewById(R.id.travel_CheckBox);

        // checkBoxList
        checkBoxList.add(checkBox_Arts);
        checkBoxList.add(checkBox_Business);
        checkBoxList.add(checkBox_Entrepreneurs);
        checkBoxList.add(checkBox_Politics);
        checkBoxList.add(checkBox_Sports);
        checkBoxList.add(checkBox_Travel);

        // Put the switch to off
        notifications_switch.setChecked(false);

        // Listeners

        // - EditText
        queryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("Information : ",getString(R.string.query_need_something));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0) {
                    SharedPreferences.Editor editor = getSharedPreferences("QUERY_INPUT", MODE_PRIVATE).edit();
                    editor.putBoolean("QUERY_INPUT",true);
                    editor.apply();
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("QUERY_INPUT", MODE_PRIVATE).edit();
                    editor.putBoolean("QUERY_INPUT",false);
                    editor.apply();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // - Switch
        notifications_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("SWITCH", MODE_PRIVATE).edit();
                    editor.putBoolean("SWITCH",true);
                    editor.apply();
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("SWITCH", MODE_PRIVATE).edit();
                    editor.putBoolean("SWITCH",false);
                    editor.apply();
                }
            }
        });

        // - Checkboxes

        for (int i = 0; i < 6 ; i++) {
            checkBoxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        SharedPreferences.Editor editor = getSharedPreferences("CHECKBOX_ARTS", MODE_PRIVATE).edit();
                        editor.putBoolean("CHECKBOX_ARTS",true);
                        editor.apply();
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences("CHECKBOX_ARTS", MODE_PRIVATE).edit();
                        editor.putBoolean("CHECKBOX_ARTS",false);
                        editor.apply();
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.conditionsForNotifications();
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
        String query_input = sharedPreferences.getString("", null);
        String notifications_switch = sharedPreferences.getString("",null);
        String arts = sharedPreferences.getString("",null);
        String business = sharedPreferences.getString("",null);
        String entrepreneurs = sharedPreferences.getString("",null);
        String politics = sharedPreferences.getString("",null);
        String sports = sharedPreferences.getString("",null);
        String travel = sharedPreferences.getString("",null);

        if ((query_input.equals(true)) && (notifications_switch.equals(true))
                && ((arts.equals(true)) || (business.equals(true)) || (entrepreneurs.equals(true)) ||
                    (politics.equals(true)) || (sports.equals(true)) || (travel.equals(true)))) {
            configureAlarmManager();
        }
        else {
            Log.i("Information : ",getString(R.string.information_no_notification));
        }
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
        // Methods to load articles => if new articles send log information
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