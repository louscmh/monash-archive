package com.fit2081.fit2081assignment3;

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

import java.util.Random;

public class EventActivity extends AppCompatActivity {

    TextView editEventId;
    TextView editEventName;
    TextView editEventCategoryId;
    TextView editTicketsAvailable;
    Switch eventActiveSwitch;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);;
        sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        editEventId = findViewById(R.id.editEventId);
        editEventName = findViewById(R.id.editEventName);
        editEventCategoryId = findViewById(R.id.editEventCategoryId);
        editTicketsAvailable = findViewById(R.id.editTicketsAvailable);
        eventActiveSwitch = findViewById(R.id.eventActiveSwitch);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_EVENT), RECEIVER_EXPORTED);
    }

    public void onSaveEventButtonClick(View view) {

        String eventName = editEventName.getText().toString();
        String categoryId = editEventCategoryId.getText().toString();
        // default value if theres no value in ticketsAvailable will be 0
        int ticketsAvailable = editTicketsAvailable.getText().toString().equals("") ? 0 : Integer.parseInt(editTicketsAvailable.getText().toString());
        boolean isActive = eventActiveSwitch.isChecked();

        if (eventName.length() > 0) {
            Random r = new Random();
            char charOne = (char)(r.nextInt(26) + 'A');
            char charTwo = (char)(r.nextInt(26) + 'A');
            int randomInt = r.nextInt(10001);
            String eventId = String.format("E%1$s%2$s-%3$05d",charOne,charTwo,randomInt);
            editEventId.setText(eventId);
            saveDataToSharedPreference(eventName,categoryId,eventId,ticketsAvailable,isActive);
            Toast.makeText(this, String.format("Event saved: %1$s to %2$s", eventId, categoryId), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saving failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataToSharedPreference(String eventName, String categoryId, String eventId, int ticketsAvailable, boolean isActive){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KEY_EVENT_NAME", eventName);
        editor.putString("KEY_EVENT_ID", eventId);
        editor.putString("KEY_CATEGORY_ID", categoryId);
        editor.putInt("KEY_TICKETS_AVAILABLE", ticketsAvailable);
        editor.putBoolean("KEY_EVENT_IS_ACTIVE", isActive);

        editor.apply();
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            assert msg != null;
            String[] messageList = msg.split(";",-1);

            if (messageList.length == 4 && (messageList[2].matches("[0-9]+") || messageList[2].equals(""))
                    && (messageList[3].matches("\\b(TRUE|FALSE)\\b") || messageList[3].equals(""))) {
                editEventName.setText(messageList[0].substring(6));
                editEventCategoryId.setText(messageList[1]);
                editTicketsAvailable.setText(String.valueOf(messageList[2]));
                eventActiveSwitch.setChecked(messageList[3].equals("TRUE"));
            } else {
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_SHORT).show();
            }

        }
    }
}