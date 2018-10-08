package com.example.android.mynews.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.mynews.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_webview);
        this.feedTheWebView(); // Call to load the url given by intent
    }


    private void feedTheWebView() {
        WebView webView;
        String url = getIntent().getStringExtra("URL");
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}