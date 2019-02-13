package com.example.android.mynews.Controllers.Helpers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.android.mynews.Controllers.Fragments.SearchFragment;
import com.example.android.mynews.R;

public class NotificationsArticles extends AppCompatActivity {

    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_articles);
        this.configureAndShowSearchFragment();
    }

    protected void configureAndShowSearchFragment(){
        searchFragment = (SearchFragment) getSupportFragmentManager().
                findFragmentById(R.id.activity_notifications_frame_layout);

        if (searchFragment == null) {
            searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_notifications_frame_layout, searchFragment)
                    .commit();
        }
    }
}