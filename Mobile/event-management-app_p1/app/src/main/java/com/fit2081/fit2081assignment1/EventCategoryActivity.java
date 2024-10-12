package com.fit2081.fit2081assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class EventCategoryActivity extends AppCompatActivity {

    TextView editCategoryId;
    TextView editCategoryName;
    TextView editEventCount;
    Switch activeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_category);

        editCategoryId = findViewById(R.id.editCategoryId);
        editCategoryName = findViewById(R.id.editCategoryName);
        editEventCount = findViewById(R.id.editEventCount);
        activeSwitch = findViewById(R.id.activeSwitch);

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

        if (categoryName.length() > 0) {
            Random r = new Random();
            char charOne = (char)(r.nextInt(26) + 'A');
            char charTwo = (char)(r.nextInt(26) + 'A');
            int randomInt = r.nextInt(1001);
            String categoryId = String.format("C%1$s%2$s-%3$04d",charOne,charTwo,randomInt);
            editCategoryId.setText(categoryId);
            saveDataToSharedPreference(categoryId,categoryName,eventCount,isActive);
            Toast.makeText(this, String.format("Category saved successfully: %s",categoryId), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saving failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataToSharedPreference(String categoryId, String categoryName, int eventCount, boolean isActive){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KEY_CATEGORY_ID", categoryId);
        editor.putString("KEY_CATEGORY_NAME", categoryName);
        editor.putInt("KEY_EVENT_COUNT", eventCount);
        editor.putBoolean("KEY_IS_ACTIVE", isActive);

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