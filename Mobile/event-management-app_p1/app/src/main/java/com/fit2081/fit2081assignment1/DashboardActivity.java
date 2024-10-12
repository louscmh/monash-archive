package com.fit2081.fit2081assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void onEventCategoryButtonClick(View view) {
        Intent intent = new Intent(this, EventCategoryActivity.class);
        startActivity(intent);
    }

    public void onEventButtonClick(View view) {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
}