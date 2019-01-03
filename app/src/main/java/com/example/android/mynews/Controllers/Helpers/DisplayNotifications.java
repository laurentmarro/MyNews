package com.example.android.mynews.Controllers.Helpers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import com.example.android.mynews.Controllers.Activities.MainActivity;
import com.example.android.mynews.Controllers.Activities.NotificationsActivity;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.AlarmReceiver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DisplayNotifications extends AppCompatActivity {

    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    Boolean queryInput, switchPosition;
    String urlToShow, today, query, filter,
            forAll = "search/v2/articlesearch.json?api-key=ff58457c72574ee094c10a7b22f5ebc7&q=";
    private StringBuilder filterQuery = new StringBuilder();
    private final List<String> categoriesList = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            categoriesList.add(String.valueOf(categories));
        }
        this.configureUrl();
        // set alarm
        this.configureAlarmManager();
        this.goBackToMain();
    }

    @SuppressLint("SimpleDateFormat")
    private void configureUrl() {

        queryInput = preferences.getBoolean("QUERY_INPUT", true);
        switchPosition = preferences.getBoolean("SWITCH", true);
        query = preferences.getString("SENTENCE", null);

        if (!switchPosition) {
            showAlertDialogAndReturn();
        }

        if (!queryInput) {
            showAlertDialogAndReturn();
        }

        if (query == null) {
            showAlertDialogAndReturn();
        }
        query = query.toLowerCase();

        // Today
        Date date = new Date();
        today = new SimpleDateFormat("yyyyMMdd").format(date);

        // filterQuery
        for (int j = 0; j < 6 ; j++) {
            if (preferences.getBoolean(categoriesList.get(j),false)) {
                filterQuery.append(filterQuery).append("\"").append(categoriesList.get(j)
                        .substring(9).toLowerCase()).append("\" ");
            }
        }

        filter = filterQuery.toString().substring(0,filterQuery.toString().length()-1);
        urlToShow = forAll + query + "&fq=news_desk:("+filter+")&begin_date="+today;
        editor.putString("URLTOSHOW", urlToShow);
        editor.apply();
    }

    // Configuring the AlarmManager
    private void configureAlarmManager(){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Set the alarm to start at 1:00 p.m. (7 a.m in NY)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 54);
        calendar.set(Calendar.SECOND, 0);
        // Set interval
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void showAlertDialogAndReturn(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
        builder.setMessage(getString(R.string.errornotification));
        builder.setCancelable(false);
        builder.setNeutralButton(
                getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(DisplayNotifications.this, NotificationsActivity.class);
                        startActivity(intent);
                    }
                });

        android.app.AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow();

        // Setting Dialog View
        Window window = alert.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        alert.show();
    }

    private void goBackToMain(){
        // go back to mainActivity
        Intent intent = new Intent(DisplayNotifications.this, MainActivity.class);
        startActivity(intent);
    }
}
