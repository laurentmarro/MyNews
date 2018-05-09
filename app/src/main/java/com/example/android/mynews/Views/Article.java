package com.example.android.mynews.Views;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.android.mynews.Models.AsyncHTTP;
import com.example.android.mynews.R;

public class Article extends AppCompatActivity {

    String url_simple = "https://openclassrooms.com/";
    String url_darskynet = "https://api.darksky.net/forecast/b97f3149cc32995fcdb0479454555dd6/37.8267,-122.4233";
    String url_nytimes = "https://api.nytimes.com/svc/topstories/v2/home.json?.param({ff58457c72574ee094c10a7b22f5ebc7});";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_webview);
        //1 - Configuring Toolbar
        // this.configureToolbar();
        this.configureWebView();
    }

    private void configureToolbar() {
        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void configureWebView() {
        // Get ViewPager from layout
        WebView webView = (WebView) findViewById(R.id.webview);
        AsyncHTTP task = new AsyncHTTP(Article.this);
        task.execute(url_darskynet);
        }
}
