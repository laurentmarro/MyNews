package com.example.android.mynews.Controllers.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.example.android.mynews.Controllers.Fragments.SearchFragment;
import com.example.android.mynews.R;

public class ShowNotificationsActivity extends AppCompatActivity {

    SearchFragment searchFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notifications);
        this.defineOrigin();
        this.configureAndShowNotifications();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowNotifications(){

        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_display_notifications_frame_layout);

        if (searchFragment == null) {
            searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_display_notifications_frame_layout, searchFragment)
                    .commit();
        }
    }

    private void defineOrigin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ORIGINE", String.valueOf(R.string.notifications));
        editor.apply();
    }
}
