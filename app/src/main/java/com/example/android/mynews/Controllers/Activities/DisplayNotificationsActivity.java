package com.example.android.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import com.example.android.mynews.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DisplayNotificationsActivity extends AppCompatActivity {
    static DisplayNotificationsActivity instance;

    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    final int NOTIFICATION_ID = 5; // as project number
    final String NOTIFICATION_TAG = "MyNews"; // name of the app
    Boolean queryInput, switchPosition;
    String urlToShow, today, query, filterQuery="",
            forAll = "search/v2/articlesearch.json?api-key=ff58457c72574ee094c10a7b22f5ebc7&q=";
    private final List<String> categoriesList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notifications);
        instance = this;

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            categoriesList.add(String.valueOf(categories));
        }
        this.configureUrl();
        // set alarm
        this.configureAlarmManager();
        this.goBackToMain();
    }

    public static DisplayNotificationsActivity getInstance() {
        return instance;
    }

    @SuppressLint("SimpleDateFormat")
    private void configureUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        queryInput = preferences.getBoolean("QUERY_INPUT", true);
        switchPosition = preferences.getBoolean("SWITCH", true);
        query = preferences.getString("SENTENCE", null);

        if (!switchPosition) {
            ShowAlertDialogAndReturn();
        }

        if (!queryInput) {
            ShowAlertDialogAndReturn();
        }

        if (query == null) {
            ShowAlertDialogAndReturn();
        }

        query = query.toLowerCase();

        // Today
        Date date = new Date();
        today = new SimpleDateFormat("yyyyMMdd").format(date);

        // filterQuery
        filterQuery="";
        for (int j = 0; j < 6 ; j++) {
            if (preferences.getBoolean(categoriesList.get(j),false)) {
                filterQuery = filterQuery + "\"" + (categoriesList.get(j).substring(9).toLowerCase()) +"\" ";
            }
        }

        filterQuery = filterQuery.substring(0,filterQuery.length()-1);
        urlToShow = forAll + query + "&fq=news_desk:("+filterQuery+")&begin_date="+today;
        editor.putString("URLTOSHOW", urlToShow);
        editor.apply();
    }

    private void configureAlarmManager(){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DisplayNotificationsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        // Set the alarm to start at 12:00 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12); // 6:00 a.m in NY
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        // Set interval
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager
                .INTERVAL_DAY,pendingIntent);
    }

    public void sendVisualNotification() {

        // Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(this, ShowNotificationsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Channel (Android 8)
        String channelId = "5";

        // Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.baseline_notification_important_white_18dp)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.notification_title))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        // Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = getString(R.string.new_articles);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }

        // Show notification
        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }

    private void ShowAlertDialogAndReturn(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(getString(R.string.errornotification));
        builder.setCancelable(false);
        builder.setNeutralButton(
                getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(DisplayNotificationsActivity.this, NotificationsActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        Button neutralButton = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        //Set negative button text color
        neutralButton.setTextColor(Color.MAGENTA);
        alert.getWindow();

        // Setting Dialog View
        Window window = alert.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        alert.show();
    }

    private void goBackToMain(){
        // go back to mainActivity
        Intent intent = new Intent(DisplayNotificationsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}