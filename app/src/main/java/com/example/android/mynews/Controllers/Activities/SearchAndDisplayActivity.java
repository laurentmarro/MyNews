package com.example.android.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.android.mynews.Controllers.Fragments.SearchFragment;
import com.example.android.mynews.R;

public class SearchAndDisplayActivity extends AppCompatActivity {

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_display);
        this.configureAndShowSearchFragment();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

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