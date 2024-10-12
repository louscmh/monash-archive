package com.fit2081.fit2081assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fit2081.fit2081assignment3.provider.EntityViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class EventCategoryActivity extends AppCompatActivity {

    Gson gson = new Gson();
    TextView editCategoryId;
    TextView editCategoryName;
    TextView editEventCount;
    Switch activeSwitch;
    TextView editEventLocation;
    private EntityViewModel entityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_category);

        entityViewModel = new ViewModelProvider(this).get(EntityViewModel.class);

        editCategoryId = findViewById(R.id.editCategoryId);
        editCategoryName = findViewById(R.id.editCategoryName);
        editEventCount = findViewById(R.id.editEventCount);
        activeSwitch = findViewById(R.id.activeSwitch);
        editEventLocation = findViewById(R.id.editEventLocation);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_CATEGORY), RECEIVER_EXPORTED);
    }

    public void onSaveCategoryButtonClick(View view) {

        String categoryName = editCategoryName.getText().toString();
        // default value if theres no value in eventCount will be 0
        int eventCount = editEventCount.getText().toString().equals("") ? 0 : Integer.parseInt(editEventCount.getText().toString());
        boolean isActive = activeSwitch.isChecked();
        String eventLocation = editEventLocation.getText().toString().equals("") ? null : editEventLocation.getText().toString();

        if (categoryName.length() > 0 & categoryName.matches("^(?!^[\\d\\s]+$)[a-zA-Z\\d\\s]+$")) {
            Category newCategory = new Category(categoryName,eventCount,isActive,eventLocation);
            editCategoryId.setText(newCategory.getCategoryId());
            entityViewModel.insert(newCategory);
//            saveDataToSharedPreference(newCategory);
            Toast.makeText(this, String.format("Category saved successfully: %s",newCategory.getCategoryId()), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Saving failed: Invalid Category Name!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataToSharedPreference(Category category){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<String> categoryIdArray;
        ArrayList<Category> categoryArray;
        String categoryKey = sharedPreferences.getString("category_array", "");
        String categoryArrayKey = sharedPreferences.getString("category_id_array", "");

        if (!categoryKey.equals("")) {
            Type type = new TypeToken<ArrayList<Category>>() {}.getType();
            categoryArray = gson.fromJson(categoryKey,type);
        } else {
            categoryArray = new ArrayList<>();
        }

        if (!categoryArrayKey.equals("")) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            categoryIdArray = gson.fromJson(categoryArrayKey,type);
        } else {
            categoryIdArray = new ArrayList<>();
        }

        categoryArray.add(category);
        categoryIdArray.add(category.getCategoryId());
        editor.putString("category_array", gson.toJson(categoryArray));
        editor.putString("category_id_array", gson.toJson(categoryIdArray));
        editor.apply();
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            assert msg != null;
            String[] messageList = msg.split(";",-1);

            if (messageList.length == 3 && (messageList[1].matches("[0-9]+") || Objects.equals(messageList[1], ""))
                    && (messageList[2].matches("\\b(TRUE|FALSE)\\b") || Objects.equals(messageList[2], ""))) {
                editCategoryName.setText(messageList[0].substring(9));
                editEventCount.setText(String.valueOf(messageList[1]));
                activeSwitch.setChecked(messageList[2].equals("TRUE"));
            } else {
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }

        }
    }
}