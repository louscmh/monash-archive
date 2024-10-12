package com.fit2081.fit2081assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fit2081.fit2081assignment3.provider.EntityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    TextView lastGesture;
    Switch eventActiveSwitch;
    Gson gson = new Gson();
    Type typeEvent;
    Type typeCategory;
    private EntityViewModel entityViewModel;
    private String cachedCurrentId;
    private String cachedCategoryId;
    private boolean passEventSave = false;
    private GestureDetectorCompat mDetector;
    FloatingActionButton fab;
    Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        entityViewModel = new ViewModelProvider(this).get(EntityViewModel.class);

        // Event edittext
        editEventId = findViewById(R.id.edit_event_id);
        editEventName = findViewById(R.id.edit_event_name);
        editEventCategoryId = findViewById(R.id.edit_category_id);
        editTicketsAvailable = findViewById(R.id.edit_tickets);
        eventActiveSwitch = findViewById(R.id.edit_event_active);

        lastGesture = findViewById(R.id.lastGesture);

        // SP
        sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // TOOLBAR STUFF
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Assignment 3");

        // ACTION BAR DRAWER
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        // FLOATING ACTION BUTTON
        fab = findViewById(R.id.fab);

        // TP
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mDetector = new GestureDetectorCompat(this, customGestureDetector);
        mDetector.setOnDoubleTapListener(customGestureDetector);

        View touchpad = findViewById(R.id.dashboardTouchpad);
        touchpad.setOnTouchListener((v, event) -> {
            mDetector.onTouchEvent(event);
            return true;
        });

        fab.setOnClickListener(view -> {
            onSaveEventButtonClick();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (passEventSave) {
                        entityViewModel.incrementEventCount(cachedCategoryId);
                        loadCategoryFragment();
                        Snackbar.make(view, "Event saved successfully", Snackbar.LENGTH_SHORT)
                                .setAction("UNDO", new MyUndoListener(cachedCurrentId)).show();
                    }
                }
            }, 500);
        });

        // Loading fragment
        loadCategoryFragment();

        // Typing for GSON
        typeEvent = new TypeToken<ArrayList<Event>>() {}.getType();
        typeCategory = new TypeToken<ArrayList<String>>() {}.getType();
    }

    public void onSaveEventButtonClick() {
        String eventName = editEventName.getText().toString();
        String categoryId = editEventCategoryId.getText().toString();
        // default value if theres no value in ticketsAvailable will be 0
        int ticketsAvailable = editTicketsAvailable.getText().toString().equals("") ? 0 : Integer.parseInt(editTicketsAvailable.getText().toString());
        boolean isActive = eventActiveSwitch.isChecked();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Category> categoryList = entityViewModel.getCategoryById(categoryId);
            if (eventName.length() > 0 & !categoryList.isEmpty() & eventName.matches("^(?!^[\\d\\s]+$)[a-zA-Z\\d\\s]+$")) {
                Event newEvent = new Event(categoryId,eventName,ticketsAvailable,isActive);
                markSuccess(newEvent,true);
            } else if (!categoryList.isEmpty()) {
                markSuccess(null,false);
                toastFailure(1);
            } else {
                markSuccess(null,false);
                toastFailure(2);
            }
        });
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
            entityViewModel.deleteAllCategories();
            loadCategoryFragment();
            Toast.makeText(this, "Removed all categories!", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.delete_all_events_menu_id) {
            entityViewModel.deleteAllEvents();
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
            String eventId = getRemoveCategory();
            entityViewModel.deleteEvent(eventId);
        }
    }

    public void loadCategoryFragment() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.category_container, new FragmentListCategory()).commit();
    }

    private void markSuccess(Event event, boolean pass) {
        cachedCategoryId = event == null ? null : event.getCategoryId();
        cachedCurrentId = event == null ? null : event.getEventId();
        passEventSave = pass;
        if (pass) {
            editEventId.setText(event.getEventId());
            entityViewModel.insert(event);
        }
    }

    private void toastFailure(int outcome) {
        uiHandler.post(() -> {
            if (outcome == 1) {
                Toast.makeText(this, "Saving failed: Category does not exist!", Toast.LENGTH_SHORT).show();
            } else if (outcome == 2) {
                Toast.makeText(this, "Saving failed: Invalid event name!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            lastGesture.setText("onLongPress");
            editEventId.setText(null);
            editEventName.setText(null);
            editEventCategoryId.setText(null);
            editTicketsAvailable.setText(null);
            eventActiveSwitch.setChecked(false);
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            lastGesture.setText("onDoubleTap");
            fab.performClick();
            return super.onDoubleTap(e);
        }
    }
}