package com.example.android.mynews.Controllers.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.android.mynews.R;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    private final List<CheckBox> checkBoxList = new ArrayList<>();
    private final List<String> categoriesList = new ArrayList<>();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        this.configureToolbar();

        // Widgets initialization
        Switch notifications_switch = findViewById(R.id.notifications_switch);
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

        // - EditText
        queryInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = preferences.edit();
                if (s.toString().length() > 1) {
                    editor.putBoolean("QUERY_INPUT", true);
                    editor.putString("SENTENCE", String.valueOf(s));
                    editor.apply();
                } else {
                    editor.putBoolean("QUERY_INPUT", false);
                    editor.apply();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // - Switch
        notifications_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) {
                    editor.putBoolean("SWITCH", true);
                    editor.apply();
                } else {
                    editor.putBoolean("SWITCH", false);
                    editor.apply();
                }
            }
        });

        // - Checkboxes

        for (int i = 0; i < 6; i++) {
            final String CHECKBOX_NAME = categoriesList.get(i);

            checkBoxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    SharedPreferences.Editor editor = preferences.edit();
                    if (isChecked) {
                        editor.putBoolean(CHECKBOX_NAME, true);
                        editor.apply();
                    } else {
                        editor.putBoolean(CHECKBOX_NAME, false);
                        editor.apply();
                    }
                }
            });
        }
        this.updateWidgetDisplay();
        this.conditionsForNotifications();
    }

    private void updateWidgetDisplay() {

        // QUERY_INPUT
        Boolean query = preferences.getBoolean("QUERY_INPUT", false);
        String sentence = preferences.getString("SENTENCE", "");
        if (query) {
            EditText editText = findViewById(R.id.activity_query_input);
            editText.setText(sentence);
        }

        // SWITCH_BUTTON
        Boolean switch_button = preferences.getBoolean("SWITCH", false);
        Switch notification_switch = findViewById(R.id.notifications_switch);
        if (switch_button) {
            notification_switch.setChecked(true);
        } else {
            notification_switch.setChecked(false);
        }

        // CHECKBOXES
        for (int j = 0; j < 6; j++) {

            Boolean checkboxes = preferences.getBoolean(categoriesList.get(j), false);

            if (checkboxes) {
                checkBoxList.get(j).setChecked(true);
            } else {
                checkBoxList.get(j).setChecked(false);
            }
        }
    }

    private void conditionsForNotifications() {
        Boolean query = preferences.getBoolean("QUERY_INPUT", false);
        Boolean switch_button = preferences.getBoolean("SWITCH", false);

        if ((query) && (switch_button)) {
            int k = 0;

            while (!preferences.getBoolean(categoriesList.get(k), false)) {
                k++;
                if (k < 6) {
                    this.showAlertDialogButtonClicked();
                }
                else {
                    Log.i("Information : ", getString(R.string.information_no_notification));
                }
            }

            this.showAlertDialogButtonClicked();
        }
        else {
            Log.i("Information : ", getString(R.string.information_no_notification));
            this.showAlertDialogButtonClicked();
        }
    }

    public void showAlertDialogButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setMessage(R.string.do_you_want_to_update);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("SWITCH", false);
                Toast.makeText(getApplicationContext(), R.string.notifications_off,
                        2*Toast.LENGTH_LONG).show();
                editor.apply();
                updateWidgetDisplay();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent displayNotificationsActivity = new Intent(NotificationsActivity.this,
                        DisplayNotificationsActivity.class);
                startActivity(displayNotificationsActivity);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button button = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        //Set negative button text color
        button.setTextColor(Color.MAGENTA);
        Button button1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        //Set positive button text color
        button1.setTextColor(Color.MAGENTA);

        alert.getWindow();

        // Setting Dialog View
        Window window = alert.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        alert.show();

        // Convert the dps to pixels, based on density scale
        final float scale = getResources().getDisplayMetrics().density;
        int width = (int) (200 * scale + 0.5f);
        int height = (int) (200 * scale + 0.5f); // because it's a square

        window.setLayout(width, height);
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
}