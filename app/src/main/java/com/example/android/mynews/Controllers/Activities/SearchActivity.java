package com.example.android.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.mynews.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    private Boolean search = false;
    private List<CheckBox> checkBoxList = new ArrayList<>();
    private List<String> searchCategoriesList = new ArrayList<>();
    private final List<String> categoriesList = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener beginDateSetListener, endDateSetListener;
    private String query="", day2, month1, endDate ="01/11/2018", day1, beginDate="01/11/2018", today;
    private int year, month, day, numberOfTrue=0;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        this.configureToolbar();

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            searchCategoriesList.add("Search"+String.valueOf(categories));
            categoriesList.add(String.valueOf(categories));
        }

        // Checkboxes
        for (int i = 0; i < 6; i++) {
            final String CHECKBOXSEARCHNAME = searchCategoriesList.get(i);
             editor.putBoolean(CHECKBOXSEARCHNAME, false);
        }

        editor.apply();

        // Widgets initialization

        final TextView date1 = findViewById(R.id.datepickerbegin);
        final TextView date2 = findViewById(R.id.datepickerend);
        final Button search_button = findViewById(R.id.searchButton);
        final EditText queryInput = findViewById(R.id.activity_query_input);
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

        // Today
        Date date = new Date();
        today = new SimpleDateFormat("dd/MM/yyyy").format(date);

        // Listeners

        // First DateListener in EditText

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SearchActivity.this,
                        beginDateSetListener,
                        year,month,day);

                datePickerDialog.getWindow();
                datePickerDialog.show();
            }
        });

        beginDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1;
                if (month<10) {
                    month1 = "0";
                } else month1 ="";

                if (dayOfMonth<10) {
                    day1 = "0";
                } else day1 ="";

                beginDate = day1+dayOfMonth+"/"+month1+month+"/"+year;

                if (beginDate.compareTo(today)>0) {
                    Log.i("Information ", getString(R.string.present));
                    beginDate = today;
                }
                date1.setText(beginDate);
            }
        };

        // Second DateListener in EditText

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SearchActivity.this,
                        endDateSetListener,
                        year,month,day);

                datePickerDialog.getWindow();
                datePickerDialog.show();
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker2, int year2, int month2, int dayOfMonth2) {
                month2 = month2 +1;

                if (month2<10) {
                    month1 = "0";
                } else month1 ="";

                if (dayOfMonth2<10) {
                    day2 = "0";
                } else day2 ="";

                endDate = day2+dayOfMonth2+"/"+month1+month2+"/"+year2;

                if (endDate.compareTo(today)>0) {
                    Log.i("Information ", getString(R.string.present));
                    endDate = today;
                }

                date2.setText(endDate);
            }
        };

        // Third Edit Text : Sentence to search

        queryInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                query = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Checkboxes
        for (int i = 0; i < 6; i++) {

            final int finalI = i;
            checkBoxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        editor.putBoolean(searchCategoriesList.get(finalI), true);
                        numberOfTrue +=1;
                        editor.apply();

                    } else {
                        editor.putBoolean(searchCategoriesList.get(finalI), false);
                        numberOfTrue -=1;
                        editor.apply();
                    }
                }
            });
        }

        // Search Button
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionsForSearch();
                if (search) {
                    upDate();
                }
            }
        });
    }

    private void conditionsForSearch() {
        if (query.length()>1) {
            if (numberOfTrue>0) {
                if (endDate.compareTo(beginDate)>=0) {
                    search = true;
                }
            }
        }
        if (!search) {
            Log.i("Information", getString(R.string.informationnosearch));
        }
    }

    // Asking To Update
    private void upDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setMessage(R.string.doyouwanttobenotified);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.notificationsupdated,
                        Toast.LENGTH_LONG).show();
                updateNotifications();
                executeSearch();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                executeSearch();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        //Set negative button text color
        negativeButton.setTextColor(Color.MAGENTA);
        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        //Set positive button text color
        positiveButton.setTextColor(Color.MAGENTA);

        alert.getWindow();

        // Setting Dialog View
        Window window = alert.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        alert.show();

        // Convert the dps to pixels, based on density scale
        final float scale = getResources().getDisplayMetrics().density;
        int width = (int) (200 * scale + 0.5f);
        int height = (int) (200 * scale + 0.5f);

        window.setLayout(width, height);
    }

    private void executeSearch() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        // define origin
        preferences.edit().putString("ORIGINE", getString(R.string.search)).apply();

        // Launch new activity to search and display results
        editor.putString("SENTENCE", query);
        editor.putString("BEGINDATE", beginDate);
        editor.putString("ENDDATE",endDate);
        editor.apply();
        Intent intent = new Intent(SearchActivity.this, SearchAndDisplayActivity.class);
        startActivity(intent);
    }

    private void updateNotifications() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        // Checkboxes
        for (int j = 0; j < 6; j++) {
            editor.putBoolean(categoriesList.get(j),preferences.getBoolean(searchCategoriesList.get(j), false));
        }
        editor.putBoolean("QUERY_INPUT", true);
        editor.putBoolean("SWITCH", true);
        editor.putString("SENTENCE", query);
        editor.apply();
    }

    // ToolBar
    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}