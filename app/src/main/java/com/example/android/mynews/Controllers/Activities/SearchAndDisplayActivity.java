package com.example.android.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.example.android.mynews.Controllers.Fragments.SearchFragment;
import com.example.android.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchAndDisplayActivity extends AppCompatActivity {

    SearchFragment searchFragment;
    private enum Categories {
        CHECKBOX_ARTS, CHECKBOX_BUSINESS, CHECKBOX_ENTREPRENEURS,
        CHECKBOX_POLITICS, CHECKBOX_SPORTS, CHECKBOX_TRAVEL
    }

    String urlToShow, beginDate,endDate, query, filterQuery="",
            forAll = "search/v2/articlesearch.json?api-key=ff58457c72574ee094c10a7b22f5ebc7&q=";
    private final List<String> searchCategoriesList = new ArrayList<>();
    Date newDate, newDate2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_display);

        // Create categoriesList from enum
        for (Categories categories : Categories.values()) {
            searchCategoriesList.add("Search"+String.valueOf(categories));
        }

        this.configureUrl();
        this.configureAndShowSearchFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    @SuppressLint("SimpleDateFormat")
    private void configureUrl() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

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
        filterQuery="";
        for (int j = 0; j < 6 ; j++) {
            if (preferences.getBoolean(searchCategoriesList.get(j),false)) {
                filterQuery = filterQuery + "\"" + (searchCategoriesList.get(j).substring(15).toLowerCase()) + "\" ";
            }
        }

        filterQuery = filterQuery.substring(0,filterQuery.length()-1);
        urlToShow = forAll + query + "&fq=news_desk:("+filterQuery+")&begin_date="+beginDate+"&end_date="+endDate;
        editor.putString("URLTOSEARCH", urlToShow);
        editor.apply();
    }

    private void configureAndShowSearchFragment(){

        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_and_display_frame_layout);

        if (searchFragment == null) {
            searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_and_display_frame_layout, searchFragment)
                    .commit();
        }
    }
}