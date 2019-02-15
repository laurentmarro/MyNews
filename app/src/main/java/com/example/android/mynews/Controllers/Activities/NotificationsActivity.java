package com.example.android.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.Toast;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.AlarmReceiver;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }
    private final List<CheckBox> checkBoxList = new ArrayList<>();
    private final List<String> categoriesList = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String sentence, urlToShow, filter,
            forAll = "search/v2/articlesearch.json?api-key=HNly5h3iNYcprPPIOGMINGxwHTJQUutA&q=";
    Boolean query, switchButton, ready, checkboxes;
    int numberOfCheckBox =0;
    StringBuilder filterQuery;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        this.configureToolbar();

        // Widgets initialization
        Switch notificationsSwitch = findViewById(R.id.notifications_switch);
        EditText queryInput = findViewById(R.id.activity_query_input);
        CheckBox checkBox_Arts = findViewById(R.id.arts_CheckBox);
        CheckBox checkBox_Business = findViewById(R.id.business_CheckBox);
        CheckBox checkBox_Entrepreneurs = findViewById(R.id.entrepreneurs_CheckBox);
        CheckBox checkBox_Politics = findViewById(R.id.politics_CheckBox);
        CheckBox checkBox_Sports = findViewById(R.id.sports_CheckBox);
        CheckBox checkBox_Travel = findViewById(R.id.travel_CheckBox);

        // checkBoxList
        checkBoxList.add(checkBox_Arts);
        checkBoxList.add(checkBox_Business);
        checkBoxList.add(checkBox_Entrepreneurs);
        checkBoxList.add(checkBox_Politics);
        checkBoxList.add(checkBox_Sports);
        checkBoxList.add(checkBox_Travel);

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            categoriesList.add(String.valueOf(categories));
        }

        this.updateWidgetDisplay();
        this.conditionsForNotifications();

        // - EditText
        queryInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() > 1) {
                    editor.putBoolean("QUERY_INPUT", true);
                    editor.putString("SENTENCE", String.valueOf(s));
                    editor.apply();
                    conditionsForNotifications();
                } else {
                    editor.putBoolean("QUERY_INPUT", false);
                    editor.putString("SENTENCE", "");
                    editor.apply();
                    conditionsForNotifications();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // - Switch
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("SWITCH", true);
                    editor.apply();
                    conditionsForNotifications();
                } else {
                    editor.putBoolean("SWITCH", false);
                    editor.apply();
                    conditionsForNotifications();
                }
            }
        });

        // - Checkboxes
        for (int i = 0; i < 6; i++) {
            final String CheckBoxName = categoriesList.get(i);
            checkBoxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        editor.putBoolean(CheckBoxName, true);
                        editor.apply();
                        conditionsForNotifications();
                    } else {
                        editor.putBoolean(CheckBoxName, false);
                        editor.apply();
                        conditionsForNotifications();
                    }
                }
            });
        }
    }

    private void updateWidgetDisplay() {

        // QUERY_INPUT
        query = preferences.getBoolean("QUERY_INPUT", false);
        sentence = preferences.getString("SENTENCE", "");
        if (query) {
            EditText editText = findViewById(R.id.activity_query_input);
            editText.setText(sentence);
        }

        // SWITCH_BUTTON
        switchButton = preferences.getBoolean("SWITCH", false);
        Switch notificationSwitch = findViewById(R.id.notifications_switch);
        if (switchButton) {
            notificationSwitch.setChecked(true);
        } else {
            notificationSwitch.setChecked(false);
        }

        // CHECKBOXES
        for (int j = 0; j < 6; j++) {

            checkboxes = preferences.getBoolean(categoriesList.get(j), false);
            if (checkboxes) {
                checkBoxList.get(j).setChecked(true);
            } else {
                checkBoxList.get(j).setChecked(false);
            }
        }
    }

    private void conditionsForNotifications() {
        query = preferences.getBoolean("QUERY_INPUT", false);
        switchButton = preferences.getBoolean("SWITCH", false);
        ready = false;
        numberOfCheckBox = 0;

        if ((query) && (switchButton)) {
            for (int k = 0; k < 6 ; k++) {
                if(preferences.getBoolean(categoriesList.get(k), false)) {
                    numberOfCheckBox +=1;
                }
            }
            if (numberOfCheckBox > 0) {
                ready = true;
            }
        }

        if (ready) {
            Toast.makeText(getApplicationContext(), R.string.notificationareok,
                    Toast.LENGTH_LONG).show();
            this.configureUrl();

        } else {
            Toast.makeText(getApplicationContext(), R.string.errornotification,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureUrl() {
        filterQuery = new StringBuilder();
        query = preferences.getBoolean("QUERY_INPUT", true);
        switchButton = preferences.getBoolean("SWITCH", true);
        sentence = preferences.getString("SENTENCE", null);
        assert sentence != null;
        sentence = sentence.toLowerCase();

        // filterQuery
        for (int j = 0; j < 6 ; j++) {
            if (preferences.getBoolean(categoriesList.get(j),false)) {
                filterQuery.append(filterQuery).append("\"").append(categoriesList.get(j)
                        .substring(9).toLowerCase()).append("\" ");
            }
        }

        filter = filterQuery.toString().substring(0,filterQuery.toString().length()-1);
        urlToShow = forAll + sentence + "&fq=news_desk:(";
        editor.putString("URLTOSHOW", urlToShow);
        editor.putString("FILTER", filter);
        editor.apply();
        this.configureAlarmManager();
    }

    private void configureAlarmManager(){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to start at 6:00 a.m.
        // (0 a.m in NY : To see all the news of the day before)

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}