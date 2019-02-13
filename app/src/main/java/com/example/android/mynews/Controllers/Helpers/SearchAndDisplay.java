package com.example.android.mynews.Controllers.Helpers;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.mynews.Controllers.Fragments.SearchFragment;
import com.example.android.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchAndDisplay extends AppCompatActivity {

    SearchFragment searchFragment;
    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    String urlToShow, beginDate,endDate, query, filter, origin = "Search",
            forAll = "search/v2/articlesearch.json?api-key=HNly5h3iNYcprPPIOGMINGxwHTJQUutA&q=";
    private final List<String> searchCategoriesList = new ArrayList<>();
    Date newDate, newDate2 = null;
    private StringBuilder filterQuery = new StringBuilder();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_display);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            searchCategoriesList.add("Search"+String.valueOf(categories));
        }

        this.configureUrl();
        editor.putString("URLTOSEARCH", urlToShow);
        editor.putString("ORIGIN", origin);
        editor.apply();
        this.configureAndShowSearchFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    @SuppressLint("SimpleDateFormat")
    private void configureUrl() {
        query = preferences.getString("SENTENCE", null);
        beginDate = preferences.getString("BEGINDATE", null);
        endDate = preferences.getString("ENDDATE", null);

        // Convert Dates to formatted dates (same names)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            newDate = simpleDateFormat.parse(beginDate);
            newDate2 = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat= new SimpleDateFormat("yyyyMMdd");
        beginDate = simpleDateFormat.format(newDate);
        endDate = simpleDateFormat.format(newDate2);

        // query
        assert query != null;
        query = query.toLowerCase();

        // filterQuery
        for (int j = 0; j < 6 ; j++) {
            if (preferences.getBoolean(searchCategoriesList.get(j),false)) {
                filterQuery.append(filterQuery).append("\"").append(searchCategoriesList.get(j).substring(15).toLowerCase()).append("\" ");
            }
        }

        filter = filterQuery.toString().substring(0,filterQuery.toString().length()-1);
        urlToShow = forAll + query + "&fq=news_desk:("+filter+")&begin_date="+beginDate+"&end_date="+endDate;
        editor.putString("URLTOSEARCH", urlToShow);
        editor.apply();
    }

    // CONFIGURATION

    private void configureAndShowSearchFragment(){
        searchFragment = (SearchFragment) getSupportFragmentManager().
                findFragmentById(R.id.activity_search_and_display_frame_layout);

        if (searchFragment == null) {
            searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_and_display_frame_layout, searchFragment)
                    .commit();
        }
    }
}