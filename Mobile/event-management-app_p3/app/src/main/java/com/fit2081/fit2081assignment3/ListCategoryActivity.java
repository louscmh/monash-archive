package com.fit2081.fit2081assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ListCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_category_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.categoryToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("All Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(
                R.id.category_container, new FragmentListCategory()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}