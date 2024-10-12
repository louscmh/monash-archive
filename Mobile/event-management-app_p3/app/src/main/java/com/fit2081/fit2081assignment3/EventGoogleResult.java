package com.fit2081.fit2081assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventGoogleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        WebView webView = findViewById(R.id.webView);
        String eventName = getIntent().getExtras().getString("eventName");
        String wikiPageURL = "https://www.google.com/search?q=" + eventName;
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(wikiPageURL);
    }
}