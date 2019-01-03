package com.example.android.mynews.Controllers.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import com.example.android.mynews.Adapter.FragmentAdapter;
import com.example.android.mynews.R;

public class MainActivity extends AppCompatActivity {

    // For AlertDialog
    String [] PARAMETERS = {"Notifications", "Help", "About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure all views
        this.configureToolBar();
        this.configureViewPagerAndTabs();
    }

    // building options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    // Actions to do on click in options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.activity_main_params:
                alertDialogActivity();
                return true;
            case R.id.activity_main_search:
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Method to manage action to do on clicking in params button
    public void alertDialogActivity(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(PARAMETERS, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Switch
                switch (which){
                    case 0 :
                        Intent intent1 = new Intent(getApplicationContext(), NotificationsActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(), HelpActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent3);
                        break;
                    default:
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow();

        // Setting Dialog View
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.END | Gravity.TOP);
        dialog.show();

        // Convert the dps to pixels, based on density scale
        final float scale = getResources().getDisplayMetrics().density;
        int width = (int) (200 * scale + 0.5f);
        int height = (int) (200 * scale + 0.5f); // because it's a square

        window.setLayout(width,height);
    }

    // CONFIGURATION

    // Configure Toolbar
    private void configureToolBar(){
        // For Design
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureViewPagerAndTabs(){
        // For Design
        TabLayout tabLayout;
        ViewPager viewPager;
        FragmentAdapter fragmentAdapter;

        // Get TabLayout from layout
        tabLayout = findViewById(R.id.activity_main_tabs);
        // Get ViewPager from layout
        viewPager = findViewById(R.id.activity_main_viewpager);
        //Set Adapter and glue it together
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        // Design purpose. Tabs have the same width
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // Glue TabLayout and ViewPager together
        tabLayout.setupWithViewPager(viewPager);
    }
}