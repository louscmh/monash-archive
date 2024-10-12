package com.fit2081.fit2081assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar myToolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView editEventId;
    TextView editEventName;
    TextView editEventCategoryId;
    TextView editTicketsAvailable;
    Switch eventActiveSwitch;
    Gson gson = new Gson();
    Type typeEvent;
    Type typeCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // Event edittext
        editEventId = findViewById(R.id.edit_event_id);
        editEventName = findViewById(R.id.edit_event_name);
        editEventCategoryId = findViewById(R.id.edit_category_id);
        editTicketsAvailable = findViewById(R.id.edit_tickets);
        eventActiveSwitch = findViewById(R.id.edit_event_active);

        // SP
        sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // TOOLBAR STUFF
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assignment 2");

        // ACTION BAR DRAWER
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        // FLOATING ACTION BUTTON
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultText = onSaveEventButtonClick();
                if (resultText != null) {
                    Snackbar.make(view, "Event saved successfully", Snackbar.LENGTH_SHORT)
                            .setAction("UNDO", new MyUndoListener(resultText)).show();
                }
            }
        });

        // Loading fragment
        loadCategoryFragment();

        // Typing for GSON
        typeEvent = new TypeToken<ArrayList<Event>>() {}.getType();
        typeCategory = new TypeToken<ArrayList<String>>() {}.getType();
    }

    public String onSaveEventButtonClick() {

        String eventName = editEventName.getText().toString();
        String categoryId = editEventCategoryId.getText().toString();
        // default value if theres no value in ticketsAvailable will be 0
        int ticketsAvailable = editTicketsAvailable.getText().toString().equals("") ? 0 : Integer.parseInt(editTicketsAvailable.getText().toString());
        boolean isActive = eventActiveSwitch.isChecked();
        String result = null;

        String categoryChecker = sharedPreferences.getString("category_id_array", "[]");
        ArrayList<String> categoryArray = gson.fromJson(categoryChecker,typeCategory);

        if (eventName.length() > 0 & categoryArray.contains(categoryId) & eventName.matches("^(?!^[\\d\\s]+$)[a-zA-Z\\d\\s]+$")) {
            Event newEvent = new Event(categoryId,eventName,ticketsAvailable,isActive);
            editEventId.setText(newEvent.getEventId());
            saveDataToSharedPreference(newEvent);
            incrementCategory(categoryId);
            result = categoryId;
        } else if (!categoryArray.contains(categoryId)) {
            Toast.makeText(this, "Saving failed: Category does not exist!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saving failed: Invalid event name!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private void saveDataToSharedPreference(Event event){
        ArrayList<Event> eventArray;
        String savedValue;
        String eventKey = sharedPreferences.getString("event_array", "[]");

        if (!eventKey.equals("")) {
            eventArray = gson.fromJson(eventKey,typeEvent);
        } else {
            eventArray = new ArrayList<>();
        }
        eventArray.add(event);
        savedValue = gson.toJson(eventArray);
        editor.putString("event_array", savedValue);
        editor.apply();
    }

    public void incrementCategory(String categoryId) {
        ArrayList<Category> categoryArray;
        String categoryKey = sharedPreferences.getString("category_array", "");
        categoryArray = gson.fromJson(categoryKey,new TypeToken<ArrayList<Category>>() {}.getType());
        for (Category category : categoryArray) {
            if (category.getCategoryId().equals(categoryId)) {
                category.setEventCount(category.getEventCount()+1);
            }
        }
        editor.putString("category_array", gson.toJson(categoryArray));
        editor.apply();
        loadCategoryFragment();
    }

    public void decrementCategory(String categoryId) {
        ArrayList<Category> categoryArray;
        String categoryKey = sharedPreferences.getString("category_array", "");
        categoryArray = gson.fromJson(categoryKey,new TypeToken<ArrayList<Category>>() {}.getType());
        for (Category category : categoryArray) {
            if (category.getCategoryId().equals(categoryId)) {
                category.setEventCount(category.getEventCount()-1);
            }
        }
        editor.putString("category_array", gson.toJson(categoryArray));
        editor.apply();
        loadCategoryFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refresh_menu_id) {
            loadCategoryFragment();
        } else if (item.getItemId() == R.id.clear_event_form_menu_id) {
            editEventId.setText(null);
            editEventName.setText(null);
            editEventCategoryId.setText(null);
            editTicketsAvailable.setText(null);
            eventActiveSwitch.setChecked(false);
        } else if (item.getItemId() == R.id.delete_all_categories_menu_id){
            editor.putString("category_array", "[]");
            editor.putString("category_id_array", "[]");
            editor.apply();
            loadCategoryFragment();
            Toast.makeText(this, "Removed all categories!", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.delete_all_events_menu_id) {
            ArrayList<Event> eventArray;
            String eventKey = sharedPreferences.getString("event_array", "[]");
            eventArray = gson.fromJson(eventKey,new TypeToken<ArrayList<Event>>() {}.getType());
            for (Event event : eventArray) {
                decrementCategory(event.getCategoryId());
            }
            editor.putString("event_array", "[]");
            editor.apply();
            Toast.makeText(this, "Removed all events!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }



    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.view_all_categories_menu_id) {
                Intent intent = new Intent(getApplicationContext(), ListCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.add_category_menu_id) {
                Intent intent = new Intent(getApplicationContext(), EventCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.view_all_events_menu_id) {
                Intent intent = new Intent(getApplicationContext(), ListEventActivity.class);
                startActivity(intent);
            } else if (id == R.id.logout_menu_id) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

    class MyUndoListener implements View.OnClickListener {

        public String getRemoveCategory() {
            return removeCategory;
        }

        String removeCategory;
        public MyUndoListener(String resultText) {
            this.removeCategory = resultText;
        }

        @Override
        public void onClick(View v) {

            ArrayList<Event> eventArray;
            String eventKey = sharedPreferences.getString("event_array", "[]");
            eventArray = gson.fromJson(eventKey,new TypeToken<ArrayList<Event>>() {}.getType());
            for (int i = 0;i<eventArray.size();i++) {
                if (eventArray.get(i).getCategoryId().equals(this.getRemoveCategory())) {
                    eventArray.remove(i);
                    break;
                };
            }
            editor.putString("event_array", gson.toJson(eventArray));
            editor.apply();

            decrementCategory(this.removeCategory);
        }
    }

    public void loadCategoryFragment() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.category_container, new FragmentListCategory()).commit();
    }

}