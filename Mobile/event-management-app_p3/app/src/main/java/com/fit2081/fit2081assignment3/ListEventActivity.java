package com.fit2081.fit2081assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ListEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_event_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.eventToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Events page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.event_container, new FragmentListEvent()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}