package com.example.android.mynews.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import com.example.android.mynews.Adapter.PageAdapter;
import com.example.android.mynews.R;
import com.example.android.mynews.Views.About;
import com.example.android.mynews.Views.Help;
import com.example.android.mynews.Views.Notifications;
import com.example.android.mynews.Views.Search;

public class MainActivity extends AppCompatActivity {

    // For Design
    private Toolbar toolbar;

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

    private void configureViewPagerAndTabs(){
        // Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        // Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()) {
        });
        // Get TabLayout from layout
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.activity_main_params:
                alertDialogActivity();
                return true;
            case R.id.activity_main_search:
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ---------------------
    // DIALOG
    // ---------------------

    public void alertDialogActivity(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(PARAMETERS, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Switch
                switch (which){
                    case 0 :
                        Intent intent1 = new Intent(getApplicationContext(), Notifications.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(), Help.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getApplicationContext(), About.class);
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
        window.setGravity(Gravity.END | Gravity.TOP);
        dialog.show();

        // Convert the dps to pixels, based on density scale
        final float scale = getResources().getDisplayMetrics().density;
        int width = (int) (200 * scale + 0.5f);
        int height = width; // because it's a square

        window.setLayout(width,height);
    }
}