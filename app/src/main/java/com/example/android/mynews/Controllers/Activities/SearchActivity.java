package com.example.android.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;
import com.example.android.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    private final List<CheckBox> checkBoxList = new ArrayList<>();
    private final List<String> searchCategoriesList = new ArrayList<>();
    private String day1, day2, month1, month2, beginDate, endDate;
    private DatePickerDialog.OnDateSetListener dateSetListener, dateSetListener2;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureToolbar();

        // Widgets initialization
        final EditText date1 = findViewById(R.id.datepickerbegin);
        final EditText date2 = findViewById(R.id.datepickerend);
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

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            searchCategoriesList.add("Search"+String.valueOf(categories));
        }

        // Listeners

        // First Edit Text
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        R.style.DialogTheme,
                        dateSetListener,
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                // To have two numbers for day and numbers
                if (day < 10) {
                    day1 = "0";
                } else {
                    day1="";
                }

                if (month < 10) {
                    month1 = "0";
                }

                String date = day1 + day + "/" + month1 + month + "/" + year;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("BEGINDATE", date);
                editor.apply();
                date1.setText(date);
                SimpleDateFormat sourceFormat = new SimpleDateFormat("dd/mm/yyyy");
                SimpleDateFormat DesiredFormat = new SimpleDateFormat("yyyymmdd");

                try {
                    beginDate = DesiredFormat.format(sourceFormat.parse(date));
                } catch (ParseException e) {
                    beginDate = null;
                }
            }
        };

        // Second Edit Text
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        R.style.DialogTheme,
                        dateSetListener2,
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                // To have two numbers for day and numbers
                if (day < 10) {
                    day2 = "0";
                } else {
                    day2="";
                }

                if (month < 10) {
                    month2 = "0";
                }

                String date = day2 + day + "/" + month2 + month + "/" + year;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ENDDATE", date);
                editor.apply();
                date2.setText(date);
                SimpleDateFormat sourceFormat = new SimpleDateFormat("dd/mm/yyyy");
                SimpleDateFormat DesiredFormat = new SimpleDateFormat("yyyymmdd");
                try {
                    endDate = DesiredFormat.format(sourceFormat.parse(date));
                } catch (ParseException e) {
                    endDate = null;
                }
            }
        };

        // Third Edit Text
        queryInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = preferences.edit();
                if (s.toString().length() > 1) {
                    editor.putBoolean("QUERYSEARCHPOSITION", true);
                    editor.putString("QUERYSEARCH", String.valueOf(s));
                    editor.apply();
                } else {
                    editor.putBoolean("QUERYSEARCHPOSITION", false);
                    editor.apply();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Checkboxes
        for (int i = 0; i < 6; i++) {
            final String CHECKBOXSEARCHNAME = searchCategoriesList.get(i);
            checkBoxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    SharedPreferences.Editor editor = preferences.edit();
                    if (isChecked) {
                        editor.putBoolean(CHECKBOXSEARCHNAME, true);
                        editor.apply();
                    } else {
                        editor.putBoolean(CHECKBOXSEARCHNAME, false);
                        editor.apply();
                    }
                }
            });
        }

        // COMPARE DATES
        if (endDate.compareTo(beginDate) < 0) {
            Toast.makeText(getApplicationContext(), R.string.endatemustbeafterorsame,
                    2 * Toast.LENGTH_LONG).show();
            editor.putBoolean("COMPARE", false);
            editor.apply();
        } else {
            editor.putBoolean("COMPARE", true);
            editor.apply();
        }

        // Search Button
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Information : ","Search !!!");
            }
        });
        // this.updateWidgetsDisplay();
        // this.conditionsForSearch();
    }

    private void updateWidgetsDisplay() {
        // QUERY_INPUT
        Boolean querysearchposition = preferences.getBoolean("QUERYSEARCHPOSITION", false);
        String querysearch = preferences.getString("QUERYSEARCH", "");
        if (querysearchposition) {
            EditText editText = findViewById(R.id.activity_query_input);
            editText.setText(querysearch);
        }

        // BEGIN_DATE
        beginDate = preferences.getString("BEGINDATE", "" + R.string.date);
        final EditText date1 = findViewById(R.id.datepickerbegin);
        date1.setText(beginDate);

        // END_DATE
        endDate = preferences.getString("ENDDATE", "" + R.string.date);
        final EditText date2 = findViewById(R.id.datepickerend);
        date2.setText(endDate);

        // CHECKBOXES
        for (int j = 0; j < 6; j++) {

            Boolean checkboxes = preferences.getBoolean(searchCategoriesList.get(j), false);

            if (checkboxes) {
                checkBoxList.get(j).setChecked(true);
            } else {
                checkBoxList.get(j).setChecked(false);
            }
        }
    }

    private void conditionsForSearch() {
        Boolean querysearchposition = preferences.getBoolean("QUERYSEARCHPOSITION", false);
        Boolean compare = preferences.getBoolean("COMPARE", false);

        if ((querysearchposition) && (compare)) {
            int k = 0;

            while (!preferences.getBoolean(searchCategoriesList.get(k), false)) {
                k++;
                if (k < 6) {
                    this.showAlertDialogButtonClicked();
                }
                else {
                    Log.i("Information : ", getString(R.string.information_no_search));
                }
            }
            this.showAlertDialogButtonClicked();
        }
        else {
            Log.i("Information : ", getString(R.string.information_no_search));
            this.showAlertDialogButtonClicked();
        }
    }

    private void executeSearch() {
        Log.i("Information : ", "Search");
        // Launch new activity to search and display results
        Intent intent = new Intent(SearchActivity.this, SearchAndDisplayActivty.class);
        startActivity(intent);
    }

    private void updateNotifications() {

    }

    public void showAlertDialogButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);
        builder.setMessage(R.string.doyouwanttobenotified);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.notificationsupdated,
                        2*Toast.LENGTH_LONG).show();
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